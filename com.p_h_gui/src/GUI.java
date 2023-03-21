import java.awt.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
* This class sets up the GUI display for our application
* @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class GUI extends JFrame{

    private NavPanel navPanel;
    private ManagerPanel managerPanel = null;
        
    /**
    * constructor for the GUI panel, displays manager panel and different buttons if a manager is using the program
    * @param isManager a boolean for if the user is a manager or not (affects what will be displayed)
    * @return JPanel
    */
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
    
    /**
    * takes in a grid constraint, modifies the settings within it, then returns the updated grid constraint
    * @param GridBagConstraint 
    * @return GridBagConstraint 
    */
    private GridBagConstraints setGridBagConstraints(GridBagConstraints c){
        System.out.println("GRIDBAGCONSTRAINTS USED");
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 1/6.0;
        c.gridx = 0; // Position the first button at column 0
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = GridBagConstraints.REMAINDER;

        return c;
    } // Moved constraints to clean code

    /**
    * Frame creation settings for the GUI panel
    * @param none
    * @return none
    */
    private void setGUIFrame(){
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setVisible(true);
    } // Cleaning up GUI Constructor by moving some of the frame creation stuff outside of the constructor

}
