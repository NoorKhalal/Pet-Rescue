import java.util.ArrayList;

/**
 * BlocCouleur est une PieceDeplacable du plateau qui se supprime par groupe de couleur
 */
class BlocCouleur extends PieceDeplacable {

    /**
     * Une Couleur est une caractéristique du BlocCouleur.
     * Cette classe est aussi utiliser pour donner sa couleur au bonus Ballon
     */
    public enum Couleur{
        ROUGE {
            @Override
            public String toString() { return "Rouge";}
        },
        BLEU{
            @Override
            public String toString() { return "Bleu";}
        },
        VERT{
            @Override
            public String toString() { return "Vert";}
        },
        JAUNE{
            @Override
            public String toString() { return "Jaune";}
        },
        VIOLET{
            @Override
            public String toString() { return "Violet";}
        };          
    }

    public Couleur couleur;
    boolean fortifie;
    boolean dejaASupprimer;
    static final long serialVersionUID=42L;
    
    //getter
    public Couleur getCouleur() {return couleur;}

    //constructeurs
    public BlocCouleur(int x, int y,Couleur c, boolean b)
    {
        super(x, y);
        couleur = c;
        fortifie = b;
        dejaASupprimer=false;
        this.setExiste(true);
    }
    public BlocCouleur(BlocCouleur b)
    {
        super((PieceDeplacable)b);
        fortifie = b.fortifie;
        couleur = b.getCouleur();
        dejaASupprimer=b.dejaASupprimer;
        this.setExiste(true);
    }
    public BlocCouleur(){}


    /**
     * Regarde si il y a des bloc de la même couleur autour du bloc
     * @return true si il y a un BlocCouleur de la même couleur à coté
     */
    public boolean caseToucheePasSeule(Plateau p){
        if(p.getCase(getX()-1, getY()).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX()-1, getY()).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaASupprimer && !b.fortifie){return true;}
        }
        if(p.getCase(getX(), getY()-1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()-1).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaASupprimer && !b.fortifie){return true;}
        }
        if(p.getCase(getX(), getY()+1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()+1).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaASupprimer && !b.fortifie ){return true;}
        }
        if(p.getCase(getX()+1, getY()).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX()+1, getY()).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaASupprimer && !b.fortifie){return true;}
        }
        return false;
    }

    

    public static ArrayList<BlocCouleur> blocsASupprimer = new ArrayList<>(); //liste des blocs a supprimer
    ArrayList<BlocCouleur> blocsARecurser; //liste de blocs sur lesquels effectuer la recursivité de la suppression

    /**
     * Remplit les listes ci-dessus, surtout la liste blocsASupprimer qui sert pour la suppression
     * @param p le plateau
     */
    public void blocsASupprimer(Plateau p){
        
        blocsARecurser = new ArrayList<>();

        if (caseToucheePasSeule(p)){
            
            if(p.getCase(getX()-1, getY()).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX()-1, getY()).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsASupprimer.contains(b) && !b.fortifie){
                        blocsASupprimer.add(b);
                        blocsARecurser.add(b); b.dejaASupprimer=true;
                    }
                }
            }
            if(p.getCase(getX(), getY()-1).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()-1).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsASupprimer.contains(b) && !b.fortifie){
                        blocsASupprimer.add(b);
                        blocsARecurser.add(b); b.dejaASupprimer=true;
                    }
                }
            }
            if(p.getCase(getX(), getY()+1).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()+1).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsASupprimer.contains(b) && !b.fortifie){
                        blocsASupprimer.add(b);
                        blocsARecurser.add(b); b.dejaASupprimer=true;
                    }
                }
            }
            if(p.getCase(getX()+1, getY()).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX()+1, getY()).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsASupprimer.contains(b) && !b.fortifie){
                        blocsASupprimer.add(b);
                        blocsARecurser.add(b); b.dejaASupprimer=true;
                    }
                }
            }
        }
      
        for (BlocCouleur bc : blocsARecurser){
            bc.blocsASupprimer(p);
        }   
    }

    /**
     * Enleve les fortifications des blocs autour de ceux supprimés
     * @param p plateau
     */
    public void enleverFortifications(Plateau p){
        if(p.getCase(getX()-1, getY()).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX()-1, getY()).getPiece();
            if (b.fortifie){b.fortifie=false;}
        }
        if(p.getCase(getX(), getY()-1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()-1).getPiece();
            if (b.fortifie){b.fortifie=false;}
        }
        if(p.getCase(getX(), getY()+1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()+1).getPiece();
            if (b.fortifie){b.fortifie=false;}
        }
        if(p.getCase(getX()+1, getY()).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX()+1, getY()).getPiece();
            if (b.fortifie){b.fortifie=false;}
        }
    }

    /**
     * Supprime les blocs de la liste blocsASupprimer
     * @param p plateau
     */
    public void supprimer(Plateau p){
        if(blocsASupprimer.size()==0){return;}
        blocsASupprimer.add(this);
        for (BlocCouleur c : blocsASupprimer){
            c.enleverFortifications(p);
            c.setExiste(false);
        }
    }

    /**
     * Doit être appelée juste après la méthode BlocsASupprimer,
     * sinon la liste garde en mémoire tous les blocs supprimés (ceux des autres couleurs des suppressions précédentes)
     */
    public void viderListeASupprimer()
    {
        blocsASupprimer.clear();
    }

    /**
     * @return le nombre de blocs à supprimer
     */
    public int nbBlocsSupprimes(){
        if (blocsASupprimer.size()!=0){
            return blocsASupprimer.size()-1;
        }
        return 0;
    }


                        /***************************************************** */

    /*les méthodes qui suivent sont utilisées dans la méthode aideSup() de Plateau,
      afin de compter le plus grand groupe de BlocCouleur destructible (qui rapporte le plus de points)
      les méthodes suivent le même fonctionnement que les méthodes de suppression mais sans supprimer les blocs à la fin
      la différence est le changement du boolean dejaCompte à la place de celui dejaASupprimer*/

    boolean dejaCompte = false;

    public static ArrayList<BlocCouleur> blocsACompter = new ArrayList<>();

     /**
      * Regarde si il y a des bloc de la même couleur autour du bloc
      * @param p plateau
      * @return true si une case du plateau a un bloc adjacent de la même couleur
      */
    public boolean caseToucheePasSeule2(Plateau p){
        if(p.getCase(getX()-1, getY()).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX()-1, getY()).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaCompte && b.getExiste()){return true;}
        }
        if(p.getCase(getX(), getY()-1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()-1).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaCompte && b.getExiste()){return true;}
        }
        if(p.getCase(getX(), getY()+1).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()+1).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaCompte && b.getExiste()){return true;}
        }
        if(p.getCase(getX()+1, getY()).getPiece() instanceof BlocCouleur){
            BlocCouleur b = (BlocCouleur)p.getCase(getX()+1, getY()).getPiece();
            if (b.getCouleur().equals(couleur) && !b.dejaCompte && b.getExiste()){return true;}
        }
        return false;
    }

     /**
      * Met à jour les listes blocsARecurser et blocsACompter
      * @param p le plateau
      */ 
    public void blocsACompter(Plateau p){
        
        blocsARecurser = new ArrayList<>();

        if (caseToucheePasSeule2(p)){

            if(p.getCase(getX()-1, getY()).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX()-1, getY()).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsACompter.contains(b) && !b.fortifie)
                    {
                        blocsACompter.add(b);
                        blocsARecurser.add(b); 
                        b.dejaCompte=true;
                    }
                }
            }
            if(p.getCase(getX(), getY()-1).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()-1).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsACompter.contains(b) && !b.fortifie)
                    {
                        blocsACompter.add(b);
                        blocsARecurser.add(b); 
                        b.dejaCompte=true;
                    }
                }
            }
            if(p.getCase(getX(), getY()+1).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX(), getY()+1).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsACompter.contains(b) && !b.fortifie)
                    {
                        blocsACompter.add(b);
                        blocsARecurser.add(b); 
                        b.dejaCompte=true;
                    }
                }
            }
            if(p.getCase(getX()+1, getY()).getPiece() instanceof BlocCouleur){
                BlocCouleur b = (BlocCouleur)p.getCase(getX()+1, getY()).getPiece();
                if (b.getCouleur().equals(couleur)){
                    if(!blocsACompter.contains(b) && !b.fortifie)
                    {
                        blocsACompter.add(b);
                        blocsARecurser.add(b); 
                        b.dejaCompte=true;
                    }
                }
            }
        }
      
        for (BlocCouleur bc : blocsARecurser){
            bc.blocsACompter(p);
        }   
    }
    /**
     * Ajoute les blocs à compter à la liste blocACompter
     */
    public void compter(){
        if(blocsACompter.size()==0){return;}
        blocsACompter.add(this);
      
    }

    /**
     * Vide la liste blocsACompter
     */
    public void viderListeACompter()
    {
        blocsACompter.clear();
    }

    /**
     * Après avoir compté les blocs adjacents à un BlocCouleur,
     * on remet le boolean dejaCompte à faux pour compter les blocs adjacents aux autres blocs du plateau,
     * et pour comparer ensuite
     * @param p plateau
     */
    public void remettreACompterAFaux(Plateau p){

        Case[][] tablCases = p.getTableau();
        int n = tablCases.length;
        int m = tablCases[0].length;
        for(int i = 0 ;  i < n ; i++ )
        {
            for(int j = 0 ; j < m ; j++)
            {
                if(tablCases[i][j].getPiece() instanceof PieceDeplacable)
                {
                    PieceDeplacable pi = (PieceDeplacable) tablCases[i][j].getPiece();
                    if(pi instanceof BlocCouleur)
                    {
                        BlocCouleur bc = (BlocCouleur) pi;
                        if(bc.getExiste()) bc.dejaCompte = false;
                    }
                }
            }
        }
    }

    /**
     * @return le nombre de blocs à compter
     */
    public int nbBlocsComptes(){
        if (blocsACompter.size()!=0){
            return blocsACompter.size()-1;
        }
        return 0;
    }



}

