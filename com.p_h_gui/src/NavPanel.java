import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

public class NavPanel extends JPanel{

    JButton button1;
    JLabel tempLabel;

    public NavPanel(){
        BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(b);

        for (int i = 1; i <=5; i++){
            add(new JButton("Button " + i));
        }

        System.out.print("Nav Panel Created\n");
    } // Constructor for NavPanel
}
