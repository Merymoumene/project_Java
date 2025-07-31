import javax.swing.*;  // Bibliothèque pour créer des interfaces graphiques (fenêtres, boutons, etc.)
import java.awt.*;     // Bibliothèque pour la gestion des composants graphiques (polices, couleurs, images, etc.)

public class Accueil {

    // Méthode principale pour afficher l'écran d'accueil du jeu
    public static void launchAccueil() {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Jeu de Dames");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer le programme quand on ferme la fenêtre
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);        // Mettre la fenêtre en plein écran
        frame.setSize(400, 300);                              // Taille initiale (avant le plein écran)
        frame.setLocationRelativeTo(null);                    // Centrer la fenêtre à l’écran

        // Panneau de fond avec une image personnalisée
        ImagePanel backgroundPanel = new ImagePanel("dames.jpg");
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS)); // Disposition verticale des éléments

        // Titre du jeu
        JLabel title = new JLabel("Bienvenue dans le Jeu de Dames");
        title.setFont(new Font("Arial", Font.BOLD, 18));       // Style du texte
        title.setForeground(Color.WHITE);                      // Couleur du texte
        title.setAlignmentX(Component.CENTER_ALIGNMENT);       // Centrer horizontalement dans le panel

        // Création du bouton "Commencer le jeu"
        JButton startBtn = new JButton("Commencer le jeu");
        startBtn.setFont(new Font("Arial Black", Font.BOLD, 24));   // Style du texte
        startBtn.setBackground(new Color(206, 179, 128));           // Couleur de fond
        startBtn.setForeground(Color.WHITE);                        // Couleur du texte
        startBtn.setFocusPainted(false);                            // Ne pas dessiner le contour de focus
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);         // Centrage horizontal
        startBtn.setPreferredSize(new Dimension(300, 50));          // Taille préférée
        startBtn.setMaximumSize(new Dimension(300, 50));            // Taille maximale

        // Action quand on clique sur "Commencer le jeu"
        startBtn.addActionListener(e -> {
            frame.dispose();       // Fermer la fenêtre d’accueil
            new FenetreJeu();      // Ouvrir la fenêtre du jeu (à condition que cette classe existe)
        });

        // Création du bouton "Quitter le jeu"
        JButton quitBtn = new JButton("Quitter le jeu");
        quitBtn.setFont(new Font("Arial Black", Font.BOLD, 24));
        quitBtn.setBackground(new Color(153, 0, 0));               // Rouge foncé
        quitBtn.setForeground(Color.WHITE);
        quitBtn.setFocusPainted(false);
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitBtn.setPreferredSize(new Dimension(300, 50));
        quitBtn.setMaximumSize(new Dimension(300, 50));

        // Action quand on clique sur "Quitter le jeu"
        quitBtn.addActionListener(e -> {
            System.exit(0);   // Fermer complètement l'application
        });

        // Panneau transparent pour placer les boutons par-dessus l'image
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Disposition verticale
        panel.setOpaque(false);                                  // Rendre le panneau transparent
        panel.setBorder(BorderFactory.createEmptyBorder(300, 0, 0, 0)); // Marge en haut pour centrer verticalement

        //panel.add(title); // Ligne commentée : à décommenter si tu veux afficher le titre
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // Espace vide vertical
        panel.add(startBtn); // Ajouter le bouton "Commencer"
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Espace entre les deux boutons
        panel.add(quitBtn);  // Ajouter le bouton "Quitter"

        // Ajouter le panneau contenant les boutons au fond
        backgroundPanel.add(panel);
        frame.setContentPane(backgroundPanel); // Définir l’image comme contenu principal
        frame.setVisible(true);                // Afficher la fenêtre
    }

    // Classe interne pour dessiner une image comme fond d’écran
    static class ImagePanel extends JPanel {
        private Image backgroundImage;

        // Constructeur qui charge une image à partir du nom de fichier
        public ImagePanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        // Redéfinition de la méthode paintComponent pour dessiner l’image en fond
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Appel de la méthode de la superclasse
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Redimensionner et dessiner l’image
        }
    }
}
