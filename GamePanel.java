import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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
    private Player player;
    private ArrayList<Tile> tileList = new ArrayList<Tile>();
    private ArrayList<NPC> npcList = new ArrayList<NPC>();
    
    // background
    BufferedImage bg = null;
    
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
        player = new Player(this, keyH, 2 * tileSize, 18 * tileSize);
        // create background
        /* for (int i = 1; i < 32; i++) {
            for (int j = 0; j <= 20; j++) {
                tileList.add(new Tile(this, (0 + i) * tileSize, (0 + j) * tileSize, false, false, "sky background"));
            }
        } */
        // create exit
        tileList.add(new Tile(this, (2) * tileSize, (2) * tileSize, true, false, true, "shell home"));
        // create spikes
        for (int i = 1; i < 31; i++) {
            if (i == 6 || i == 12 || i == 25) {
                tileList.add(new Tile(this, (0 + i) * tileSize, (20) * tileSize, "underwater stone"));
            }
            else {
                tileList.add(new Tile(this, (0 + i) * tileSize, (20) * tileSize, true, true, false, "spikey coral"));
            }
        }
        // create platforms
        for (int i = 0; i < 32; i++) {
            tileList.add(new Tile(this, (0 + i) * tileSize, (21) * tileSize, "underwater stone"));
        }
        for (int i = 0; i < 32; i++) {
            tileList.add(new Tile(this, (0 + i) * tileSize, (-1) * tileSize, "underwater stone"));
        }
        for (int i = 0; i <= 20; i++) {
            tileList.add(new Tile(this, (0) * tileSize, (i) * tileSize, "underwater stone"));
        }
        for (int i = 0; i <= 20; i++) {
            tileList.add(new Tile(this, (31) * tileSize, (i) * tileSize, "underwater stone"));
        }
        //bottom layer
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile(this, (1+i) * tileSize, (19) * tileSize, "underwater stone"));
        }
        tileList.add(new Tile(this, (6) * tileSize, (19) * tileSize, "underwater stone"));
        tileList.add(new Tile(this, (12) * tileSize, (19) * tileSize, "underwater stone"));
        for (int i = 0; i < 5; i++) {
            tileList.add(new Tile(this, (18+i) * tileSize, (19) * tileSize, "underwater stone"));
        }
        tileList.add(new Tile(this, (25) * tileSize, (19) * tileSize, "underwater stone"));
        //layer2
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile(this, (5+i) * tileSize, (18) * tileSize, "underwater stone"));
        }
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile(this, (11+i) * tileSize, (18) * tileSize, "underwater stone"));
        }
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile(this, (24+i) * tileSize, (18) * tileSize, "underwater stone"));
        }
        //layer3
        tileList.add(new Tile(this, (29) * tileSize, (17) * tileSize, "underwater stone"));
        //layer4
        tileList.add(new Tile(this, (30) * tileSize, (16) * tileSize, "underwater stone"));
        //layer5
        //layer6
        npcList.add(new NPC(this, 17 * tileSize, 13 * tileSize, 1, 150));
        for (int i = 0; i < 7; i++) {
            tileList.add(new Tile(this, (17+i) * tileSize, (14) * tileSize, "underwater stone"));
        }
        tileList.add(new Tile(this, (28) * tileSize, (14) * tileSize, "underwater stone"));
        //layer7
        tileList.add(new Tile(this, (5) * tileSize, (13) * tileSize, "underwater stone"));
        for (int i = 0; i < 3; i++) {
            tileList.add(new Tile(this, (12+i) * tileSize, (13) * tileSize, "underwater stone"));
        }
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (24+i) * tileSize, (13) * tileSize, "underwater stone"));
        }
        //layer8
        tileList.add(new Tile(this, (2) * tileSize, (12) * tileSize, "underwater stone"));
        tileList.add(new Tile(this, (7) * tileSize, (12) * tileSize, "underwater stone"));
        //layer9
        tileList.add(new Tile(this, (1) * tileSize, (11) * tileSize, "underwater stone"));
        //layer10
        npcList.add(new NPC(this, 23 * tileSize, 9 * tileSize, -1, 180));
        for (int i = 0; i < 8; i++) {
            tileList.add(new Tile(this, (16+i) * tileSize, (10) * tileSize, "underwater stone"));
        }
        tileList.add(new Tile(this, (28) * tileSize, (10) * tileSize, "underwater stone"));
        //layer11
        tileList.add(new Tile(this, (3) * tileSize, (9) * tileSize, "underwater stone"));
        for (int i = 0; i < 2; i++) {
            tileList.add(new Tile(this, (14+i) * tileSize, (9) * tileSize, "underwater stone"));
        }
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (24+i) * tileSize, (9) * tileSize, "underwater stone"));
        }
        //layer12
        npcList.add(new NPC(this, 10 * tileSize, 7 * tileSize, -1, 150));
        for (int i = 0; i < 7; i++) {
            tileList.add(new Tile(this, (4+i) * tileSize, (8) * tileSize, "underwater stone"));
        }
        tileList.add(new Tile(this, (30) * tileSize, (8) * tileSize, "underwater stone"));
        //layer13
        //layer14
        tileList.add(new Tile(this, (28) * tileSize, (6) * tileSize, "underwater stone"));
        //layer15
        npcList.add(new NPC(this, 21 * tileSize, 4 * tileSize, 1, 150));
        for (int i = 0; i < 7; i++) {
            tileList.add(new Tile(this, (21+i) * tileSize, (5) * tileSize, "underwater stone"));
        }
        for (int i = 0; i < 2; i ++) {
            tileList.add(new Tile(this, (15 + i) * tileSize, (5) * tileSize, "underwater stone"));
        }
        //layer16
        for (int i = 0; i < 2; i ++) {
            tileList.add(new Tile(this, (9 + i) * tileSize, (4) * tileSize, "underwater stone"));
        }
        //layer17
        for (int i = 0; i < 4; i++) {
            tileList.add(new Tile(this, (1+i) * tileSize, (3) * tileSize, "underwater stone"));
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
        
        if (bg == null) {
            try {
                bg = ImageIO.read(getClass().getResourceAsStream("/sprites/underwater background.png"));
            }
            catch (IOException e) {
    
                e.printStackTrace();
    
            }
        }
        
        g2.drawImage(bg, (int) Math.round(cameraX), (int) Math.round(cameraY), 32 * tileSize, 21 * tileSize, null);
            
        for (Tile tile : tileList) {
            tile.draw(g2, (int) Math.round(tile.getX() + cameraX), (int) Math.round(tile.getY() + cameraY));
        }
        
        for (NPC npc : npcList) {
            npc.draw(g2, (int) Math.round(npc.getX() + cameraX), (int) Math.round(npc.getY() + cameraY));
        }
        
        player.draw(g2, (int) Math.round(player.getX() + cameraX), (int) Math.round(player.getY() + cameraY));
        
        if (player.getIsDead()) {
            g2.setColor(new Color(0, 0, 0, 128)); // transparent black
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            g2.setFont(new Font("Serif", Font.BOLD, 128));
            g2.setColor(Color.RED.darker());
            
            String text = "YOU DIED";
            FontMetrics fm = g2.getFontMetrics();
            
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            
            g2.drawString(text, x, y);
        }
        
        if (player.getIsWon()) {
            int alpha = 128; // or any value between 0 (fully transparent) and 255 (fully opaque)
            g2.setColor(new Color(0, 0, 0, alpha)); // transparent black
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            g2.setFont(new Font("Serif", Font.BOLD, 128));
            g2.setColor(Color.GREEN.darker());
            
            String text = "YOU WIN";
            FontMetrics fm = g2.getFontMetrics();
            
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            
            g2.drawString(text, x, y);
        }
        
        g2.dispose();
    }
    
}
