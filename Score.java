import java.io.*;

/**
 * La classe gère le Score du joueur, et le Score du niveau
 */
public class Score implements Serializable{
    
    int valeur;

    //constructeur
    public Score(){valeur=0;}

    //setter (utilisé dans le jeu du robot)
    public void SetScore(int n){ valeur = n;}


    //augmentation du score dans un niveau

    /**
     * Le score du niveau augmente de 1000 points pour chaque animal sauvé
     */
    public void animalSauve(){
        valeur+=1000;
    }

    /**
     * Le score du niveau augmente selon le nombre de blocs détruits
     * @param nbBlocs le nombre de blocs détruits
     */
    public void blocsDetruits(int nbBlocs){
        valeur+= Math.pow(nbBlocs, 2)*10;
    }

    /**
     * Le score du niveau augmente de 300 points par bombe utilisée
     */
    public void bombe(){
        valeur+=300;
    }

    /**
     * Le score du niveau augmente selon le nombre de blocs détruits
     * @param nbBlocs le nombre de blocs détruits
     */
    public void ballonOuFusee(int nbBlocs){
        valeur+= nbBlocs *20;
    }


    //augmentation du score général du joueur

    /**
     * Le score du joueur augmente de 600 points par partie gagnée
     */
    public void partieGagnee(){
        valeur += 600;
    }

    /**
     * Si le niveau est perdu, le score du joueur augmente de 10 points par animal sauvé
     * @param nbAnimauxSauves le nombre d'animaux sauvés
     */
    public void partiePerdue(int nbAnimauxSauves){
        valeur += nbAnimauxSauves*10;
    }

}
