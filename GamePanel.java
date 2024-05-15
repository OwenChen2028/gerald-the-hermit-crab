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
    
    // game objects
    private Player player = new Player(this, keyH);
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    
    private double cameraX;
    private double cameraY;

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
    }
    public void createGameObjects() {
        // create platforms
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (2 + i) * tileSize, (5) * tileSize, true, "grass"));
        }
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (12 + i) * tileSize, (5) * tileSize, true, "grass"));
        }
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (7 + i) * tileSize, (3) * tileSize, true, "grass"));
        }
        
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (7 + i) * tileSize, (7) * tileSize, true, "grass"));
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
    public double lerp(double start, double end, double t) { // linear interpolation, for camera
        return start + t * (end - start);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
                 
        /* player.draw(g2, Math.round((float) player.getX()), Math.round((float) player.getY()));
        
        for (Tile tile : tileList) {
            tile.draw(g2, Math.round((float) tile.getX()), Math.round((float) tile.getY()));
        } */
        
        // move camera
        cameraX = lerp(cameraX, screenWidth / 2 - player.getX() - tileSize / 2, 0.1);
        cameraY = lerp(cameraY, screenHeight / 2 - player.getY() - tileSize / 2, 0.05);
        
        player.draw(g2, (int) Math.round(player.getX() + cameraX), (int) Math.round(player.getY() + cameraY));

        for (Tile tile : tileList) {
            tile.draw(g2, (int) Math.round(tile.getX() + cameraX), (int) Math.round(tile.getY() + cameraY));
        }
        
        g2.dispose();
    }
    
}
