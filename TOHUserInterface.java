import javax.swing.*;        
import java.awt.*;           // For Graphics, etc.
import java.awt.geom.*;      // For Ellipse2D, etc.
import java.awt.image.*;
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
    ui.setIgnoreRepaint (true);
    ui.setResizable (false);
    ui.setSize (XSIZE, YSIZE);
    ui.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    ui.addWindowListener (new WindowAdapter ()
      {
        public void windowOpened(WindowEvent e)
        {
          ui.applicationUpdateTimer.start ();
        } 
        public void windowClosing(WindowEvent e)
        {
          System.exit (0);          
        }
        public void windowClosed(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowActivated(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowStateChanged(WindowEvent e) {}
        public void windowGainedFocus(WindowEvent e) {}
        public void windowLostFocus(WindowEvent e) {}
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
        }
      };
    ui.applicationUpdateTimer = new Timer (ui.updateIntervalMs,
                                           updateTimerListener);
    ui.applicationUpdateTimer.setLogTimers (false);
    ui.applicationUpdateTimer.setCoalesce (true);
  }
    
  public static final int XSIZE = 1024;
  public static final int YSIZE = 768;
  private static Timer applicationUpdateTimer;
  public static TOHUserInterface ui;
  
  public final static int updateIntervalMs = 5000;
  private boolean useHardwareAcceleratedImages = false;
  private TowerOfHanoi toh;
  private TowerOfHanoi workingTOH;

  private void updateApplication ()
  {
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

  public void drawApplication (Graphics2D graphics)
  {
    assert graphics != null;
    graphics.setColor (Color.black);
    graphics.fillRect (0, 0, XSIZE - 1, YSIZE - 1);
    graphics.setColor (Color.green);
    drawString (graphics, workingTOH.toString (), 100, 100);
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

}

class DrawPanel extends JPanel
{
  protected void paintComponent (Graphics graphics)
  {
    assert graphics != null;
    TOHUserInterface.ui.drawApplication ((Graphics2D) graphics);
  }
}
