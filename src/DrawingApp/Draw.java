/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DrawingApp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author Aditi
 */
public class Draw {
    
    private DrawingPanel dPanel = new DrawingPanel();
    
    KeyListener kL = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            switch(code)
            {
                case KeyEvent.VK_UP:
                    dPanel.up();
                    break;
                case KeyEvent.VK_DOWN:
                    dPanel.down();
                    break;
                case KeyEvent.VK_RIGHT:
                    dPanel.right();
                    break;
                case KeyEvent.VK_LEFT:
                    dPanel.left();
                    break;
                default:
                    
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    };
    
    public static void main(String args[])
    {
        new Draw().show();
    }
    
    public void show()
    {
        JFrame1 f = new JFrame1("Drawing App");
        dPanel = f.getDP();
        
        dPanel.addKeyListener(kL);
        dPanel.setFocusable(true);
        dPanel.requestFocusInWindow();
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
         
    }
}
