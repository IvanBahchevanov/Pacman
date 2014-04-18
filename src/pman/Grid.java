

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
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Grid extends JPanel implements ActionListener {
   
    private final short STEP_SIZE = 20;
    private int pacX;
    private int pacY;
    private short ghost1X;
    private short ghost1Y;
    private short ghost2X;
    private short ghost2Y;
    private short ghost3X;
    private short ghost3Y;
    private short ghost4X;
    private short ghost4Y;    
    private short ghost5X;
    private short ghost5Y;
    private short ghost6X;
    private short ghost6Y;
    private short[] dotsX;
    private short[] dotsY;
    private short[][] dots;
    protected static short score;
    private Dimension d;
    private Image pacRightImage;
    private Image pacLeftImage;
    private Image pacDownImage;
    private Image pacUpImage;
    private Image pacRightClosedImage;
    private Image pacLeftClosedImage;
    private Image pacUpClosedImage;
    private Image pacDownClosedImage;
    private Image ghost1;
    private Image ghost2;
    private Image ghost3;
    private Image ghost4;
    private Image ghost5;
    private Image ghost6;
    protected static boolean pacLeft;
    protected static boolean pacRight;
    protected static boolean pacUp;
    protected static boolean pacDown;
    protected static boolean g1Left, g1Right, g1Up, g1Down ;
    protected static boolean g2Left, g2Right, g2Up, g2Down ;
    protected static boolean g3Left, g3Right, g3Up, g3Down ;
    protected static boolean g4Left, g4Right, g4Up, g4Down ;;
    private Timer timer;
    protected static JLabel scoreLabel;
    private short mouth;
    Random rand;
    
    
    
    public Grid() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);        
        d = new Dimension(400, 400);
        setPreferredSize(d);
        dotsX = new short[d.width / STEP_SIZE];        
        dotsY = new short[d.height / STEP_SIZE];
        dots = new short[d.width / STEP_SIZE][d.height / STEP_SIZE];
        pacX = (STEP_SIZE /2 )* 3;
        pacY = (STEP_SIZE / 2 ) * 3 ;
        ghost1X = (STEP_SIZE /2 )* 7;
        ghost1Y = (STEP_SIZE /2 )* 7;
        ghost2X = (STEP_SIZE /2 )* 7;
        ghost2Y = (STEP_SIZE /2 )* 7;
        ghost3X = (STEP_SIZE /2 )* 3;
        ghost3Y = (STEP_SIZE /2 )* 3;
        ghost4X = (STEP_SIZE /2 )* 4;
        ghost4Y = (STEP_SIZE /2 )* 4;
        ghost5X = (STEP_SIZE /2 )* 5;
        ghost5Y = (STEP_SIZE /2 )* 5;
        ghost6X = (STEP_SIZE /2 )* 6;
        ghost6Y = (STEP_SIZE /2 )* 6;
        timer = new Timer(180, this);
        score = 0;
        scoreLabel = new JLabel("Score: " + Grid.score);
        mouth = 0;
        rand = new Random();
        
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
        drawGhosts(gd);
               
        Toolkit.getDefaultToolkit().sync();    
        gd.dispose();
    }
    
    public void drawBorder(Graphics2D gd) {
        gd.setColor(Color.green);
        gd.setStroke(new BasicStroke(2));
        gd.drawRect(0, 0, d.width, d.height);     
        
    }
    
    public void drawDots(Graphics2D gd) {
        
      for (int i = 1; i < d.width / STEP_SIZE; i++) {
            for (int j = 1; j < d.height / STEP_SIZE; j++) {
                if ( dots[j][i] != -1 ) {
                    gd.fillRect(dotsX[j]  , dotsY[i], 2, 2);
                }
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
        
        for (int i = 1; i < d.width / STEP_SIZE; i++) {
            for (int j = 1; j < d.height / STEP_SIZE; j++) {
                dots[i][j] = 0;
            }
        }
    }
    
    private void loadImages()  {
        try {
            pacRightImage = new ImageIcon(Grid.class.getResource("resources/pacright.png")).getImage();   
            pacLeftImage = new ImageIcon(Grid.class.getResource("resources/pacleft.png")).getImage();   
            pacUpImage = new ImageIcon(Grid.class.getResource("resources/pacup.png")).getImage();  
            pacDownImage = new ImageIcon(Grid.class.getResource("resources/pacdown.png")).getImage();  
            pacRightClosedImage = new ImageIcon(Grid.class.getResource("resources/closedright.png")).getImage();
            pacLeftClosedImage = new ImageIcon(Grid.class.getResource("resources/closedleft.png")).getImage();
            pacUpClosedImage = new ImageIcon(Grid.class.getResource("resources/closedup.png")).getImage();
            pacDownClosedImage = new ImageIcon(Grid.class.getResource("resources/closeddown.png")).getImage();
            ghost1 = new ImageIcon(Grid.class.getResource("resources/ghost1.png")).getImage();
            ghost2 = new ImageIcon(Grid.class.getResource("resources/ghost2.png")).getImage();
            ghost3 = new ImageIcon(Grid.class.getResource("resources/ghost3.png")).getImage();
            ghost4 = new ImageIcon(Grid.class.getResource("resources/ghost4.png")).getImage();
            ghost5 = new ImageIcon(Grid.class.getResource("resources/ghost5.png")).getImage();
            ghost6 = new ImageIcon(Grid.class.getResource("resources/ghost6.png")).getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Can't find images...");
        }
    }
    
    public void drawPacman(Graphics2D gd) {
        if (pacRight) {
            if (mouth % 2 == 0) {
                gd.drawImage(pacRightImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacRightClosedImage, pacX, pacY, this);
            }
            mouth++;
        }
        if (pacLeft) {
            if (mouth % 2 == 0) {
                gd.drawImage(pacLeftImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacLeftClosedImage, pacX, pacY, this);
            }
            mouth++;
        }
        if (pacDown) {
            if (mouth % 2 == 0) {
                gd.drawImage(pacDownImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacDownClosedImage, pacX, pacY, this);
            }
            mouth++;
        }
        if (pacUp) {
            if (mouth % 2 == 0) {
                gd.drawImage(pacUpImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacUpClosedImage, pacX, pacY, this);
            }
            mouth++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chooseGhostsDirection();
        moveAllGhosts();
        movePacman();
        checkPlumes();
        
       
        repaint();
    }
    
    public void movePacman() {
        
        if (pacLeft && pacX > STEP_SIZE / 2) {
            pacX -= STEP_SIZE;
        }
        
        if (pacRight && pacX <  ( d.width - ( STEP_SIZE / 2 + STEP_SIZE ) ) ) {
            pacX += STEP_SIZE;
        }
        
        if (pacUp && pacY > STEP_SIZE / 2) {
            pacY -= STEP_SIZE;
        }
        
        if (pacDown && pacY < ( d.height - (STEP_SIZE / 2 + STEP_SIZE))) {
            pacY += STEP_SIZE;
        }
    }
    
    public void checkPlumes() {
        
        int px =( pacX + STEP_SIZE / 2) / STEP_SIZE ;
        int py = (pacY + STEP_SIZE / 2) / STEP_SIZE ;        
        if (dots[px][py] != -1) {
            dots[px][py] =  -1;
            score++;
            scoreLabel.setText("Score: " + score);
        }
    }
    
    public void drawGhosts(Graphics2D gd) {
        gd.drawImage(ghost1, ghost1X, ghost1Y, this);
        gd.drawImage(ghost2, ghost2X, ghost2Y, this);
        gd.drawImage(ghost3, ghost3X, ghost3Y, this);
        gd.drawImage(ghost4, ghost4X, ghost4Y, this);
        gd.drawImage(ghost5, ghost5X, ghost5Y, this);
        gd.drawImage(ghost6, ghost6X, ghost6Y, this);
    }
    
    public void chooseGhostsDirection() {
        int direction1 = rand.nextInt(4);
        int direction2 = rand.nextInt(4);
        int direction3 = rand.nextInt(4);
        int direction4 = rand.nextInt(4);
        switch (direction1) {
            case 0 : g1Up = true; 
                     g1Down = false;
                     g1Left = false;
                     g1Right = false;
                break;
                
            case 1 : g1Up = false; 
                     g1Down = false;
                     g1Left = false;
                     g1Right = true;                    
                     
                break;
                
            case 2 : g1Up = false; 
                     g1Down = true;
                     g1Left = false;
                     g1Right = false;
                     
                     
                break;
                
            case 3 : g1Up = false; 
                     g1Down = false;
                     g1Left = true;
                     g1Right = false;
                     
                     
                break;
       }
       
      
     //   System.out.println(direction2);
       switch ( direction2) {
           case 0 :                      
                     g2Up = false; 
                     g2Down = false;
                     g2Left = false;
                     g2Right = true;
                break;
                
            case 1 : {                     
                     g2Up = true; 
                     g2Down = false;
                     g2Left = false;
                     g2Right = false; 
                break;
            }
                
            case 2 :                      
                     g2Up = false; 
                     g2Down = false;
                     g2Left = true;
                     g2Right = false;
                break;
                
            case 3 :                      
                     g2Up = false; 
                     g2Down = true;
                     g2Left = false;
                     g2Right = false;
                break;
       }
       
       switch (direction3) {
            case 0 :                      
                     g3Up = false; 
                     g3Down = true;
                     g3Left = false;
                     g3Right = false;
                break;
                
            case 1 :                     
                     g3Up = false; 
                     g3Down = false;
                     g3Left = false;
                     g3Right = true;
                break;
                
            case 2 :                      
                     g3Up = true; 
                     g3Down = false;
                     g3Left = false;
                     g3Right = false;
                break;
                
            case 3 :                      
                     g3Up = false; 
                     g3Down = false;
                     g3Left = true;
                     g3Right = false;
                break;
       }
           
       
    }
    
    public void moveAllGhosts() {
        moveGhost1();
        moveGhost2();
        moveGhost3();
    }
    
    public void moveGhost1() {
        
        if ( g1Left && ghost1X > STEP_SIZE ) {
            ghost1X -= STEP_SIZE;
        }
        
        if ( g1Right && ghost1X <  ( d.width - ( STEP_SIZE / 2 + STEP_SIZE ) ) ) {
            ghost1X += STEP_SIZE;
        }
        
        if ( g1Up && ghost1Y > STEP_SIZE / 2) {
            ghost1Y -= STEP_SIZE;
        }
        
        if (g1Down && ghost1Y < ( d.height - (STEP_SIZE / 2 + STEP_SIZE))) {
            ghost1Y += STEP_SIZE;
        }
    }
    
    public void moveGhost2() {
        if ( g2Left && ghost2X > STEP_SIZE / 2) {
            ghost2X -= STEP_SIZE;
        }
        
        if ( g2Right && ghost2X <  ( d.width - ( STEP_SIZE / 2 + STEP_SIZE ) ) ) {
            ghost2X += STEP_SIZE;
        }
        
        if ( g2Up && ghost2Y > STEP_SIZE / 2) {
            ghost2Y -= STEP_SIZE;
        }
        
        if (g2Down && ghost2Y < ( d.height - (STEP_SIZE / 2 + STEP_SIZE))) {  
            ghost2Y += STEP_SIZE;
        }
    }
    
    public void moveGhost3() {
        if ( g3Left && ghost3X > STEP_SIZE / 2) {
            ghost3X -= STEP_SIZE;
        }
        
        if ( g3Right && ghost3X <  ( d.width - ( STEP_SIZE / 2 + STEP_SIZE ) ) ) {
            ghost3X += STEP_SIZE;
        }
        
        if ( g3Up && ghost3Y > STEP_SIZE / 2) {
            ghost3Y -= STEP_SIZE;
        }
        
        if (g3Down && ghost3Y < ( d.height - (STEP_SIZE / 2 + STEP_SIZE))) {  
            ghost3Y += STEP_SIZE;
        }
    }
    
    
    
    
}


class TAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Grid.pacUp = true;
            Grid.pacDown = false;
            Grid.pacRight = false;
            Grid.pacLeft = false;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Grid.pacUp = false;
            Grid.pacDown = true;
            Grid.pacRight = false;
            Grid.pacLeft = false;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            Grid.pacUp = false;
            Grid.pacDown = false;
            Grid.pacRight = false;
            Grid.pacLeft = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Grid.pacUp = false;
            Grid.pacDown = false;
            Grid.pacRight = true;
            Grid.pacLeft = false;
        }     
    }
}
