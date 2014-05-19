 
package pman;

import javax.swing.JPanel;

 /**
  * this represent a panel to hold the curretn score result
  * @author Ivan Bahchevanov
  */
public class InfoPanel extends JPanel {   
    
    public InfoPanel() {
        add(Grid.scoreLabel);
    }
}
