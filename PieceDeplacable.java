/**
 * Une PieceDeplacable est une Piece qui peut se déplacer dans le plateau : Animal, BlocCouleur, BonusPlateau
 */
public class PieceDeplacable extends Piece {

    protected boolean existe;

    //constructeurs
    public PieceDeplacable() {}
    public PieceDeplacable(int x, int y){
      setX(x);
      setY(y);
  
    }
    public PieceDeplacable(PieceDeplacable p){
      setX(p.getX());
      setY(p.getY());
    }
   
    //getters
    public int getX() {
        return super.getX();
    }
    public int getY() {
        return super.getY();
    }
    public boolean getExiste() { return existe;}

    //setters
    public void setExiste(boolean b) { existe = b;}
    public void setX(int x) { super.setX(x);}
    public void setY(int y) { super.setY(y);}
    
}
