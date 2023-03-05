import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class NavPanel extends JPanel{
    JPanel navPanel;

    JButton button1;
    JButton button2;
    JButton button3;
    
    JLabel tempLabel;

    public NavPanel(){
        navPanel = new JPanel();

        navPanel.add(button1 = new JButton("Button1"));
        navPanel.add(button2 = new JButton("Button2"));
        navPanel.add(button3 = new JButton("Button3"));

        navPanel.add(tempLabel = new JLabel("test"));

        System.out.print("Nav Panel Created");
    } // Constructor for NavPanel
}
