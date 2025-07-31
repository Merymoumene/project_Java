// Classe Dame qui hérite de la classe abstraite Piece
public class Dame extends Piece {

    // Constructeur de la classe Dame
    public Dame(boolean joueur1) {
        super(joueur1);     // Appelle le constructeur de la classe mère (Piece) pour initialiser la couleur (joueur1 ou joueur2)
        this.estDame = true; // Marque cette pièce comme une Dame (et non un simple pion)
    }
}
