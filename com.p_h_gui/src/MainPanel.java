import javax.swing.JPanel;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.CardLayout;
// import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;


public class MainPanel extends JPanel{
    
    // CardLayout for MainPanel
    public CardLayout mainCardLayout;
    //Sub Panels to Main Panel
    public JPanel checkoutPanel;
    public JPanel entreesPanel;
    public JPanel extrasPanel;
    public JPanel drinksPanel;


    public MainPanel(){
        mainCardLayout = new CardLayout();
        setLayout(mainCardLayout);
        setPreferredSize(new Dimension(1100, 700));
    
      //Checkout Menu TODO: MOVE TO ANOTHER CLASS TO CLEAN UP CODE
        JPanel checkoutPanel = new JPanel();
        
        JButton entrees_Button = new JButton("Entrees");
        entrees_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("entreesPanel");
            }
        });
        
        JButton extras_Button = new JButton("Extras");
        extras_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("extrasPanel");
            }
        });
        
        JButton drinks_Button = new JButton("Drinks");
        drinks_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("drinksPanel");
            }
        });
        
        checkoutPanel.add(entrees_Button);
        checkoutPanel.add(extras_Button);
        checkoutPanel.add(drinks_Button);
        add(checkoutPanel, "checkoutPanel");
        
      //Entrees Menu TODO: MOVE TO ANOTHER CLASS TO CLEAN UP CODE
        JPanel entreesPanel = new JPanel();
        
        JButton gyro_Button = new JButton("Gyro");
        gyro_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("extrasPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        JButton bowl_Button = new JButton("Bowl");
        bowl_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("extrasPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        entreesPanel.add(gyro_Button);
        entreesPanel.add(bowl_Button);
        add(entreesPanel, "entreesPanel");

      //Extras Menu TODO: MOVE TO ANOTHER CLASS TO CLEAN UP CODE
        JPanel extrasPanel = new JPanel();
        
        JButton dosFalafel_Button = new JButton("2 Falafells");
        dosFalafel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        JButton humPita_Button = new JButton("Hummas & Pita");
        humPita_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        JButton xProtein_Button = new JButton("Extra Protein");
        xProtein_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        JButton xDressing_Button = new JButton("Extra Dressing");
        xDressing_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        extrasPanel.add(dosFalafel_Button);
        extrasPanel.add(humPita_Button);
        extrasPanel.add(xProtein_Button);
        extrasPanel.add(xDressing_Button);
        add(extrasPanel, "extrasPanel");

      //Drinks Menu TODO: MOVE TO ANOTHER CLASS TO CLEAN UP CODE
        JPanel drinksPanel = new JPanel();
        
        JButton drink_Button = new JButton("Soft Drink");
        drink_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("entreesPanel");
            }
        });
        
        drinksPanel.add(drink_Button);
        add(drinksPanel, "drinksPanel");

        showMainPanel("checkoutPanel");
    } // Constructor of Main Panel

    public void showMainPanel(String panelName){
        mainCardLayout.show(this, panelName);
    }

}