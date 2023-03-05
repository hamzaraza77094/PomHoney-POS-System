import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;



public class GUI extends JFrame{
    // private JFrame frame;
    private MainPanel mainPanel;
    private NavPanel navPanel;
   
    public GUI(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c = setGridBagConstraints(c);

        navPanel = new NavPanel();
        navPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(navPanel, c);

        mainPanel = new MainPanel(); // This panel SHOULD CHANGE based on what is needed by the user.
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        c.gridx = 1;
        c.gridy = 0;
        c. gridwidth = 2;
        add(mainPanel, c);


        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setVisible(true);
        
    } // Contructor
    private GridBagConstraints setGridBagConstraints(GridBagConstraints c){
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 1/6.0;
        c.gridx = 0; // Position the first button at column 0
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.REMAINDER;

        return c;
    } // Moved constraints to clean code

}
