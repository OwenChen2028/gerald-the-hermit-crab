import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener
{
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    
    public void keyTyped(KeyEvent e)
    { }
    public void keyPressed(KeyEvent e)
    {
        int keyNum = e.getKeyCode();
        
        if (keyNum == KeyEvent.VK_W)
        {
            upPressed = true;
        }
        if (keyNum == KeyEvent.VK_A)
        {
            leftPressed = true;
        }
        if (keyNum == KeyEvent.VK_S)
        {
            downPressed = true;
        }
        if (keyNum == KeyEvent.VK_D)
        {
            rightPressed = true;
        }
        if (keyNum == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }
    public void keyReleased(KeyEvent e)
    {
        int keyNum = e.getKeyCode();
        
        if (keyNum == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if (keyNum == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if (keyNum == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if (keyNum == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if (keyNum == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
    public boolean getUpPressed()
    {
        return upPressed;
    }
    public boolean getDownPressed()
    {
        return downPressed;
    }
    public boolean getRightPressed()
    {
        return rightPressed;
    }
    public boolean getLeftPressed()
    {
        return leftPressed;
    }
    public boolean getSpacePressed()
    {
        return spacePressed;
    }
}
