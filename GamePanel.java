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
    private final int screenCol = 15;
    private final int screenRow = 10;
    private final int screenWidth = screenCol * tileSize;
    private final int screenHeight = screenRow * tileSize;
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    
    // game objects
    private Player player = new Player(this, keyH, 3 * tileSize, 2 * tileSize);
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private ArrayList<Tile> bgTiles = new ArrayList<Tile>();
    private ArrayList<NPC> npcList = new ArrayList<NPC>();
    
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
        // create background
        for (int i = 0; i <= 20; i++) {
            for (int j = 0; j <= 10; j++) {
                tileList.add(new Tile(this, (0 + i) * tileSize, (0 + j) * tileSize, false, "sky background"));
            }
        }
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
        // create npcs
        npcList.add(new NPC(this, 10 * tileSize, 2 * tileSize, -1, 70));
        npcList.add(new NPC(this, 15 * tileSize, 4 * tileSize, -1, 70));
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
        player.update(tileList, npcList);
        for (NPC npc : npcList) {
            npc.update(tileList);
        }
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
        if (!player.getIsDead()) {
            cameraX = lerp(cameraX, screenWidth / 2 - player.getX() - tileSize / 2, 0.1);
            cameraY = lerp(cameraY, screenHeight / 2 - player.getY() - tileSize / 2, 0.05);
        }
        
        for (Tile tile : tileList) {
            tile.draw(g2, (int) Math.round(tile.getX() + cameraX), (int) Math.round(tile.getY() + cameraY));
        }
        
        for (Tile tile : bgTiles) {
            tile.draw(g2, (int) Math.round(tile.getX() + cameraX), (int) Math.round(tile.getY() + cameraY));
        }
        
        for (NPC npc : npcList) {
            npc.draw(g2, (int) Math.round(npc.getX() + cameraX), (int) Math.round(npc.getY() + cameraY));
        }
        
        player.draw(g2, (int) Math.round(player.getX() + cameraX), (int) Math.round(player.getY() + cameraY));
        
        
        
        if (player.getIsDead()) {
            g2.setColor(new Color(0, 0, 0, 20)); // RGB with alpha for transparency
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            g2.setFont(new Font("Serif", Font.BOLD, 128));
            g2.setColor(Color.RED.darker());
            
            String text = "YOU DIED";
            FontMetrics fm = g2.getFontMetrics();
            
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            
            g2.drawString(text, x, y);
        }
        
        g2.dispose();
    }
    
}
