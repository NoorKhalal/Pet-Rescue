/**
 * BriseBloc est un Bonus que le joueur peut acheter
 */
public class BriseBloc extends Bonus {

    public static final int PRIX = 500;
    
    //constructeur
    public BriseBloc(){
        super("BriseBloc");
    }

    /**
     * Détruit un BlocCouleur que le joueur choisit
     * @param scoreNiveau
     * @param j le joueur
     * @param c la case du bloc à supprimer
     */
    public static void action(Score scoreNiveau,Joueur j, Case c){
        if (c.getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)c.getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
            if(j != null) j.diminuerBriseBloc();
            scoreNiveau.valeur+=20;
        }
    }

}
