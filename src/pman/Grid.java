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
    protected static JLabel scoreLabel;  
    private final short STEP_SIZE = 20;    
    protected static short score;
    private short mouthPosition;
    private byte ghostNumber;
    private short pacX;
    private short pacY;
    private short[] dotsX;
    private short[] dotsY;
    private short[][] dots;
    private short [] ghostX, ghostY;
    private Dimension d;
    private Image pacRightImage;
    private Image pacLeftImage;
    private Image pacDownImage;
    private Image pacUpImage;
    private Image pacRightClosedImage;
    private Image pacLeftClosedImage;
    private Image pacUpClosedImage;
    private Image pacDownClosedImage;
    private Image[] ghostImageArray;
    protected static boolean pacLeft, pacRight, pacUp, pacDown;
    private  boolean ghostUp, ghostRight, ghostDown, ghostLeft;
    private boolean isDeath;
        
    private Timer timer;
    Random rand;
    
    
    
    
    public Grid() {
        
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);        
        d = new Dimension(400, 400);
        setPreferredSize(d);
        scoreLabel = new JLabel("Score: " + Grid.score);
        initGame();
        
        timer = new Timer(180, this);
        timer.start();
    }
    
    private void initGame() {
        
        dotsX = new short[d.width / STEP_SIZE];        
        dotsY = new short[d.height / STEP_SIZE];
        dots = new short[d.width / STEP_SIZE][d.height / STEP_SIZE];
        pacX = (STEP_SIZE /2 )* 21;
        pacY = (STEP_SIZE / 2 ) * 21 ;
        mouthPosition = 0;
        score = 0;
        rand = new Random();  
        ghostNumber = 6;
        ghostImageArray = new Image[ghostNumber];
        ghostX = new short[ghostNumber];
        ghostY = new short[ghostNumber];
        isDeath = false;
        generateDots();
        loadImages();
    }
        
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gd = (Graphics2D) g;
        super.paintComponent(gd);
        drawBorder(gd);        
        drawDots(gd);
        drawPacman(gd);
        drawGhosts(gd); 
        
        if (isDeath) {
            gd.setColor(Color.red);
            gd.drawString("YOU ARE DEATH !!!", 150, 200);
            timer.stop();
        }
               
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
            
            for (int i = 0; i < ghostImageArray.length; i++) {
                ghostImageArray[i] = new ImageIcon(Grid.class.getResource("resources/ghost1.png")).getImage();
                ghostX[i] = (STEP_SIZE /2 )* 7;
                ghostY[i] = (STEP_SIZE /2 )* 7;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Can't find images...");
        }
    }
    
    public void drawPacman(Graphics2D gd) {
        
        if (pacRight) {
            if (mouthPosition % 2 == 0) {
                gd.drawImage(pacRightImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacRightClosedImage, pacX, pacY, this);
            }
            mouthPosition++;
        }
        if (pacLeft) {
            if (mouthPosition % 2 == 0) {
                gd.drawImage(pacLeftImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacLeftClosedImage, pacX, pacY, this);
            }
            mouthPosition++;
        }
        if (pacDown) {
            if (mouthPosition % 2 == 0) {
                gd.drawImage(pacDownImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacDownClosedImage, pacX, pacY, this);
            }
            mouthPosition++;
        }
        if (pacUp) {
            if (mouthPosition % 2 == 0) {
                gd.drawImage(pacUpImage, pacX, pacY, this);
            }
            else {
                gd.drawImage(pacUpClosedImage, pacX, pacY, this);
            }
            mouthPosition++;
        }
        mouthPosition = mouthPosition % 2 == 0 ? (short) 0 :(short) 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveGhosts();
        movePacman();
        checkPlumes();  
        checkDeath();
       
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
        
        for (int i = 0; i < ghostImageArray.length; i++) {
            gd.drawImage(ghostImageArray[i], ghostX[i], ghostY[i], this);
        }
    }
    
    public void moveGhosts() {
        
        for (int i = 0; i < ghostImageArray.length; i++) {
            short dir = (short)rand.nextInt(4);            
            switch (dir) {               
                
                case 0 : ghostUp = true; 
                         ghostDown = false;
                         ghostLeft = false;
                         ghostRight = false;                         
                   break;
                
                case 1 : ghostUp = false; 
                         ghostDown = false;
                         ghostLeft = false;
                         ghostRight = true;  
                    break;
                
                case 2 : ghostUp = false; 
                         ghostDown = true;
                         ghostLeft = false;
                         ghostRight = false;   
                    break;
                
                case 3 : ghostUp = false; 
                         ghostDown = false;
                         ghostLeft = true;
                         ghostRight = false;    
                    break;
            }
            if ( ghostLeft && ghostX[i] > STEP_SIZE ) {
                ghostX[i] -= STEP_SIZE;
            }
            if ( ghostRight && ghostX[i] <  ( d.width - ( STEP_SIZE / 2 + STEP_SIZE ) ) ) {
                ghostX[i] += STEP_SIZE;
            }
            if ( ghostUp && ghostY[i] > STEP_SIZE / 2) {
                ghostY[i] -= STEP_SIZE;
            }
        
            if (ghostDown && ghostY[i] < ( d.height - (STEP_SIZE / 2 + STEP_SIZE))) {
                ghostY[i] += STEP_SIZE;
            }            
       }
    } 
    
    
    public void checkDeath() {
        for (int i = 0; i < ghostImageArray.length; i++) {
            if (ghostX[i] == pacX && ghostY[i] == pacY) {
                isDeath = true;
            }
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
