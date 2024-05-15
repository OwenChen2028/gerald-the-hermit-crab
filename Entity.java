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
    
    public boolean isTouching(double newX, double newY, Entity other, double width1, double height1, double width2, double height2) {
        double minX1 = newX;
        double maxX1 = newX + width1;
        double minY1 = newY;
        double maxY1 = newY + height1;
        
        double minX2 = other.getX();
        double maxX2 = other.getX() + width2;
        double minY2 = other.getY();
        double maxY2 = other.getY() + height2;
        
        if (minX1 <= maxX2 && maxX1 >= minX2 && minY1 <= maxY2 && maxY1 >= minY2)
            return true;
        else
            return false;
    }
}
