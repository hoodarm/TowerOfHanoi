public class Move
{
  public Move (Peg from, Peg to)
  {
    this.from = from;
    this.to = to;
  }
  
  public String toString ()
  {
    return from + " -> " + to;
  }
  
  public Peg from, to;
}
