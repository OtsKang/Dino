package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean leftPressed, rightPressed, jumpPressed = false;
    @Override
    public void keyTyped(KeyEvent e) {
   // stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A) leftPressed = true; // vasen nuoli
        if(code == KeyEvent.VK_D) rightPressed = true; // oikea nuoli
        if(code == 32) jumpPressed = true; // space
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_A) leftPressed = false; // vasen nuoli
        if(code == KeyEvent.VK_D) rightPressed = false; // oikea nuoli
        if(code == 32) jumpPressed = false; // space
    }
}
