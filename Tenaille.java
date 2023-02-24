/**
 * Tenaille est un Bonus que le joueur peut acheter
 */
public class Tenaille extends Bonus {
    
    public static final int PRIX = 800;

    //constructeur
    public Tenaille(){
        super("Tenaille");
    }

    /**
     * Enlève les fortifications de tous les BlocCouleur fortifiés du plateau
     * @param p le plateau
     * @param joueur
     */
    public static void action(Plateau p, Joueur joueur){
        if(joueur.getNbTenaille()>0){
            for (int i=0; i<p.getTableau().length;i++){
                for (int j=0;j<p.getTableau()[0].length;j++){
                    if (p.getCase(i, j).getPiece() instanceof BlocCouleur){
                    BlocCouleur b = (BlocCouleur)p.getCase(i, j).getPiece();
                    if(b.fortifie){b.fortifie=false;}
                    }
                }
            }
            joueur.diminuerTenaille();
        }
    }
}
