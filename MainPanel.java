import java.text.DecimalFormat;
// import javax.swing.JPanel;
import javax.swing.*;
// import javax.swing.Action;
import javax.swing.JButton;
// import javax.swing.JLabel;
import java.awt.CardLayout;
// import java.awt.Color;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.*;
import java.util.*;


public class MainPanel extends JPanel{
    /** * Creates the 'MainPanel' with all of the other subpanels that are apart of MainPanel.
    * @author Adam Vick
    * @author Jacob Parker
    * @param void There are no parameter for the input of this fuction.
    * @return mainPanel JPanel This Constructor returns the JPanel 'MainPanel' w/ managerPanel, checkoutPanel, entreesPanel, extrasPanel, drinksPanel, cartPanel, etc as subpanels accessible via card view.
    */

    // CardLayout for MainPanel
    public CardLayout mainCardLayout;
    //Sub Panels to Main Panel
    public JPanel checkoutPanel, entreesPanel, extrasPanel, drinksPanel, cartPanel, gyroPanel, bowlPanel;

    public String lastPanel; //TODO: Wanted to use this to record what the last panel was so I could have a back button.

    // Array containing the item ID's of what is added to the order
    public ArrayList<Integer> order_Items_Array = new ArrayList<Integer>(50);
    public ArrayList<Integer> order_Items_Array_copy = new ArrayList<Integer>(50);

    private Connection conn;

    String order_string = "";

    // public ArrayList<Integer> ingredients_ID_Array = new ArrayList<Integer>(50);
    // public ArrayList<Integer> ingredients_Amount_Array = new ArrayList<Float>(50);
    Integer ingredients_ID_Array[];
    Double ingredients_Amount_Array[];

    JTextField myOutput = new JTextField(100);


    public MainPanel(){
        mainCardLayout = new CardLayout();
        setLayout(mainCardLayout);
        setPreferredSize(new Dimension(1100, 700));

        // checkout panel & buttons
        // ------------------------
        JPanel checkoutPanel = new JPanel();
        
        JButton entreesPanel_Button = new JButton("Entrees");
        entreesPanel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("entreesPanel");
            }
        });
        
        JButton extrasPanel_Button = new JButton("Extras");
        extrasPanel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                lastPanel = checkoutPanel.getName();
                showMainPanel("extrasPanel");
            }
        });
        
        JButton drinksPanel_Button = new JButton("Drinks");
        drinksPanel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                lastPanel = checkoutPanel.getName();
                showMainPanel("drinksPanel");
            }
        });

        JButton viewCart_Button = new JButton("View Cart");
        viewCart_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                for (int i = 0; i < order_Items_Array.size(); ++i) {
                    if (order_Items_Array.get(i) == 1) { // chicken gyro
                        order_string += "chicken gyro";
                    } else if (order_Items_Array.get(i) == 2) { // chicken bowl
                        order_string += "chicken bowl";
                    } else if (order_Items_Array.get(i) == 3) { // hummus & pita
                        order_string += "hummus & pita";
                    } else if (order_Items_Array.get(i) == 4) { // 2 falafels
                        order_string += "2 falafels";
                    } else if (order_Items_Array.get(i) == 5) { // extra protein
                        order_string += "<--extra protein";
                    } else if (order_Items_Array.get(i) == 6) { // extra dressing
                        order_string += "extra dressing";
                    } else if (order_Items_Array.get(i) == 7) { // fountain drink
                        order_string += "fountain drink";
                    } else if (order_Items_Array.get(i) == 8) { // lamb gyro
                        order_string += "lamb gyro";
                    } else if (order_Items_Array.get(i) == 9) { // lamb bowl
                        order_string += "lamb bowl";
                    }
                    if (!(i == order_Items_Array.size() - 1)) {
                        order_string += ", ";
                    }
                }
                myOutput.setText(order_string);
                showMainPanel("cartPanel");
            }
        });
        
        checkoutPanel.add(entreesPanel_Button);
        checkoutPanel.add(extrasPanel_Button);
        checkoutPanel.add(drinksPanel_Button);
        checkoutPanel.add(viewCart_Button);
        this.add(checkoutPanel, "checkoutPanel");
        

        // entree panel & buttons
        // ----------------------
        JPanel entreesPanel = new JPanel();
        
        JButton gyro_Button = new JButton("Gyro");
        gyro_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("gyroPanel");
            }
        });
        
        JButton bowl_Button = new JButton("Bowl");
        bowl_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("bowlPanel");
            }
        });
        
        JButton back_Button = new JButton("Back");
        back_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("checkoutPanel");
            }
        });
        entreesPanel.add(gyro_Button);
        entreesPanel.add(bowl_Button);
        entreesPanel.add(back_Button);
        add(entreesPanel, "entreesPanel");

        // Panel for selecting if customer wants chicken gyro or lamb gyro
        JPanel gyroPanel = new JPanel();
        JButton back_Button5 = new JButton("Back");
        back_Button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel("entreesPanel");
            }
        });

        JButton chickenGyro_Button = new JButton("Chicken Gyro");
        chickenGyro_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_Items_Array.add(1);
            }
        });

        JButton lambGyro_Button = new JButton("Lamb Gyro");
        lambGyro_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_Items_Array.add(8);
            }
        });

        gyroPanel.add(chickenGyro_Button);
        gyroPanel.add(lambGyro_Button);
        gyroPanel.add(back_Button5);
        add(gyroPanel, "gyroPanel");


        // panel for selecting if user wants chicken bowl or lamb bowl
        JPanel bowlPanel = new JPanel();

        JButton chickenBowl_Button = new JButton("Chicken Bowl");
        chickenBowl_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_Items_Array.add(2);
            }
        });

        JButton lambBowl_Button = new JButton("Lamb Bowl");
        lambBowl_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_Items_Array.add(9);
            }
        });

        JButton back_Button6 = new JButton("Back");
        back_Button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("entreesPanel");
            }
        });

        bowlPanel.add(chickenBowl_Button);
        bowlPanel.add(lambBowl_Button);
        bowlPanel.add(back_Button6);
        add(bowlPanel, "bowlPanel");

      //Extras Menu TODO: MOVE TO ANOTHER CLASS TO CLEAN UP CODE
        JPanel extrasPanel = new JPanel();
        
        JButton dosFalafel_Button = new JButton("2 Falafells");
        dosFalafel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS    
                order_Items_Array.add(4);
            }
        });
        
        JButton humPita_Button = new JButton("Hummas & Pita");
        humPita_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS
                order_Items_Array.add(3);    
            }
        });
        
        JButton xProtein_Button = new JButton("Extra Protein");
        xProtein_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                order_Items_Array.add(5);
                // showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS    
            }
        });
        
        JButton xDressing_Button = new JButton("Extra Dressing");
        xDressing_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // showMainPanel("drinksPanel"); //TODO: ADD CORRECT ACTIONS 
                order_Items_Array.add(6);   
            }
        });
        
        JButton back_Button2 = new JButton("Back");
        back_Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("checkoutPanel");
            }
        });
        extrasPanel.add(dosFalafel_Button);
        extrasPanel.add(humPita_Button);
        extrasPanel.add(xProtein_Button);
        extrasPanel.add(xDressing_Button);
        extrasPanel.add(back_Button2);
        add(extrasPanel, "extrasPanel");

        JPanel drinksPanel = new JPanel();
        
        JButton drink_Button = new JButton("Soft Drink");
        drink_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                order_Items_Array.add(7);
            }
        });
        
        JButton back_Button3 = new JButton("Back");
        back_Button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("checkoutPanel");
            }
        });

        drinksPanel.add(drink_Button);
        drinksPanel.add(back_Button3);
        add(drinksPanel, "drinksPanel");

        lastPanel = checkoutPanel.getName();
        showMainPanel("checkoutPanel");


        cartPanel = new JPanel();

        myOutput.setText(order_string);
        cartPanel.add(myOutput);

        JButton submitOrder_Button = new JButton("Submit Order");
        submitOrder_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                // System.out.println("Calling the function here");
                double orderSubtotal = calculateTotal(getOrderItemsArray());

                double salesTax = orderSubtotal * 0.0625;
                double total = orderSubtotal + salesTax;

        // insert the order into the salesBridge table
                // TODO: Communicate with db and decrement ingredients based on preset ingredient constants for each menu item
                int current_Array_Size = order_Items_Array.size();
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon", "csce315331_epsilon_master", "EPSILON");   
                    Statement stmt = conn.createStatement();
                    String insertQuery = "INSERT INTO salesBridge (orderid, dayid, date, ordercontents, ordersubtotal, salestax, total) " +
                     "VALUES (COALESCE((SELECT MAX(orderid) FROM salesBridge), 0) + 1, " +
                             "COALESCE((SELECT MAX(dayid) FROM salesBridge WHERE date = CURRENT_DATE), " +
                                      "(SELECT COALESCE(MAX(dayid), 0) + 1 FROM salesBridge)), " +
                             "CURRENT_DATE, " +
                             "'" + order_string + "', " +
                             "'" + String.valueOf(orderSubtotal) + "', " +
                             "'" + String.valueOf(salesTax) + "', " +
                             "'" + String.valueOf(total) + "'" +
                             ")";
                    stmt.executeUpdate(insertQuery);
                } catch (SQLException ex) {
                    System.out.println("Could not open database successfully" + ex.getMessage());
                    return;
                }
                System.out.println("Opened database successfully");
        
                detractIngredients(order_Items_Array);

                order_Items_Array.clear();
                order_string = "";
                showMainPanel("checkoutPanel");
            }
        });

        JButton resetOrder_Button = new JButton("Reset Order");
        resetOrder_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // clearing array and returning to checkoutPanel (to restart order)
                order_Items_Array.clear();
                order_string = "";
                showMainPanel("checkoutPanel");
            }
        });

        JButton back_Button4 = new JButton("Back");
        back_Button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showMainPanel("checkoutPanel");
            }
        });
        
        cartPanel.add(submitOrder_Button);
        cartPanel.add(resetOrder_Button);
        cartPanel.add(back_Button4);
        this.add(cartPanel, "cartPanel");
    }

    // getter for order_Items_Array
    public ArrayList<Integer> getOrderItemsArray() {
        System.out.println("In Function");
        ArrayList<Integer> order_Items_Array_copy = new ArrayList<Integer>();
        for (Integer item : this.order_Items_Array) {
            order_Items_Array_copy.add(item);
        }
        System.out.println(order_Items_Array_copy);
        return order_Items_Array_copy;
    }

    // function for calculating total
    public double calculateTotal(ArrayList<Integer> items) {
        double subtotal = 0.0;
        for (int i = 0; i < items.size(); i++) {
            int item = items.get(i);
            if (item == 1 || item == 2 || item == 8 || item == 9) {
                subtotal += 8.09;
            } else if (item == 3 || item == 4) {
                subtotal += 3.49;
            } else if (item == 5) {
                subtotal += 2.49;
            } else if (item == 6) {
                subtotal += 0.49;
            } else if (item == 7) {
                subtotal += 2.45;
            }
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String roundedTotal = df.format(subtotal);
        double roundedTotalDouble = Double.parseDouble(roundedTotal);

        System.out.println("Function total");
        System.out.println(subtotal);
        return subtotal;
    }
    
    // function for detracting ingredients for each item ordered
    public void detractIngredients(ArrayList<Integer> order_Items) { 
        for (int i = 0; i < order_Items.size(); ++i) {
            if (order_Items.get(i) == 1) { // chicken gyro
                ingredients_ID_Array = new Integer[] {1, 3, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            } else if (order_Items.get(i) == 2) { // chicken bowl
                ingredients_ID_Array = new Integer[] {19, 3, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            } else if (order_Items.get(i) == 3) { // hummus and pita
                ingredients_ID_Array = new Integer[] {1, 15, 23};
                ingredients_Amount_Array = new Double[] {1.0, 0.02, 2.0};
            } else if (order_Items.get(i) == 4) { // 2 falalfels
                ingredients_ID_Array = new Integer[] {23};
                ingredients_Amount_Array = new Double[] {2.0};
            } else if (order_Items.get(i) == 5) { // extra protein
                if ((order_Items.get(i-1) == 1) || (order_Items.get(i-1) == 2)) {
                    ingredients_ID_Array = new Integer[] {3};
                    ingredients_Amount_Array = new Double[] {0.25};
                } else if ((order_Items.get(i-1) == 8) || (order_Items.get(i-1) == 9)) {
                    ingredients_ID_Array = new Integer[] {2};
                    ingredients_Amount_Array = new Double[] {0.25};
                }
            } else if (order_Items.get(i) == 6) { // extra dressing
                ingredients_ID_Array = new Integer[] {16};
                ingredients_Amount_Array = new Double[] {0.05};
            } else if (order_Items.get(i) == 7) { // fountain drink
                ingredients_ID_Array = new Integer[] {24};
                ingredients_Amount_Array = new Double[] {1.0};
            } else if (order_Items.get(i) == 8) { // lamb gyro
                ingredients_ID_Array = new Integer[] {1, 2, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            } else if (order_Items.get(i) == 9) { // lamb bowl
                ingredients_ID_Array = new Integer[] {19, 2, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            }

            for (int j = 0; j < ingredients_ID_Array.length; ++j) {
                try {
                    String query = "UPDATE inventory SET item_amount = item_amount - " + ingredients_Amount_Array[j] + " WHERE item_id = " + ingredients_ID_Array[j];
                    Statement stmt = conn.createStatement();
                    ResultSet resultSet = stmt.executeQuery(query);
                } catch(Exception ex){}
            }
        }
    }

    
    /**
    This is a quick function to change the panel that is displayed in the 'MainPanel'. This fucntion uses the 
    'show' method associated with the 'Cardlayout mainCardLayout'. Further, it will update 'lastPanel' to the panel that was 
    just switched to. 
    @param panelName This is the name (as a string) of the subpanel which is trying to be switched to. 
    @return void This function does not return anything. However, it does 'switch' the order of panels being displayed to display 'panelName'.
    */
    public void showMainPanel(String panelName){
        mainCardLayout.show(this, panelName);
        lastPanel = panelName;
    }

}