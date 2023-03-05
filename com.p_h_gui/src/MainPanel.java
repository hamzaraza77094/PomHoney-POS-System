import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


public class MainPanel extends JPanel{
    
    public CardLayout cardLayout;
    private JPanel subPanel1;
    private JPanel subPanel2;


    public MainPanel(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(1100, 700));
        
        subPanel1 = new JPanel();
        JLabel tempLabel1 = new JLabel("HOwdy!");
        JButton switchToPanel2Button = new JButton("Switch to Panel 2");
        switchToPanel2Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    showPanel("subPanel2");
                }
            }
        );
        subPanel1.add(tempLabel1);
        subPanel1.add(switchToPanel2Button);

        subPanel2 = new JPanel();
        JLabel tempLabel2 = new JLabel("Bitch!");
        JButton switchToPanel1Button = new JButton("Switch to Panel 1");
        switchToPanel1Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    showPanel("subPanel1");
                }
            }
        );
        subPanel2.add(tempLabel2);
        subPanel2.add(switchToPanel1Button);

        add(subPanel1, "subPanel1");
        add(subPanel2, "subPanel2");
        
        // showPanel("subPanel1");
        System.out.print("Main Panel Created\n");
    } // Constructor of Main Panel


    public void showPanel(String panelName){
        cardLayout.show(this, panelName);
    }


}
