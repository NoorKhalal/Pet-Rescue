/**
 * Animal est une PieceDeplacable du plateau
 */
class Animal extends PieceDeplacable {
    String nom;

    //constructeur
    public Animal(int x, int y, String n){
        super(x,y);
        nom = n;
        setExiste(true);
    }
    
    /**
     * Test si l'animal est sorti du plateau
     * @param p le plateau
     * @return true si l'animal est en position d'être sauvé
     */
    public boolean estSauve(Plateau p){
        if(super.getX() > p.getTableau().length-3 || super.getY() > p.getTableau()[1].length-1){
            return true;
        }
        return false;
    }

    /**
     * Si l'animal est en position d'être sauvé, il est enlevé du plateau et le score augmente
     * @param n le niveau
     * @param j le joueur
     * @return true si l'animal a été sauvé
     */
    public boolean sauvetage(Niveau n, Joueur j){
        if(estSauve(n.getPlateau()) && this.existe){
            setExiste(false);
            n.scoreNiveau.valeur+=1000;
            return true;
        }
        return false;
    }

}