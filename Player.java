import java.awt.*;
import java.io.*;
import java.util.*;

public class Player extends Entity
{
    GamePanel gp;
    KeyHandler keyH;
    double jumpImpulse;
    boolean jumpReady;
    
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
                flag = true;
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
    }
    public void draw(Graphics2D g2, int xPos, int yPos)
    {
        g2.setColor(Color.white);
        g2.fillRect(xPos, yPos, gp.tileSize, gp.tileSize);
    }
}
