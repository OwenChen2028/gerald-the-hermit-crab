import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    
    ArrayList<Tile> tileList = new ArrayList<Tile>();

    //FPS
    private int fps = 60;
    
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue.darker());
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
        
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, 100 + tileSize * i, 500));
        }
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, 400 + tileSize * i, 600));
        }
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, 700 + tileSize * i, 500));
        }
        
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, 400 + tileSize * i, 300));
        }
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
        player.update(tileList);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
         //int playerX = (screenWidth - tileSize) / 2;
         //int playerY = (screenHeight - tileSize) / 2;
        
         //player.draw(g2, playerX, playerY);
                 
        player.draw(g2, Math.round((float) player.getX()), Math.round((float) player.getY()));
        
        for (Tile tile : tileList) {
            tile.draw(g2, Math.round((float) tile.getX()), Math.round((float) tile.getY()));
        }
        
        g2.dispose();
    }
    
}
