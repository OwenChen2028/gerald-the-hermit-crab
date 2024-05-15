import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Tile extends Entity
{
    private GamePanel gp;
    private BufferedImage image;
    private boolean isSolid;
    private String tileName;
    
    public Tile(GamePanel gp, double x, double y, boolean isSolid, String tileName)
    {
        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = new double[2];
        accel = new double[2];
        this.isSolid = isSolid;
        this.tileName = tileName;
        getTileImage();
    }
    public boolean getIsSolid()
    {
        return isSolid;
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
