/**
 * Les BonusPlateau sont des PieceDeplacable, les bonus du plateau : Ballon et Bombe
 */
public abstract class BonusPlateau extends PieceDeplacable{
    
    String nom;

    //constructeur
    public BonusPlateau(String n, int x, int y){
        super(x, y);
        nom=n;
        this.setExiste(true);
    }

}
