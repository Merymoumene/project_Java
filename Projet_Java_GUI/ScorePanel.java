import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    // Labels pour afficher les scores des joueurs
    private JLabel scoreJ1;
    private JLabel scoreJ2;
    // Variables pour stocker les points des joueurs
    private int pointsJ1 = 0;
    private int pointsJ2 = 0;

    // Constructeur du panneau de score
    public ScorePanel() {
        // Utilisation d'un BoxLayout vertical pour empiler les composants
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Couleur de fond du panneau
        setBackground(new Color(181, 136, 99));
        // Dimension préférée du panneau (largeur 150 px, hauteur 700 px)
        setPreferredSize(new Dimension(150, 700));

        // Titre du panneau "Scores"
        JLabel title = new JLabel("Scores", JLabel.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 30));
        // Centrer horizontalement le titre
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Couleur du texte du titre
        title.setForeground(new Color(240, 217, 181));

        // Initialisation des labels de score pour chaque joueur
        scoreJ1 = new JLabel("Noir : 0", JLabel.CENTER);  // Joueur 1 (Noir)
        scoreJ2 = new JLabel("Blanc : 0", JLabel.CENTER); // Joueur 2 (Blanc)

        // Couleur du texte des scores
        scoreJ1.setForeground(Color.black);
        scoreJ2.setForeground(Color.white);
        // Police et taille des textes des scores
        scoreJ1.setFont(new Font("Arial", Font.BOLD, 20));
        scoreJ2.setFont(new Font("Arial", Font.BOLD, 20));
        // Centrer horizontalement les scores
        scoreJ1.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreJ2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajout d'espaces verticaux entre les composants pour la mise en forme
        add(Box.createVerticalStrut(40));
        add(title);
        add(Box.createVerticalStrut(200));
        add(scoreJ2);
        add(Box.createVerticalStrut(50));
        add(scoreJ1);
    }

    // Méthode pour ajouter un point à un joueur
    public void ajouterPoint(boolean joueur1) {
        if (joueur1) pointsJ1++;  // Incrémente le score du joueur 1 (Noir)
        else pointsJ2++;          // Incrémente le score du joueur 2 (Blanc)
        // Mise à jour des textes des labels avec les nouveaux scores
        scoreJ1.setText("Noir : " + pointsJ1);
        scoreJ2.setText("Blanc : " + pointsJ2);
    }

    // Méthode pour réinitialiser les scores à zéro
    public void resetScores() {
        pointsJ1 = 0;
        pointsJ2 = 0;
        // Mise à jour des labels pour afficher zéro partout
        scoreJ1.setText("Noir : 0");
        scoreJ2.setText("Blanc : 0");
    }
}
