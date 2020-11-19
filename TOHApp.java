public class TOHApp
{
  static void move (TowerOfHanoi toh, int numDiscs, Peg from, Peg to)
    throws IllegalTowerOfHanoiMoveException
  {
    Peg intermediate;
    if (from != Peg.A && to != Peg.A)
      intermediate = Peg.A;
    else if (from != Peg.B && to != Peg.B)
      intermediate = Peg.B;
    else
      intermediate = Peg.C;

    // code to be written to replace this feeble attempt
    toh.move (from, intermediate);
    toh.move (from, to);
  }
  
  public static void main (String[] args) 
  {
    TowerOfHanoi toh = new TowerOfHanoi (5);
    
    try
    {
      move (toh, toh.getNumDiscs (), Peg.A, Peg.C);
    }
    catch (IllegalTowerOfHanoiMoveException e)
    {
      System.out.println ("illegal move: " + e);
    }
    
    ui = new TOHUserInterface (toh);
    ui.start ();
  }

  private static TOHUserInterface ui;
}
