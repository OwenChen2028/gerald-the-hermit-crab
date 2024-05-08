
import javax.swing.*;
import java.awt.*;
//Written 5/6/24
public class GamePanel extends JPanel implements Runnable
{
    //screen settings
    private final int bitRate = 32; //32x32 per tile
    private final int scale = 2;
    
    public final int tileSize = bitRate * scale; 
    private final int screenCol = 16;
    private final int screenRow = 12;
    private final int screenWidth = screenCol * tileSize;
    private final int screenHeight = screenRow * tileSize;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    //FPS
    private int fps = 60;
    
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run()
    {
        //making fps work
        double drawInterval = (double)1000000000/fps;
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while (gameThread != null)
        {
            //Checking internal time
            long currentTime = System.nanoTime();
            
            //1: update game state
            update();
            //2: draw screen with new game state
            repaint();
                        
            try 
            {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                
                //failsafe if update and 
                //repaint take longer than one frame
                if (remainingTime < 0)
                {
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                
                nextDrawTime += drawInterval;
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void update()
    {
        player.update();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        player.draw(g2);
        g2.dispose();
    }
    
}
