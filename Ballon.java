/**
 * Ballon est un BonusPlateau
 */
public class Ballon extends BonusPlateau{
    
    private BlocCouleur.Couleur couleur;

    //constructeur
    public Ballon(int x, int y, BlocCouleur.Couleur c){
        super("ballon", x, y);
        couleur=c;
    }

    //getter
    public BlocCouleur.Couleur getCouleur(){return couleur;}

    /**
     * Détruit tous les blocs du plateau qui sont de la même couleur que le ballon
     * @param p le plateau
     * @return le nombre de blocs détruits
     */
    public int action(Plateau p){
        int nb=0;
        for (int i =0; i<p.getTableau().length-1;i++){
            for (int j=0; j<p.getTableau()[i].length-1;j++){
                Case c = p.getTableau()[i][j];
                if (c.getPiece() instanceof BlocCouleur){
                    BlocCouleur b = (BlocCouleur)c.getPiece();
                    if (b.getCouleur().equals(this.couleur) && !b.fortifie){
                        b.setExiste(false);
                        nb++;
                    }
                }
            }
        }
        this.setExiste(false);
        return nb;
    }
}
