/**
 * Fusee est un Bonus disponible dans les niveaux
 */
public class Fusee extends Bonus{
    
    public Score score = new Score();

    //constructeur
    public Fusee(){
        super("Fusée");
    }

    /**
     * La fusée est accessible tous les 2000 points dans un niveau
     * @return true si elle est accessible
     */
    public boolean estAccessible(){
        return (score.valeur>=2000);
    }


    /**
     * Détruit une colonne du plateau que le joueur choisit
     * @param scoreNiveau
     * @param colonne le numéro de la colonne
     * @param p la plateau
     * @return le nombre d'animaux sauvés par la fusée
     */
    public int action(Score scoreNiveau, int colonne, Plateau p){
        int nb=0;
        int nbAnimaux=0;
        boolean flag=true;
        if(estAccessible()){
            for(int i=0;i<p.getTableau().length;i++){
                if(p.getTableau()[i][colonne].getPiece() instanceof BlocCouleur){
                    BlocCouleur b = (BlocCouleur)p.getTableau()[i][colonne].getPiece();
                    if(b.fortifie){b.fortifie=false; flag=false;}
                    else{
                        b.setExiste(false);
                        nb++;
                    }
                }
            }
            for (int i=0;i<p.getTableau().length;i++){
                if(p.getTableau()[i][colonne].getPiece() instanceof Animal){
                    Animal a = (Animal)p.getTableau()[i][colonne].getPiece();
                    if(flag){
                        a.setExiste(false);
                        nbAnimaux++;
                    }
                }
            }
            if(scoreNiveau != null) scoreNiveau.ballonOuFusee(nb);
            score.ballonOuFusee(nb);
            score.valeur-=2000;
        }
        return nbAnimaux;
    }

}
