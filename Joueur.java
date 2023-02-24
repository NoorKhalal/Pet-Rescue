import java.io.*;
import java.util.LinkedList;

/**
 * Le Joueur possède un Score, un Environnement
 */
public class Joueur implements Serializable{
    
    private String pseudo;
    private Score scoreGeneral;
    private int nbBriseBloc; 
    private int nbTenaille;
    private Environnement environnement;
    private int[] scoreNiveaux; 

    //constructeur
    public Joueur(String p){
        pseudo = p;
        scoreGeneral = new Score();
        nbBriseBloc=0; nbTenaille=0;
        environnement= new Environnement();
        scoreNiveaux = new int[5];
    }

    //getters
    public String getPseudo(){return pseudo;}
    public Score getScore(){return scoreGeneral;}
    public int getNbBriseBloc(){return nbBriseBloc;}
    public int getNbTenaille(){return nbTenaille;}
    public Environnement getEnvironnement(){return environnement;}
    public int[] getScoreNiveaux(){return scoreNiveaux;}

    public int getNiveauxGagnes(){
        int nb=0;
        for(Niveau n : environnement.niveaux){
            if (!n.bloque){nb++;}
        }
        if(environnement.niveaux.get(4).bloque==false){return 5;}
        return nb-1;
    }

    /**
     * Le joueur et ses données sont envoyer dans un fichier .ser pour pouvoir récupérer sa sauvegarde 
     * @param j le joueur
     */
    public static void createJoueur(Joueur j){
        try{
            FileOutputStream fileOut = new FileOutputStream(j.pseudo+".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(j);
            out.close();
            fileOut.close();
        } catch(IOException e){e.printStackTrace();System.out.println("probleme serialization");}
    }

    /**
     * Le joueur dans le fichier .ser est récupéré grace à son pseudo
     * @param p le pseudo
     * @return le joueur
     */
    public static Joueur getJoueur(String p){
        Joueur joueur = null;
        try{
            FileInputStream fileIn = new FileInputStream(p+".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            joueur = (Joueur) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ie){ie.printStackTrace(); System.out.println("probleme deserialization");
        }catch(ClassNotFoundException ce){ce.printStackTrace(); System.out.println("pas de fichier");}
        return joueur;
    }
    
    /**
     * Le joueur achète un bonus BriseBloc si il a assez de points
     * @return true si l'achat est effectué
     */
    public boolean acheterBriseBloc(){
        if (scoreGeneral.valeur>= BriseBloc.PRIX){
            scoreGeneral.valeur -= BriseBloc.PRIX;
            nbBriseBloc+=1;
            createJoueur(this);
            return true;
        }
        return false;
    }

    /**
     * Le joueur achète un bonus Tenaille si il a assez de points
     * @return true si l'achat est effectué
     */
    public boolean acheterTenaille(){
        if (scoreGeneral.valeur>= Tenaille.PRIX){
            scoreGeneral.valeur -= Tenaille.PRIX;
            nbTenaille+=1;
            createJoueur(this);
            return true;
        }
        return false;
    }

    /**
     * Le nombre de BriseBloc du joueur diminue après qu'il l'ai utilisé
     */
    public void diminuerBriseBloc(){nbBriseBloc--;}

    /**
     * Le nombre de Tenaille du joueur diminue après qu'il l'ai utilisé
     */
    public void diminuerTenaille(){nbTenaille--;}

    /**
     * Regarde si il reste des BriseBloc ou des Tenailles au joueur
     * @return true si il lui reste des bonus
     */
    public boolean ilResteDesBonusJoueur(){
        if (nbTenaille>0 || nbBriseBloc>0){return true;}
        return false;
    }

}
