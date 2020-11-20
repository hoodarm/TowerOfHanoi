import javax.swing.*;        
import java.awt.*;           // For Graphics, etc.
import java.awt.geom.*;      // For Ellipse2D, etc.
import java.awt.event.*;
import java.awt.font.*;

public class TOHUserInterface extends JFrame implements KeyListener, MouseListener
{
  public TOHUserInterface (TowerOfHanoi toh)
  {
    ui = this;
    this.toh = toh;
    workingTOH = new TowerOfHanoi (toh.getNumDiscs ());
  }
    
  public static void start ()
  {
    // schedule a job for the event-dispatching thread: creating and
    // showing this application's GUI and starting application timer
    javax.swing.SwingUtilities.invokeLater (
      new Runnable()
      {
        public void run() { initializeGUIAndTimer (); }
      }
      );
  }
  
  private static void initializeGUIAndTimer ()
  {
    // basic elements and settings
    ui.setSize (XSIZE, YSIZE);
    ui.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    ui.addWindowListener (new WindowAdapter ()
      {
        @Override
        public void windowOpened (WindowEvent e)
        {
          ui.applicationUpdateTimer.start ();
        } 
        @Override
        public void windowClosing (WindowEvent e)
        {
          System.exit (0);          
        }
      });
        
    // add drawing panel
    JPanel panel = new DrawPanel ();
    // get ready for key and mouse input and make window visible
    panel.setFocusable (true);
    panel.addKeyListener (ui);
    panel.requestFocus ();
    panel.addMouseListener (ui);
    ui.add (panel);
    ui.setVisible (true);

    // game update timer
    ActionListener updateTimerListener = new ActionListener ()
      {
        public void actionPerformed (ActionEvent e)
        {
          ui.updateApplication ();
          ui.repaint ();
        }
      };
    ui.applicationUpdateTimer = new Timer (ui.updateIntervalMs,
                                           updateTimerListener);
    ui.applicationUpdateTimer.setLogTimers (false);
  }
    
  private void updateApplication ()
  {
    java.util.Queue<Move> moves = toh.getMoves ();
    if (!moves.isEmpty ())
    {
      Move move = moves.remove ();
      try
      {
        workingTOH.move (move.from, move.to);
      }
      catch (IllegalTowerOfHanoiMoveException e)
      {
      }
    }
  }
    
  public void drawApplication (Graphics2D graphics)
  {
    assert graphics != null;
    final int width = getWidth (), height = getHeight ();
    graphics.setColor (Color.black);
    graphics.fillRect (0, 0, width - 1,  height - 1);
    graphics.setColor (Color.green);

    // graphical element scaling to window dimensions
    int pegLabelY = 11 * height / 12;
    int pegBaseY = 5 * height / 6;
    int pegHeight = 2 * height / 3;
    int pegTopY = pegBaseY - pegHeight;
    int pegWidth = width / 36;
    int pegIndex = 1;
    int discHeight = height / (workingTOH.getNumDiscs () * 4);
    
    for (Peg peg : Peg.values ())
    {
      final int x = pegIndex * width / (NUM_PEGS + 1);
      drawString (graphics, peg.toString (), x, pegLabelY); // peg label
      graphics.drawRect (x - pegWidth / 2, pegTopY, pegWidth, pegHeight); // peg

      // discs
      Integer[] discs = workingTOH.getDiscs (peg);
      int numDiscs = discs.length;
      for (int i = 0; i < numDiscs; i++)
        drawString (graphics, String.valueOf (discs [i]), x, pegBaseY - i * discHeight);

      pegIndex++;
    }
  }
  
  public void drawString (Graphics2D graphics,
                          String str,
                          int x,
                          int y,
                          int fontSize,
                          boolean antiAliasing)
  {
    assert graphics != null;
    if (antiAliasing)
      graphics.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
    Font font = new Font ("Courier", Font.PLAIN, fontSize);
    graphics.setFont (font);
    graphics.drawString (str, x, y);
  }

  public void drawString (Graphics2D graphics,
                          String str,
                          int x,
                          int y,
                          int fontSize)
  {
    assert graphics != null;
    drawString (graphics, str, x, y, fontSize, true);
  }
    
  public void drawString (Graphics2D graphics,
                          String str,
                          int x,
                          int y)
  {
    assert graphics != null;
    drawString (graphics, str, x, y, 16);
  }

  public void keyTyped (KeyEvent e)
  {
    if (e.getKeyChar () == 'q')
      System.exit (0);
  }

  public void keyPressed (KeyEvent e)
  {
    if (e.getKeyCode () == KeyEvent.VK_ENTER)
      enterPressed ();
  }
  
  public void enterPressed ()
  {
  }
  
  public void keyReleased (KeyEvent e)
  {
  }

  public void mouseClicked (MouseEvent e)
  {
  }

  public void mouseEntered (MouseEvent e)
  {
  }

  public void mouseExited (MouseEvent e)
  {
  }

  public void mousePressed (MouseEvent e)
  {
  }

  public void mouseReleased (MouseEvent e)
  {
  }

  public static final int XSIZE = 1024;
  public static final int YSIZE = 768;
  private static Timer applicationUpdateTimer;
  public static TOHUserInterface ui;
  
  public final static int updateIntervalMs = 1000;
  public final static int NUM_PEGS = Peg.values ().length;
  private TowerOfHanoi toh;
  private TowerOfHanoi workingTOH;

}

class DrawPanel extends JPanel
{
  protected void paintComponent (Graphics graphics)
  {
    assert graphics != null;
    TOHUserInterface.ui.drawApplication ((Graphics2D) graphics);
  }
}
