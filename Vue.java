import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.Timer;

/**
 * Vue est la classe qui gère l'interface graphique
 */
public class Vue extends JFrame{

    private Partie partie;
    private int nbAnimauxSauves=0;
    private int nbDeplacements=0;

    //pour savoir quel actionListener utiliser pour les blocs :
    private boolean fusee=false; 
    private boolean brisebloc=false;

    //pour ne pas débloquer le jeu suivant et changer le score du joueur en lançant une démo
    private boolean demo = false;

    private JPanel niveaux = new JPanel();
    private JMenuBar menu = new JMenuBar();
    private JFrame niveau; //pour avoir la fenêtre du niveau en cours

    //getters
    public Partie getPartie(){ return partie;}
    public int getAnimauxSauves(){return nbAnimauxSauves;}

    //setter
    public void setAnimauxSauves(int nb){nbAnimauxSauves=nbAnimauxSauves+=nb;}

    /**
     * Le Profil regroupe toutes les informations du joueur :
     * ses bonus, ses scores...
     */
    class Profil extends JFrame{
        JLabel joueur = new JLabel();
        JLabel score = new JLabel();
        JLabel niveaux = new JLabel();
        JLabel brisebloc = new JLabel();
        JLabel tenaille = new JLabel();
        JLabel text = new JLabel();
        JLabel score1 = new JLabel();
        JLabel score2 = new JLabel();
        JLabel score3 = new JLabel();
        JLabel score4 = new JLabel();
        JLabel score5 = new JLabel();

        //constructeur
        public Profil(){
            this.setTitle("Profil");
            this.setSize(300,400);
            this.setVisible(true);
            
            this.getContentPane().setLayout(new GridLayout(12,1));
            this.getContentPane().add(joueur);
            this.getContentPane().add(score);
            this.getContentPane().add(niveaux);
            this.getContentPane().add(brisebloc);
            this.getContentPane().add(tenaille);
            this.getContentPane().add(new JLabel());
            this.getContentPane().add(text);
            this.getContentPane().add(score1);
            this.getContentPane().add(score2);
            this.getContentPane().add(score3);
            this.getContentPane().add(score4);
            this.getContentPane().add(score5);
            
            joueur.setText("   Pseudo : "+partie.getJoueur().getPseudo());
            score.setText("   Score : "+partie.getJoueur().getScore().valeur);
            niveaux.setText("   Nombre de niveaux gagnés : "+partie.getJoueur().getNiveauxGagnes());
            brisebloc.setText("   Nombre de brise-bloc : "+partie.getJoueur().getNbBriseBloc());
            tenaille.setText("   Nombre de tenaille : "+partie.getJoueur().getNbTenaille());
            text.setText("   Meilleurs scores :");
            score1.setText("   Niveau 1 : "+partie.getJoueur().getScoreNiveaux()[0]);
            score2.setText("   Niveau 2 : "+partie.getJoueur().getScoreNiveaux()[1]);
            score3.setText("   Niveau 3 : "+partie.getJoueur().getScoreNiveaux()[2]);
            score4.setText("   Niveau 4 : "+partie.getJoueur().getScoreNiveaux()[3]);
            score5.setText("   Niveau 5 : "+partie.getJoueur().getScoreNiveaux()[4]);
        }

    }

    /**
     * La Boutique permet au joueur d'acheter les bonus Brise-Bloc et Tenaille
     */
    class Boutique extends JFrame{

        JButton brisebloc= new JButton();
        JButton tenaille = new JButton();

        //constructeur
        public Boutique(){
            this.setTitle("Boutique");
            this.setSize(300,300);
            this.setVisible(true);
            this.setBackground(Color.white);
            this.getContentPane().setLayout(new FlowLayout());

            JTextArea text = new JTextArea("\n Acheter des bonus \n Score : "+partie.getJoueur().getScore().valeur);
            text.setEditable(false);
            text.setOpaque(false);
            this.getContentPane().add(text);

            this.getContentPane().add(brisebloc);
            brisebloc.setText("Brise-Bloc : 500 points");
            Dimension d = new Dimension(250, 100);
            brisebloc.setPreferredSize(d);
            if(partie.getJoueur().getScore().valeur<500){brisebloc.setEnabled(false);}
            brisebloc.addActionListener(event -> {partie.getJoueur().acheterBriseBloc(); this.dispose(); new Boutique();});
            
            this.getContentPane().add(tenaille);
            tenaille.setText("Tenaille : 800 points");
            tenaille.setPreferredSize(d);
            if(partie.getJoueur().getScore().valeur<800){tenaille.setEnabled(false);}
            tenaille.addActionListener(event -> {partie.getJoueur().acheterTenaille(); this.dispose(); new Boutique();});

        }
    }

    /**
     * Le joueur peut consulter les règles du jeu :
     * les objectifs, contraintes, bonus...
     */
    class RegleDuJeu extends JFrame{
        JTextArea regles;

        //constructeur
        public RegleDuJeu(){
            this.setTitle("Règle du jeu");
            this.setSize(600, 400);
            this.setVisible(true);

            regles = new JTextArea("\n But du jeu : sauver tous les animaux ! \n \n A chaque tour, vous pouvez détruire un groupe de blocs de la même couleur \n Un animal est sauvé quand il arrive en bas du plateau \n Certains niveaux ont d'autres contraintes de déplacements ou de score à atteindre\n\n Les blocs marrons sont en bois, ce sont des obstacles fixes\n Les blocs gris sont fortifiés, leur couleur apparaitra lorsque la fortification sera détruite\n\n Pour vous aider, vous avez des bonus : \n - la fusée détruit la colonne de votre choix,\n elle est disponible tout les 2000 points dans un niveau \n - le ballon détruit tous les blocs de sa couleur \n - la bombe détruit les 9 blocs l'entourant \n - le brise-bloc détruit un bloc de votre choix,\n vous pouvez en acheter pour 500 points à la boutique \n - la tenaille enlève toutes les fortifications des blocs,\n vous pouvez en acheter pour 800 points à la boutique");
            regles.setEditable(false);
            regles.setOpaque(false);
            this.getContentPane().add(regles);
        }
    }

    /**
     * Le joueur peut demander à voir une démonstration d'une façon aléatoire de jouer un niveau :
     * il sera choisi aléatoirement mais le joueur choisit s'il s'agit d'un niveau déjà débloqué ou non
     * le jeu d'un même niveau peut se faire différemment
     * il y a deux LinkedLists, une pour les niveaux débloqués et une pour les non débloqués
     */
    class Demo extends JFrame{

        JButton spoil= new JButton();
        JButton pasSpoil = new JButton();
        Robot robot;
        Plateau plateau;
        Vue vue;
        Partie partie;
        LinkedList<Integer> niveauxBloques ;
        LinkedList<Integer> niveauxNonBloques;
        int r = 0;
        
        //constructeur
        public Demo(Partie p){
            this.setTitle("Démo");
            this.setSize(500,350);
            this.setVisible(true);
            this.setBackground(Color.white);
            this.getContentPane().setLayout(new FlowLayout());

            JTextArea text = new JTextArea("\n\tVous pouvez assister à la démonstration d'un niveau \n\t aléatoirement choisi.\n\n\t Voulez-vous être spoilé(e)?\n ");
            text.setEditable(false);
            text.setOpaque(false);
            this.getContentPane().add(text);

            this.getContentPane().add(pasSpoil);
            pasSpoil.setText("Niveaux débloqués");
            Dimension d = new Dimension(250, 100);
            pasSpoil.setPreferredSize(d);
            pasSpoil.addActionListener(event -> { demo = true; demoPasSpoil() ; this.dispose();});

            this.getContentPane().add(spoil);
            spoil.setText("Niveaux bloqués");
            spoil.setPreferredSize(d);
           
            spoil.addActionListener(event -> { demo = true ;demoSpoil() ; this.dispose();});

            niveauxNonBloques = new LinkedList<Integer>();
            niveauxBloques = new LinkedList<Integer>();
            LinkedList<Niveau> liste = p.getJoueur().getEnvironnement().niveaux;
            for(Niveau niveau : liste)
            {
                if(niveau.getBloque()) niveauxBloques.add(niveau.getNumero());
                else niveauxNonBloques.add(niveau.getNumero());
            }
        }


        /**
         * Si le joueur choisit un niveau déjà débloqué :
         * la méthode choisit un niveau au hasard dans la LinkedList et lance la démo
         */
        public void demoPasSpoil(){

            int taille = niveauxNonBloques.size();
            int indiceNiveauAleatoire =(int) (Math.random() * (taille));
            int numNiveau = niveauxNonBloques.get(indiceNiveauAleatoire);
            NiveauFrame niveau;

            switch (numNiveau) {
                case 1:
                    Niveau.createNiveau1();
                    niveau = new NiveauFrame(Niveau.getNiveau1());
        
                   break;
            
                case 2:
                    Niveau.createNiveau2();
                    niveau = new NiveauFrame(Niveau.getNiveau2()); 
                    break;
            
                case 3:
                    Niveau.createNiveau3();
                    niveau = new NiveauFrame(Niveau.getNiveau3());                   
                    break;
        
                case 4:
                    Niveau.createNiveau4();
                    niveau = new NiveauFrame(Niveau.getNiveau4());              
                    break;
                    
                default:
                    Niveau.createNiveau5();
                    niveau = new NiveauFrame(Niveau.getNiveau5());
                    break;
            }

            lancerDemo(niveau);
        }

        /**
         * Si le joueur choisit un niveau non débloqué :
         * la méthode choisit un niveau au hasard dans la LinkedList et lance la démo
         */
        public void demoSpoil(){
            int taille = niveauxBloques.size();
            if(taille>0){
                int indiceNiveauAleatoire =(int) (Math.random() * (taille));
                int numNiveau = niveauxBloques.get(indiceNiveauAleatoire);
                NiveauFrame niveau;

                switch (numNiveau) {

                    case 2:
                        Niveau.createNiveau2();
                        niveau = new NiveauFrame(Niveau.getNiveau2());
                        break;
            
                    case 3:
                        Niveau.createNiveau3();
                        niveau = new NiveauFrame(Niveau.getNiveau3());    
                        break;
        
                    case 4:
                        Niveau.createNiveau4();
                        niveau = new NiveauFrame(Niveau.getNiveau4());
                        break;
                    
                    default:
                        Niveau.createNiveau5();
                        niveau = new NiveauFrame(Niveau.getNiveau5());
                        break;
                }

                lancerDemo(niveau);
            }
        }

        
        /**
         * Lance le début du jeu d'un niveau,
         * fait avancer le jeu toutes les deux secondes,
         * s'arrete quand un niveau est perdu ou gagné
         * @param niveau le niveau qui sera lancé en démo
         */
        public void lancerDemo(JFrame niveau){
            
            int delay = 1000;
            Timer timer = new Timer(delay, new ActionListener()  {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(niveauGagne() || niveauPerdu() )
                    { 
                       try {
                           Thread.sleep(1000);
                          
                       } catch (Exception ea) {
                           ea.printStackTrace();
                       }
                       
                       ((Timer)e.getSource()).stop();
                      quitter(niveau);
                      niveau.dispose();
                     
                    } 
                    else    aidePasTresMalin();
                }
      
            }
            );
            timer.setDelay(2000);
            timer.setRepeats(true);
            timer.start();
            if(niveauGagne() || niveauPerdu() ) timer.stop();
        }

    }

    /**
     * Le Robot peut remplacer le joueur et jouer les 5 niveaux.
     * Montre en général la meilleure façon de gagner un niveau compte tenu des bonus en sa possession.
     * Le niveau joué par le robot est choisit par le joueur.
     */
    class Robot extends JFrame{
        JTextArea question;
        JPanel niveauxRobot;
        JButton niveau1 = new JButton();
        JButton niveau2 = new JButton();
        JButton niveau3 = new JButton();
        JButton niveau4 = new JButton();
        JButton niveau5 = new JButton();
        NiveauFrame niveau;

        //constructeur
        public Robot(){
            this.setTitle("Robot");
            this.setSize(550,550);
            this.setVisible(true);
            this.setBackground(Color.white);
            this.getContentPane().setLayout(new FlowLayout());

            JTextArea text = new JTextArea("\n  Le robot jouera le niveau de votre choix avec les bonus que vous avez maintenant,\n   s'il échoue, il faudra penser à acheter d'autres bonus \n\n     Quel niveau souhaitez-vous voir le robot jouer?\n");
            text.setEditable(false);
            text.setOpaque(false);
            this.getContentPane().add(text);

            Dimension d = new Dimension(200, 100);

            this.getContentPane().add(niveau1);
            niveau1.setText("Niveau 1");
            niveau1.setPreferredSize(d);
            niveau1.addActionListener(event -> { 
                demo = true;
                Niveau.createNiveau1();
                niveau = new NiveauFrame(Niveau.getNiveau1());
                lancerRobot(niveau);
                this.dispose();
            });

            this.getContentPane().add(niveau2);
            niveau2.setText("Niveau 2");
            niveau2.setPreferredSize(d);
            niveau2.addActionListener(event -> { 
                demo = true;
                Niveau.createNiveau2();
                niveau = new NiveauFrame(Niveau.getNiveau2());
                lancerRobot(niveau);
                this.dispose();
            });
            
            this.getContentPane().add(niveau3);
            niveau3.setText("Niveau 3");
            niveau3.setPreferredSize(d);
            niveau3.addActionListener(event -> { 
                demo = true;
                Niveau.createNiveau1();
                niveau = new NiveauFrame(Niveau.getNiveau3());
                lancerRobot(niveau);
                this.dispose();
            });

            this.getContentPane().add(niveau4);
            niveau4.setText("Niveau 4");
            niveau4.setPreferredSize(d);
            niveau4.addActionListener(event -> { 
                demo = true;
                Niveau.createNiveau4();
                niveau = new NiveauFrame(Niveau.getNiveau4());
                lancerRobot(niveau);
                this.dispose();
            });

            this.getContentPane().add(niveau5);
            niveau5.setText("Niveau 5");
            niveau5.setPreferredSize(d);
            niveau5.addActionListener(event -> { 
                demo = true;
                Niveau.createNiveau1();
                niveau = new NiveauFrame(Niveau.getNiveau5());
                lancerRobot(niveau);
                this.dispose();
            });
        } 
        
         /**
         * Lance le début du jeu d'un niveau :
         * fait avancer le jeu toutes les deux secondes,
         * s'arrete quand un niveau est perdu ou gagné
         * @param niveau le niveau qui sera lancé en démo
         */
        public void lancerRobot(JFrame niveau){
            
            int delay = 1000;
            Timer timer = new Timer(delay, new ActionListener()  {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(niveauGagne() || niveauPerdu() )
                    { 
                       try {
                           Thread.sleep(1000);
                          
                       } catch (Exception ea) {
                           ea.printStackTrace();
                       }
                      
                       ((Timer)e.getSource()).stop();
                      quitter(niveau);
                      niveau.dispose();
                     
                    } 
                    else    aide();
                }
      
            }
            );
            timer.setDelay(2000);
            timer.setRepeats(true);
            timer.start();
            if(niveauGagne() || niveauPerdu() ) timer.stop();
        }
    }

    /**
     * La fenêtre affiche le niveau : 
     * les boutons Quitter et Aide ;
     * les informations/objectifs du niveau ;
     * le plateau et les bonus
     */
    class NiveauFrame extends JFrame{

        JPanel plateau = new JPanel();
        JTextArea text;

        //constructeur
        public NiveauFrame(Niveau n){
            partie.setNiveau(n);
            niveau=this;

            this.setTitle("Niveau "+partie.getNiveau().numero);
            this.setVisible(true);

            if(partie.getNiveau().numero==1){
                this.setSize(600,600);
            }else if(partie.getNiveau().numero==2){
                this.setSize(450,600);
            }else if(partie.getNiveau().numero==3){
                this.setSize(480,600);
            }else if(partie.getNiveau().numero==4 || partie.getNiveau().numero==5){
                this.setSize(500,600);
            }

            //menu en haut
            JMenuBar menu = new JMenuBar();
            JButton quitter = new JButton();
            quitter.setText("Quitter");
            menu.add(quitter);
            quitter.addActionListener(event -> quitter(this));
            JButton aide = new JButton();
            aide.setText("Aide");
            menu.add(aide);
            aide.addActionListener(event -> aide());
            this.setJMenuBar(menu);


            //informations niveau + plateau
            plateau = afficherPlateau();
            this.getContentPane().add(plateau);

            //bonus
            JMenuBar bonus = new JMenuBar();
            JButton fusee = new JButton();
            fusee.setText("Fusée");
            if(partie.getFusee().score.valeur<2000){fusee.setEnabled(false);}
            bonus.add(fusee);
            fusee.addActionListener(event -> utiliserFusee());
            JButton brisebloc = new JButton();
            brisebloc.setText("Brise-Bloc");
            if(partie.getJoueur().getNbBriseBloc()<1){brisebloc.setEnabled(false);}
            bonus.add(brisebloc);
            brisebloc.addActionListener(event -> utiliserBriseBloc());
            JButton tenaille = new JButton();
            tenaille.setText("Tenaille");
            if(partie.getJoueur().getNbTenaille()<1){tenaille.setEnabled(false);}
            bonus.add(tenaille);
            tenaille.addActionListener(event -> utiliserTenaille());
            this.getContentPane().add(bonus, "South");

        }
    }

    /**
     * Constructeur de la classe : 
     * affiche le menu en haut et les boutons des différents niveaux
     * @param p la partie en cours
     */
    public Vue(Partie p){
        partie=p;
        this.setVisible(true);
        this.setSize(600,600);
        this.setTitle("Pet Rescue Saga");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(niveaux);
        this.setJMenuBar(menu);

        //menu
        JButton profil = new JButton();
        profil.setText("Profil");
        menu.add(profil);
        profil.addActionListener(event -> new Profil());

        JButton boutique = new JButton();
        boutique.setText("Boutique");
        menu.add(boutique);
        boutique.addActionListener(event -> new Boutique());

        JButton regles = new JButton();
        regles.setText("Règle du jeu");
        menu.add(regles);
        regles.addActionListener(event -> new RegleDuJeu());

        JButton demo = new JButton();
        demo.setText("Démo");
        menu.add(demo);
        demo.addActionListener(event -> new Demo(p));
        
        JButton robot = new JButton();
        robot.setText("Robot");
        menu.add(robot);
        robot.addActionListener(event -> new Robot());
        
        JButton quitter = new JButton();
        quitter.setText("Quitter");
        menu.add(quitter);
        quitter.addActionListener(event -> Partie.quitterLaPartie());


        //niveaux
        niveaux.setLayout(new FlowLayout());
        Font font = new Font(Font.DIALOG, Font.PLAIN, 30);
        Dimension d = new Dimension(500, 100);

        JButton niveau1 = new JButton();
        niveau1.setBackground(Color.GREEN);
        niveau1.setOpaque(true);
        niveau1.setBorderPainted(false);
        niveau1.setPreferredSize(d);
        niveau1.setText("1");
        niveau1.setFont(font);
        niveaux.add(niveau1);
        Niveau.createNiveau1();
        niveau1.addActionListener(event -> new NiveauFrame(Niveau.getNiveau1()));

        JButton niveau2 = new JButton();
        if(partie.getJoueur().getEnvironnement().niveaux.get(1).bloque==true){
            niveau2.setBackground(Color.lightGray);
            niveau2.setEnabled(false);
        }
        else {niveau2.setBackground(Color.GREEN);}
        niveau2.setOpaque(true);
        niveau2.setBorderPainted(false);
        niveau2.setPreferredSize(d);
        niveau2.setText("2");
        niveau2.setFont(font);
        niveaux.add(niveau2);
        Niveau.createNiveau2();
        niveau2.addActionListener(event -> new NiveauFrame(Niveau.getNiveau2()));
        
        JButton niveau3 = new JButton();
        if(partie.getJoueur().getEnvironnement().niveaux.get(2).bloque==true){
            niveau3.setBackground(Color.lightGray);
            niveau3.setEnabled(false);
        }
        else {niveau3.setBackground(Color.GREEN);}
        niveau3.setOpaque(true);
        niveau3.setBorderPainted(false);
        niveau3.setPreferredSize(d);
        niveau3.setText("3");
        niveau3.setFont(font);
        niveaux.add(niveau3);
        Niveau.createNiveau3();
        niveau3.addActionListener(event -> new NiveauFrame(Niveau.getNiveau3()));

        JButton niveau4 = new JButton();
        if(partie.getJoueur().getEnvironnement().niveaux.get(3).bloque==true){
            niveau4.setBackground(Color.lightGray);
            niveau4.setEnabled(false);
        }
        else {niveau4.setBackground(Color.GREEN);}
        niveau4.setOpaque(true);
        niveau4.setBorderPainted(false);
        niveau4.setPreferredSize(d);
        niveau4.setText("4");
        niveau4.setFont(font);
        niveaux.add(niveau4);
        Niveau.createNiveau4();
        niveau4.addActionListener(event -> new NiveauFrame(Niveau.getNiveau4()));

        JButton niveau5 = new JButton();
        if(partie.getJoueur().getEnvironnement().niveaux.get(4).bloque==true){
            niveau5.setBackground(Color.lightGray);
            niveau5.setEnabled(false);
        }
        else {niveau5.setBackground(Color.GREEN);}
        niveau5.setOpaque(true);
        niveau5.setBorderPainted(false);
        niveau5.setPreferredSize(d);
        niveau5.setText("5");
        niveau5.setFont(font);
        niveaux.add(niveau5);
        Niveau.createNiveau5();
        niveau5.addActionListener(event -> new NiveauFrame(Niveau.getNiveau5()));
    }

    
    /**
     * Affiche le plateau : 
     * les objectifs/informations du niveau et le plateau composé de boutons représentant les différentes pièces.
     * Décide des ActionListeners à atttribuer aux boutons selon les bonus utilisés ou non.
     */
    public JPanel afficherPlateau(){
        JPanel plateau = new JPanel();

        //informations niveau
        String s = "";
        if(partie.getNiveau().scoreAAtteindre!=-1){s="/"+partie.getNiveau().scoreAAtteindre;}
        String de = "";
        if(partie.getNiveau().nbDeplacement!=-1){de="/"+partie.getNiveau().nbDeplacement;}
        JTextArea text = new JTextArea("                          Animaux : "+nbAnimauxSauves+"/"+partie.getNiveau().nbAnimaux+ "                Déplacements : "+nbDeplacements+de+"               Score : "+partie.getNiveau().scoreNiveau.valeur+s+"                                      \n");
        text.setEditable(false);
        text.setOpaque(false);
        plateau.add(text);

        //plateau
        Dimension d = new Dimension(0,0);
        if(partie.getNiveau().numero==1){
            d = new Dimension(56,56);
        }else if (partie.getNiveau().numero==2){
            d = new Dimension(45,45);
        }else if(partie.getNiveau().numero==3){
            d = new Dimension(50,50);
        }else if(partie.getNiveau().numero==4 || partie.getNiveau().numero==5){
            d = new Dimension(40,40);
        }
        
        Case[][] tableau = partie.getNiveau().getPlateau().getTableau();
        for(int i=0; i<tableau.length;i++){
            for(Case c : tableau[i]){
                    
                if (c.getPiece() instanceof Animal){
                    Animal a = (Animal)c.getPiece();
                    if (!a.existe){
                        JButton b = new JButton();
                        b.setBackground(Color.WHITE);
                        b.setBorderPainted(false);
                        b.setPreferredSize(d);
                        b.setEnabled(false);
                        plateau.add(b);
                    }
                    else {
                        JButton b = new JButton();
                        try{
                            BufferedImage image = null;
                            if(a.nom.equals("chien")){
                                image = ImageIO.read(new File("images/chien.PNG"));
                            }else if(a.nom.equals("chat")){
                                image = ImageIO.read(new File("images/chat.PNG"));
                            }
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            b = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        b.setBorderPainted(false);
                        b.setPreferredSize(d);
                        plateau.add(b);
                    }
                }
                else if (c.getPiece() instanceof Ballon){
                    Ballon b = (Ballon)c.getPiece();
                    if (!b.existe){
                        JButton bu = new JButton();
                        bu.setBackground(Color.WHITE);
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        bu.setEnabled(false);
                        plateau.add(bu);
                    }
                    else if (b.getCouleur() == BlocCouleur.Couleur.ROUGE) {
                        JButton bu = new JButton();
                        try{
                            BufferedImage image = ImageIO.read(new File("images/ballonRouge.PNG"));
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            bu = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        plateau.add(bu);
                        bu.addActionListener(event -> toucherBallon(b));
                    } else if (b.getCouleur() == BlocCouleur.Couleur.BLEU) {
                        JButton bu = new JButton();
                        try{
                            BufferedImage image = ImageIO.read(new File("images/ballonBleu.PNG"));
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            bu = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        plateau.add(bu);
                        bu.addActionListener(event -> toucherBallon(b));
                    } else if (b.getCouleur() == BlocCouleur.Couleur.VERT) {
                        JButton bu = new JButton();
                        try{
                            BufferedImage image = ImageIO.read(new File("images/ballonVert.PNG"));
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            bu = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        plateau.add(bu);
                        bu.addActionListener(event -> toucherBallon(b));
                    } else if (b.getCouleur() == BlocCouleur.Couleur.JAUNE) {
                        JButton bu = new JButton();
                        try{
                            BufferedImage image = ImageIO.read(new File("images/ballonJaune.PNG"));
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            bu = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        plateau.add(bu);
                        bu.addActionListener(event -> toucherBallon(b));
                    } else if (b.getCouleur() == BlocCouleur.Couleur.VIOLET) {
                        JButton bu = new JButton();
                        try{
                            BufferedImage image = ImageIO.read(new File("images/ballonViolet.PNG"));
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            bu = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        plateau.add(bu);
                        bu.addActionListener(event -> toucherBallon(b));
                    }
                }
                else if (c.getPiece() instanceof Bombe){
                    Bombe b = (Bombe)c.getPiece();
                    if(!b.existe){
                        JButton bu = new JButton();
                        bu.setBackground(Color.WHITE);
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        bu.setEnabled(false);
                        plateau.add(bu);
                    }
                    else {
                        JButton bu = new JButton();
                        try{
                            BufferedImage image = ImageIO.read(new File("images/bombe.PNG"));
                            ImageIcon icon = new ImageIcon(image.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), Image.SCALE_DEFAULT));
                            bu = new JButton(icon);
                        }catch(IOException e){e.printStackTrace();}
                        bu.setBorderPainted(false);
                        bu.setPreferredSize(d);
                        plateau.add(bu);
                        bu.addActionListener(event -> toucherBombe(b));
                    }
                }
                else if (c.getPiece() instanceof BlocBois){
                    JButton b = new JButton();
                    Color color = new Color(138, 108, 66);
                    b.setBackground(color);
                    b.setOpaque(true);
                    b.setBorderPainted(false);
                    b.setPreferredSize(d);
                    b.setEnabled(false);
                    plateau.add(b);
                }
                else if (c.getPiece() instanceof BlocCouleur) {

                    //pour que la forme du plateau reste la même malgré les nouveaux blocs qui tombent dans le niveau
                    if(partie.getNiveau().numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==1
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==2
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==3
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==7
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==8
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==1 && c.getPiece().getY()==9
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==1
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==2
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==8
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==2 && c.getPiece().getY()==9
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==3 && c.getPiece().getY()==1
                        || partie.getNiveau().numero==4 && c.getPiece().getX()==3 && c.getPiece().getY()==9
                        || partie.getNiveau().numero==3 && c.getPiece().getX()==1 && c.getPiece().getY()==2
                        || partie.getNiveau().numero==3 && c.getPiece().getX()==1 && c.getPiece().getY()==5
                        ){
                        JButton b = new JButton();
                        b.setBackground(Color.WHITE);
                        b.setBorderPainted(false);
                        b.setEnabled(false);
                        b.setPreferredSize(d);
                        plateau.add(b);
                    }

                    else{
                        BlocCouleur b = (BlocCouleur) c.getPiece();
                        if (b.existe) {
                            if(b.fortifie){
                                JButton bu = new JButton();
                                bu.setBackground(Color.DARK_GRAY);
                                bu.setOpaque(true);
                                bu.setBorderPainted(false);
                                bu.setPreferredSize(d);
                                plateau.add(bu);
                                if(brisebloc){bu.addActionListener(event -> actionBriseBloc(b));}
                                if(fusee){bu.addActionListener(event -> actionFusee(b));}
                            }
                            else{
                                if (b.getCouleur() == BlocCouleur.Couleur.ROUGE) {
                                    JButton bu = new JButton();
                                    bu.setBackground(Color.RED);
                                    bu.setOpaque(true);
                                    bu.setBorderPainted(false);
                                    bu.setPreferredSize(d);
                                    plateau.add(bu);
                                    if(fusee){bu.addActionListener(event -> actionFusee(b));}
                                    else if(brisebloc){bu.addActionListener(event -> actionBriseBloc(b));}
                                    else {bu.addActionListener(event -> toucherBloc(b));}
                                } else if (b.getCouleur() == BlocCouleur.Couleur.BLEU) {
                                    JButton bu = new JButton();
                                    bu.setBackground(Color.BLUE);
                                    bu.setOpaque(true);
                                    bu.setBorderPainted(false);
                                    bu.setPreferredSize(d);
                                    plateau.add(bu);
                                    if(fusee){bu.addActionListener(event -> actionFusee(b));}
                                    else if(brisebloc){bu.addActionListener(event -> actionBriseBloc(b));}
                                    else {bu.addActionListener(event -> toucherBloc(b));}
                                } else if (b.getCouleur() == BlocCouleur.Couleur.VERT) {
                                    JButton bu = new JButton();
                                    bu.setBackground(Color.GREEN);
                                    bu.setOpaque(true);
                                    bu.setBorderPainted(false);
                                    bu.setPreferredSize(d);
                                    plateau.add(bu);
                                    if(fusee){bu.addActionListener(event -> actionFusee(b));}
                                    else if(brisebloc){bu.addActionListener(event -> actionBriseBloc(b));}
                                    else {bu.addActionListener(event -> toucherBloc(b));}
                                } else if (b.getCouleur() == BlocCouleur.Couleur.JAUNE) {
                                    JButton bu = new JButton();
                                    bu.setBackground(Color.YELLOW);
                                    bu.setOpaque(true);
                                    bu.setBorderPainted(false);
                                    bu.setPreferredSize(d);
                                    plateau.add(bu);
                                    if(fusee){bu.addActionListener(event -> actionFusee(b));}
                                    else if(brisebloc){bu.addActionListener(event -> actionBriseBloc(b));}
                                    else {bu.addActionListener(event -> toucherBloc(b));}
                                } else if (b.getCouleur() == BlocCouleur.Couleur.VIOLET) {
                                    JButton bu = new JButton();
                                    Color color = new Color(223,115,255);
                                    bu.setBackground(color);
                                    bu.setOpaque(true);
                                    bu.setBorderPainted(false);
                                    bu.setPreferredSize(d);
                                    plateau.add(bu);
                                    if(fusee){bu.addActionListener(event -> actionFusee(b));}
                                    else if(brisebloc){bu.addActionListener(event -> actionBriseBloc(b));}
                                    else {bu.addActionListener(event -> toucherBloc(b));}
                                }
                            }
                        }
                        if(!b.existe){
                            JButton bu = new JButton();
                            bu.setBackground(Color.WHITE);
                            bu.setBorderPainted(false);
                            bu.setEnabled(false);
                            bu.setPreferredSize(d);
                            plateau.add(bu);
                        }
                    }
                    
                }
                else{
                    JButton b = new JButton();
                    b.setBackground(Color.WHITE);
                    b.setBorderPainted(false);
                    b.setEnabled(false);
                    b.setPreferredSize(d);
                    plateau.add(b);
                }
            }
        }
        return plateau;
    }

    /**
     * Quand le joueur touche un bloc : 
     * les blocs se suppriment, le plateau se réorganise...
     * -> ouvre une fenêtre du niveau actualisé avec le plateau réorganisé
     * @param b le BlocCouleur que le joueur touche
     */
    public void toucherBloc(BlocCouleur b){
        if(b.caseToucheePasSeule(partie.getNiveau().getPlateau())) nbDeplacements++;
        b.viderListeASupprimer();
        b.blocsASupprimer(partie.getNiveau().getPlateau());
        b.supprimer(partie.getNiveau().getPlateau());
        int nb = b.nbBlocsSupprimes();
        partie.getNiveau().scoreNiveau.blocsDetruits(nb);
        partie.getFusee().score.blocsDetruits(nb);
        b.viderListeASupprimer();
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        finPartie();
    }

    /**
     * Quand le joueur touche un ballon sur le plateau : 
     * les blocs se suppriment, le plateau se réorganise...
     * -> ouvre une fenêtre du niveau actualisé avec le plateau réorganisé
     * @param b le Ballon que le joueur touche
     */
    public void toucherBallon(Ballon b){
        nbDeplacements++;
        int nb = b.action(partie.getNiveau().getPlateau());
        partie.getNiveau().scoreNiveau.ballonOuFusee(nb);
        partie.getFusee().score.ballonOuFusee(nb);
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        finPartie();
    }

    /**
     * Quand le joueur touche une bombe sur le plateau : 
     * les blocs se suppriment, le plateau se réorganise...
     * -> ouvre une fenêtre du niveau actualisé avec le plateau réorganisé
     * @param b la Bombe que le joueur touche
     */
    public void toucherBombe(Bombe b){
        nbDeplacements++;
        b.action(partie.getNiveau().getPlateau());
        partie.getNiveau().scoreNiveau.bombe();
        partie.getFusee().score.bombe();
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        finPartie();
    }

    /**
     * Gère la fin du niveau, selon si le joueur l'a gagné ou perdu : 
     * augmente son score, débloque le niveau suivant ou non... 
     * Si le joueur est dans le mode Démo/Robot son profil n'est pas affecté
     */
    public void finPartie(){
        if(niveauPerdu()){
            if(partie.getNiveau().numero==3){
                JOptionPane.showMessageDialog(niveau, "Niveau perdu... \nPensez à utiliser le Brise-Bloc !");
            }else if(partie.getNiveau().numero==4 || partie.getNiveau().numero==4){
                JOptionPane.showMessageDialog(niveau, "Niveau perdu... \nPensez à utiliser les bonus !");
            }else{
                JOptionPane.showMessageDialog(niveau, "Niveau perdu...");
            }
            niveau.dispose();
            partie.getJoueur().getScore().partiePerdue(nbAnimauxSauves);
            Joueur.createJoueur(partie.getJoueur());
            nbAnimauxSauves=0;nbDeplacements=0;partie.getFusee().score.valeur=0;
            this.dispose();
            new Vue(partie);
        }
        else if(niveauGagne()){
            if(!demo){
                if(partie.getNiveau().numero==5 && partie.getJoueur().getScoreNiveaux()[4]==0){
                    JOptionPane.showMessageDialog(niveau, "Niveau gagné ! \nBravo! Vous avez fini le jeu !");
                    partie.getJoueur().getScoreNiveaux()[partie.getNiveau().numero-1]=partie.getNiveau().scoreNiveau.valeur;
                }
                else if(partie.getNiveau().scoreNiveau.valeur>partie.getJoueur().getScoreNiveaux()[partie.getNiveau().numero-1]){
                    JOptionPane.showMessageDialog(niveau, "Niveau gagné ! \nMeilleur score battu !");
                    partie.getJoueur().getScoreNiveaux()[partie.getNiveau().numero-1]=partie.getNiveau().scoreNiveau.valeur;
                }
                else JOptionPane.showMessageDialog(niveau, "Niveau gagné !");
            }
            else JOptionPane.showMessageDialog(niveau, "Niveau gagné !");
            niveau.dispose();
            nbAnimauxSauves=0;nbDeplacements=0;partie.getFusee().score.valeur=0;
            if(!demo){

                partie.getJoueur().getScore().partieGagnee();
                if(partie.getNiveau().numero<5){
                    partie.getJoueur().getEnvironnement().niveaux.get(partie.getNiveau().numero).bloque=false;
                }
            }
            Joueur.createJoueur(partie.getJoueur());
            this.dispose();
            new Vue(partie);
        }
    }

    /**
     * Effectue les tests pour savoir si le niveau est gagné
     * @return true si le niveau est gagné
     */
    public boolean niveauGagne(){
        if(partie.getNiveau().getPlateau().animauxTousSauves() && partie.getNiveau().scoreAAtteindre<=partie.getNiveau().scoreNiveau.valeur){
            return true;
        }
        return false;
    }

    /**
     * Effectue les tests pour savoir si le niveau est perdu
     * @return true si le niveau est perdu
     */
    public boolean niveauPerdu(){
        if((!partie.getNiveau().getPlateau().animauxTousSauves() && !partie.getNiveau().getPlateau().ilResteDesDeplacements() && !partie.getFusee().estAccessible() && !partie.getJoueur().ilResteDesBonusJoueur()) 
            || (nbDeplacements>=partie.getNiveau().nbDeplacement && partie.getNiveau().nbDeplacement!=-1)   
            || (!partie.getNiveau().getPlateau().ilResteDesDeplacements() && partie.getNiveau().scoreAAtteindre>partie.getNiveau().scoreNiveau.valeur ) 
            || (!partie.getNiveau().getPlateau().animauxTousSauves() && !partie.getNiveau().getPlateau().ilResteDesDeplacements() && demo)
            || (demo && partie.getNiveau().scoreAAtteindre>partie.getNiveau().scoreNiveau.valeur && !partie.getNiveau().getPlateau().ilResteDesDeplacements())
            || (demo && !partie.getNiveau().getPlateau().animauxTousSauves() && !partie.getNiveau().getPlateau().ilResteDesDeplacements())
            || (demo && !partie.getNiveau().getPlateau().animauxTousSauves() && partie.getNiveau().scoreAAtteindre>partie.getNiveau().scoreNiveau.valeur && nbDeplacements>partie.getNiveau().nbDeplacement)
            ){
            return true;
        }
        return false;
    }

    /**
     * Quitter la partie : les nombres d'animaux/de déplacements et les scores se réinitialisent,
     * la fenêtre se ferme
     * @param frame la fenêtre à quitter
     */
    public void quitter(JFrame frame){
        nbAnimauxSauves=0;nbDeplacements=0;partie.getFusee().score.valeur=0;
        frame.dispose();
    }

    /**
     * Quand le joueur demande une aide :
     * fait l'action associé au BonusPlateau ou au BlocCouleur qu'il récupère,
     * supprime les blocs, réorganise le plateau...
     * -> ouvre une fenêtre du niveau actualisé avec le plateau réorganisé
     */
    public void aide(){
        PieceDeplacable pi = partie.getNiveau().getPlateau().aideSup(partie, this);
        if(pi instanceof BlocCouleur){
            BlocCouleur bc = (BlocCouleur)pi;
            if(!bc.fortifie){
                bc.viderListeASupprimer();
                bc.blocsASupprimer(partie.getNiveau().getPlateau());
                bc.supprimer(partie.getNiveau().getPlateau());
                int nb = bc.nbBlocsSupprimes();
                partie.getNiveau().scoreNiveau.blocsDetruits(nb);
                partie.getFusee().score.blocsDetruits(nb);
                nbDeplacements++;
                bc.viderListeASupprimer();
            }
        }
        else if(pi instanceof BonusPlateau){
            if( pi instanceof Ballon){
                Ballon ba = (Ballon)pi;
                int nb = ba.action(partie.getNiveau().getPlateau());
                partie.getNiveau().scoreNiveau.ballonOuFusee(nb);
                partie.getFusee().score.ballonOuFusee(nb);
                nbDeplacements++;
            }
            else if(pi instanceof Bombe){
                Bombe bo = (Bombe)pi;
                bo.action(partie.getNiveau().getPlateau());
                partie.getNiveau().scoreNiveau.bombe();
                partie.getFusee().score.bombe();
                nbDeplacements++;
            }
        }
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        if(demo)
        {
            if(partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
            if(niveauGagne() || niveauPerdu())
            {
                if(niveauPerdu())
                JOptionPane.showMessageDialog(niveau, "Niveau perdu...");
                else  JOptionPane.showMessageDialog(niveau, "Niveau gagné!");
                niveau.dispose();
                return ;
            }
        }
        finPartie();
    }

    /**
     * Méthode appelée par la démo,
     * appelle aidePasTresMalin de Plateau.
     * Choisit des coups au hasard plutôt que le meilleur coup,
     * il sera naturel que plusieurs appels a cette méthode ne donne pas le même résultat à chaque fois 
     */
    public void aidePasTresMalin(){
        PieceDeplacable pi = partie.getNiveau().getPlateau().aidePasTresMalin(partie, this);
        if(pi instanceof BlocCouleur){
            BlocCouleur bc = (BlocCouleur)pi;
            if(!bc.fortifie){
                bc.viderListeASupprimer();
                bc.blocsASupprimer(partie.getNiveau().getPlateau());
                bc.supprimer(partie.getNiveau().getPlateau());
                int nb = bc.nbBlocsSupprimes();
                partie.getNiveau().scoreNiveau.blocsDetruits(nb);
                partie.getFusee().score.blocsDetruits(nb);
                nbDeplacements++;
                bc.viderListeASupprimer();
            }
        }
        else if(pi instanceof BonusPlateau){
            if( pi instanceof Ballon){
                Ballon ba = (Ballon)pi;
                int nb = ba.action(partie.getNiveau().getPlateau());
                partie.getNiveau().scoreNiveau.ballonOuFusee(nb);
                partie.getFusee().score.ballonOuFusee(nb);
                nbDeplacements++;
            }
            else if(pi instanceof Bombe){
                Bombe bo = (Bombe)pi;
                bo.action(partie.getNiveau().getPlateau());
                partie.getNiveau().scoreNiveau.bombe();
                partie.getFusee().score.bombe();
                nbDeplacements++;
            }
        }
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        if(demo)
        {
            if(niveauGagne() || niveauPerdu())
            {
                if(niveauPerdu())
                JOptionPane.showMessageDialog(niveau, "Niveau perdu...");
                else  JOptionPane.showMessageDialog(niveau, "Niveau gagné!");
                niveau.dispose();
                return ;
            }
        }
        finPartie();
    }

    /**
     * Quand le joueur utilise une Tenaille :
     * ouvre une fenêtre du niveau actualisé avec les blocs sans fortifications
     */
    public void utiliserTenaille(){
        nbDeplacements++;
        Tenaille.action(partie.getNiveau().getPlateau(), partie.getJoueur());
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
    }

    /**
     * Quand le joueur clique sur Brise-Bloc :
     * ouvre une nouvelle fenêtre avec les actionsListeners des blocs modifiés
     */
    public void utiliserBriseBloc(){
        brisebloc=true;
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
    }

    /**
     * Quand le joueur utilise le Brise-Bloc :
     * le bloc est supprimé, le plateau se réorganise...
     * -> ouvre une fenêtre du niveau actualisé avec le plateau réorganisé
     * @param b le BlocCouleur que le joueur touche
     */
    public void actionBriseBloc(BlocCouleur b){
        brisebloc=false;
        nbDeplacements++;
        Case c = partie.getNiveau().getPlateau().getCase(b.getX(), b.getY());
        BriseBloc.action(partie.getNiveau().scoreNiveau, partie.getJoueur(), c);
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        finPartie();
    }

    /**
     * Quand le joueur clique sur Fusée :
     * ouvre une nouvelle fenêtre avec les actionsListeners des blocs modifiés
     */
    public void utiliserFusee(){
        fusee=true;
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
    }

    /**
     * Quand le joueur utilise la fusée :
     * la colonne du bloc touché se supprime, le plateau se réorganise...
     * -> ouvre une fenêtre du niveau actualisé avec le plateau réorganisé
     * @param b le BlocCouleur que le joueur touche
     */
    public void actionFusee(BlocCouleur b){
        fusee=false;
        nbDeplacements++;
        int colonne = b.getY();
        int nbAnF = partie.getFusee().action(partie.getNiveau().scoreNiveau, colonne, partie.getNiveau().getPlateau());
        if(nbAnF!=0){
            partie.getFusee().score.valeur+=nbAnF*1000;
            nbAnimauxSauves+=nbAnF;
            partie.getNiveau().scoreNiveau.animalSauve();
        }
        if(partie.getNiveau().getNumero() == 3 || partie.getNiveau().getNumero() == 4 || partie.getNiveau().getNumero() == 5)
        {
            partie.getNiveau().getPlateau().reorganisation();
            partie.getNiveau().refillIfPieceDoesNotExist(); 
        }
        partie.getNiveau().getPlateau().reorganisation();
        for (int i=0;i<3;i++){
            partie.getNiveau().getPlateau().reorganisation();
            int nbAn = partie.sauvetage();
            if(nbAn!=0){
                for(int j=0; j<nbAn;j++){
                    nbAnimauxSauves++;
                    partie.getFusee().score.animalSauve();
                }
            }
        }
        partie.getNiveau().getPlateau().reorganisation();
        niveau.dispose();
        new NiveauFrame(partie.getNiveau());
        finPartie();
    }

    
}
