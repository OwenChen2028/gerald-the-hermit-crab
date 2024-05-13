import java.awt.*;
import java.io.*;

public class Entity
{
    public double x;
    public double y;
    
    public double baseV;
    public double gravity = -0.5;
    
    public double[] speed;
    public double[] accel;
    
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    
    public boolean isTouching(double newX, double newY, Entity other, int tileSize) {
        double minX1 = newX;
        double maxX1 = newX + tileSize;
        double minY1 = newY;
        double maxY1 = newY + tileSize;
        
        double minX2 = other.getX();
        double maxX2 = other.getX() + tileSize;
        double minY2 = other.getY();
        double maxY2 = other.getY() + tileSize;
        
        if (minX1 <= maxX2 && maxX1 >= minX2 && minY1 <= maxY2 && maxY1 >= minY2)
            return true;
        else
            return false;
    }
}
