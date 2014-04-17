
package pman;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class PMan extends JFrame {

    public PMan() {
        setTitle("JPacman");
        add(new Grid(), BorderLayout.CENTER);
        add(new InfoPanel(), BorderLayout.SOUTH);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 3, 
                Toolkit.getDefaultToolkit().getScreenSize().height / 10
        );
        
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
