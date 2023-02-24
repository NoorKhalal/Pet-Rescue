import java.io.*;

/**
 * Une Piece est contenu dans une Case du plateau
 */
public class Piece implements Serializable {
  private int posX;
  private int posY;

  //constructeurs
  public Piece() {}
  public Piece(int x, int y){
    posX = x;
    posY = y;

  }
  public Piece(Piece p){
    posX = p.getX();
    posY = p.getY();
  }

  //getters
  public int getX() {return posX;}
  public int getY() {return posY;}

  //setters
  public void setX(int x) { posX = x;}
  public void setY(int y) { posY = y;}
}