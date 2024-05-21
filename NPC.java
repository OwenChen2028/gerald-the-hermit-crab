import java.awt.*;
import java.io.*;
import java.util.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class NPC extends Entity
{
    private GamePanel gp;
    private BufferedImage image;
    
    private BufferedImage[] right;
    private BufferedImage[] left;
    
    private int spriteCounter;
    private int spriteNum;
    
    private int direction;
    private int cycleCounter;
    private int cycleDuration;
    
    public NPC(GamePanel gp, double x, double y, int dir, int cycle)
    {
        this.gp = gp;
        
        this.x = x;
        this.y = y;
        
        direction = dir;
        cycleCounter = 0;
        cycleDuration = cycle;
        
        setDefaultStats();
        getNPCImage();
        
        spriteCounter = 0;
        spriteNum = 1;
        
        image = right[0];
    }
    public void getNPCImage()
    {
        right = new BufferedImage[18];
        left = new BufferedImage[18];
        
        for (int i = 0; i <= 17; i++) {
            //try {right[0]= ImageIO.read(getClass().getResourceAsStream("/sprites/Enemy3.png"));}
            //catch (IOException e) {
            //e.printStackTrace();}
            
            try {
                
                right[i] = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemy" + (i + 1) + ".png"));
                left[i] = ImageIO.read(getClass().getResourceAsStream("/sprites/Enemyleft" + (i + 1) + ".png"));
            }
            catch (IOException e) {
    
                e.printStackTrace();
    
            }
            
        }
    }
    public void setDefaultStats()
    {
        baseV = 2.5;
        
        speed = new double[2];
        speed[0] = direction * baseV;
        speed[1] = 0;
        
        accel = new double[2];
        accel[0] = 0;
        accel[1] = -1 * gravity;
    }
    public void update(ArrayList<Tile> tileList)
    {
        double newX = x + speed[0];
        double newY = y + speed[1];
        
        boolean flag = false;
        
        for (Tile tile : tileList) { // also need one for enemies/npcs later
            if (tile.getIsSolid() == false)
            {
                continue;
            }
            if (this.isTouching(newX + 0.005, y + 0.005, tile, gp.tileSize - 0.01, gp.tileSize - 0.01, gp.tileSize, gp.tileSize)) {
                speed[0] = 0;
                if (newX > x) {
                    x = tile.getX() - gp.tileSize - 0.001; // don't remove 0.001
                }
                else if (newX < x) {
                    x = tile.getX() + gp.tileSize + 0.001;
                }
            }
            if (this.isTouching(x + 0.005, newY + 0.005, tile, gp.tileSize - 0.01, gp.tileSize - 0.01, gp.tileSize, gp.tileSize)) {
                speed[1] = 0;
                if (newY  > y) {
                    flag = true;
                }
                if (newY > y) {
                    y = tile.getY() - gp.tileSize - 0.001;
                }
                else if (newY < y) {
                    y = tile.getY() + gp.tileSize + 0.001;
                }
            }
        }
        
        x += speed[0];
        y += speed[1];
        speed[0] += accel[0];
        speed[1] += accel[1];
        
        cycleCounter++;
        if (cycleCounter == cycleDuration) {
            cycleCounter = 0;
            speed[0] = speed[0] * -1;
        }
        
        spriteCounter++;
        if (spriteCounter > 1) // how fast the sprites change
        {
            spriteNum++;
            if (spriteNum == 19) {
                spriteNum = 1;
            }
            spriteCounter = 0;
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
