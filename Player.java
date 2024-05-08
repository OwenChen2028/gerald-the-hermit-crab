import java.awt.*;
import java.io.*;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player extends Entity
{
    GamePanel gp;
    KeyHandler keyH;
    
    
    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        
        setDefaultStats();
    }
    public void getPlayerImage()
    {
        
    }
    public void setDefaultStats()
    {
        x = 100;
        y = 100;
        speed = 20;
    }
    public void update()
    {
        if (keyH.getUpPressed() == true)
        {
            y -= speed;
        }
        else if (keyH.getLeftPressed() == true)
        {
            x -= speed;
        }
        else if (keyH.getRightPressed() == true)
        {
            x += speed;
        }
        else if (keyH.getDownPressed() == true)
        {
            y += speed;
        }
    }
    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.white);
        
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
