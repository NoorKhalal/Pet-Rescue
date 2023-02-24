/**
 * Bombe est un BonusPlateau
 */
public class Bombe extends BonusPlateau{
    
    //constructeur
    public Bombe(int x, int y){
        super("bombe", x, y);
        setExiste(true);
    }

    /**
     * Détruit les blocs de couleur se situant dans les 9 cases autour de la bombe sur le plateau
     * @param p le plateau
     */
    public void action(Plateau p){
        int x = this.getX();
        int y = this.getY();
        
        if(p.getCase(x-1, y-1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x-1, y-1).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        if(p.getCase(x-1, y).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x-1, y).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        if(p.getCase(x-1, y+1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x-1, y+1).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;;
        }
        if(p.getCase(x, y-1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x, y-1).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        if(p.getCase(x, y+1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x, y+1).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        if(p.getCase(x+1, y-1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x+1, y-1).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        if(p.getCase(x+1, y).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x+1, y).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        if(p.getCase(x+1, y+1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(x+1, y+1).getPiece();
            if(b.fortifie){b.fortifie=false;}
            else b.existe=false;
        }
        this.setExiste(false);
    }
}
