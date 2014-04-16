


package pman;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;




public class PMan extends JFrame {

    public PMan() {
        add(new Grid());
       // setPreferredSize(new Dimension(440, 460));
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 3, 
                Toolkit.getDefaultToolkit().getScreenSize().height / 10);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       // setResizable(false);
        pack();
        
    }
   
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                PMan pm = new PMan();
                pm.setVisible(true);
            }
        });
        
    }

}
