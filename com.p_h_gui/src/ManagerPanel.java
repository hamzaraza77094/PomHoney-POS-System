import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.*;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;



// public class ManagerPanel extends JPanel{
    
//     public JTable menuTable;
    
//     public ManagerPanel(){
//         // Allow managers to View, Add, & Update Menu Items and Prices
//         //1) Display Menu Items and prices
//         String columnNames[] = {"Test1", "Test2", "Test3", "Test4"};
//         String data[][] = {
//             {"Test5", "Test6", "Test7", "Test8"},
//             {"Test9", "Test10", "Test11", "Test12"},
//             {"Test9", "Test10", "Test11", "Test12"}
//         }; // Tests Data for the table
    
//         JTable menuTable = new JTable(data, columnNames);
//         // menuTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
//         menuTable.setFillsViewportHeight(true);
        
//         JScrollPane scrollPane = new JScrollPane(menuTable);
    
//         add(scrollPane);
    
//         //2) Add Menu Items and Prices
//         //3) Edit Menu Items and Prices
        
//         // Add a back button to go back to the NavPanel
//         JButton backButton = new JButton("Back");
//         backButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 GUI gui = (GUI) SwingUtilities.getWindowAncestor(ManagerPanel.this);
//                 gui.getContentPane().removeAll();
//                 gui.getContentPane().add(new NavPanel());
//                 gui.getContentPane().revalidate();
//                 gui.getContentPane().repaint();
//             }
//         });
//         add(backButton);
//     }
    
    
//     // Allow managers to View & Update inventory

    
// }
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManagerPanel extends JPanel{

    public JTable menuTable;

    public ManagerPanel(){
        // Allow managers to View, Add, & Update Menu Items and Prices
    // 1) Display Menu Items and prices
    String columnNames[] = { "menu_item_id", "menu_item_name", "menu_item_price" };

    String data[][] = null;
    Connection conn = null;
    try {
        String url = "jdbc:postgresql://localhost:5432/csce315331_epsilon";
        String username = "csce315331_epsilon_master";
        String password = "EPSILON";

        // Load the driver class
        Class.forName("org.postgresql.Driver");

        // Create a Connection object
        conn = DriverManager.getConnection(url, username, password);

        // Create a Statement object
        Statement stmt = conn.createStatement();

        // Execute a SQL statement to retrieve data from a "menu" table
        ResultSet rs = stmt.executeQuery("SELECT * FROM menu");

        // Loop through the result set and populate the 2D array with data
        rs.last();
        int numRows = rs.getRow();
        rs.beforeFirst();
        data = new String[numRows][3];
        int i = 0;
        while (rs.next()) {
            int itemId = rs.getInt("menu_item_id");
            String itemName = rs.getString("menu_item_name");
            double price = rs.getDouble("menu_item_price");
            data[i][0] = Integer.toString(itemId);
            data[i][1] = itemName;
            data[i][2] = Double.toString(price);
            i++;
        }

        // Close the result set, statement, and connection
        rs.close();
        stmt.close();
    } catch (ClassNotFoundException e) {
        System.out.println("Error loading PostgreSQL JDBC driver: " + e.getMessage());
    } catch (SQLException e) {
        System.out.println("Error connecting to database: " + e.getMessage());
    } finally {
        // Close the connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
    
        // Create a JTable and add it to a scroll pane
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        menuTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        add(scrollPane);
    
        //2) Add Menu Items and Prices
        //3) Edit Menu Items and Prices
    
        // Add buttons to navigate between different manager functions
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        JButton employeesButton = new JButton("Employees");
        employeesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the employees panel
                System.out.println("Showing employees panel");
            }
        });
        buttonPanel.add(employeesButton);
        JButton inventoryButton = new JButton("Inventory");
        inventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the inventory panel
                System.out.println("Showing inventory panel");
            }
        });
        buttonPanel.add(inventoryButton);
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the menu panel
                System.out.println("Showing menu panel");
            }
        });
        buttonPanel.add(menuButton);
        JButton salesButton = new JButton("Sales");
        salesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the sales panel
                System.out.println("Showing sales panel");
            }
        });
        buttonPanel.add(salesButton);
        JButton scheduleButton = new JButton("Schedule");
        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the schedule panel
                System.out.println("Showing schedule panel");
            }
        });
        buttonPanel.add(scheduleButton);
    
        add(buttonPanel);
    }
}    