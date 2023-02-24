import java.io.Serializable;
import java.util.LinkedList;

/**
 * Environnement a une liste de niveaux du jeu
 */
public class Environnement implements Serializable{
    public final LinkedList<Niveau> niveaux = new LinkedList<>();

    //constructeur
    public Environnement(){
        Niveau.createNiveau1();
        Niveau niveau1 = Niveau.getNiveau1();
        niveaux.add(niveau1);
        Niveau.createNiveau2();
        niveaux.add(Niveau.getNiveau2());
        Niveau.createNiveau3();
        niveaux.add(Niveau.getNiveau3());
        Niveau.createNiveau4();
        niveaux.add(Niveau.getNiveau4());
        Niveau.createNiveau5();
        niveaux.add(Niveau.getNiveau5());
    }

}
    
