public class TOHApp
{
  public static void solve (TowerOfHanoi toh)
  {
    
  }

  public static void main (String[] args) 
  {
    final int NUM_DISCS = 5;
    TowerOfHanoi toh = new TowerOfHanoi (NUM_DISCS);
    System.out.println (toh);
    
    solve (toh);
    ui = new TOHUserInterface ();
    ui.start ();
  }

  public static TOHUserInterface ui;
}
