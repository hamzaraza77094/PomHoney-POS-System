import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.CardLayout;
// import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


public class MainPanel extends JPanel{
    
    public CardLayout mainCardLayout;
    public CardLayout checokCardLayout;
    // private JPanel subPanel1;
    // private JPanel subPanel2;

    public JPanel checkoutPanel;
    public JPanel entreesPanel;
    public JPanel extrasPanel;
    public JPanel drinksPanel;


    public MainPanel(){
        mainCardLayout = new CardLayout();
        setLayout(mainCardLayout);
        setPreferredSize(new Dimension(1100, 700));
        
        
        entreesPanel = createEntreePanel();
        extrasPanel = createExtrasPanel();
        drinksPanel = createDrinksPanel();
        checkoutPanel = createCheckoutPanel();

        checkoutPanel.add(entreesPanel, "entreesSubPanel");
        checkoutPanel.add(extrasPanel, "extrasSubPanel");
        checkoutPanel.add(drinksPanel, "drinksSubPanel");

        add(checkoutPanel, "checkoutPanel");
        showCheckoutPanel("extrasSubPanel");
        // subPanel1 = new JPanel();
        // JLabel tempLabel1 = new JLabel("HOwdy!");
        // JButton switchToPanel2Button = new JButton("Switch to Panel 2");
        // switchToPanel2Button.addActionListener(new ActionListener() {
        //         @Override
        //         public void actionPerformed(ActionEvent e){
        //             showMainPanel("subPanel2");
        //         }
        //     }
        // );
        // subPanel1.add(tempLabel1);
        // subPanel1.add(switchToPanel2Button);

        // subPanel2 = new JPanel();
        // JLabel tempLabel2 = new JLabel("Bitch!");
        // JButton switchToPanel1Button = new JButton("Switch to Panel 1");
        // switchToPanel1Button.addActionListener(new ActionListener() {
        //         @Override
        //         public void actionPerformed(ActionEvent e){
        //             showMainPanel("subPanel1");
        //         }
        //     }
        // );
        // subPanel2.add(tempLabel2);
        // subPanel2.add(switchToPanel1Button);

        // add(subPanel1, "subPanel1");
        // add(subPanel2, "subPanel2");
        
        // showPanel("subPanel1");
        System.out.print("Main Panel Created\n");
    } // Constructor of Main Panel


    public void showMainPanel(String panelName){
        mainCardLayout.show(this, panelName);
    }
    public void showCheckoutPanel(String panelName){
        checokCardLayout.show(this, panelName);
    }

    public JPanel createCheckoutPanel(){
        //Idea is to have 3 submenus that loop back on eachother.
        JPanel checkoutPanel = new JPanel(); // Make wrapper panel
        CardLayout checkCardLayout = new CardLayout();
        checkoutPanel.setLayout(checkCardLayout); // Make it so the sub panels can be selected and take up the whole space

        JLabel temp = new JLabel("This is the Checkout Panel");
        checkoutPanel.add(temp);
        //Entree SubPanel
        // JPanel entreePanel = new JPanel();
        // JButton gyroButton = new JButton();
        // JButton bowlButton = new JButton();
        // entreePanel.add(gyroButton);
        // entreePanel.add(bowlButton);
        // return entreePanel;   
        //checkoutPanel.add(createEntreePanel(), "entreeSubMenu");
        
        //Extras SubPanel
        // JPanel extrasPanel = new JPanel();
        // JButton hum_fria = new JButton();
        // JButton dos_falafels = new JButton();
        // JButton x_protein = new JButton();
        // JButton x_dressing = new JButton();
        // extrasPanel.add(hum_fria);
        // extrasPanel.add(dos_falafels);
        // extrasPanel.add(x_protein);
        // extrasPanel.add(x_dressing);
        // return extrasPanel;
        //checkoutPanel.add(createExtrasPanel(), "extrasSubMenu");

        //Drinks SubPanel
        // JPanel drinksPanel = new JPanel();
        // JButton soft_drink = new JButton();
        // drinksPanel.add(soft_drink);
        //checkoutPanel.add(createDrinksPanel(), "drinksSubMenu");
        
        return checkoutPanel;

    }
    public JPanel createEntreePanel(){
        JPanel entreePanel = new JPanel();

        //Entree Panel Buttons w/ Action Listener. Currently set to go to extras submenu
        JButton gyroButton = new JButton("Gyro");
        gyroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showCheckoutPanel("extrasSubPanel"); //TODO: Give correct options
                System.out.println("Gyro Button was pressed.");
            }
        });
        JButton bowlButton = new JButton("Bowl");
        bowlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showCheckoutPanel("extrasSubPanel"); //TODO: Give correct options
                System.out.println("Bowl Button was pressed.");
            }
        });

        entreePanel.add(gyroButton);
        entreePanel.add(bowlButton);
        return entreePanel;
    }
    public JPanel createExtrasPanel(){
        JPanel extrasPanel = new JPanel();
        JButton hum_pita= new JButton("Hummas & Pita");
        JButton dos_falafels = new JButton("2 Falafels");
        JButton x_protein = new JButton("Extra Protein");
        JButton x_dressing = new JButton("Extra Dressing");
        extrasPanel.add(hum_pita);
        extrasPanel.add(dos_falafels);
        extrasPanel.add(x_protein);
        extrasPanel.add(x_dressing);
        return extrasPanel;
    }
    public JPanel createDrinksPanel(){
        JPanel drinksPanel = new JPanel();
        JButton soft_drink = new JButton("Soft Drink");
        drinksPanel.add(soft_drink);
        return drinksPanel;
    }

}
