import javax.swing.*;
// import javax.swing.Action;
import javax.swing.JButton;
// import javax.swing.JLabel;
import java.awt.CardLayout;
// import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// -------------------------------
// NOT CURRENTLY USING THIS FILE, ALL OF THIS CODE IS INTEGRATED INTO MainPanel.java
// -------------------------------

public class CartPanel extends JPanel {

    public CardLayout CartLayout;
    public JPanel cartPanel;

    /** 
    @param void Constructor
    @return CartPanel Obj.
    */
    public CartPanel() {
        CartLayout = new CardLayout();
        setLayout(CartLayout);
        setPreferredSize(new Dimension(1100, 700));

        // cart panel
        JPanel cartPanel = new JPanel();

        JButton submit_Order_Button = new JButton("Submit Order");
        submit_Order_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // showMainPanel("entreesPanel");
                System.out.println("Order submitted");
                // SQL statement that detracts from inventory
            }
        });

        JButton reset_Order_Button = new JButton("Reset Order");
        reset_Order_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Order reset");
                // load checkout stuff to start order over
            }
        });

        cartPanel.add(submit_Order_Button);
        cartPanel.add(reset_Order_Button);
        add(cartPanel, "cartPanel");
    }
}