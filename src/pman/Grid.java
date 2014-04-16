

package pman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Grid extends JPanel {
   
    private final short STEP_SIZE = 20;
    private int pacX;
    private int pacY;
    private short[] dotsX;
    private short[] dotsY;
    private int[] borderLine;
    private int width;
    private int height;
    private Dimension d;
    private Image pacman;
    private Image ghost;
    private boolean pacLeft;
    
    public Grid() {
        setBackground(Color.BLACK);
        d = new Dimension(400, 400);
        dotsX = new short[d.width / STEP_SIZE];
        dotsY = new short[d.height / STEP_SIZE];
        pacX = (STEP_SIZE /2 )* 3;
        pacY = (STEP_SIZE / 2 ) * 3 ;
        
        generateDots();
        loadImages();
        
        setPreferredSize(d);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gd = (Graphics2D) g;
        super.paintComponent(gd);
        drawBorder(gd);        
        drawDots(gd);
        drawPacman(gd);
               
        Toolkit.getDefaultToolkit().sync();        
    }
    
    public void drawBorder(Graphics2D gd) {
        gd.setColor(Color.green);
        gd.setStroke(new BasicStroke(2));
        gd.drawRect(0, 0, d.width, d.height);     
        
    }
    
    public void drawDots(Graphics2D gd) {
      for (int i = 1; i < d.width / STEP_SIZE; i++) {
            for (int j = 1; j < d.height / STEP_SIZE; j++) {
               
                gd.fillRect(dotsX[j]  , dotsY[i], 2, 2);
                
            }
        }
        
    }
    
    private void generateDots() {
        for (short i = 1; i < d.width / STEP_SIZE; i++) {
            dotsX[i] = (short) (i * STEP_SIZE);
        }
        for  (short i = 1; i < d.height / STEP_SIZE; i++) {
            dotsY[i] = (short) (i * STEP_SIZE);
        }
    }
    
    private void loadImages() {
        pacman = new ImageIcon(Grid.class.getResource("resources/pacman1.png")).getImage();
    }
    
    public void drawPacman(Graphics2D gd) {
        gd.drawImage(pacman, pacX, pacY, this);
    }
        
   
  
}
