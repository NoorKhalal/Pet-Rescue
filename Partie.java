import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Partie est la classe qui gère la vue du terminal
 */
public class Partie {
    
    private Niveau niveau;
    private Joueur joueur;
    private static boolean quitterLeNiveau=false;
    private static boolean quitterLaPartie=false;
    private static boolean continuer=false;
    private static Niveau niveauEnCours;
    private Fusee fusee = new Fusee();
    private int nbDeplacements;
    int nbAnimauxSauves;
    public boolean demo = false;

    //constructeur
    public Partie(Niveau n, Joueur j){
        niveau=n; joueur=j;
    }

    //getters
    public Niveau getNiveau(){return niveau;}
    public Joueur getJoueur(){return joueur;}
    public Fusee getFusee(){return fusee;}

    //setter
    public void setNiveau(Niveau n){niveau=n;}

    /**
     * Demande au joueur sur quelle vue il veut jouer :
     * soit sur le terminal soit sur l'interface graphique
     * @return "t" pour VueTerminal ou "i" pour VueInterfaceGraphique, "q" si le joueur veut quitter le jeu
     */
    public static String choixVue(){
        boolean mauvaiseCommande=false;
        Scanner sc = new Scanner(System.in);
        String input;
        do
        {
            mauvaiseCommande=false;
            System.out.println("Quelle vue choisissez-vous ?");
            System.out.println("t = terminal - i = interface graphique - q = quitter");
            input = sc.next();
            System.out.println();
            if(input.length()!=1 && !input.equals("t") && !input.equals("i") && !input.equals("q"))
            {System.out.println("Mauvaise commande, veuillez choisir une action valide"); mauvaiseCommande = true;}

        }while(mauvaiseCommande);
        return input;
    }

    /**
     * Récupère le pseudo entré par le joueur, et si il y a déjà un fichier .ser avec son pseudo sa sauvegarde est récupérée,
     * sinon cela créé une nouvelle sauvegarde.
     * Affiche le profil du joueur
     * @return le joueur
     */
    public static Joueur creationJoueur(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez votre pseudo : ");
        String pseudo = sc.next();
        Joueur j = new Joueur(pseudo);
        String path = pseudo+".ser";
        
        if(!((new File(path)).exists())){
            Joueur.createJoueur(j);
        }
        else{
            j = Joueur.getJoueur(pseudo);
        }

        //afficher son profil
        System.out.println(j.getPseudo()+" : "+j.getScore().valeur+" points, "+j.getNiveauxGagnes()+" niveaux gagnés, "+j.getNbBriseBloc()+" BriseBloc(s), "+j.getNbTenaille()+" Tenaille(s)");
        System.out.println();
        System.out.println("Meilleurs score :");
        System.out.println("niveau 1 : "+j.getScoreNiveaux()[0]+"   niveau 2 : "+j.getScoreNiveaux()[1]+"   niveau 3 : "+j.getScoreNiveaux()[2]+"   niveau 4 : "+j.getScoreNiveaux()[3]+"   niveau 5 : "+j.getScoreNiveaux()[4]);
        System.out.println();

        return j;
    }

    /**
     * Demande au joueur quelle action il souhaite faire :
     * jouer, acheter des bonus, voir les règles du jeu, voir une démo ou quitter
     * @return la lettre correspondant à l'action
     */
    public static String choixEnvironnement(){
        boolean mauvaiseCommande = false;
        Scanner sc = new Scanner(System.in);
        String input;
        do
        {
            mauvaiseCommande=false;
            System.out.println("Quelle action voulez-vous faire?");
            System.out.println("j = jouer - b = acheter des bonus - r = voir les règles du jeu - d = voir une démo du jeu (robot) - q = quitter ");
            input = sc.next();
            System.out.println();
            if(input.length()!=1 && !input.equals("j") && !input.equals("b") && !input.equals("r") && !input.equals("d") && !input.equals("q"))
            {System.out.println("Mauvaise commande, veuillez choisir une action valide"); mauvaiseCommande = true;}
        }while(mauvaiseCommande);
        return input;
    }

    /**
     * Affiche les niveaux que le joueur peut jouer suivant si il les a débloqués ou non
     */
    public void afficherEnvironnement(){
        System.out.print("Niveaux disponibles : ");
        System.out.print("1 ");
        if(joueur.getEnvironnement().niveaux.get(1).bloque){return;}
        System.out.print("  /  2 ");
        if(joueur.getEnvironnement().niveaux.get(2).bloque){return;}
        System.out.print("  /  3 ");
        if(joueur.getEnvironnement().niveaux.get(3).bloque){return;}
        System.out.print("  /  4 ");
        if(joueur.getEnvironnement().niveaux.get(4).bloque){return;}
        System.out.println("  /  5");
    }

    /**
     * Affiche les règles du jeu :
     * les objectifs, contraintes, bonus...
     */
    public static void afficherRègleDuJeu(){
        System.out.println("But du jeu : sauver tous les animaux !");
        System.out.println();
        System.out.println("A chaque tour, vous pouvez détruire un groupe de blocs de la même couleur");
        System.out.println("Un animal est sauvé quand il arrive en bas du plateau");
        System.out.println("Certains niveaux ont d'autres contraintes de déplacements ou de score à atteindre");
        System.out.println();
        System.out.println("Les pièces du plateau sont représentées par leurs initiales");
        System.out.println("exemples : R = Rouge ; Vi = Violet ; An = Animal ; Ba = Ballon ; Bo = Bombe ...");
        System.out.println("Les blocs X sont en bois, ce sont des obstacles fixes");
        System.out.println("Les blocs # sont fortifiés, leur couleur apparait après avoir détruit leur fortification");
        System.out.println();
        System.out.println("Pour vous aider, vous avez des bonus : ");
        System.out.println("- la fusée détruit la colonne de votre choix, elle est disponible tout les 2000 points dans un niveau");
        System.out.println("- le ballon détruit tous les blocs de sa couleur");
        System.out.println("- la bombe détruit les 9 blocs l'entourant");
        System.out.println("- le brise-bloc détruit un bloc de votre choix, vous pouvez en acheter pour 500 points à la boutique");
        System.out.println("- la tenaille enlève toutes les fortifications des blocs, vous pouvez en acheter pour 800 points à la boutique");
        System.out.println();
    }


    /**
     * Représente la boutique, le joueur peut y acheter Brise-Bloc et Tenaille
     * @param joueur le joueur
     */
    public static void acheterBonus(Joueur joueur){
        System.out.println("Quel bonus voulez-vous acheter ?");
        System.out.println("b = brise-bloc - t = tenaille");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.next();
        System.out.println();
        if(reponse.equals("b")){
            System.out.println("Prix : "+BriseBloc.PRIX);
            if(!joueur.acheterBriseBloc()){System.out.println("Vous n'avez pas assez de points"); System.out.println();}
            else {System.out.println("Brise-Bloc acheté");System.out.println();}
        }
        else if(reponse.equals("t")){
            System.out.println("Prix : "+Tenaille.PRIX);
            if(!joueur.acheterTenaille()){System.out.println("Vous n'avez pas assez de points"); System.out.println();}
            else {System.out.println("Tenaille achetée"); System.out.println();}
        }
        else {System.out.println("Mauvaise commande");}
    }


    /**
     * Le joueur choisit le niveau qu'il souhaite jouer
     * @param j le joueur
     * @return le niveau choisi
     */
    public static Niveau choixNiveau(Joueur j){
        Scanner sc = new Scanner(System.in);
        System.out.print("Choix du niveau : ");
        int input = sc.nextInt();
        System.out.println();
       
        System.out.println();
        if(input==1){return Niveau.getNiveau1();}
        if(input==2){
            Niveau niveau2 = Niveau.getNiveau2();
            if(j.getEnvironnement().niveaux.get(1).bloque==true){System.out.println("Niveau non débloqué"); return null;}
            else {return niveau2;}
        }
        
        if(input==3){
            Niveau niveau3 = Niveau.getNiveau3();
            if(j.getEnvironnement().niveaux.get(2).bloque==true){System.out.println("Niveau non débloqué"); return null;}
            else {return niveau3;}
        }
        if(input==4){
            Niveau niveau4 = Niveau.getNiveau4();
            if(j.getEnvironnement().niveaux.get(3).bloque==true){System.out.println("Niveau non débloqué"); return null;}
            else {return niveau4;}
        }
        if(input==5){
            Niveau niveau5 = Niveau.getNiveau5();
            if(j.getEnvironnement().niveaux.get(4).bloque==true){System.out.println("Niveau non débloqué"); return null;}
            else {return niveau5;}
        }
        else{System.out.println("Niveau non existant"); return null;}
    }

    /**
     * Affiche le plateau du niveau avec les initiales des pièces.
     * Il y a des lettres représentant les colonnes et des chiffres représentant les lignes pour une meilleur visibilité
     */
    public void afficherPlateau() {
        Case[][] tableau = niveau.getPlateau().getTableau();

        //affichage de la première ligne de la matrice
        int m = tableau[0].length;
        char limite = (char) ('A' + m-2);  
        System.out.print("      ");
        for(char car = 'A' ;  car < limite ; car++)
            System.out.print(car + "  ");
        System.out.println();
        System.out.println();

        for (int i = 0; i < tableau.length -1; i++) {

            //affiche le numéro de la ligne
            if(i<9){System.out.print((i+1) + "  " );}
            else{System.out.print((i+1)+" ");}
            for (Case c : tableau[i]) {
             
                if (c.getPiece() instanceof Animal){
                    Animal a = (Animal)c.getPiece();
                    if (!a.existe){System.out.print("   ");}
                    else System.out.print("An ");
                }
                else if (c.getPiece() instanceof Ballon){
                    Ballon b = (Ballon)c.getPiece();
                    if (!b.existe){System.out.print("   ");}
                    else if (b.getCouleur() == BlocCouleur.Couleur.ROUGE) {
                        System.out.print("bR ");
                    } else if (b.getCouleur() == BlocCouleur.Couleur.BLEU) {
                        System.out.print("bB ");
                    } else if (b.getCouleur() == BlocCouleur.Couleur.VERT) {
                        System.out.print("bV ");
                    } else if (b.getCouleur() == BlocCouleur.Couleur.JAUNE) {
                        System.out.print("bJ ");
                    } else if (b.getCouleur() == BlocCouleur.Couleur.VIOLET) {
                        System.out.print("bVi ");
                    }
                }
                else if (c.getPiece() instanceof Bombe){
                    Bombe b = (Bombe)c.getPiece();
                    if(!b.existe){System.out.print("   ");}
                    else System.out.print("Bo ");
                }
                else if (c.getPiece() instanceof BlocBois){System.out.print("X  ");}
                else if (c.getPiece() instanceof BlocCouleur) {

                    //pour que la forme du plateau reste la même malgré les nouveaux blocs qui tombent dans le niveau
                    if(niveau.numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==1
                        || niveau.numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==2
                        || niveau.numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==3
                        || niveau.numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==7
                        || niveau.numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==8
                        || niveau.numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==9
                        || niveau.numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==1
                        || niveau.numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==2
                        || niveau.numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==8
                        || niveau.numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==9
                        || niveau.numero==4 && c.getPiece().getX()==3 && c.getPiece().getY()==1
                        || niveau.numero==4 && c.getPiece().getX()==3 && c.getPiece().getY()==9
                        || niveau.numero==3 && c.getPiece().getX()==1 && c.getPiece().getY()==2
                        || niveau.numero==3 && c.getPiece().getX()==1 && c.getPiece().getY()==5
                        ){
                        System.out.print("   ");
                    }

                    else{
                        BlocCouleur b = (BlocCouleur) c.getPiece();
                        if (b.existe) {
                            if(b.fortifie){
                                System.out.print("#  ");
                            }
                            else{
                                if (b.getCouleur() == BlocCouleur.Couleur.ROUGE) {
                                    System.out.print("R  ");
                                } else if (b.getCouleur() == BlocCouleur.Couleur.BLEU) {
                                    System.out.print("B  ");
                                } else if (b.getCouleur() == BlocCouleur.Couleur.VERT) {
                                    System.out.print("V  ");
                                } else if (b.getCouleur() == BlocCouleur.Couleur.JAUNE) {
                                    System.out.print("J  ");
                                } else if (b.getCouleur() == BlocCouleur.Couleur.VIOLET) {
                                    System.out.print("Vi ");
                                }
                            }
                        }
                        if(!b.existe){System.out.print("   ");}
                    }
                }
                else {
                    System.out.print("   ");
                }  
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    /**
     * Demande au joueur la case qu'il souhaite toucher,
     * puis récupère la case et la renvoie si c'est un BlocCouleur ou un BonusPlateau
     * @return le bloc ou le bonus touché
     */
    public PieceDeplacable caseTouche(){
        boolean mauvaiseCommande = false;
        String input;
        Scanner sc = new Scanner(System.in);
        System.out.println("Vous écrirez les cases dans le format LettreChiffre");
        do
        {
            mauvaiseCommande=false;
            System.out.print("Choix de la case : ");
            input = sc.next();
            System.out.println();
            System.out.println(input.length());
            if(input.length()!=2 && input.length()!=3)
            {System.out.println("Mauvaise commande, veuillez choisir une case valide"); mauvaiseCommande = true;}
        }while(mauvaiseCommande);
        char lettre = input.charAt(0);
        int j = (int)lettre-64;
        int i=0;
        if(input.length()==3){
            String chiffre = ""+input.charAt(1)+input.charAt(2);
            i = Integer.valueOf(chiffre);
        }
        else if(input.length()==2){
            char chiffre = input.charAt(1);
            i = Character.getNumericValue(chiffre);
        }
        if(i<0 || i>niveau.getPlateau().getTableau().length-1 || j<0 || j>niveau.getPlateau().getTableau()[0].length-2){
            System.out.println("Case en dehors du plateau"); return null;
        }
        Case c = niveau.getPlateau().getCase(i-1, j);
        if(c.getPiece() instanceof BlocBois || c.getPiece() instanceof Animal){
            return null;
        }
        if(c.getPiece() instanceof BlocCouleur){
            BlocCouleur bc = (BlocCouleur)c.getPiece();
            return bc;
        }
        if(c.getPiece() instanceof BonusPlateau){
            BonusPlateau b = (BonusPlateau)c.getPiece();
            return b;
        }
        return null;
    }

    /**
     * Demande au joueur l'action qu'il souhaite faire dans le niveau :
     * utiliser l'aide, toucher une case, utiliser un bonus ou quitter
     * @return la lettre correspondant à l'action
     */
    public static String choixAction(){
        boolean mauvaiseCommande = false;
        Scanner sc = new Scanner(System.in);
        String input;
        do
        {
            mauvaiseCommande=false;
            System.out.println("Quelle action voulez-vous faire?");
            System.out.println("a = aide - c = toucher une case - b = utiliser un bonus - q = quitter ");
            input = sc.next();
            System.out.println();
            if(input.length()!=1 && !input.equals("q") && !input.equals("c") && !input.equals("b"))
            {System.out.println("Mauvaise commande, veuillez choisir une action valide"); mauvaiseCommande = true;}
        }while(mauvaiseCommande);
        return input;
    }

    /**
     * Demande au joueur le niveau de la démo qu'il souhaite voir
     * @return le numéro du niveau
     */
    public static String choixNiveauDemo(){
        boolean mauvaiseCommande = false;
        Scanner sc = new Scanner(System.in);
        String input;
        do
        {
            mauvaiseCommande=false;
            System.out.println("De quel niveau voulez-vous voir la démo?");
            System.out.println("1 = niveau 1 - 2 = niveau 2 - 3 = niveau 3 - 4 = niveau 4 - 5 = niveau 5 ");
            input = sc.next();
            System.out.println();
            if(input.length()!=1 && !input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5"))
            {System.out.println("Mauvaise commande, veuillez choisir un niveau valide"); mauvaiseCommande = true;}
        } while(mauvaiseCommande);
        return input;
    }

    /**
     * @return le niveau correspondant au choix du joueur
     */
    public Niveau demo(){
        demo = true;
        String choixNiveauDemo = choixNiveauDemo();
        Niveau niveau;
        switch (choixNiveauDemo) {
            case "1":
               niveau = Niveau.getNiveau1();
                break;
        
            case "2":
               niveau = Niveau.getNiveau2();
                break;

            case "3":
               niveau = Niveau.getNiveau3();
                break;

            case "4":
               niveau = Niveau.getNiveau4();
                break;
            
            default:
               niveau = Niveau.getNiveau5();
                break;
        }
        return niveau;
    }

    /**
     * Effectue la démo : le robot effectue une action jusqu'à ce que le niveau soit terminé
     * @param niveau le niveau que la démo effectue
     */
    private void lancerDemo(Niveau niveau){
        TimerTask repeatedTask;
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 2000L;

        System.out.println("Score : "+niveau.scoreNiveau.valeur+" , Déplacements : "+nbDeplacements+" , Animaux : "+nbAnimauxSauves+"/"+niveau.nbAnimaux);
        System.out.println();
        afficherPlateau();

        repeatedTask = new TimerTask() {
            public void run() {
                
                actionDemo(niveau);
                if(niveauGagne(niveau) || niveauPerdu(niveau) )
                { try {
                    Thread.sleep(2*1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cancel();
                    if(niveauGagne(niveau)){System.out.println("Niveau gagné !");}
                    else if(niveauPerdu(niveau)){System.out.println("Niveau perdu...");}
                    System.out.println();
                    quitterLeNiveau();
                    quitterLaPartie();
                }
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }

    /**
     * Effectue une action de la démo : 
     * récupère la pièce à jouer, fait son action, supprime les blocs, réorganise le plateau
     * @param niveau le niveau de la démo
     */
    private void actionDemo(Niveau niveau){
        PieceDeplacable pi = niveau.getPlateau().aideSup(this, null);
        if(pi != null)
        {
            if(pi instanceof BlocCouleur)
            {
                BlocCouleur bc = (BlocCouleur)pi;
                if(!bc.fortifie)
                {
                    bc.viderListeASupprimer();
                    bc.blocsASupprimer(niveau.getPlateau());
                    bc.supprimer(niveau.getPlateau());
                    int nb = bc.nbBlocsSupprimes();
                    niveau.scoreNiveau.blocsDetruits(nb);
                    this.fusee.score.blocsDetruits(nb);
                    this.nbDeplacements++;
                    bc.viderListeASupprimer();
                }
            }
            else if(pi instanceof BonusPlateau){
                if( pi instanceof Ballon){
                    Ballon ba = (Ballon)pi;
                    int nb = ba.action(niveau.getPlateau());
                    niveau.scoreNiveau.ballonOuFusee(nb);
                    this.fusee.score.ballonOuFusee(nb);
                    this.nbDeplacements++;
                }
                else if(pi instanceof Bombe){
                    Bombe bo = (Bombe)pi;
                    bo.action(niveau.getPlateau());
                    niveau.scoreNiveau.bombe();
                    this.fusee.score.bombe();
                    this.nbDeplacements++;
                }
            }

        }
        if(niveau.getNumero() == 3 || niveau.getNumero() == 4 || niveau.getNumero() == 5)
        {
            niveau.getPlateau().reorganisation();
            niveau.refillIfPieceDoesNotExist(); 
        }                    
        for (int i=0;i<3;i++){
            niveau.getPlateau().reorganisation();
            int nbAn = sauvetage();
            nbAnimauxSauves+=nbAn;

        }
        System.out.println("Score : "+niveau.scoreNiveau.valeur+" , Déplacements : "+nbDeplacements+" , Animaux : "+nbAnimauxSauves+"/"+niveau.nbAnimaux);
        System.out.println();
        niveau.getPlateau().reorganisation();
        afficherPlateau();
    }

    /**
     * Effectue les tests pour savoir si le niveau est gagné
     * @param niveau le niveau en cours
     * @return true si le niveau gagné
     */
    private boolean niveauGagne(Niveau niveau){
        if( niveau.getPlateau().animauxTousSauves() && niveau.scoreAAtteindre <= niveau.scoreNiveau.valeur)
        {
            return true;
        }
        return false;
    }
       
    /**
     * Effectue les tests pour savoir si le niveau est perdu
     * @param niveau le niveau en cours
     * @return true si le niveau est perdu
     */
    private boolean niveauPerdu(Niveau niveau){
        if((!niveau.getPlateau().animauxTousSauves() && !niveau.getPlateau().ilResteDesDeplacements() && !this.getFusee().estAccessible() && !this.getJoueur().ilResteDesBonusJoueur()) || (nbDeplacements >= niveau.nbDeplacement && niveau.nbDeplacement!=-1) || (!niveau.getPlateau().ilResteDesDeplacements() && niveau.scoreAAtteindre > niveau.scoreNiveau.valeur ) || (!niveau.getPlateau().animauxTousSauves() && !niveau.getPlateau().ilResteDesDeplacements() )){
            return true;
        }
        return false;
    }

    /**
     * Le joueur quitte le niveau
     */
    public static void quitterLeNiveau(){
        System.out.println("Niveau quitté");
        quitterLeNiveau=true;
    }

    /**
     * Le joueur quitte la partie
     */
    public static void quitterLaPartie(){
        System.out.println("Partie quittée");
        quitterLaPartie=true;
        System.exit(0);
    }

    /**
     * Demande au joueur le bonus qu'il souhaite utiliser,
     * puis effectue son action
     * @return le nombre d'animaux sauvés si il a utilisé la fusée, 0 sinon
     */
    public int utiliserBonus(){
        String input;
        boolean mauvaiseCommande=false;
        Scanner sc = new Scanner(System.in);
        do
        {
            mauvaiseCommande=false;
            System.out.println("Quel bonus voulez-vous utiliser ?");
            System.out.println("f = fusee - b = brise-bloc - t = tenaille");
            input = sc.next();
            System.out.println();
            if(input.length()!=1 && !input.equals("f") && !input.equals("b") && !input.equals("t"))
            {System.out.println("Mauvaise commande, veuillez choisir un bonus valide"); mauvaiseCommande = true;}
        }while(mauvaiseCommande);

        //fusée
        if(input.equals("f")){
            if(!fusee.estAccessible()){System.out.println("Il vous manque "+ (2000-fusee.score.valeur) +" points pour accéder à la fusée"); System.out.println();}
            else{
                String colonne;
                int j;
                System.out.println("Sur quelle colonne voulez-vous envoyer la fusee?");
                Scanner s = new Scanner(System.in);
                do
                {
                    mauvaiseCommande=false;
                    System.out.print("Choix de la colonne : ");
                    colonne = s.next();
                    char lettre = colonne.charAt(0);
                    j = (int)lettre-64;
                    if(j>niveau.getPlateau().getTableau()[0].length || j<1){
                        System.out.println("Mauvais numero de colonne"); mauvaiseCommande=true;
                    }
                }while(mauvaiseCommande);
                return fusee.action(niveau.scoreNiveau, j, niveau.getPlateau());
                
            }
        }

        //brise-bloc
        else if(input.equals("b")){
            if(joueur.getNbBriseBloc()<=0){System.out.println("Vous n'avez pas de bonus Brise-bloc"); System.out.println();}
            else{
                System.out.println("Sur quelle case voulez-vous utiliser le brise-bloc ?");
                System.out.println("Vous ecrirez les cases dans le format LettreChiffre");
                System.out.println();
                Scanner s = new Scanner(System.in);
                do
                {
                    mauvaiseCommande=false;
                    System.out.print("Choix de la case : ");
                    input = sc.next();
                    System.out.println();
                    if(input.length()!=2 && input.length()!=3){
                    System.out.println("Mauvaise commande, veuillez choisir une case valide"); mauvaiseCommande = true;
                    }
                }while(mauvaiseCommande);
                char lettre = input.charAt(0);
                int j = (int)lettre-64;
                int i=0;
                if(input.length()==3){
                    String chiffre = ""+input.charAt(1)+input.charAt(2);
                    i = Integer.valueOf(chiffre);
                }
                else if(input.length()==2){
                    char chiffre = input.charAt(1);
                    i = Character.getNumericValue(chiffre);
                }
                if(i<0 || i>niveau.getPlateau().getTableau().length-1 || j<0 || j>niveau.getPlateau().getTableau()[0].length-2){
                    System.out.println("Case en dehors du plateau");
                }
                else{ 
                    Case c = niveau.getPlateau().getCase(i-1, j);
                    if(!(c.getPiece() instanceof BlocCouleur)){System.out.println("Ce n'est pas un bloc de couleur"); System.out.println();}
                    else BriseBloc.action(niveau.scoreNiveau, joueur, c);
                }
            }
        }

        //tenaille
        else if(input.equals("t")){
            if(joueur.getNbTenaille()<=0){
                System.out.println("Vous n'avez pas de bonus Tenaille");
                System.out.println();
            }
            else{
                Tenaille.action(niveau.getPlateau(), joueur);
            }
        }
        return 0;
    }

    /**
     * Cherche si il y a des animaux arrivés en bas du plateau donc sauvés
     * @return le nombre d'animaux sauvés
     */
    public int sauvetage(){
        int nb = 0;
        Case[][] tableau = niveau.getPlateau().getTableau();
        for (int i=0;i<tableau.length;i++){
            for (int j=0;j<tableau[0].length;j++){
                if(tableau[i][j].getPiece() instanceof Animal){
                    Animal a = (Animal)tableau[i][j].getPiece();
                    if(a.sauvetage(niveau, joueur)){nb++;}
                }
            }
        }
        return nb;
    }

    /**
     * Quand le joueur perd le niveau, propose de le recommencer, de voir les autres niveaux disponible, ou de quitter la partie
     */
    public void recommencer(){
        System.out.println("Que voulez-vous faire ?");
        System.out.println("r = recommencer - a = autres niveaux - q = quitter");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.next();
        System.out.println();
        if(reponse.equals("r")){
            continuer = true;
            if(niveau.numero==1){niveauEnCours=Niveau.getNiveau1();}
            else if(niveau.numero==2){niveauEnCours=Niveau.getNiveau2();}
            else if(niveau.numero==3){niveauEnCours=Niveau.getNiveau3();}
            else if(niveau.numero==4){niveauEnCours=Niveau.getNiveau4();}
            else if(niveau.numero==5){niveauEnCours=Niveau.getNiveau5();}
        }
        else if(reponse.equals("q")) quitterLaPartie();
    }

    /**
     * Quand le joueur gagne le niveau, propose de passer au suivant, de voir les autres niveaux disponibles ou de quitter
     */
    public void prochainNiveau(){
        if(niveau.numero==1){joueur.getEnvironnement().niveaux.get(1).bloque=false;}
        else if(niveau.numero==2){joueur.getEnvironnement().niveaux.get(2).bloque=false;}
        else if(niveau.numero==3){joueur.getEnvironnement().niveaux.get(3).bloque=false;}
        else if(niveau.numero==4){joueur.getEnvironnement().niveaux.get(4).bloque=false;}
        Joueur.createJoueur(joueur);
        System.out.println("Voulez-vous continuer ?");
        System.out.println("p = prochain niveau - a = autres niveaux - q = quitter ");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.next();
        System.out.println();
        if(reponse.equals("q")){quitterLaPartie();}
        else if (reponse.equals("p")){
            continuer=true;
            if(niveau.numero==1){
                niveauEnCours=Niveau.getNiveau2();
            }
            else if(niveau.numero==2){
                niveauEnCours=Niveau.getNiveau3();
            }
            
            else if(niveau.numero==3){
                niveauEnCours=Niveau.getNiveau4();
            }
            else if(niveau.numero==4){
                niveauEnCours=Niveau.getNiveau5();
            }
            else if(niveau.numero==5){
                niveauEnCours=Niveau.getNiveau5();
            }
        }
    }

    /**
     * Si le nombre de déplacements n'est pas illimité dans un niveau, test si le joueur les a tous utilisés
     * @return true si il reste des déplacements
     */
    public boolean ilResteDesDeplacements(){
        if(niveau.nbDeplacement==-1){return true;} //si c'est illimité
        else if (niveau.nbDeplacement>nbDeplacements){return true;}
        return false;
    }

    /**
     * Si il y a un score à atteindre dans un niveau, test si le joueur l'a atteint
     * @return true si le score est atteint
     */
    public boolean scoreAtteint(){
        if(niveau.scoreAAtteindre==-1){return true;} //si il n'y en a pas
        else if(niveau.scoreAAtteindre>niveau.scoreNiveau.valeur){return false;}
        else return true;
    }

    /**
     * Le main du programme :
     * effectue une partie
     * @param args
     */
    public static void main(String[] args){

        //creation niveaux
        Niveau.createNiveau1();
        Niveau.createNiveau2();
        Niveau.createNiveau3();
        Niveau.createNiveau4();
        Niveau.createNiveau5();
        
        Joueur joueur = creationJoueur();

        String choixVue = choixVue();
        if(choixVue.equals("q")){quitterLaPartie();}
        else if(choixVue.equals("i")){
            Partie p = new Partie(null, joueur);
            Vue vue = new Vue(p);
        }
        else{
            //faire une partie
            do{
                String action = choixEnvironnement();
                if(action.equals("q")){quitterLaPartie(); return;}
                else{
                    while(!action.equals("j") && !action.equals("d")){
                        if(action.equals("q")){quitterLaPartie(); return;}
                        else if (action.equals("b")){acheterBonus(joueur);}
                        else if(action.equals("r")){afficherRègleDuJeu();}
    
                        action = choixEnvironnement();
                    }
                }
            
                Partie p = new Partie(null, joueur);
                if(action.equals("d")){
                    Niveau niveau = p.demo();
                    p=new Partie(niveau, joueur);
                    p.lancerDemo(niveau);
                    return;
                }
            
                else if(continuer){
                    continuer=false;
                    p = new Partie(niveauEnCours, joueur);
                    
                }
                else{
                    p.afficherEnvironnement();
                    System.out.println();
                    Niveau niveau = choixNiveau(joueur);
                    p = new Partie(niveau, joueur);
                }

                Niveau niveau=p.getNiveau();
                
                p.afficherPlateau();
                String s = "";
                if(p.niveau.scoreAAtteindre!=-1){s="/"+p.niveau.scoreAAtteindre;}
                String d = "";
                if(p.niveau.nbDeplacement!=-1){d="/"+p.niveau.nbDeplacement;}

                System.out.println("Score : "+niveau.scoreNiveau.valeur+s+" , Déplacements : "+p.nbDeplacements+d+" , Animaux : "+p.nbAnimauxSauves+"/"+p.niveau.nbAnimaux);
                System.out.println();
        

                //faire un coup
                do
                {
                    String choix = choixAction();
                    if(choix.equals("q")){quitterLeNiveau();}
                    else{
                        //utiliser un bonus
                        if(choix.equals("b")){
                            int nbAn = p.utiliserBonus();
                            p.nbDeplacements++;
                            if(nbAn!=0){
                                for(int i=0; i<nbAn;i++){System.out.println("animal sauvé !");}
                                p.fusee.score.valeur+=nbAn*1000;
                                p.nbAnimauxSauves+=nbAn;
                                niveau.scoreNiveau.animalSauve();
                            }
                            System.out.println();
                        }
                        //toucher une case
                        else if(choix.equals("c")){
                            PieceDeplacable pi = p.caseTouche();
                            if(pi instanceof BlocCouleur){
                                BlocCouleur bc = (BlocCouleur)pi;
                                if(!bc.fortifie){
                                    if(bc.caseToucheePasSeule(p.niveau.getPlateau())) p.nbDeplacements++;
                                    bc.viderListeASupprimer();
                                    bc.blocsASupprimer(niveau.getPlateau());
                                    bc.supprimer(niveau.getPlateau());
                                    int nb = bc.nbBlocsSupprimes();
                                    niveau.scoreNiveau.blocsDetruits(nb);
                                    p.fusee.score.blocsDetruits(nb);
                                    bc.viderListeASupprimer();
                                    
                                }
                            }
                            if(pi instanceof BonusPlateau){
                                if(pi instanceof Bombe){
                                    Bombe bombe = (Bombe) pi;
                                    bombe.action(niveau.getPlateau());
                                    niveau.scoreNiveau.bombe();
                                    p.fusee.score.bombe();
                                    p.nbDeplacements++;
                                }
                                if(pi instanceof Ballon){
                                    Ballon ballon = (Ballon) pi;
                                    int nb = ballon.action(niveau.getPlateau());
                                    niveau.scoreNiveau.ballonOuFusee(nb);
                                    p.fusee.score.ballonOuFusee(nb);
                                    p.nbDeplacements++;
                                }
                            }
                        }
                        //demander l'aide
                        else if(choix.equals("a")){
                            if(p.niveau.getPlateau().ilResteDesBlocsADetruire()){
                                PieceDeplacable pi = p.niveau.getPlateau().aideSup(p,null);
                                if(p != null) //si aideSup n'utilise pas de Bonus
                                {
                                    if(pi instanceof BlocCouleur){
                                        BlocCouleur bc = (BlocCouleur)pi;
                                        if(!bc.fortifie){
                                            bc.viderListeASupprimer();
                                            bc.blocsASupprimer(niveau.getPlateau());
                                            bc.supprimer(niveau.getPlateau());
                                            int nb = bc.nbBlocsSupprimes();
                                            niveau.scoreNiveau.blocsDetruits(nb);
                                            p.fusee.score.blocsDetruits(nb);
                                            p.nbDeplacements++;
                                            bc.viderListeASupprimer();
                                        }
                                    }
                                    else if(pi instanceof BonusPlateau){
                                        if( pi instanceof Ballon){
                                            Ballon ba = (Ballon)pi;
                                            int nb = ba.action(niveau.getPlateau());
                                            niveau.scoreNiveau.ballonOuFusee(nb);
                                            p.fusee.score.ballonOuFusee(nb);
                                            p.nbDeplacements++;
                                        }
                                        else if(pi instanceof Bombe){
                                            Bombe bo = (Bombe)pi;
                                            bo.action(niveau.getPlateau());
                                            niveau.scoreNiveau.bombe();
                                            p.fusee.score.bombe();
                                            p.nbDeplacements++;
                                        }
                                    }
                                }
                                
                            }
                            else{ 
                                System.out.println("Il n'y a plus de blocs destructibles, partie perdue...");
                                quitterLeNiveau();
                            }
                        }
                       

                        if(p.niveau.getNumero() == 3 || p.niveau.getNumero() == 4 || p.niveau.getNumero() == 5)
                        {
                            p.niveau.getPlateau().reorganisation();
                            p.niveau.refillIfPieceDoesNotExist(); 
                        }
                                    
                        p.niveau.getPlateau().reorganisation();
                        for (int i=0;i<3;i++){
                            p.niveau.getPlateau().reorganisation();
                            int nbAn = p.sauvetage();
                            if(nbAn!=0){
                                for(int j=0; j<nbAn;j++){System.out.println("animal sauvé !");}
                                p.fusee.score.valeur+=nbAn*1000;
                                p.nbAnimauxSauves+=nbAn;
                            }
                        }
                        System.out.println("Score : "+niveau.scoreNiveau.valeur+s+" , Déplacements : "+p.nbDeplacements+d+" , Animaux : "+p.nbAnimauxSauves+"/"+p.niveau.nbAnimaux);
                        System.out.println();
                        p.niveau.getPlateau().reorganisation();
                        p.afficherPlateau();
                    }
                } while(p.niveau.getPlateau().ilResteDesBlocs() && !p.niveau.getPlateau().animauxTousSauves() && !quitterLeNiveau && (p.niveau.getPlateau().ilResteDesDeplacements() || p.fusee.estAccessible() || joueur.ilResteDesBonusJoueur()) && p.ilResteDesDeplacements() || ((p.niveau.getPlateau().ilResteDesDeplacements() || p.fusee.estAccessible() || joueur.ilResteDesBonusJoueur()) && p.getNiveau().getPlateau().ilResteDesBlocs() && !quitterLeNiveau && !p.scoreAtteint()));
                
                quitterLeNiveau=false;

                //niveau gagné
                if(p.niveauGagne(niveau)){
                    if(niveau.numero==5 && joueur.getScoreNiveaux()[4]==0){
                        System.out.println("Niveau gagné !\nBravo! Vous avez fini le jeu!");
                        joueur.getScoreNiveaux()[niveau.numero-1]=niveau.scoreNiveau.valeur;
                    }
                    else if(niveau.scoreNiveau.valeur>joueur.getScoreNiveaux()[niveau.numero-1]){
                        System.out.println("Niveau gagné!\nMeilleur score battu!");
                        joueur.getScoreNiveaux()[niveau.numero-1]=niveau.scoreNiveau.valeur;
                    }
                    else {System.out.println("Niveau gagné !");}
                    System.out.println("Score : "+niveau.scoreNiveau.valeur);
                    System.out.println();
                    joueur.getScore().partieGagnee();
                    System.out.println("Score : "+niveau.scoreNiveau.valeur+s+" , Déplacements : "+p.nbDeplacements+d+" , Animaux : "+p.nbAnimauxSauves+"/"+p.niveau.nbAnimaux);
                    p.prochainNiveau();
                    Joueur.createJoueur(joueur);
                }

                //niveau perdu
                else{
                    if(niveau.numero==3){System.out.println("Niveau perdu... Pensez à utiliser le Brise-Bloc !");}
                    else if (niveau.numero==4 || niveau.numero==5){System.out.println("Niveau perdu... Pensez à utiliser les bonus !");}
                    else {System.out.println("Niveau perdu...");}
                    System.out.println("Score : "+niveau.scoreNiveau.valeur+s+" , Déplacements : "+p.nbDeplacements+d+" , Animaux : "+p.nbAnimauxSauves+"/"+p.niveau.nbAnimaux);
                    System.out.println();
                    joueur.getScore().partiePerdue(p.nbAnimauxSauves);
                    System.out.println("Score general : "+ joueur.getScore().valeur);
                    Joueur.createJoueur(joueur);
                    p.recommencer();
                }
            }while(!quitterLaPartie);
        }
    }
}
