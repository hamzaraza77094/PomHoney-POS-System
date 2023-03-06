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
        
    //Checkout Panel
        JPanel checkoutPanel = new JPanel(); // Make wrapper panel
        CardLayout checkCardLayout = new CardLayout();
        checkoutPanel.setLayout(checkCardLayout); // Make it so the sub panels can be selected and take up the whole space
        JButton entrees_button = new JButton("Entrees");
        entrees_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("entreesSubMenu"); //TODO: Give correct options
                System.out.println("Entrees SubMenu Button was pressed.");
            }
        });
        checkoutPanel.add(entrees_button);
        JButton extras_button = new JButton("Extras");
        extras_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("extrasSubMenu"); //TODO: Give correct options
                System.out.println("Extras SubMenu Button was pressed.");
            }
        });
        checkoutPanel.add(extras_button);
        JButton drinks_button = new JButton("Drinks");
        drinks_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("drinksEntreeMenu"); //TODO: Give correct options
                System.out.println("Drinks SubMenu Button was pressed.");
            }
        });      
        checkoutPanel.add(drinks_button);

        JLabel temp = new JLabel("This is the Checkout Panel");

        // entreesPanel = createEntreePanel();
        // extrasPanel = createExtrasPanel();
        // drinksPanel = createDrinksPanel();

        // checkoutPanel.add(entreesPanel, "entreesSubPanel");
        // checkoutPanel.add(extrasPanel, "extrasSubPanel");
        // checkoutPanel.add(drinksPanel, "drinksSubPanel");

        // showCheckoutPanel("extrasSubPanel");
        checkoutPanel.add(temp);
        add(checkoutPanel, "checkoutPanel");
    //Entree SubPanel
        JPanel entreePanel = new JPanel();
        JButton gyroButton = new JButton("Gyro");
        gyroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("extrasSubMenu"); //TODO: Give correct options
                System.out.println("Gyro Button was pressed.");
            }
        });
        JButton bowlButton = new JButton("Bowl");
        bowlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("extrasSubMenu"); //TODO: Give correct options
                System.out.println("Bowl Button was pressed.");
            }
        });
        entreePanel.add(gyroButton);
        entreePanel.add(bowlButton);
        add(entreePanel, "entreeSubMenu");
        
    //Extras SubPanel
        JPanel extrasPanel = new JPanel();
        
        JButton hum_fria = new JButton("Hummas & Pita");
        hum_fria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("drinksSubMenu"); //TODO: Give correct options
                System.out.println("Hummas & Pita was pressed.");
            }
        });
        JButton dos_falafels = new JButton("2 Falafels");
        dos_falafels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("drinksSubMenu"); //TODO: Give correct options
                System.out.println("2 Falafells was pressed.");
            }
        });
        JButton x_protein = new JButton("Extra Protein");
        hum_fria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("drinksSubMenu"); //TODO: Give correct options
                System.out.println("Extra Protein was pressed.");
            }
        });
        JButton x_dressing = new JButton("Extra Dressing");
        x_dressing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("drinksSubMenu"); //TODO: Give correct options
                System.out.println("Extra Dressing was pressed.");
            }
        });
        
        extrasPanel.add(hum_fria);
        extrasPanel.add(dos_falafels);
        extrasPanel.add(x_protein);
        extrasPanel.add(x_dressing);
        add(extrasPanel, "extrasSubMenu");
        System.out.print("Extras Panel Created.");

    //Drinks SubPanel
        JPanel drinksPanel = new JPanel();
        JButton soft_drink = new JButton("Soft Drink");
        soft_drink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("entreesSubMenu"); //TODO: Give correct options
                System.out.println("Drinks SubMenu button was pressed.");
            }
        });
        drinksPanel.add(soft_drink);
        add(drinksPanel, "drinksSubMenu");
        // checkoutPanel = createCheckoutPanel();
        showMainPanel("drinksSubMenu");
        System.out.print("Main Panel Created\n");
    } // Constructor of Main Panel


    public void showMainPanel(String panelName){
        mainCardLayout.show(this, panelName);
    }

}
