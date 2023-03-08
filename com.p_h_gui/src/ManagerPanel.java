// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.*;

// public class ManagerPanel extends JPanel{

//     public JTable menuTable;

//     public ManagerPanel(){
//         // Allow managers to View, Add, & Update Menu Items and Prices
//     // 1) Display Menu Items and prices
//     String columnNames[] = { "menu_item_id", "menu_item_name", "menu_item_price" };

//     String data[][] = null;
//     Connection conn = null;
    
//     try {
//         // String url = "jdbc:postgresql://localhost:5432/csce315331_epsilon";
//         String url = "jdbc:postgresql://192.168.0.100:5433/csce315331_epsilon";
//         String username = "csce315331_epsilon_master";
//         String password = "EPSILON";
//         conn = Login.getConnection();

//         // Create a Statement object
//         Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

//         // Execute a SQL statement to retrieve data from a "menu" table
//         ResultSet rs = stmt.executeQuery("SELECT * FROM menu");

//         // Loop through the result set and populate the 2D array with data
//         rs.last();
//         int numRows = rs.getRow();
//         rs.beforeFirst();
//         data = new String[numRows][3];
//         int i = 0;
//         while (rs.next()) {
//             int itemId = rs.getInt("menu_item_id");
//             String itemName = rs.getString("menu_item_name");
//             double price = rs.getDouble("menu_item_price");
//             data[i][0] = Integer.toString(itemId);
//             data[i][1] = itemName;
//             data[i][2] = Double.toString(price);
//             i++;
//         }

//         // Close the result set, statement, and connection
//         rs.close();
//         stmt.close();
//     }
//     // } catch (ClassNotFoundException e) {
//         // System.out.println("Error loading PostgreSQL JDBC driver: " + e.getMessage());
//      catch (SQLException e) {
//         System.out.println("Error connecting to database: " + e.getMessage());
//     } finally {
//         // Close the connection
//         if (conn != null) {
//             try {
//                 conn.close();
//             } catch (SQLException e) {
//                 System.out.println("Error closing database connection: " + e.getMessage());
//             }
//         }
//     }
    
//         // Create a JTable and add it to a scroll pane
//         DefaultTableModel model = new DefaultTableModel(data, columnNames);
//         menuTable = new JTable(model);
//         JScrollPane scrollPane = new JScrollPane(menuTable);
//         add(scrollPane);
    
//         //2) Add Menu Items and Prices
//         //3) Edit Menu Items and Prices
    
//         // Add buttons to navigate between different manager functions
//         JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
//         JButton inventoryButton = new JButton("Inventory");
//         // inventoryButton.addActionListener(new ActionListener() {
//         //     public void actionPerformed(ActionEvent e) {
//         //         // Show the inventory panel
//         //         System.out.println("Showing inventory panel");
//         //     }
//         // });
//         inventoryButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 // Show the inventory panel
//                 System.out.println("Showing inventory panel");
        
//                 // Retrieve data from the inventory table
//                 String columnNames[] = { "item_id", "item_name", "quantity" };
        
//                 String data[][] = null;
//                 Connection conn = null;
        
//                 try {
//                     String url = "jdbc:postgresql://192.168.0.100:5433/csce315331_epsilon";
//                     String username = "csce315331_epsilon_master";
//                     String password = "EPSILON";
//                     conn = Login.getConnection();
        
//                     Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
//                     ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");
        
//                     rs.last();
//                     int numRows = rs.getRow();
//                     rs.beforeFirst();
//                     data = new String[numRows][3];
//                     int i = 0;
//                     while (rs.next()) {
//                         int itemId = rs.getInt("item_id");
//                         String itemName = rs.getString("item_name");
//                         int quantity = rs.getInt("quantity");
//                         data[i][0] = Integer.toString(itemId);
//                         data[i][1] = itemName;
//                         data[i][2] = Integer.toString(quantity);
//                         i++;
//                     }
        
//                     rs.close();
//                     stmt.close();
//                 } catch (SQLException ex) {
//                     System.out.println("Error connecting to database: " + ex.getMessage());
//                 } finally {
//                     if (conn != null) {
//                         try {
//                             conn.close();
//                         } catch (SQLException ex) {
//                             System.out.println("Error closing database connection: " + ex.getMessage());
//                         }
//                     }
//                 }
        
//                 // Create a JTable and add it to a scroll pane
//                 DefaultTableModel model = new DefaultTableModel(data, columnNames);
//                 JTable inventoryTable = new JTable(model);
//                 JScrollPane scrollPane = new JScrollPane(inventoryTable);
        
//                 // Remove the menu table and add the inventory table
//                 remove(menuTable);
//                 add(scrollPane);
        
//                 // Update the panel
//                 revalidate();
//                 repaint();
//             }
//         });
        
//         buttonPanel.add(inventoryButton);
//         JButton menuButton = new JButton("Menu");
//         menuButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 // Show the menu panel
//                 System.out.println("Showing menu panel");
        
//                 // Retrieve data from the menu table
//                 String columnNames[] = { "menu_item_id", "menu_item_name", "menu_item_price" };
        
//                 String data[][] = null;
//                 Connection conn = null;
        
//                 try {
//                     String url = "jdbc:postgresql://192.168.0.100:5433/csce315331_epsilon";
//                     String username = "csce315331_epsilon_master";
//                     String password = "EPSILON";
//                     conn = Login.getConnection();
        
//                     Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
//                     ResultSet rs = stmt.executeQuery("SELECT * FROM menu");
        
//                     rs.last();
//                     int numRows = rs.getRow();
//                     rs.beforeFirst();
//                     data = new String[numRows][3];
//                     int i = 0;
//                     while (rs.next()) {
//                         int itemId = rs.getInt("menu_item_id");
//                         String itemName = rs.getString("menu_item_name");
//                         double price = rs.getDouble("menu_item_price");
//                         data[i][0] = Integer.toString(itemId);
//                         data[i][1] = itemName;
//                         data[i][2] = Double.toString(price);
//                         i++;
//                     }
        
//                     rs.close();
//                     stmt.close();
//                 } catch (SQLException ex) {
//                     System.out.println("Error connecting to database: " + ex.getMessage());
//                 } finally {
//                     if (conn != null) {
//                         try {
//                             conn.close();
//                         } catch (SQLException ex) {
//                             System.out.println("Error closing database connection: " + ex.getMessage());
//                         }
//                     }
//                 }
        
//                 // Create a JTable and add it to a scroll pane
//                 DefaultTableModel model = new DefaultTableModel(data, columnNames);
//                 JTable menuTable = new JTable(model);
//                 JScrollPane scrollPane = new JScrollPane(menuTable);
        
//                 // Remove the inventory table and add the menu table
//                 remove(getComponent(0));
//                 add(scrollPane);
        
//                 // Update the panel
//                 revalidate();
//                 repaint();
//             }
//         });
        
//         buttonPanel.add(menuButton);

//         add(buttonPanel);
//     }
      
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

        //1) Display Menu Items and prices
        // displayMenu();

        //2) Add Menu Items and Prices
        //3) Edit Menu Items and Prices
    
        // Add buttons to navigate between different manager functions
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
JButton inventoryButton = new JButton("Inventory");
inventoryButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // Show the inventory panel
        System.out.println("Showing inventory panel");

        // Retrieve data from the inventory table
        String columnNames[] = { "item_id", "item_name", "item_amount", "item_measurement_type"};

        String data[][] = null;
        Connection conn = null;

        try {
            String url = "jdbc:postgresql://192.168.0.100:5433/csce315331_epsilon";
            String username = "csce315331_epsilon_master";
            String password = "EPSILON";
            conn = Login.getConnection();

            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory");

            rs.last();
            int numRows = rs.getRow();
            rs.beforeFirst();
            data = new String[numRows][4];
            int i = 0;
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int amount = rs.getInt("item_amount");
                String measurementType = rs.getString("item_measurement_type");
                data[i][0] = Integer.toString(itemId);
                data[i][1] = itemName;
                data[i][2] = Integer.toString(amount);
                data[i][3] = measurementType;
                i++;
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing database connection: " + ex.getMessage());
                }
            }
        }

        // Create a JTable and add it to a scroll pane
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable inventoryTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        // Remove the menu table and add the inventory table
        remove(menuTable);
        add(scrollPane);

        // Update the panel
        revalidate();
        repaint();
    }
});

buttonPanel.add(inventoryButton);

        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show the menu panel
                System.out.println("Showing menu panel");
        
                // Retrieve data from the menu table
                String columnNames[] = { "menu_item_id", "menu_item_name", "menu_item_price" };
        
                String data[][] = null;
                Connection conn = null;
        
                try {
                    String url = "jdbc:postgresql://192.168.0.100:5433/csce315331_epsilon";
                    String username = "csce315331_epsilon_master";
                    String password = "EPSILON";
                    conn = Login.getConnection();
        
                    Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
                    ResultSet rs = stmt.executeQuery("SELECT * FROM menu");
        
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
        
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error connecting to database: " + ex.getMessage());
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            System.out.println("Error closing database connection: " + ex.getMessage());
                        }
                    }
                }
        
                // Create a JTable and add it to a scroll pane
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                JTable menuTable = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(menuTable);
        
                // Remove the inventory table and add the menu table
                remove(getComponent(0));
                add(scrollPane);
        
                // Update the panel
                revalidate();
                repaint();
            }
        });
        buttonPanel.add(menuButton);

        add(buttonPanel);
    }
      
}    
