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
        baseV = 10;
        speed = new double[2];
        speed[0] = 0;
        speed[1] = 0;
        accel = new double[2];
        accel[0] = 0;
        accel[1] = -1 * gravity;
    }
    public void update()
    {
        boolean dir = false;
        if (keyH.getLeftPressed() == true)
        {
            speed[0] = -1 * baseV;
            dir = true;
        }
        if (keyH.getRightPressed() == true)
        {
            speed[0] = baseV;
            dir = true;
        }
        if (dir == false) {
            speed[0] = 0;
        }
        
        x += speed[0];
        y += speed[1];
        speed[0] += accel[0];
        speed[1] += accel[1];
    }
    public void draw(Graphics2D g2)
    {
        g2.setColor(Color.white);
        
        g2.fillRect((int) x, (int) y, gp.tileSize, gp.tileSize);
    }
}
