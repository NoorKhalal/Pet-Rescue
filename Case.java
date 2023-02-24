import java.io.*;

/**
 * les Case composent le Plateau du niveau, elles ont une Piece
 */
public class Case implements Serializable {
    
    private Piece piece;

    //constructeur
    public Case(Piece p){
        piece=p;
    }

    //getter
    public Piece getPiece(){return piece;}

    //setters
    public void setPiece(Piece p){piece=p;}

	public void setPiece(PieceDeplacable p1) { piece = new PieceDeplacable(p1);}
}
