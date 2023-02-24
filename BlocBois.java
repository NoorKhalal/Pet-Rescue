/**
 * BlocBois est une Piece du plateau qui est un obstacle fixe
 */
class BlocBois extends Piece {
  
  //constructeurs
  public BlocBois() {super();}
  public BlocBois(int x, int y){
    super(x, y);

  }
  public BlocBois(BlocBois b){
   super((Piece)b);
  }
}