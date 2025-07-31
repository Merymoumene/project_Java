public abstract class Piece {
    // Indique si la pièce appartient au joueur 1 (true) ou au joueur 2 (false)
    public boolean joueur1;

    // Indique si la pièce est une dame (true) ou un pion (false)
    public boolean estDame;

    // Constructeur : initialise la pièce en précisant à quel joueur elle appartient
    // Par défaut, la pièce n'est pas une dame (estDame = false)
    public Piece(boolean joueur1) {
        this.joueur1 = joueur1;
        this.estDame = false;
    }
}
