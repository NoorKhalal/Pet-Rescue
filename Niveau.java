import java.io.*;
import java.util.LinkedList;

/**
 * Niveau contient les méthodes créant les 5 niveaux du jeu
 */
public class Niveau implements Serializable{
    
    protected int numero;
    protected int nbAnimaux;
    protected Score scoreNiveau = new Score();
    private Plateau plateau;
    protected boolean bloque; //false si le joueur reussi le niveau
    protected int nbDeplacement; //-1 si il n'y a pas limite
    protected final int scoreAAtteindre; //-1 si il n'y en a pas
    protected LinkedList<BlocCouleur> blocsQuiTombent;
    

    //constructeur
    public Niveau(int num, int nbA, Plateau p, int nbD, int scA, LinkedList<BlocCouleur> bT, boolean b){
        numero=num;
        nbAnimaux=nbA;
        scoreNiveau.valeur=0;
        plateau=p;
        nbDeplacement=nbD;   
        scoreAAtteindre=scA;
        blocsQuiTombent=bT;
        bloque=b;
    }

    //getters
    public Plateau getPlateau(){return plateau;}
    public int getNumero(){ return numero;}
    public boolean getBloque(){ return bloque;}

    /**
     * le niveau devient accessible
     */
    public void niveauGagne(){bloque=false;}

    /**
     * Création du niveau 1 : 
     * le niveau est sérialisé dans un fichier pour pouvoir le récupérer dans son état initial
     */
    public static void createNiveau1(){
        //construction du plateau
        BlocCouleur b1 = new BlocCouleur(1,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b2 = new BlocCouleur(1,2,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b3 = new BlocCouleur(1,3,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b4 = new BlocCouleur(1,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b5 = new BlocCouleur(1,5,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b6 = new BlocCouleur(1,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b7 = new BlocCouleur(1,7,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b8 = new BlocCouleur(2,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b9 = new BlocCouleur(2,2,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b10 = new BlocCouleur(2,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b11 = new BlocCouleur(2,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b12 = new BlocCouleur(2,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b13 = new BlocCouleur(2,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b14 = new BlocCouleur(2,7,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b15 = new BlocCouleur(3,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b16 = new BlocCouleur(3,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b17 = new BlocCouleur(3,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b18 = new BlocCouleur(3,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b19 = new BlocCouleur(3,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b20 = new BlocCouleur(3,6,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b21 = new BlocCouleur(3,7,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b22 = new BlocCouleur(4,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b23 = new BlocCouleur(4,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b24 = new BlocCouleur(4,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b25 = new BlocCouleur(4,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b26 = new BlocCouleur(4,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b27 = new BlocCouleur(4,6,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b28 = new BlocCouleur(4,7,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b29 = new BlocCouleur(5,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b30 = new BlocCouleur(5,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b31 = new BlocCouleur(5,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b32 = new BlocCouleur(5,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b33 = new BlocCouleur(5,5,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b34 = new BlocCouleur(5,6,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b35 = new BlocCouleur(5,7,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b36 = new BlocCouleur(6,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b37 = new BlocCouleur(6,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b38 = new BlocCouleur(6,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b39 = new BlocCouleur(6,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b40 = new BlocCouleur(6,5,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b41 = new BlocCouleur(6,6,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b42 = new BlocCouleur(6,7,BlocCouleur.Couleur.ROUGE,false);
        Animal a1 = new Animal(0,2,"chien");
        Animal a2 = new Animal(0,6,"chat");
        Piece pi = new Piece();
        Piece piD = new PieceDeplacable();

        Case c1 = new Case(b1);
        Case c2 = new Case(b2);
        Case c3 = new Case(b3);
        Case c4 = new Case(b4);
        Case c5 = new Case(b5);
        Case c6 = new Case(b6);
        Case c7 = new Case(b7);
        Case c8 = new Case(b8);
        Case c9 = new Case(b9);
        Case c10 = new Case(b10);
        Case c11 = new Case(b11);
        Case c12 = new Case(b12);
        Case c13 = new Case(b13);
        Case c14 = new Case(b14);
        Case c15 = new Case(b15);
        Case c16 = new Case(b16);
        Case c17 = new Case(b17);
        Case c18 = new Case(b18);
        Case c19 = new Case(b19);
        Case c20 = new Case(b20);
        Case c21 = new Case(b21);
        Case c22 = new Case(b22);
        Case c23 = new Case(b23);
        Case c24 = new Case(b24);
        Case c25 = new Case(b25);
        Case c26 = new Case(b26);
        Case c27 = new Case(b27);
        Case c28 = new Case(b28);
        Case c29 = new Case(b29);
        Case c30 = new Case(b30);
        Case c31 = new Case(b31);
        Case c32 = new Case(b32);
        Case c33 = new Case(b33);
        Case c34 = new Case(b34);
        Case c35 = new Case(b35);
        Case c36 = new Case(b36);
        Case c37 = new Case(b37);
        Case c38 = new Case(b38);
        Case c39 = new Case(b39);
        Case c40 = new Case(b40);
        Case c41 = new Case(b41);
        Case c42 = new Case(b42);
        Case c0 = new Case(pi);
        Case c00 = new Case(piD);
        Case c43 = new Case(a1);
        Case c44 = new Case(a2);

        Case[][] tab = {{c0,c00,c43,c00,c00,c00,c44,c00,c0},
                        {c0,c1, c2, c3, c4, c5, c6, c7, c0},
                        {c0,c8 ,c9 ,c10,c11,c12,c13,c14,c0},
                        {c0,c15,c16,c17,c18,c19,c20,c21,c0},
                        {c0,c22,c23,c24,c25,c26,c27,c28,c0},
                        {c0,c29,c30,c31,c32,c33,c34,c35,c0},
                        {c0,c36,c37,c38,c39,c40,c41,c42,c0},
                        {c0,c0, c0, c0, c0, c0, c0, c0, c0}};
        Plateau p = new Plateau(tab);

        //création du niveau
        Niveau niveau1 = new Niveau(1, 2, p, -1, -1, null,false);

        //envoyer le niveau dans un fichier
        try{
            FileOutputStream fileOut = new FileOutputStream("Niveau1.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(niveau1);
            out.close();
            fileOut.close();
        } catch(IOException e){e.printStackTrace();System.out.println("probleme serialization");}
    }

    /**
     * Recupère le niveau 1 présent dans le fichier .ser
     * @return le niveau 1
     */
    public static Niveau getNiveau1(){
        Niveau niveau1 = null;
        try{
            FileInputStream fileIn = new FileInputStream("Niveau1.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            niveau1 = (Niveau) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ie){ie.printStackTrace(); System.out.println("probleme deserialization");
        }catch(ClassNotFoundException ce){ce.printStackTrace(); System.out.println("pas de fichier");}
        return niveau1;
    }

    /**
     * Création du niveau 2 : 
     * le niveau est sérialisé dans un fichier pour pouvoir le récupérer dans son état initial
     */
    public static void createNiveau2(){
        //construction du plateau
        BlocCouleur b1 = new BlocCouleur(1,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b2 = new BlocCouleur(1,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b3 = new BlocCouleur(1,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b4 = new BlocCouleur(1,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b5 = new BlocCouleur(2,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b6 = new BlocCouleur(2,4,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b7 = new BlocCouleur(2,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b8 = new BlocCouleur(2,6,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b9 = new BlocCouleur(3,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b10 = new BlocCouleur(3,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b11 = new BlocCouleur(3,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b12 = new BlocCouleur(3,4,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b13 = new BlocCouleur(3,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b14 = new BlocCouleur(3,6,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b15 = new BlocCouleur(3,7,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b16 = new BlocCouleur(4,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b17 = new BlocCouleur(4,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b18 = new BlocCouleur(4,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b19 = new BlocCouleur(4,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b20 = new BlocCouleur(4,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b21 = new BlocCouleur(4,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b22 = new BlocCouleur(4,7,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b23 = new BlocCouleur(5,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b24 = new BlocCouleur(5,2,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b25 = new BlocCouleur(5,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b26 = new BlocCouleur(5,4,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b27 = new BlocCouleur(5,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b28 = new BlocCouleur(5,6,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b29 = new BlocCouleur(5,7,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b30 = new BlocCouleur(6,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b31 = new BlocCouleur(6,2,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b32 = new BlocCouleur(6,4,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b33 = new BlocCouleur(6,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b34 = new BlocCouleur(6,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b35 = new BlocCouleur(6,7,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b36 = new BlocCouleur(7,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b37 = new BlocCouleur(7,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b38 = new BlocCouleur(7,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b39 = new BlocCouleur(7,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b40 = new BlocCouleur(7,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b41 = new BlocCouleur(8,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b42 = new BlocCouleur(8,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b43 = new BlocCouleur(8,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b44 = new BlocCouleur(8,4,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b45 = new BlocCouleur(8,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b46 = new BlocCouleur(8,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b47 = new BlocCouleur(8,7,BlocCouleur.Couleur.ROUGE,false);
        Animal a1 = new Animal(2,2, "chien");
        Animal a2 = new Animal(2,7, "chien");
        Animal a3 = new Animal(7,1, "chat");
        Animal a4 = new Animal(7,7, "chat");
        Ballon b = new Ballon(6,3,BlocCouleur.Couleur.ROUGE);
        Piece pi = new Piece();
        Piece piD = new PieceDeplacable();

        Case c00 = new Case(piD);
        Case c0 = new Case(pi);
        Case c1 = new Case(b1);
        Case c2 = new Case(b2);
        Case c3 = new Case(b3);
        Case c4 = new Case(b4);
        Case c5 = new Case(b5);
        Case c6 = new Case(b6);
        Case c7 = new Case(b7);
        Case c8 = new Case(b8);
        Case c9 = new Case(b9);
        Case c10 = new Case(b10);
        Case c11 = new Case(b11);
        Case c12 = new Case(b12);
        Case c13 = new Case(b13);
        Case c14 = new Case(b14);
        Case c15 = new Case(b15);
        Case c16 = new Case(b16);
        Case c17 = new Case(b17);
        Case c18 = new Case(b18);
        Case c19 = new Case(b19);
        Case c20 = new Case(b20);
        Case c21 = new Case(b21);
        Case c22 = new Case(b22);
        Case c23 = new Case(b23);
        Case c24 = new Case(b24);
        Case c25 = new Case(b25);
        Case c26 = new Case(b26);
        Case c27 = new Case(b27);
        Case c28 = new Case(b28);
        Case c29 = new Case(b29);
        Case c30 = new Case(b30);
        Case c31 = new Case(b31);
        Case c32 = new Case(b32);
        Case c33 = new Case(b33);
        Case c34 = new Case(b34);
        Case c35 = new Case(b35);
        Case c36 = new Case(b36);
        Case c37 = new Case(b37);
        Case c38 = new Case(b38);
        Case c39 = new Case(b39);
        Case c40 = new Case(b40);
        Case c41 = new Case(b41);
        Case c42 = new Case(b42);
        Case c43 = new Case(b43);
        Case c44 = new Case(b44);
        Case c45 = new Case(b45);
        Case c46 = new Case(b46);
        Case c47 = new Case(b47);
        Case c48 = new Case(a1);
        Case c49 = new Case(a2);
        Case c50 = new Case(a3);
        Case c51 = new Case(a4);
        Case c52 = new Case(b);

        Case[][] tab = {{c0,c00,c00,c00,c00,c00,c00,c00, c0},
                        {c0,c00,c00,c1, c2, c3, c4, c00,c0},
                        {c0,c00,c48,c5 ,c6 ,c7 ,c8 ,c49,c0},
                        {c0,c9 ,c10,c11,c12,c13,c14,c15,c0},
                        {c0,c16,c17,c18,c19,c20,c21,c22,c0},
                        {c0,c23,c24,c25,c26,c27,c28,c29,c0},
                        {c0,c30,c31,c52,c32,c33,c34,c35,c0},
                        {c0,c50,c36,c37,c38,c39,c40,c51,c0},
                        {c0,c41,c42,c43,c44,c45,c46,c47,c0},
                        {c0,c0, c0, c0, c0, c0, c0, c0, c0}};

        Plateau p = new Plateau(tab);

        //création du niveau
        Niveau niveau2 = new Niveau(2, 4, p, -1, -1, null,true);

        //envoyer le niveau dans un fichier
        try{
            FileOutputStream fileOut = new FileOutputStream("Niveau2.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(niveau2);
            out.close();
            fileOut.close();
        } catch(IOException e){e.printStackTrace();System.out.println("probleme serialization");}
    }

    /**
     * Recupère le niveau 2 présent dans le fichier .ser
     * @return le niveau 2
     */
    public static Niveau getNiveau2(){
        Niveau niveau2 = null;
        try{
            FileInputStream fileIn = new FileInputStream("Niveau2.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            niveau2 = (Niveau) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ie){ie.printStackTrace(); System.out.println("probleme deserialization");
        }catch(ClassNotFoundException ce){ce.printStackTrace(); System.out.println("pas de fichier");}
        return niveau2;
    }

    /**
     * Création du niveau 3 : 
     * le niveau est sérialisé dans un fichier pour pouvoir le récupérer dans son état initial
     */
    public static void createNiveau3(){
        //création du plateau
        BlocCouleur b1 = new BlocCouleur(1,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b2 = new BlocCouleur(2,2,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b3 = new BlocCouleur(2,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b4 = new BlocCouleur(2,5,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b5 = new BlocCouleur(2,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b6 = new BlocCouleur(3,1,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b7 = new BlocCouleur(3,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b8 = new BlocCouleur(3,3,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b9 = new BlocCouleur(3,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b10 = new BlocCouleur(3,5,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b11 = new BlocCouleur(3,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b12 = new BlocCouleur(4,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b13 = new BlocCouleur(4,2,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b14 = new BlocCouleur(4,5,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b15 = new BlocCouleur(4,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b16 = new BlocCouleur(5,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b17 = new BlocCouleur(5,2,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b18 = new BlocCouleur(5,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b19 = new BlocCouleur(5,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b20 = new BlocCouleur(5,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b21 = new BlocCouleur(5,6,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b22 = new BlocCouleur(6,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b23 = new BlocCouleur(6,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b24 = new BlocCouleur(6,3,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b25 = new BlocCouleur(6,4,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b26 = new BlocCouleur(6,5,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b27 = new BlocCouleur(6,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b28 = new BlocCouleur(7,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b29 = new BlocCouleur(7,2,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b30 = new BlocCouleur(7,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b31 = new BlocCouleur(7,4,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b32 = new BlocCouleur(7,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b33 = new BlocCouleur(7,6,BlocCouleur.Couleur.VIOLET,false);
        Animal a1 = new Animal(1,1,"chien");
        Animal a2 = new Animal(1,6,"chat");
        Animal a3 = new Animal(4,3,"chien");
        Animal a4 = new Animal(4,4,"chat");
        Ballon b = new Ballon(2,1,BlocCouleur.Couleur.ROUGE);
        Bombe bo1 = new Bombe(1,3);
        Bombe bo2 = new Bombe(2,4);
        Piece pi = new Piece();
        Piece piD = new PieceDeplacable();

        Case c1 = new Case(b1);
        Case c2 = new Case(b2);
        Case c3 = new Case(b3);
        Case c4 = new Case(b4);
        Case c5 = new Case(b5);
        Case c6 = new Case(b6);
        Case c7 = new Case(b7);
        Case c8 = new Case(b8);
        Case c9 = new Case(b9);
        Case c10 = new Case(b10);
        Case c11 = new Case(b11);
        Case c12 = new Case(b12);
        Case c13 = new Case(b13);
        Case c14 = new Case(b14);
        Case c15 = new Case(b15);
        Case c16 = new Case(b16);
        Case c17 = new Case(b17);
        Case c18 = new Case(b18);
        Case c19 = new Case(b19);
        Case c20 = new Case(b20);
        Case c21 = new Case(b21);
        Case c22 = new Case(b22);
        Case c23 = new Case(b23);
        Case c24 = new Case(b24);
        Case c25 = new Case(b25);
        Case c26 = new Case(b26);
        Case c27 = new Case(b27);
        Case c28 = new Case(b28);
        Case c29 = new Case(b29);
        Case c30 = new Case(b30);
        Case c31 = new Case(b31);
        Case c32 = new Case(b32);
        Case c33 = new Case(b33);
        Case c0 = new Case(pi);
        Case c00 = new Case(piD);
        Case c34 = new Case(a1);
        Case c35 = new Case(a2);
        Case c36 = new Case(a3);
        Case c37 = new Case(a4);
        Case c38 = new Case(b);
        Case c39 = new Case(bo1);
        Case c40 = new Case(bo2);

        Case[][] tab = {{c0,c00,c00,c00,c00,c00,c00,c0},
                        {c0,c34,c00,c39,c1, c00,c35,c0},
                        {c0,c38,c2, c3, c40,c4, c5, c0},
                        {c0,c6 ,c7 ,c8 ,c9 ,c10,c11,c0},
                        {c0,c12,c13,c36,c37,c14,c15,c0},
                        {c0,c16,c17,c18,c19,c20,c21,c0},
                        {c0,c22,c23,c24,c25,c26,c27,c0},
                        {c0,c28,c29,c30,c31,c32,c33,c0},
                        {c0,c0, c0, c0, c0, c0, c0, c0}};
        Plateau p = new Plateau(tab);

        //blocs qui tombent 
        LinkedList<BlocCouleur> liste = new LinkedList<>();
        BlocCouleur bTombeBleu = new BlocCouleur(0,0,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur bTombeRouge = new BlocCouleur(0,0,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur bTombeVert = new BlocCouleur(0,0,BlocCouleur.Couleur.VERT,false);
        BlocCouleur bTombeJaune = new BlocCouleur(0,0,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur bTombeViolet = new BlocCouleur(0,0,BlocCouleur.Couleur.VIOLET,false);

        liste.add(new BlocCouleur(bTombeBleu));
        liste.add(new BlocCouleur(bTombeBleu));
        liste.add(new BlocCouleur(bTombeRouge));
        liste.add(new BlocCouleur(bTombeVert));
        liste.add(new BlocCouleur(bTombeViolet));
        liste.add(new BlocCouleur(bTombeVert));
        liste.add(new BlocCouleur(bTombeJaune));
        liste.add(new BlocCouleur(bTombeViolet));
        
        //création du niveau
        Niveau niveau3 = new Niveau(3, 4, p, 15, -1, liste,true);

        //envoyer le niveau dans un fichier
        try{
            FileOutputStream fileOut = new FileOutputStream("Niveau3.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(niveau3);
            out.close();
            fileOut.close();
        } catch(IOException e){e.printStackTrace();System.out.println("probleme serialization");}
    }

    /**
     * Recupère le niveau 3 présent dans le fichier .ser
     * @return le niveau 3
     */
    public static Niveau getNiveau3(){
        Niveau niveau3 = null;
        try{
            FileInputStream fileIn = new FileInputStream("Niveau3.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            niveau3 = (Niveau) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ie){ie.printStackTrace(); System.out.println("probleme deserialization");
        }catch(ClassNotFoundException ce){ce.printStackTrace(); System.out.println("pas de fichier");}
        return niveau3;
    }

    /**
     * Création du niveau 4 : 
     * le niveau est sérialisé dans un fichier pour pouvoir le récupérer dans son état initial
     */
    public static void createNiveau4(){
        //construction du plateau
        BlocBois b1 = new BlocBois(1, 5);
        BlocCouleur b2 = new BlocCouleur(1,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b3 = new BlocCouleur(2,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b4 = new BlocCouleur(2,4,BlocCouleur.Couleur.VIOLET,false);
        BlocBois b5 = new BlocBois(2, 5);
        BlocCouleur b6 = new BlocCouleur(2,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b7 = new BlocCouleur(2,7,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b8 = new BlocCouleur(3,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b9 = new BlocCouleur(3,4,BlocCouleur.Couleur.JAUNE,false);
        BlocBois b10 = new BlocBois(3, 5);
        BlocCouleur b11 = new BlocCouleur(3,6,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b12 = new BlocCouleur(3,7,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b13 = new BlocCouleur(3,8,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b14 = new BlocCouleur(4,2,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b15 = new BlocCouleur(4,3,BlocCouleur.Couleur.VIOLET,false);
        BlocCouleur b16 = new BlocCouleur(4,4,BlocCouleur.Couleur.VIOLET,false);
        BlocBois b17 = new BlocBois(4, 5);
        BlocCouleur b18 = new BlocCouleur(4,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b19 = new BlocCouleur(4,7,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b20 = new BlocCouleur(4,8,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b21 = new BlocCouleur(4,9,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b22 = new BlocCouleur(5,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b23 = new BlocCouleur(5,3,BlocCouleur.Couleur.VERT,false);
        BlocBois b24 = new BlocBois(5, 5);
        BlocCouleur b25 = new BlocCouleur(5,6,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b26 = new BlocCouleur(5,7,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b27 = new BlocCouleur(5,8,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b28 = new BlocCouleur(5,9,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b29 = new BlocCouleur(6,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b30 = new BlocCouleur(6,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b31 = new BlocCouleur(6,3,BlocCouleur.Couleur.BLEU,false);
        BlocBois b32 = new BlocBois(6, 4);
        BlocBois b33 = new BlocBois(6, 5);
        BlocCouleur b34 = new BlocCouleur(6,6,BlocCouleur.Couleur.JAUNE,true);
        BlocCouleur b35 = new BlocCouleur(6,7,BlocCouleur.Couleur.VERT,true);
        BlocCouleur b36 = new BlocCouleur(6,8,BlocCouleur.Couleur.VERT,true);
        BlocCouleur b37 = new BlocCouleur(6,9,BlocCouleur.Couleur.VERT,true);
        BlocCouleur b38 = new BlocCouleur(7,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b39 = new BlocCouleur(7,2,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b40 = new BlocCouleur(7,4,BlocCouleur.Couleur.VIOLET,false);
        BlocBois b41 = new BlocBois(7, 5);
        BlocCouleur b42 = new BlocCouleur(7,6,BlocCouleur.Couleur.VERT,true);
        BlocCouleur b43 = new BlocCouleur(7,7,BlocCouleur.Couleur.VERT,true);
        BlocCouleur b44 = new BlocCouleur(7,8,BlocCouleur.Couleur.BLEU,true);
        BlocCouleur b45 = new BlocCouleur(7,9,BlocCouleur.Couleur.VERT,true);
        BlocCouleur b46 = new BlocCouleur(8,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b47 = new BlocCouleur(8,3,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b48 = new BlocCouleur(8,4,BlocCouleur.Couleur.JAUNE,false);
        BlocBois b49 = new BlocBois(8, 5);
        BlocCouleur b50 = new BlocCouleur(8,6,BlocCouleur.Couleur.JAUNE,true);
        BlocCouleur b51 = new BlocCouleur(8,8,BlocCouleur.Couleur.BLEU,true);
        BlocCouleur b52 = new BlocCouleur(8,9,BlocCouleur.Couleur.BLEU,true);
        BlocCouleur b53 = new BlocCouleur(9,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b54 = new BlocCouleur(9,2,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b55 = new BlocCouleur(9,3,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b56 = new BlocCouleur(9,4,BlocCouleur.Couleur.VIOLET,false);
        BlocBois b57 = new BlocBois(9,5);
        BlocCouleur b58 = new BlocCouleur(9,6,BlocCouleur.Couleur.VIOLET,true);
        BlocCouleur b59 = new BlocCouleur(9,7,BlocCouleur.Couleur.BLEU,true);
        BlocCouleur b60 = new BlocCouleur(9,8,BlocCouleur.Couleur.BLEU,true);
        BlocCouleur b61 = new BlocCouleur(9,9,BlocCouleur.Couleur.JAUNE,true);
        Animal a1 = new Animal(1,4, "chien");
        Animal a2 = new Animal(3,3, "chat");
        Animal a3 = new Animal(4,1, "chien");
        Animal a4 = new Animal(5,2, "chat");
        Animal a5 = new Animal(5,4, "chien");
        Animal a6 = new Animal(7,3, "chat");
        Animal a7 = new Animal(8,8, "chien");
        Bombe b = new Bombe(8,2);
        Piece pi = new Piece();
        Piece piD = new PieceDeplacable();

        Case c00 = new Case(piD);
        Case c0 = new Case(pi);
        Case c1 = new Case(b1);
        Case c2 = new Case(b2);
        Case c3 = new Case(b3);
        Case c4 = new Case(b4);
        Case c5 = new Case(b5);
        Case c6 = new Case(b6);
        Case c7 = new Case(b7);
        Case c8 = new Case(b8);
        Case c9 = new Case(b9);
        Case c10 = new Case(b10);
        Case c11 = new Case(b11);
        Case c12 = new Case(b12);
        Case c13 = new Case(b13);
        Case c14 = new Case(b14);
        Case c15 = new Case(b15);
        Case c16 = new Case(b16);
        Case c17 = new Case(b17);
        Case c18 = new Case(b18);
        Case c19 = new Case(b19);
        Case c20 = new Case(b20);
        Case c21 = new Case(b21);
        Case c22 = new Case(b22);
        Case c23 = new Case(b23);
        Case c24 = new Case(b24);
        Case c25 = new Case(b25);
        Case c26 = new Case(b26);
        Case c27 = new Case(b27);
        Case c28 = new Case(b28);
        Case c29 = new Case(b29);
        Case c30 = new Case(b30);
        Case c31 = new Case(b31);
        Case c32 = new Case(b32);
        Case c33 = new Case(b33);
        Case c34 = new Case(b34);
        Case c35 = new Case(b35);
        Case c36 = new Case(b36);
        Case c37 = new Case(b37);
        Case c38 = new Case(b38);
        Case c39 = new Case(b39);
        Case c40 = new Case(b40);
        Case c41 = new Case(b41);
        Case c42 = new Case(b42);
        Case c43 = new Case(b43);
        Case c44 = new Case(b44);
        Case c45 = new Case(b45);
        Case c46 = new Case(b46);
        Case c47 = new Case(b47);
        Case c48 = new Case(b48);
        Case c49 = new Case(b49);
        Case c50 = new Case(b50);
        Case c51 = new Case(b51);
        Case c52 = new Case(b52);
        Case c53 = new Case(b53);
        Case c54 = new Case(b54);
        Case c55 = new Case(b55);
        Case c56 = new Case(b56);
        Case c57 = new Case(b57);
        Case c58 = new Case(b58);
        Case c59 = new Case(b59);
        Case c60 = new Case(b60);
        Case c61 = new Case(b61);
        Case c62 = new Case(a1);
        Case c63 = new Case(a2);
        Case c64 = new Case(a3);
        Case c65 = new Case(a4);
        Case c66 = new Case(a5);
        Case c67 = new Case(a6);
        Case c68 = new Case(a7);
        Case c69 = new Case(b);

        Case[][] tab = {{c0,c00,c00,c00,c00,c00,c00,c00,c00,c00,c0},
                        {c0,c00,c00,c00,c62,c1, c2, c00,c00,c00,c0},
                        {c0,c00,c00,c3 ,c4 ,c5 ,c6 ,c7 ,c00,c00,c0},
                        {c0,c00,c8 ,c63,c9 ,c10,c11,c12,c13,c00,c0},
                        {c0,c64,c14,c15,c16,c17,c18,c19,c20,c21,c0},
                        {c0,c22,c65,c23,c66,c24,c25,c26,c27,c28,c0},
                        {c0,c29,c30,c31,c32,c33,c34,c35,c36,c37,c0},
                        {c0,c38,c39,c67,c40,c41,c42,c43,c44,c45,c0},
                        {c0,c46,c69,c47,c48,c49,c50,c51,c68,c52,c0},
                        {c0,c53,c54,c55,c56,c57,c58,c59,c60,c61,c0},
                        {c0,c0, c0, c0, c0, c0, c0, c0, c0, c0, c0}};

        Plateau p = new Plateau(tab);

        //blocs qui tombent 
        LinkedList<BlocCouleur> liste = new LinkedList<>();
        BlocCouleur bTombeBleu = new BlocCouleur(0,0,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur bTombeRouge = new BlocCouleur(0,0,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur bTombeVert = new BlocCouleur(0,0,BlocCouleur.Couleur.VERT,false);
        BlocCouleur bTombeJaune = new BlocCouleur(0,0,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur bTombeViolet = new BlocCouleur(0,0,BlocCouleur.Couleur.VIOLET,false);
 
        liste.add(new BlocCouleur(bTombeViolet));
        liste.add(new BlocCouleur(bTombeRouge));
        liste.add(new BlocCouleur(bTombeRouge));
        liste.add(new BlocCouleur(bTombeVert));
        liste.add(new BlocCouleur(bTombeViolet));
        liste.add(new BlocCouleur(bTombeVert));
        liste.add(new BlocCouleur(bTombeJaune));
        liste.add(new BlocCouleur(bTombeBleu));
        liste.add(new BlocCouleur(bTombeBleu));
        
        //création du niveau
        Niveau niveau4 = new Niveau(4, 7, p, 20, 7000, liste,true);

        //envoyer le niveau dans un fichier
        try{
            FileOutputStream fileOut = new FileOutputStream("Niveau4.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(niveau4);
            out.close();
            fileOut.close();
        } catch(IOException e){e.printStackTrace();System.out.println("probleme serialization");}
    }

    /**
     * Recupère le niveau 4 présent dans le fichier .ser
     * @return le niveau 4
     */
    public static Niveau getNiveau4(){
        Niveau niveau4 = null;
        try{
            FileInputStream fileIn = new FileInputStream("Niveau4.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            niveau4 = (Niveau) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ie){ie.printStackTrace(); System.out.println("probleme deserialization");
        }catch(ClassNotFoundException ce){ce.printStackTrace(); System.out.println("pas de fichier");}
        return niveau4;
    }

    /**
     * Création du niveau 5 : 
     * le niveau est sérialisé dans un fichier pour pouvoir le récupérer dans son état initial
     */
    public static void createNiveau5(){
        //construction du plateau
        BlocCouleur b1 = new BlocCouleur(1,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b2 = new BlocCouleur(1,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b3 = new BlocCouleur(1,3,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b4 = new BlocCouleur(1,4,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b5 = new BlocCouleur(1,5,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b6 = new BlocCouleur(1,6,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b7 = new BlocCouleur(1,7,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b8 = new BlocCouleur(1,8,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b9 = new BlocCouleur(2,1,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b10 = new BlocCouleur(2,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b11 = new BlocCouleur(2,3,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b12 = new BlocCouleur(2,4,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b13 = new BlocCouleur(2,5,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b14 = new BlocCouleur(2,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b15 = new BlocCouleur(2,8,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b16 = new BlocCouleur(2,9,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b17 = new BlocCouleur(3,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b18 = new BlocCouleur(3,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b19 = new BlocCouleur(3,3,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b20 = new BlocCouleur(3,4,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b21 = new BlocCouleur(3,7,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b22 = new BlocCouleur(3,8,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b23 = new BlocCouleur(3,9,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b24 = new BlocCouleur(4,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b25 = new BlocCouleur(4,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b26 = new BlocCouleur(4,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b27 = new BlocCouleur(4,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b28 = new BlocCouleur(4,7,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b29 = new BlocCouleur(4,8,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b30 = new BlocCouleur(4,9,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b31 = new BlocCouleur(5,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b32 = new BlocCouleur(5,3,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b33 = new BlocCouleur(5,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b34 = new BlocCouleur(5,5,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b35 = new BlocCouleur(5,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b36 = new BlocCouleur(5,7,BlocCouleur.Couleur.ROUGE,false);
        BlocBois b37 = new BlocBois(5,8);
        BlocCouleur b38 = new BlocCouleur(6,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b39 = new BlocCouleur(6,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b40 = new BlocCouleur(6,3,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b41 = new BlocCouleur(6,4,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b42 = new BlocCouleur(6,5,BlocCouleur.Couleur.ROUGE,false);
        BlocBois b43 = new BlocBois(6,6);
        BlocCouleur b44 = new BlocCouleur(6,8,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b45 = new BlocCouleur(6,9,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b46 = new BlocCouleur(7,1,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b47 = new BlocCouleur(7,2,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b48 = new BlocCouleur(7,3,BlocCouleur.Couleur.JAUNE,false);
        BlocBois b49 = new BlocBois(7,4);
        BlocCouleur b50 = new BlocCouleur(7,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b51 = new BlocCouleur(7,7,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b52 = new BlocCouleur(7,8,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b53 = new BlocCouleur(7,9,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b54 = new BlocCouleur(8,1,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b55 = new BlocCouleur(8,2,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur b56 = new BlocCouleur(8,4,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b57 = new BlocCouleur(8,5,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b58 = new BlocCouleur(8,6,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b59 = new BlocCouleur(8,7,BlocCouleur.Couleur.VERT,false);
        BlocCouleur b60 = new BlocCouleur(8,8,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b61 = new BlocCouleur(8,9,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur b62 = new BlocCouleur(9,1,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b63 = new BlocCouleur(9,2,BlocCouleur.Couleur.VIOLET,true);
        BlocCouleur b64 = new BlocCouleur(9,3,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b65 = new BlocCouleur(9,4,BlocCouleur.Couleur.VIOLET,true);
        BlocCouleur b66 = new BlocCouleur(9,6,BlocCouleur.Couleur.VIOLET,true);
        BlocCouleur b67 = new BlocCouleur(9,7,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur b68 = new BlocCouleur(9,8,BlocCouleur.Couleur.VIOLET,true);
        BlocCouleur b69 = new BlocCouleur(9,9,BlocCouleur.Couleur.BLEU,false);
        Animal a1 = new Animal(1,9,"chien");
        Animal a2 = new Animal(2,7,"chien");
        Animal a3 = new Animal(3,5,"chien");
        Animal a4 = new Animal(4,3,"chien");
        Animal a5 = new Animal(5,9,"chat");
        Animal a6 = new Animal(6,7,"chat");
        Animal a7 = new Animal(7,5,"chat");
        Animal a8 = new Animal(8,3,"chat");
        Bombe bo1 = new Bombe(3, 6);
        Bombe bo2 = new Bombe(4, 4);
        Bombe bo3 = new Bombe(5, 2);
        Bombe bo4 = new Bombe(9, 5);
        Piece pi = new Piece();
        Piece piD = new PieceDeplacable();

        Case c00 = new Case(piD);
        Case c0 = new Case(pi);
        Case c1 = new Case(b1);
        Case c2 = new Case(b2);
        Case c3 = new Case(b3);
        Case c4 = new Case(b4);
        Case c5 = new Case(b5);
        Case c6 = new Case(b6);
        Case c7 = new Case(b7);
        Case c8 = new Case(b8);
        Case c9 = new Case(b9);
        Case c10 = new Case(b10);
        Case c11 = new Case(b11);
        Case c12 = new Case(b12);
        Case c13 = new Case(b13);
        Case c14 = new Case(b14);
        Case c15 = new Case(b15);
        Case c16 = new Case(b16);
        Case c17 = new Case(b17);
        Case c18 = new Case(b18);
        Case c19 = new Case(b19);
        Case c20 = new Case(b20);
        Case c21 = new Case(b21);
        Case c22 = new Case(b22);
        Case c23 = new Case(b23);
        Case c24 = new Case(b24);
        Case c25 = new Case(b25);
        Case c26 = new Case(b26);
        Case c27 = new Case(b27);
        Case c28 = new Case(b28);
        Case c29 = new Case(b29);
        Case c30 = new Case(b30);
        Case c31 = new Case(b31);
        Case c32 = new Case(b32);
        Case c33 = new Case(b33);
        Case c34 = new Case(b34);
        Case c35 = new Case(b35);
        Case c36 = new Case(b36);
        Case c37 = new Case(b37);
        Case c38 = new Case(b38);
        Case c39 = new Case(b39);
        Case c40 = new Case(b40);
        Case c41 = new Case(b41);
        Case c42 = new Case(b42);
        Case c43 = new Case(b43);
        Case c44 = new Case(b44);
        Case c45 = new Case(b45);
        Case c46 = new Case(b46);
        Case c47 = new Case(b47);
        Case c48 = new Case(b48);
        Case c49 = new Case(b49);
        Case c50 = new Case(b50);
        Case c51 = new Case(b51);
        Case c52 = new Case(b52);
        Case c53 = new Case(b53);
        Case c54 = new Case(b54);
        Case c55 = new Case(b55);
        Case c56 = new Case(b56);
        Case c57 = new Case(b57);
        Case c58 = new Case(b58);
        Case c59 = new Case(b59);
        Case c60 = new Case(b60);
        Case c61 = new Case(b61);
        Case c62 = new Case(b62);
        Case c63 = new Case(b63);
        Case c64 = new Case(b64);
        Case c65 = new Case(b65);
        Case c66 = new Case(b66);
        Case c67 = new Case(b67);
        Case c68 = new Case(b68);
        Case c69 = new Case(b69);
        Case c70 = new Case(a1);
        Case c71 = new Case(a2);
        Case c72 = new Case(a3);
        Case c73 = new Case(a4);
        Case c74 = new Case(a5);
        Case c75 = new Case(a6);
        Case c76 = new Case(a7);
        Case c77 = new Case(a8);
        Case c78 = new Case(bo1);
        Case c79 = new Case(bo2);
        Case c80 = new Case(bo3);
        Case c81 = new Case(bo4);

        Case[][] tab = {{c0,c00,c00,c00,c00,c00,c00,c00,c00,c00,c0},
                        {c0,c1 ,c2 ,c3 ,c4 ,c5, c6, c7 ,c8 ,c70,c0},
                        {c0,c9 ,c10,c11,c12,c13,c14,c71,c15,c16,c0},
                        {c0,c17,c18,c19,c20,c72,c78,c21,c22,c23,c0},
                        {c0,c24,c25,c73,c79,c26,c27,c28,c29,c30,c0},
                        {c0,c31,c80,c32,c33,c34,c35,c36,c37,c74,c0},
                        {c0,c38,c39,c40,c41,c42,c43,c75,c44,c45,c0},
                        {c0,c46,c47,c48,c49,c76,c50,c51,c52,c53,c0},
                        {c0,c54,c55,c77,c56,c57,c58,c59,c60,c61,c0},
                        {c0,c62,c63,c64,c65,c81,c66,c67,c68,c69,c0},
                        {c0,c0, c0, c0, c0, c0, c0, c0, c0, c0, c0}};

        Plateau p = new Plateau(tab);

        //blocs qui tombent 
        LinkedList<BlocCouleur> liste = new LinkedList<>();
        BlocCouleur bTombeBleu = new BlocCouleur(0,0,BlocCouleur.Couleur.BLEU,false);
        BlocCouleur bTombeRouge = new BlocCouleur(0,0,BlocCouleur.Couleur.ROUGE,false);
        BlocCouleur bTombeVert = new BlocCouleur(0,0,BlocCouleur.Couleur.VERT,false);
        BlocCouleur bTombeJaune = new BlocCouleur(0,0,BlocCouleur.Couleur.JAUNE,false);
        BlocCouleur bTombeViolet = new BlocCouleur(0,0,BlocCouleur.Couleur.VIOLET,false);

        liste.add(new BlocCouleur(bTombeVert));
        liste.add(new BlocCouleur(bTombeJaune));
        liste.add(new BlocCouleur(bTombeJaune));
        liste.add(new BlocCouleur(bTombeViolet));
        liste.add(new BlocCouleur(bTombeViolet));
        liste.add(new BlocCouleur(bTombeRouge));
        liste.add(new BlocCouleur(bTombeBleu));
        liste.add(new BlocCouleur(bTombeRouge));
        liste.add(new BlocCouleur(bTombeBleu));
        
        //création du niveau
        Niveau niveau5 = new Niveau(5, 8, p, 20, 8000, liste,true);

        //envoyer le niveau dans un fichier
        try{
            FileOutputStream fileOut = new FileOutputStream("Niveau5.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(niveau5);
            out.close();
            fileOut.close();
        } catch(IOException e){e.printStackTrace();System.out.println("probleme serialization");}
    }

    /**
     * Recupère le niveau 5 présent dans le fichier .ser
     * @return le niveau 5
     */
    public static Niveau getNiveau5(){
        Niveau niveau5 = null;
        try{
            FileInputStream fileIn = new FileInputStream("Niveau5.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            niveau5 = (Niveau) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException ie){ie.printStackTrace(); System.out.println("probleme deserialization");
        }catch(ClassNotFoundException ce){ce.printStackTrace(); System.out.println("pas de fichier");}
        return niveau5;
    }


    /**
     * Re-remplie les colonnes vides avec les blocs contenus dans la liste blocsQuiTombent du niveau.
     * Puis re-remplis la liste pour qu'elle ne soit jamais vide et que des blocs puissent tomber à l'infini
     */
    public void refillIfPieceDoesNotExist(){
        Case[][] tab = plateau.getTableau();
        int m = tab[0].length;
        int Nbrefill = 0;

        for(int j = 1 ; j < m-1 ; j++)
        {
            if(plateau.colonneNonPleine(j))
            {
                if(tab[1][j].getPiece() instanceof PieceDeplacable){
                    PieceDeplacable pi = (PieceDeplacable)tab[1][j].getPiece();
                    if(!pi.existe){
                        Nbrefill = plateau.NbVidesColonne(j);
                        while(Nbrefill > 0)
                        {
                            BlocCouleur newBloc = new BlocCouleur(blocsQuiTombent.remove());
                            newBloc.setX(1);
                            newBloc.setY(j);
                            newBloc.setExiste(true);
                            tab[1][j] = new Case(newBloc);
                            plateau.reorganisation();
                            Nbrefill--;
                            blocsQuiTombent.add(new BlocCouleur(newBloc));
                        }
                    }
                }
            }
        }
    }

}
