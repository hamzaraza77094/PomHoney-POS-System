import java.awt.Color;
// import java.awt.Dimension;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.FlowLayout;

// import java.awt.CardLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

public class GUI{
    private JFrame frame;
    private MainPanel mainPanel;
    private NavPanel navPanel;
   
    public GUI(){
        frame = new JFrame();

        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        Border blueline = BorderFactory.createLineBorder(Color.blue);
        // navPanel = new NavPanel();

        mainPanel = new MainPanel(); // This panel SHOULD CHANGE based on what is needed by the user.
        navPanel = new NavPanel();

        // frame.add(navPanel.navPanel);
        frame.add(navPanel);
        frame.add(mainPanel);
        

        mainPanel.setBorder(blackline);
        navPanel.setBorder(blueline);
        // mainPanel.showPanel("subPanel1"); 

        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        
    } // Contructor


    

    


}
