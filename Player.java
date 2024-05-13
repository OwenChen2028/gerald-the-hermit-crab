import java.awt.*;
import java.io.*;
import java.util.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity
{
    private GamePanel gp;
    private KeyHandler keyH;
    private double jumpImpulse;
    private boolean jumpReady;
    
    private BufferedImage[] right;
    private BufferedImage[] left;
    
    private int spriteCounter;
    private int spriteNum;
    
    BufferedImage image;
    
    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        
        setDefaultStats();
        getPlayerImage();
        
        spriteCounter = 0;
        spriteNum = 1;
        
        image = right[0];
    }
    public void getPlayerImage()
    {
        right = new BufferedImage[11];
        left = new BufferedImage[11];
        
        for (int i = 0; i <= 10; i++) {
            try {
                right[i] = ImageIO.read(getClass().getResourceAsStream("/sprites/crab guy" + (i + 1) + ".png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream("/sprites/crab guy left" + (i + 1) + ".png"));
            }
            catch (IOException e) {
    
                e.printStackTrace();
    
            }
        }
    }
    public void setDefaultStats()
    {
        x = 100;
        y = 100;
        baseV = 7.5;
        
        speed = new double[2];
        speed[0] = 0;
        speed[1] = 0;
        accel = new double[2];
        accel[0] = 0;
        accel[1] = -1 * gravity;
        
        jumpImpulse = 12.5;
        jumpReady = false;
    }
    public void update(ArrayList<Tile> tileList)
    {
        boolean dir = false;
        if (keyH.getLeftPressed())
        {
            speed[0] = -1 * baseV;
            dir = true;
        }
        if (keyH.getRightPressed())
        {
            speed[0] = baseV;
            dir = true;
        }
        if (dir == false || (keyH.getLeftPressed() && keyH.getRightPressed())) {
            speed[0] = 0;
        }
        
        if (keyH.getSpacePressed() && jumpReady) {
            jumpReady = false;
            speed[1] -= jumpImpulse;
        }
        
        double newX = x + speed[0];
        double newY = y + speed[1];
        
        boolean flag = false;
        
        for (Tile tile : tileList) { // also need one for enemies/npcs later
            if (this.isTouching(newX, y, tile, gp.tileSize)) {
                speed[0] = 0;
                if (!this.isTouching(x, y, tile, gp.tileSize)) { // if not already touching
                    if (newX > x) {
                        x = tile.getX() - gp.tileSize - 0.001; // don't remove 0.001
                    }
                    else if (newX < x) {
                        x = tile.getX() + gp.tileSize + 0.001;
                    }
                }
            }
            if (this.isTouching(x, newY, tile, gp.tileSize)) {
                speed[1] = 0;
                if (newY  > y) {
                    flag = true;
                }
                if (!this.isTouching(x, y, tile, gp.tileSize)) {
                    if (newY > y) {
                        y = tile.getY() - gp.tileSize - 0.001;
                    }
                    else if (newY < y) {
                        y = tile.getY() + gp.tileSize + 0.001;
                    }
                }
            }
        }
        
        if (flag) { // checks if grounded
            jumpReady = true;
        }
        else {
            jumpReady = false;
        }
        
        x += speed[0];
        y += speed[1];
        speed[0] += accel[0];
        speed[1] += accel[1];
        
        if (dir && jumpReady) {
            spriteCounter++;
            if (spriteCounter > 1) // how fast the sprites change
            {
                spriteNum++;
                if (spriteNum == 12) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2, int xPos, int yPos)
    {
        //g2.setColor(Color.white);
        //g2.fillRect(xPos, yPos, gp.tileSize, gp.tileSize);
        if (speed[0] > 0) {
            image = right[spriteNum - 1];
        }
        else if (speed[0] < 0) {
            image = left[spriteNum - 1];
        }
        g2.drawImage(image, xPos, yPos, gp.tileSize, gp.tileSize, null);
    }
}
