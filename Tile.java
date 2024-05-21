import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Tile extends Entity
{
    private GamePanel gp;
    private BufferedImage image;
    private boolean isSolid;
    private boolean isDeadly;
    private boolean isExit;
    private String tileName;
    
    public Tile(GamePanel gp, double x, double y, String tileName)
    {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.isSolid = true;
        this.isDeadly = false;
        this.tileName = tileName;
        getTileImage();
    }
    public Tile(GamePanel gp, double x, double y, boolean isSolid, boolean isDeadly, boolean isExit, String tileName)
    {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.isSolid = isSolid;
        this.isDeadly = isDeadly;
        this.isExit = isExit;
        this.tileName = tileName;
        getTileImage();
    }
    public boolean getIsSolid()
    {
        return isSolid;
    }
    public boolean getIsDeadly() {
        return isDeadly;
    }
    public boolean getIsExit() {
        return isExit;
    }
    public void getTileImage()
    {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/sprites/" + tileName + ".png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2, int xPos, int yPos)
    {
        // g2.setColor(Color.red);        
        // g2.fillRect(xPos, yPos, gp.tileSize, gp.tileSize);
        g2.drawImage(image, xPos, yPos, gp.tileSize, gp.tileSize, null);
    }
}
