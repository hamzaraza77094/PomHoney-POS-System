import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** 
 * Class used to hold the buttons to switch between different menus.
 * @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick 
*/
public class NavPanel extends JPanel{

    JButton buttons[] = new JButton[5];
    JLabel tempLabel;

    /**
     * Creates a side panel on the right of the program that will allow the user to navigate the program. Takes `isManger` to determine if the user is a manager and displays
     * the correct buttons for the user based on employeement level.
     * @param isManager
     * @return JPanel
     */
    public NavPanel(boolean isManager){
        // boolean isManager = false;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c = setGridBagConstraints(c);
        if (isManager) {
        for (int i = 1; i <= 5; i++){
            JButton newButton = new JButton("Button " + i);
            c.gridy = i; // Position the first button at row i
            buttons[i - 1] = newButton;
            add(newButton, c);

            if (i == 1) { // Add ActionListener to second button
                newButton.setText("Cart");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Replace the right panel with the manager panel
                        GUI gui = (GUI) SwingUtilities.getWindowAncestor(NavPanel.this);
                        JSplitPane splitPane = (JSplitPane) gui.getContentPane().getComponent(0);
                        splitPane.setRightComponent(new CartFeature());
                    }
                });
            }
            if (i == 2) { // Add ActionListener to second button
                newButton.setText("Manager");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Replace the right panel with the manager panel
                        GUI gui = (GUI) SwingUtilities.getWindowAncestor(NavPanel.this);
                        JSplitPane splitPane = (JSplitPane) gui.getContentPane().getComponent(0);
                        splitPane.setRightComponent(new ManagerPanel());
                    }
                });
            }
            if (i == 3) { // Add ActionListener to second button
                newButton.setText("Sales");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Replace the right panel with the manager panel
                        GUI gui = (GUI) SwingUtilities.getWindowAncestor(NavPanel.this);
                        JSplitPane splitPane = (JSplitPane) gui.getContentPane().getComponent(0);
                        splitPane.setRightComponent(new SalesPanel());
                    }
                });
            }
            if (i == 4) { // Add ActionListener to second button
                newButton.setText("Analytics");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Replace the right panel with the manager panel
                        GUI gui = (GUI) SwingUtilities.getWindowAncestor(NavPanel.this);
                        JSplitPane splitPane = (JSplitPane) gui.getContentPane().getComponent(0);
                        splitPane.setRightComponent(new AnalysisPanel());
                    }
                });
            }
            else if (i == 5) { // Add ActionListener to fifth button
                newButton.setText("Logout");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Close the current window and open the login page to restart the program
                        Window window = SwingUtilities.getWindowAncestor(NavPanel.this);
                        window.dispose();
                        new Login();
                    }
                });
            }
        }
    } 
    else {
        for (int i = 1; i <= 3; i++){
            JButton newButton = new JButton("Button " + i);
            c.gridy = i; // Position the first button at row i
            buttons[i - 1] = newButton;
            add(newButton, c);

            if (i == 1) { // Add ActionListener to second button
                newButton.setText("Cart");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Replace the right panel with the manager panel
                        GUI gui = (GUI) SwingUtilities.getWindowAncestor(NavPanel.this);
                        JSplitPane splitPane = (JSplitPane) gui.getContentPane().getComponent(0);
                        splitPane.setRightComponent(new CartFeature());
                    }
                });
            }
            if (i == 2) { // Add ActionListener to second button
                newButton.setText("Sales");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Replace the right panel with the manager panel
                        GUI gui = (GUI) SwingUtilities.getWindowAncestor(NavPanel.this);
                        JSplitPane splitPane = (JSplitPane) gui.getContentPane().getComponent(0);
                        splitPane.setRightComponent(new SalesPanel());
                    }
                });
            }
            else if (i == 3) { // Add ActionListener to fifth button
                newButton.setText("Logout");
                newButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Close the current window and open the login page to restart the program
                        Window window = SwingUtilities.getWindowAncestor(NavPanel.this);
                        window.dispose();
                        new Login();
                    }
                });
            }
        }
    }

        System.out.print("Nav Panel Created\n");
    } // Constructor for NavPanel

    /**
     * Used to set the parameters of the 'Grid Bag Layout'.
     * @param c
     * @return GridBagConstriants
     */
    private GridBagConstraints setGridBagConstraints(GridBagConstraints c){
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0; // Position the first button at column 0
        c.insets = new Insets(10, 0, 10, 0); // Add some spacing between buttons
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;

        return c;
    } // Moved constraints to clean code
}
