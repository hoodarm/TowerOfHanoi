import java.util.Stack;
import java.util.EnumMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class TowerOfHanoi
{
  public TowerOfHanoi (int n)
  {
    towers = new EnumMap<> (Peg.class);
    for (Peg peg : Peg.values ())
      towers.put (peg, new Stack<> ());
    
    for (int i = n; i >= 1; i--)
      towers.get (Peg.A).push (i);
    
    moves = new LinkedList <> ();
  }

  public String toString ()
  {
    StringBuffer strBuf = new StringBuffer ();
    for (Peg peg : Peg.values ())
      strBuf.append (peg + ": " + towers.get (peg) + "\n");

    return strBuf.toString ();
      
  }
  
  public void move (Peg from, Peg to)
  {
    moves.add (new Move (from, to));
  }
    
  private Map<Peg,Stack<Integer>> towers;
  private Queue<Move> moves;
}

enum Peg
{
  A, B, C;
}

class Move
{
  public Move (Peg from, Peg to)
  {
    this.from = from;
    this.to = to;
  }

  Peg from, to;
}
