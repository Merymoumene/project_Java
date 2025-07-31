import javax.swing.*;
import java.awt.*;

// Fenêtre principale du jeu de dames, héritant de JFrame
public class FenetreJeu extends JFrame {
    public Plateau plateau;         // Plateau de jeu (l'échiquier)
    public ScorePanel scorePanel;   // Panneau affichant le score ou info du joueur

    // Constructeur de la fenêtre de jeu
    public FenetreJeu() {
        setTitle("Jeu de Dames");                        // Titre de la fenêtre
        setSize(1000, 700);                              // Taille initiale de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application à la fermeture de la fenêtre
        setExtendedState(JFrame.MAXIMIZED_BOTH);        // Ouvre en plein écran
        setLayout(new BorderLayout());                   // Gestionnaire de layout en bordures (haut, bas, centre, est, ouest)
        setLocationRelativeTo(null);                      // Centre la fenêtre à l'écran

        plateau = new Plateau(this);      // Crée et initialise le plateau (passé en paramètre pour interaction)
        scorePanel = new ScorePanel();    // Crée le panneau score (à droite)

        // Panneau central pour contenir le plateau, avec un GridBagLayout pour centrage facile
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 217, 181)); // Couleur de fond beige clair
        centerPanel.add(plateau);                            // Ajoute le plateau au centre

        add(scorePanel, BorderLayout.EAST);    // Ajoute le panneau score à droite
        add(centerPanel, BorderLayout.CENTER); // Ajoute le plateau centré

        setVisible(true); // Affiche la fenêtre
    }

    // Méthode appelée à la fin d'une partie pour afficher le gagnant
    public void finDePartie(String gagnant) {
        Plateau.playSound("victoire.wav"); // Joue un son de victoire (méthode statique dans Plateau)

        // Création d'une boîte de dialogue modale bloquante
        JDialog dialog = new JDialog(this, "Fin de la partie", true);
        dialog.setSize(400, 200);               // Taille du dialogue
        dialog.setLocationRelativeTo(this);     // Centre par rapport à la fenêtre principale

        Color backgroundColor = new Color(240, 217, 181); // Couleur de fond beige clair

        // Panneau principal du dialogue avec un BorderLayout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(backgroundColor);
        dialog.setContentPane(contentPanel); // Définit le panneau comme contenu du dialogue

        // Label affichant le message "X a gagné !" centré
        JLabel message = new JLabel(gagnant + " a gagné !", SwingConstants.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 20));    // Police en gras taille 20
        message.setForeground(new Color(60, 63, 65));         // Couleur du texte gris foncé
        message.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); // Marges internes
        message.setOpaque(false);                              // Pas d'opacité sur le label

        // Bouton "Rejouer"
        JButton rejouerBtn = new JButton("Rejouer");
        rejouerBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        rejouerBtn.setBackground(new Color(102, 153, 0));     // Vert
        rejouerBtn.setForeground(Color.WHITE);
        rejouerBtn.setFocusPainted(false);
        rejouerBtn.setPreferredSize(new Dimension(120, 40));

        // Bouton "Accueil"
        JButton accueilBtn = new JButton("Accueil");
        accueilBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        accueilBtn.setBackground(new Color(153, 0, 0));       // Rouge foncé
        accueilBtn.setForeground(Color.WHITE);
        accueilBtn.setFocusPainted(false);
        accueilBtn.setPreferredSize(new Dimension(120, 40));

        // Action du bouton "Rejouer" : ferme la fenêtre actuelle et ouvre une nouvelle partie
        rejouerBtn.addActionListener(e -> {
            dialog.dispose();  // Ferme le dialogue
            dispose();         // Ferme la fenêtre actuelle
            new FenetreJeu();  // Crée une nouvelle fenêtre de jeu (nouvelle partie)
        });

        // Action du bouton "Accueil" : retourne à l'écran d'accueil
        accueilBtn.addActionListener(e -> {
            dialog.dispose();      // Ferme le dialogue
            dispose();             // Ferme la fenêtre actuelle
            Accueil.launchAccueil(); // Lance la fenêtre d'accueil
        });

        // Panneau pour contenir les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(rejouerBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Espace horizontal entre les boutons
        buttonPanel.add(accueilBtn);

        // Ajoute le message au centre du panneau de contenu
        contentPanel.add(message, BorderLayout.CENTER);
        // Ajoute le panneau des boutons en bas
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true); // Affiche la boîte de dialogue (bloque ici jusqu'à fermeture)
    }
}
