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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
    private int oX=0,oY=0,cX,cY,turn = 0,l,b;
    private final SizedStack<Image> undoStack = new SizedStack<>(20);
    private final SizedStack<Image> redoStack = new SizedStack<>(20);
    private Color color;
    DrawingPanel p;
    
    DrawingPanel()
    {
        
        setDoubleBuffered(false);
        color = Color.BLACK;
        l=b=0;
        p = this;
        
        addMouseListener(new MouseAdapter(){
            
            @Override
            public void mousePressed(MouseEvent e) {
            
                cX = e.getX();
                cY = e.getY();
                if((cX>=oX&&cX<=(oX+l+1))&&(cY>=oY&&cY<=(oY+b+1)))
                {
                    p.setFocusable(true);
                    p.requestFocusInWindow();
                }
                else
                {
                    deleteRedoStack();
                    saveToStack(image2);
                    turn = 0;
                        oX=cX;
                        oY=cY;
                    checkX();
                    checkY();
                    JFrame1 topFrame = (JFrame1) SwingUtilities.getWindowAncestor(p);
                    l = topFrame.getL();
                    b = topFrame.getB();
                    if (gd != null) {
                        gd.fillRect(oX, oY, l, b);
                    }
                    p.setFocusable(false);
                    repaint();
                }
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
    
    public void save() throws IOException
    {
        Rectangle rec = p.getBounds();
        BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height,BufferedImage.TYPE_INT_ARGB);
        p.paint(bufferedImage.getGraphics());
        File temp = new File("screenshot.png");
        ImageIO.write(bufferedImage, "png", temp);
        // Delete temp file when program exits.
        temp.deleteOnExit();
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
        if(turn==1)
        {
            saveToStack(img);
            gd.fillRect(oX, oY, l, b);
        }
        image2 = img;
        repaint();
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
        checkY();
        move();
    }

    public void down()
    {
        oY++;
        checkY();
        move();
    }
    
    public void right()
    {
        oX++;
        checkX();
        move();
    }
    
    public void left()
    {
        oX--;
        checkX();
        move();
    }
    
    public void move()
    {
        if (undoStack.size() > 0) {
            turn = 1;
            setImage(undoStack.pop());
        }
    }
    
    public void checkX()
    {
        if((oX+l+1)>getSize().width)
            oX = getSize().width - l - 1;
        if(oX<0)
            oX = 0;
    }
    
    public void checkY()
    {
        if((oY+b+1)>getSize().height)
            oY = getSize().height - b - 1;
        if(oY<0)
            oY = 0;
    }
}
