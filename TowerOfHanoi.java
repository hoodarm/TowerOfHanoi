import java.util.Stack;
import java.util.EnumMap;
import java.util.Map;

public class TowerOfHanoi
{
  public TowerOfHanoi (int n)
  {
    towers = new EnumMap<> (Peg.class);
    for (Peg peg : Peg.values ())
      towers.put (peg, new Stack<> ());
    
    for (int i = n; i >= 1; i--)
      towers.get (Peg.A).push (i);
  }

  public String toString ()
  {
    StringBuffer strBuf = new StringBuffer ();
    for (Peg peg : Peg.values ())
      strBuf.append (peg + ": " + towers.get (peg) + "\n");

    return strBuf.toString ();
      
  }
  
  private Map<Peg,Stack<Integer>> towers;
}

enum Peg
{
  A, B, C;
}
