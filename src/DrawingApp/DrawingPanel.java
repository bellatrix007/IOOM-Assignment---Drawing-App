/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DrawingApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Aditi
 */
public class DrawingPanel extends JPanel{

    /**
     * @param args the command line arguments
     */
    private Image image1, image2;
    private Graphics2D gd;
    private int oX,oY,turn = 0;
    private final SizedStack<Image> undoStack = new SizedStack<>(20);
    private final SizedStack<Image> redoStack = new SizedStack<>(20);
    private Color color;
    
    DrawingPanel()
    {
        
        setDoubleBuffered(false);
        color = Color.BLACK;
        addMouseListener(new MouseAdapter(){
            
            public void mousePressed(MouseEvent e) {
            deleteRedoStack();
            saveToStack(image2);
            turn = 0;
            oX = e.getX();
            oY = e.getY();
            if (gd != null) {
                gd.fillRect(oX, oY, 50, 50);
            }
            repaint();
            }
        });
        
    }
            
    protected void paintComponent(Graphics g)
    {
        if(image2 == null)
        {    
            image2 = createImage(getSize().width,getSize().height);
            gd = (Graphics2D) image2.getGraphics();
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        
        g.drawImage(image2,0,0,null);
    }
    
    public void clear()
    {
        if(image1!=null)
            setImage(copyImage(image1));
        else
        {
            gd.setPaint(Color.white);
            gd.fillRect(0, 0, getSize().width, getSize().height);
            setColor(color);
        }
        repaint();
    }
    
    public void setColor(Color c)
    {
        color = c;
        gd.setPaint(color);
    }
    
    public void undo() {
        if (undoStack.size() > 0) {
            redoStack.push(copyImage(image2));
            setImage(undoStack.pop());
        }
    }
    
    public void redo()
    {
        if(redoStack.size()>0)
        {
            saveToStack(image2);
            setImage(redoStack.pop());
        }
    }
    
    private void deleteRedoStack()
    {
        while(redoStack.size()>0)
            redoStack.pop();
    }

    private void setImage(Image img) {
        gd = (Graphics2D) img.getGraphics();
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setColor(color);
        /*if(turn==1)
        {
            saveToStack(image2);
            gd.fillRect(oX, oY, 50, 50);
        }*/
        image2 = img;
        repaint();
        //return img;
    }

    public void setBackground(Image img) {
        image1 = copyImage(img);
        setImage(copyImage(img));
    }

    private BufferedImage copyImage(Image img) {
        BufferedImage copyOfImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return copyOfImage;
    }

    private void saveToStack(Image img) {
        undoStack.push(copyImage(img));
    }
    
    public void up()
    {
        oY--;
        move();
    }

    public void down()
    {
        oY++;
        move();
    }
    
    public void right()
    {
        oX++;
        move();
    }
    
    public void left()
    {
        oX--;
        move();
    }
    
    public void move()
    {
        /*if (undoStack.size() > 0) {
            turn = 1;
            setImage(undoStack.pop());
        }*/
    }
}
