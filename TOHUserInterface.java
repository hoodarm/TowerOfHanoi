import javax.swing.*;        
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.font.*;

public class TOHUserInterface extends JFrame implements KeyListener
{
  public TOHUserInterface (TowerOfHanoi toh)
  {
    ui = this;
    this.toh = toh;

    // working TOH to replicate moves
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
        
    JPanel panel = new DrawPanel ();
    panel.setFocusable (true);
    panel.addKeyListener (ui);
    panel.requestFocus ();
    ui.add (panel);
    ui.setVisible (true);

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

    // graphical element scaling to window dimensions
    int pegLabelY = 11 * height / 12;
    int pegBaseY = 5 * height / 6;
    int pegHeight = 2 * height / 3;
    int pegTopY = pegBaseY - pegHeight;
    int pegWidth = width / 36;
    int pegIndex = 1;
    int numDiscs = workingTOH.getNumDiscs ();
    int discHeight = pegHeight / (numDiscs + 1);
    int discMaxWidth = width / (NUM_PEGS + 3);
    int discMinWidth = pegWidth * 2;

    for (Peg peg : Peg.values ())
    {
      graphics.setColor (Color.green);
      final int x = pegIndex * width / (NUM_PEGS + 1);
      drawString (graphics, peg.toString (), x, pegLabelY); // peg label
      graphics.drawRect (x - pegWidth / 2, pegTopY, pegWidth, pegHeight); // peg

      // discs
      Integer[] discs = workingTOH.getDiscs (peg);
      int numPegDiscs = discs.length;
      for (int i = 0; i < numPegDiscs; i++)
      {
        graphics.setColor (Color.green);
        int disc = discs [i];
        int discWidth =
          ((discMaxWidth - discMinWidth) * disc + discMinWidth * numDiscs - discMaxWidth)
          / (numDiscs - 1);
        int rectX = x - discWidth / 2,
          rectY = pegBaseY - (i + 1) * discHeight;
        graphics.fillRect (rectX, rectY, discWidth, discHeight);
        graphics.setColor (Color.black);
        graphics.drawRect (rectX, rectY, discWidth, discHeight);
      }

      pegIndex++;
    }
  }
  
  public void speedup ()
  {
    applicationUpdateTimer.setDelay (applicationUpdateTimer.getDelay () / 2);
  }

  public void slowdown ()
  {
    applicationUpdateTimer.setDelay (applicationUpdateTimer.getDelay () * 2);
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

  @Override
  public void keyTyped (KeyEvent e)
  {
    char keyChar = e.getKeyChar ();
    switch (keyChar)
    {
      case 'q':
        System.exit (0);
        break;
      case '+':
        speedup ();
        break;
      case '-':
        slowdown ();
        break;
      default:
        break;
    }
  }

  // needed for completeness to implement abstract base class
  @Override
  public void keyPressed (KeyEvent e)
  {
  }
  
  @Override
  public void keyReleased (KeyEvent e)
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
  @Override
  protected void paintComponent (Graphics graphics)
  {
    assert graphics != null;
    TOHUserInterface.ui.drawApplication ((Graphics2D) graphics);
  }
}
