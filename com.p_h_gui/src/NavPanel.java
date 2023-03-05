import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public class NavPanel extends JPanel{

    JButton buttons[] = new JButton[5];
    JLabel tempLabel;

    public NavPanel(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c = setGridBagConstraints(c);

        for (int i = 1; i <=5; i++){
            JButton newButton = new JButton("Button " + i);
            c.gridy = i; // Position the first button at row i
            buttons[i - 1] = newButton;
            add(newButton, c);
        }

        System.out.print("Nav Panel Created\n");
    } // Constructor for NavPanel

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
