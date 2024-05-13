import java.awt.*;
import java.io.*;

public class Tile extends Entity
{
    GamePanel gp;
    
    public Tile(GamePanel gp, double x, double y)
    {
        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = new double[2];
        accel = new double[2];
    }
    public void getTileImage()
    {
        
    }
    public void draw(Graphics2D g2, int xPos, int yPos)
    {
        g2.setColor(Color.white);        
        g2.fillRect(xPos, yPos, gp.tileSize, gp.tileSize);
    }
}
