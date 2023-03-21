import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// import javax.swing.border.Border;



public class GUI extends JFrame{
    // private JFrame frame;
    // private MainPanel mainPanel;
    private NavPanel navPanel;
    private ManagerPanel managerPanel = null;
   
    // public GUI(){
    //     setLayout(new GridBagLayout());
    //     GridBagConstraints c = new GridBagConstraints();
    //     c = setGridBagConstraints(c);

    //     navPanel = new NavPanel();
    //     navPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //     add(navPanel, c);

    //     mainPanel = new MainPanel(); // This panel SHOULD CHANGE based on what is needed by the user.
    //     mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    //     c.gridx = 1;
    //     c.gridy = 0;
    //     c. gridwidth = 2;
    //     add(mainPanel, c);


    //     setGUIFrame();
        
    // } // Contructor
    public GUI(boolean isManager) {
        super("My Restaurant");
    
        // Create the NavPanel and ManagerPanel
        NavPanel navPanel = new NavPanel(isManager);
        if(isManager){
            ManagerPanel managerPanel = new ManagerPanel();
        }
        
    
        // Create a JSplitPane and add the NavPanel and ManagerPanel to it
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, new JPanel());
        splitPane.setDividerLocation(150); // Set the divider location to 150 pixels
    
        // Set the preferred size of the JSplitPane
        splitPane.setPreferredSize(new Dimension(800, 600));
    
        // Add the JSplitPane to the JFrame
        add(splitPane);
    
        // Set the properties of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    
        // Handle button clicks in the NavPanel
        if (isManager){
            navPanel.buttons[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    splitPane.setRightComponent(managerPanel);
                    splitPane.setDividerLocation(150);
                }
            });
        }
        
    }
    
    private GridBagConstraints setGridBagConstraints(GridBagConstraints c){
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 1/6.0;
        c.gridx = 0; // Position the first button at column 0
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.REMAINDER;

        return c;
    } // Moved constraints to clean code

    private void setGUIFrame(){
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setVisible(true);
    } // Cleaning up GUI Constructor by moving some of the frame creation stuff outside of the constructor

}
