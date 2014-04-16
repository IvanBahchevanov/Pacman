

package pman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Grid extends JPanel implements ActionListener {
   
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
    protected static boolean left;
    protected static boolean right;
    protected static boolean up;
    protected static boolean down;
    private Timer timer;
    
    public Grid() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);        
        d = new Dimension(400, 400);
        setPreferredSize(d);
        dotsX = new short[d.width / STEP_SIZE];
        dotsY = new short[d.height / STEP_SIZE];
        pacX = (STEP_SIZE /2 )* 3;
        pacY = (STEP_SIZE / 2 ) * 3 ;
        timer = new Timer(150, this);
        
        generateDots();
        loadImages();
        timer.start();
        
        
       
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
    
    private void loadImages()  {
        try {
            pacman = new ImageIcon(Grid.class.getResource("resources/pacman.png")).getImage();                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Can't find pacman image...");
        }
    }
    
    public void drawPacman(Graphics2D gd) {
        gd.drawImage(pacman, pacX, pacY, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movePacman();
        repaint();
    }
    
    public void movePacman() {
        if (left && pacX > STEP_SIZE / 2) {
            pacX -= STEP_SIZE;
        }
        
        if (right && pacX <  ( d.width - ( STEP_SIZE / 2 + STEP_SIZE ) ) ) {
            pacX += STEP_SIZE;
        }
        
        if (up && pacY > STEP_SIZE / 2) {
            pacY -= STEP_SIZE;
        }
        
        if (down && pacY < ( d.height - (STEP_SIZE / 2 + STEP_SIZE))) {
            pacY += STEP_SIZE;
        }
    }
      
}

class TAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Grid.up = true;
            Grid.down = false;
            Grid.right = false;
            Grid.left = false;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Grid.up = false;
            Grid.down = true;
            Grid.right = false;
            Grid.left = false;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            Grid.up = false;
            Grid.down = false;
            Grid.right = false;
            Grid.left = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Grid.up = false;
            Grid.down = false;
            Grid.right = true;
            Grid.left = false;
        }     
    }
}
