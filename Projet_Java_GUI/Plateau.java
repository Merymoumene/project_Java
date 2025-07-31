import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

// Classe représentant le plateau de jeu de dames
public class Plateau extends JPanel implements MouseListener {
    private final int TAILLE = 8;              // Taille du plateau (8x8)
    private final int CASE = 80;               // Taille d'une case en pixels
    private Piece[][] grille = new Piece[TAILLE][TAILLE]; // Grille 2D contenant les pièces
    private boolean joueur1Tour = true;        // Indique si c'est le tour du joueur 1 (true) ou 2 (false)
    private Piece pieceSelectionnee = null;   // Pièce actuellement sélectionnée par le joueur
    private int selX = -1, selY = -1;          // Coordonnées de la pièce sélectionnée
    private FenetreJeu fenetre;                 // Référence vers la fenêtre principale du jeu

    // Constructeur, initialise la fenêtre, la taille du plateau et ajoute un listener de souris
    public Plateau(FenetreJeu fenetre) {
        this.fenetre = fenetre;
        setPreferredSize(new Dimension(CASE * TAILLE, CASE * TAILLE)); // Définit la taille du panel
        addMouseListener(this); // Ecoute les clics de souris
        initPlateau();          // Initialise la grille avec les pions au départ
    }

    // Méthode statique pour jouer un son à partir d'un fichier
    public static void playSound(String soundFile) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("Erreur de son: " + e.getMessage());
        }
    }

    // Initialise la grille de jeu : place les pions aux positions de départ
    private void initPlateau() {
        // Remplit d'abord toute la grille avec null (cases vides)
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                grille[x][y] = null;
            }
        }
        // Place les pions du joueur 2 (en haut, faussement appelés false)
        for (int y = 0; y < 3; y++) {
            for (int x = (y + 1) % 2; x < TAILLE; x += 2) {
                grille[x][y] = new Pion(false);
            }
        }
        // Place les pions du joueur 1 (en bas, true)
        for (int y = 5; y < TAILLE; y++) {
            for (int x = (y + 1) % 2; x < TAILLE; x += 2) {
                grille[x][y] = new Pion(true);
            }
        }
    }

    // Méthode pour dessiner le plateau et les pièces
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine le damier
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                // Couleur claire ou foncée selon la case (damier)
                g.setColor((x + y) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
                g.fillRect(x * CASE, y * CASE, CASE, CASE);

                // Dessine la pièce si elle existe sur la case
                Piece p = grille[x][y];
                if (p != null) {
                    // Noir pour joueur 1, blanc pour joueur 2
                    g.setColor(p.joueur1 ? Color.BLACK : Color.WHITE);
                    // Dessine un cercle représentant la pièce
                    g.fillOval(x * CASE + 10, y * CASE + 10, CASE - 20, CASE - 20);

                    // Si la pièce est une dame, ajoute un "D" jaune dessus
                    if (p.estDame) {
                        g.setColor(Color.YELLOW);
                        g.setFont(new Font("Arial", Font.BOLD, 16));
                        g.drawString("D", x * CASE + CASE / 2 - 4, y * CASE + CASE / 2 + 5);
                    }
                }
            }
        }

        // Surligne la pièce sélectionnée en vert translucide
        if (pieceSelectionnee != null) {
            g.setColor(new Color(0, 255, 0, 100));
            g.fillOval(selX * CASE + 10, selY * CASE + 10, CASE - 20, CASE - 20);
        }

        // Dessine un contour noir autour du plateau
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        g2.drawRect(2, 2, CASE * TAILLE - 4, CASE * TAILLE - 4);
    }

    // Méthode déclenchée au clic de souris : sélection ou déplacement d'une pièce
    public void mousePressed(MouseEvent e) {
        int x = e.getX() / CASE; // Case cliquée (x)
        int y = e.getY() / CASE; // Case cliquée (y)

        if (x >= TAILLE || y >= TAILLE) return; // Ignore si clic hors plateau

        if (pieceSelectionnee == null) {
            // Si aucune pièce sélectionnée, on essaie d'en sélectionner une
            Piece p = grille[x][y];
            if (p != null && p.joueur1 == joueur1Tour) {
                pieceSelectionnee = p; // Sélectionne la pièce
                selX = x; selY = y;   // Sauvegarde ses coordonnées
            }
        } else {
            // Si une pièce est déjà sélectionnée, on essaie de la déplacer
            if (deplacer(selX, selY, x, y)) {
                playSound("move.wav");   // Joue un son de déplacement
                pieceSelectionnee = null; // Désélectionne la pièce
                selX = selY = -1;
                joueur1Tour = !joueur1Tour; // Change de joueur (tour à l'autre)
            } else {
                // Déplacement impossible : annule la sélection
                pieceSelectionnee = null;
                selX = selY = -1;
            }
        }
        repaint();     // Redessine le plateau avec les modifications
        verifierFin(); // Vérifie si la partie est terminée
    }

    // Tente de déplacer une pièce de (fromX, fromY) vers (toX, toY)
    private boolean deplacer(int fromX, int fromY, int toX, int toY) {
        Piece p = grille[fromX][fromY];
        if (p == null || grille[toX][toY] != null) return false; // Déplacement impossible si case cible occupée

        int dx = toX - fromX; // Différence en x
        int dy = toY - fromY; // Différence en y
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);

        boolean estCapture = absDx == 2 && absDy == 2; // S'agit-il d'une capture ?
        int midX = fromX + dx / 2; // Coordonnées de la pièce intermédiaire (à capturer)
        int midY = fromY + dy / 2;

        if (absDx == 1 && absDy == 1 && !estCapture) {
            // Déplacement simple (1 case en diagonale)
            // Vérifie le sens du déplacement (avant pour pions normaux, dame peut aller dans les deux sens)
            if ((p.joueur1 && dy == -1) || (!p.joueur1 && dy == 1) || p.estDame) {
                grille[toX][toY] = p;         // Déplace la pièce
                grille[fromX][fromY] = null; // Vide l'ancienne case
                verifierPromotion(toX, toY); // Vérifie si la pièce devient une dame
                return true;
            }
        } else if (estCapture) {
            // Capture : saute par-dessus une pièce adverse
            Piece cible = grille[midX][midY];
            if (cible != null && cible.joueur1 != p.joueur1) {
                grille[toX][toY] = p;           // Déplace la pièce
                grille[fromX][fromY] = null;   // Vide case départ
                grille[midX][midY] = null;     // Enlève la pièce capturée
                fenetre.scorePanel.ajouterPoint(p.joueur1); // Ajoute un point au joueur qui capture
                verifierPromotion(toX, toY);   // Vérifie promotion en dame
                pieceSelectionnee = p;          // Maintient la pièce sélectionnée (capture multiple possible)
                selX = toX;
                selY = toY;
                if (peutCapturer(toX, toY)) {
                    return false; // S'il y a encore une capture possible, ne change pas de tour
                }
                return true; // Sinon, fin du déplacement
            }
        }
        return false; // Déplacement invalide
    }

    // Vérifie si la pièce doit être promue en dame (atteint la ligne opposée)
    private void verifierPromotion(int x, int y) {
        Piece p = grille[x][y];
        if (p != null && !p.estDame) {
            if ((p.joueur1 && y == 0) || (!p.joueur1 && y == TAILLE - 1)) {
                grille[x][y] = new Dame(p.joueur1); // Remplace le pion par une dame
            }
        }
    }

    // Vérifie si la pièce en (x,y) peut encore capturer une pièce adverse
    private boolean peutCapturer(int x, int y) {
        Piece p = grille[x][y];
        if (p == null) return false;

        // Directions possibles pour une capture (2 cases en diagonale)
        int[][] directions = {{-2, -2}, {2, -2}, {-2, 2}, {2, 2}};
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            int mx = x + dir[0] / 2;
            int my = y + dir[1] / 2;
            // Vérifie que la case cible est dans le plateau
            if (nx >= 0 && ny >= 0 && nx < TAILLE && ny < TAILLE) {
                Piece entre = grille[mx][my];
                // Vérifie que la case intermédiaire contient une pièce adverse et que la case d'arrivée est vide
                if (entre != null && entre.joueur1 != p.joueur1 && grille[nx][ny] == null) {
                    return true; // Capture possible
                }
            }
        }
        return false; // Pas de capture possible
    }

    // Vérifie si la partie est finie (plus de pièces pour un joueur)
    private void verifierFin() {
        boolean joueur1Restant = false;
        boolean joueur2Restant = false;

        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                Piece p = grille[x][y];
                if (p != null) {
                    if (p.joueur1) joueur1Restant = true;
                    else joueur2Restant = true;
                }
            }
        }

        // Si un joueur n'a plus de pièce, annonce la fin de la partie
        if (!joueur1Restant) {
            fenetre.finDePartie("Blanc");
        } else if (!joueur2Restant) {
            fenetre.finDePartie("Noir");
        }
    }

    // Méthodes vides pour MouseListener (nécessaires mais non utilisées ici)
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
