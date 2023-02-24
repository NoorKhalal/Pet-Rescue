import java.io.*;
import java.util.HashMap;

/**
 * le Plateau du niveau a un tableau de Case
 */
public class Plateau implements Serializable{

    private Case[][] tableau;
    static final long serialVersionUID=42L;

    // constructeur
    public Plateau(Case[][] tab) {
        tableau = tab;
    }

    // getters
    public Case[][] getTableau() {
        return tableau;
    }
    public Case getCase(int x, int y) {
        return tableau[x][y];
    }

    /**
     * Reorganise le plateau après un coup :
     * fait tomber les blocs qui sont au-dessus de vides,
     * décale les colonnes vers la gauche si certaines se sont vidées
     */
    public void reorganisation() {
        blocsTombent();
        decalageColonne();
        blocsTombent();
    }

    /**
     * Cherche si il y a une colonne vide dans le plateau
     * @return le numéro de la première colonne vide, -1 si il n'y en a pas
     */
    public int colonneVide(){
        boolean colonneVide = false;
        int numero=0;
        boolean flag = true;
        int i =0;

        //on regarde si il y a une colonne vide
        for(int j=1;j<tableau[0].length;j++){
            if(numero==0){
                while(i<tableau.length-1){
                    if(tableau[i][j].getPiece() instanceof PieceDeplacable){
                        PieceDeplacable pi = (PieceDeplacable) tableau[i][j].getPiece();
                        if(pi.existe){flag=false;}
                    }
                    else if(tableau[i][j].getPiece() instanceof BlocBois){flag=false;}
                    i++;
                }
                if(flag){colonneVide=true; numero = j;}
                flag=true;
                i=0;
            }
        }

        //on vérifie si il y a des blocs à la droite de la colonne vide
        flag=false;
        if(colonneVide){
            for(int j=numero+1;j<tableau[0].length-1;j++){
                while(i<tableau.length){
                    if(tableau[i][j].getPiece() instanceof PieceDeplacable){
                        PieceDeplacable pi = (PieceDeplacable) tableau[i][j].getPiece();
                        if(pi.existe){flag=true;}
                    }
                    i++;
                }
                if(flag){return numero;}
                flag=false;
                i=0;
            }
        }
        return -1;
    }

    /**
     * Décale les colonnes vers la gauche quand il y en a qui sont vides
     */
    public void decalageColonne() {
        while(colonneVide()!=-1) {
            int numero = colonneVide();
            //on regarde qu'elle est la prochaine colonne non vide
            boolean vide = true;
            int i=0;
            int prochaineColonne=0;
            for (int j = numero+1; j<tableau[0].length-1; j++){
                if(prochaineColonne==0){
                    while(i<tableau.length){
                        if(tableau[i][j].getPiece() instanceof PieceDeplacable){
                            PieceDeplacable pi = (PieceDeplacable) tableau[i][j].getPiece();
                            if(pi.existe){vide=false;}
                        }
                        i++;
                    }
                    if(!vide){prochaineColonne=j;}
                    vide=true;
                    i=0;
                }
            }
            //on décale cette colonne non vide dans la colonne vide
            for (int x = 0; x < tableau.length-1; x++) {
                if(tableau[x][prochaineColonne].getPiece() instanceof PieceDeplacable){
                    PieceDeplacable pi = (PieceDeplacable) tableau[x][prochaineColonne].getPiece();
                    pi.setX(x);
                    pi.setY(numero);
                    Case ci = new Case(pi);
                    tableau[x][numero]=ci;
                    tableau[x][prochaineColonne].setPiece(new PieceDeplacable(x,prochaineColonne));
                }
            }
        }
    }

    /**
     * Fait tomber les blocs de haut en bas dès qu'il y a un vide
     */
    public void blocsTombent(){
       int n = tableau.length;
       int m = tableau[0].length;
       PieceDeplacable pi;
       for(int j = 0 ; j < m ; j ++) //pour chaque colonne
       {
           
        for(int i = n-1 ; i >= 0 ; i--)//le test se fait du bas de la colonne vers le haut
        {
            if(tableau[i][j].getPiece() instanceof PieceDeplacable)
                {
                    pi = (PieceDeplacable) tableau[i][j].getPiece();
                    if(pi.getExiste())
                    {
                        if(pi instanceof BlocCouleur)
                        {
                            BlocCouleur bc = (BlocCouleur) pi;
                            if(!bc.fortifie) deplacerVersLeBas(pi);
                        }
                        else deplacerVersLeBas(pi);
                    }
                     
                }
            }
        }
           
    }

    /**
     * Voir si une pièce déplaçable est au dessus d'un vide
     * @param p la pièce déplaçable
     * @return true si pièce en bas de p est vide, false sinon 
     */
    private boolean videEnBas(PieceDeplacable p){
       int n = tableau.length;
       int x = p.getX();
       int y = p.getY();
       if(x < n-1) //si la piece n'est pas sur la dernière ligne
       {
            if(tableau[x+1][y].getPiece() instanceof PieceDeplacable)
            {
                PieceDeplacable pi = (PieceDeplacable) tableau[x+1][y].getPiece();
                if(pi instanceof BlocCouleur) //si un BlocCouleur est fortifié ça retourne immédiatement false
                {
                    BlocCouleur bc = (BlocCouleur) pi;
                    if(bc.fortifie) return false;
                    else return (!bc.getExiste());
                }
                else
                    return (!pi.getExiste());
            }
            else return false;
       }
       else return false; //il n'y a plus de vides en bas de la dernière ligne
       
    }

    /**
     * Si une pièce déplaçable est en haut d'un vide, on va compter tous les vides qu'il y a en bas
     * @param p pièce déplaçable
     * @return nombre de vides en bas de p
     */
    private int nombreVidesEnBas(PieceDeplacable p){
        int n = tableau.length;
        int x = p.getX();
        int y = p.getY();
        Piece piece = tableau[x+1][y].getPiece();
        int cpt  = 0;
        while(x < n-1 && !(piece instanceof BlocBois)) //tant que la piece n'est pas sur la dernière ligne et qu'elle n'est pas sur un bloc en bois
       {
            if(piece instanceof PieceDeplacable)
            {
                PieceDeplacable pi = (PieceDeplacable) piece;
                if(pi instanceof BlocCouleur)
                {
                    BlocCouleur bc = (BlocCouleur) pi;
                    if(!bc.fortifie && !bc.getExiste()) cpt++;
                }
                else
                    if (!pi.getExiste()) cpt++;
            }
            x++;
            if(x < n-1) piece = tableau[x+1][y].getPiece();
       }
       return cpt;
    }

    /**
     * Si p est au dessus d'un vide :
     * compte le nombre de vides qu'il y a en dessous de p,
     * fait tomber p à la dernière case qui contenait un vide,
     * remplace la case qu'occupait p par un vide
     * @param p pièce déplaçable
     */
    private void deplacerVersLeBas(PieceDeplacable p){
        if(videEnBas(p)){
            int nbrDeVides = nombreVidesEnBas(p);
            int x = p.getX();
            int y = p.getY();
            Case ci = new Case(p);
            p.setX(x+nbrDeVides);
            tableau[x+nbrDeVides][y] = ci;
            tableau[x][y].setPiece(new PieceDeplacable(x,y));

        }
    }

    /**
     * Cherche dans le plateau si il reste des animaux à sauver
     * @return true si tous les animaux ont été sauvés
     */
    public boolean animauxTousSauves()
    {
        for(int i = 0 ; i < tableau.length ; i++ )
        {
            for(int j = 0 ; j < tableau[i].length ; j++)
            {
                if(tableau[i][j].getPiece() instanceof PieceDeplacable)
                {
                    PieceDeplacable p = (PieceDeplacable) tableau[i][j].getPiece();
                    if(p instanceof Animal)
                    {
                        Animal an = (Animal) p;
                        if(an.getExiste()) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Cherche dans le plateau si il reste des blocs
     * @return true si il reste des blocs
     */
    public boolean ilResteDesBlocs(){
        for(int i=0;i<tableau.length;i++){
            for (int j=0;j<tableau[0].length;j++){
                if(tableau[i][j].getPiece() instanceof BlocCouleur){
                    BlocCouleur bc = (BlocCouleur)tableau[i][j].getPiece();
                    if(bc.getExiste()){return true;}
                }
            }
        }
        return false;
    }

    /**
     * Cherche dans le plateau si il reste des blocs qui ne sont pas seuls
     * @return true si il reste des blocs à détruire
     */
    public boolean ilResteDesBlocsADetruire(){
        for(int i=0;i<tableau.length;i++){
            for (int j=0;j<tableau[0].length;j++){
                if(tableau[i][j].getPiece() instanceof BlocCouleur){
                    BlocCouleur bc = (BlocCouleur)tableau[i][j].getPiece();
                    if(tableau[i-1][j].getPiece() instanceof BlocCouleur && !bc.fortifie && bc.getExiste()){
                        BlocCouleur b = (BlocCouleur)tableau[i-1][j].getPiece();
                        if (b.getExiste() && !b.fortifie && b.getCouleur().equals(bc.getCouleur())){return true;}
                    }
                    if(tableau[i][j-1].getPiece() instanceof BlocCouleur && !bc.fortifie && bc.getExiste()){
                        BlocCouleur b = (BlocCouleur)tableau[i][j-1].getPiece();
                        if (b.getExiste() && !b.fortifie && b.getCouleur().equals(bc.getCouleur())){return true;}
                    }
                    if(tableau[i][j+1].getPiece() instanceof BlocCouleur && !bc.fortifie && bc.getExiste()){
                        BlocCouleur b = (BlocCouleur)tableau[i][j+1].getPiece();
                        if (b.getExiste() && !b.fortifie && b.getCouleur().equals(bc.getCouleur())){return true;}
                    }
                    if(tableau[i+1][j].getPiece() instanceof BlocCouleur && !bc.fortifie && bc.getExiste()){
                        BlocCouleur b = (BlocCouleur)tableau[i+1][j].getPiece();
                        if (b.getExiste() && !b.fortifie && b.getCouleur().equals(bc.getCouleur())){return true;}
                    }
                }
            }
        }
        return false;
    }

    /**
     * Cherche dans le plateau si il reste des BonusPlateau
     * @return true si il en reste
     */
    public boolean ilResteDesBonus(){
        for (int i=0;i<tableau.length;i++){
            for (int j=0; j<tableau[0].length;j++){
                if(tableau[i][j].getPiece() instanceof BonusPlateau){
                    BonusPlateau b = (BonusPlateau)tableau[i][j].getPiece();
                    if(b.getExiste()){return true;}
                }
            }
        }
        return false;
    }

    /**
     * Test si il reste des blocs à détruire ou des bonus sur le plateau
     * @return true si il reste des déplacements
     */
    public boolean ilResteDesDeplacements(){
        if(ilResteDesBlocsADetruire()){return true;}
        else if(ilResteDesBonus()){return true;}
        return false;
    }

    /**
     * Parcours le plateau en recherche du premier groupe de BlocCouleur destructible,
     * ce bloc n'appartient pas forcément au groupe de blocs le plus grand
     * @return un BlocCouleur appartenant au groupe trouvé
     */
    public BlocCouleur aide(){
        int n = tableau.length;
        int m = tableau[0].length;
        for(int i = n-1 ; i >= 0 ; i--)
        {
            for( int j = 0 ; j < m ; j++)
            {
                if(tableau[i][j].getPiece() instanceof PieceDeplacable)
                {
                    PieceDeplacable p = (PieceDeplacable) tableau[i][j].getPiece();
                    if(p instanceof BlocCouleur)
                    {   
                        BlocCouleur bc = (BlocCouleur) p;
                        if(!bc.fortifie)
                        if (bc.caseToucheePasSeule(this)) return bc;
                    }
                }
            }
        }
        return null;
    }


    /**
     * Cherche un bonus dans le plateau
     * @return le premier bonus trouvé dans le plateau
     */
    public BonusPlateau aideBonus(){
        int n = tableau.length;
        int m = tableau[0].length;
        BonusPlateau bp = null;
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(tableau[i][j].getPiece() instanceof BonusPlateau){
                    bp = (BonusPlateau)tableau[i][j].getPiece();
                    return bp;
                }
            }
        }
        return null;
    }

    /**
     * Cherche une aide à donner au joueur : 
     * soit un bonus si il y en a un, soit le plus grand nombre de bloc côte à côte
     * @return la PieceDeplacable intéressante à jouer
     */
    public PieceDeplacable aideSup(Partie partie, Vue vue){
       
        //pour tous les niveaux
        //lance la fusée si elle est accessible sur la colonne qui contient le plus d'animaux
        if(partie.getFusee().estAccessible()) {lancerFusee(partie, vue); return null;}

        //pour le niveau 3:
        //si le brise bloc est disponible 
        if(partie.getNiveau().getNumero() == 3 && partie.getJoueur().getNbBriseBloc() > 0) 
        {   utiliserBriseBloc(partie); return null; }
        
        //pour tous les niveaux
        //recherche de bonus dans le plateau
        BonusPlateau bp = aideBonus();
        if(bp != null) return bp;
        
       
        //pour le niveau 4:
        //si la tenaille est disponible enlève la fortification des blocs
        if(partie.getNiveau().getNumero() == 4 && partie.getJoueur().getNbTenaille() > 0)
        { Tenaille.action(this, partie.getJoueur()); return null; }
        //si le brise bloc est disponible
        if(partie.getNiveau().getNumero() == 4 && partie.getJoueur().getNbBriseBloc() > 0) 
        {   utiliserBriseBloc(partie); return null; }
        //renvoie les premiers blocs destructibles à partir de la fin (pas forcement le plus gros bloc de code)
        if(partie.getNiveau().getNumero() == 4 )    
        { return aide();}
        
        
        //pour tous les niveaux
        //recherche du plus grand nombre de blocCouleur côte à côte
        return plusGrosBlocCouleur();
    }

    /**
     * Une aide qui sera appelée dans la Demo de la vue Interface :
     * renvoie des suggestions au hasard plutôt que le meilleur coup à apporter 
     * @return une PieceDeplaçable au hasard
     */
    public PieceDeplacable aidePasTresMalin(Partie partie, Vue vue){
        int rand = (int) (Math.random() * 10) +1; //donne un chiffre au hasard entre 1 et 10
        if(rand % 2 == 0)
        {
            //recherche de bonus dans le plateau
            BonusPlateau bp = aideBonus();
            if(bp != null) return bp;
            return plusGrosBlocCouleur();
        }
        else
        {
            //lance la fusée si elle est accessible sur la colonne qui contient le plus d'animaux
            if(partie.getFusee().estAccessible()) {lancerFusee(partie, vue); return null;}
            return aide();
        }
    }

    /**
     * Parcours le tableau de blocs en recherche du plus gros groupe de BlocCouleur destructible
     * @return un blocCouleur faisant partie du plus plus gros groupe de BlocCouleur destructible
     */
    public BlocCouleur plusGrosBlocCouleur(){

        int n = tableau.length;
        int m = tableau[0].length;

        HashMap<Integer, BlocCouleur> plusGrandScore = new HashMap<>();
        int scoreMax = 0;
        BlocCouleur previousBlocCouleur = null;
        for(int i = n-1 ; i > 0  ; i--)
        {  
            for( int j = 0 ; j < m ; j++)
            {  
                if(tableau[i][j].getPiece() instanceof PieceDeplacable)
                {  
                    PieceDeplacable p = (PieceDeplacable) tableau[i][j].getPiece();
                    if(p instanceof BlocCouleur)
                    {  
                        BlocCouleur bc = (BlocCouleur) p;
                        if(!bc.fortifie)
                        {   bc.remettreACompterAFaux(this);
                            if (bc.caseToucheePasSeule2(this))
                            {
                                bc.viderListeACompter();
                                bc.blocsACompter(this);
                                bc.compter();
                                int blocsDetruits = bc.nbBlocsComptes();
                                bc.viderListeACompter();
                        
                                try {
                                    previousBlocCouleur = plusGrandScore.putIfAbsent(Integer.valueOf(blocsDetruits), bc);
                                    scoreMax = Math.max(scoreMax, blocsDetruits);
                                } catch (NullPointerException e1) {
                                    e1.printStackTrace();
                                    System.out.println("La cle " + blocsDetruits + " ou le bloc est null");
                                } catch (IllegalArgumentException e2){
                                    e2.printStackTrace();
                                    System.out.println("la cle " + blocsDetruits + " ou le bloc empechent d'etre stockes");
                                }
                            }
                        } 
                    }
                }
            }
        }
        BlocCouleur bc = null;
        if (scoreMax == 0) return null;
        try {
            bc = plusGrandScore.get(Integer.valueOf(scoreMax));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bc;    
    }


    /**
     * Utilise le brise-bloc pour détruire des blocs précis dans le plateau de chaque niveau
     * @param partie
     */
    public void utiliserBriseBloc(Partie partie){
        int niveau = partie.getNiveau().getNumero();
         
        switch (niveau) {
            case 3:
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[6][3]);
                if(partie.getJoueur().getNbBriseBloc() > 0) 
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[6][5]);
                if(partie.getJoueur().getNbBriseBloc() > 0) 
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[6][1]);
     
                break;

            case 4:
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][8]);
                if(partie.getJoueur().getNbBriseBloc() > 0)
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][8]);
                if(partie.getJoueur().getNbBriseBloc() > 0) 
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][2]);
                if(partie.getJoueur().getNbBriseBloc() > 0) 
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][3]);
     
                break;    
            default:
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][3]);
                if(partie.getJoueur().getNbBriseBloc() > 0) 
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][5]);
                if(partie.getJoueur().getNbBriseBloc() > 0) 
                BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), tableau[9][7]);
            break;
        }
    }


    /**
     * Si une fusée est accessible, lance la fusée sur la colonne qui contient le plus d'animaux
     * @param partie
     */
    public void lancerFusee(Partie partie, Vue vue){
        
        int nbAn = partie.getFusee().action(partie.getNiveau().scoreNiveau, colonneContientLePlusDAnimaux(), this);
        partie.nbAnimauxSauves+=nbAn;
        if(vue!=null){vue.setAnimauxSauves(nbAn);}
        
    }

    /**
     * Compte tous les animaux dans chaque colonne
     * @return la colonne qui contient le plus grand nombre d'animaux
     */
    public int colonneContientLePlusDAnimaux(){

        int m  = tableau[0].length;
        int[] tabAnimaux = new int[m-2];
        int maxAnimaux = 0;
        for(int j = 1 ; j < m-1 ; j++)
        {
            tabAnimaux[j-1] = NbAnimaauxDansColonne(j);
            maxAnimaux = Math.max(maxAnimaux, tabAnimaux[j-1]);
        }

        for(int j = 0 ; j < m-2 ; j++)
        {
            if(maxAnimaux == tabAnimaux[j]) return (j+1);
        }
        return 4;

    }

    /**
     * Compte le nombre d'animaux qu'il y a dans une colonne donnée
     * @param col la colonne où on va compter le nombre d'animaux
     * @return le nombre d'animaix que contient col
     */
    public int NbAnimaauxDansColonne(int col){
        int n = tableau.length;
        int cpt = 0;
        for(int i = 0 ; i < n ; i++)
        {
            if (tableau[i][col].getPiece() instanceof PieceDeplacable)
            {
                PieceDeplacable pid = (PieceDeplacable) tableau[i][col].getPiece();
                if(pid.getExiste() && pid instanceof Animal) 
                {
                    Animal an = (Animal) pid;
                    if(!an.estSauve(this)) cpt++;
                }    
                
            }
        }
        return cpt;
    }

    /**
     * Test si une colonne contient un vide,
     * sera utilisée pour les niveaux 3,5,4 pour re-remplir les colonnes 
     * @param col la colonne à tester
     * @return true si la colonne ne contient aucun vide, false sinon
     */
    public boolean colonneNonPleine(int col){
        int n = tableau.length;
        for(int i = 1 ; i < n ; i++)
        {
            if(tableau[i][col].getPiece() instanceof PieceDeplacable)
            {
                PieceDeplacable p = (PieceDeplacable) tableau[i][col].getPiece();
                if(!p.getExiste()) return true;
            }
        }
        return false;
    }

    /**
     * Compte le nombre de vides dans une colonne du plateau
     * @param col une colonne du plateau
     * @return nombre de vides que contient cette colonne
     */
    public int NbVidesColonne(int col){
        int n = tableau.length;
        int cpt = 0;
        for(int i = n-1 ; i >= 0 ; i--)
        {
            if(tableau[i][col].getPiece() instanceof PieceDeplacable)
            {
                PieceDeplacable p = (PieceDeplacable) tableau[i][col].getPiece();
                if(!p.getExiste()) cpt++;
            }

        }
        return cpt;
    }
}
          