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
            Connection conn = null;
            public void actionPerformed(ActionEvent e) {
                // Show the inventory panel
                System.out.println("Showing inventory panel");

                // Retrieve data from the inventory table
                String columnNames[] = { "item_id", "item_name", "item_amount", "item_measurement_type"};

                String data[][] = null;

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

                // Create a panel for the "Add Item" button
                JPanel addPanel = new JPanel();
                JButton addButton = new JButton("Add Item");
                addPanel.add(addButton);

                // Add the inventory table and the "Add Item" button to the panel
                remove(getComponent(0));
                add(scrollPane);
                add(addPanel);
                addPanel.add(addButton);

                // Add the inventory table and the "Add Item" button to the panel
                remove(getComponent(0));
                add(scrollPane);
                add(addPanel);

                addButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Prompt user to input new item data
                        String itemName = JOptionPane.showInputDialog("Enter item name:");
                        String amountStr = JOptionPane.showInputDialog("Enter item amount:");
                        String measurementType = JOptionPane.showInputDialog("Enter item measurement type:");

                        // Insert new item into the inventory table
                        PreparedStatement pstmt = null;
                        try {
                            Connection conn = Login.getConnection();

                            // Retrieve the highest item_id value from the inventory table
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT MAX(item_id) FROM inventory");
                            int newId = 1;
                            if (rs.next()) {
                                newId = rs.getInt(1) + 1;
                            }

                            // Insert the new item with the incremented item_id value
                            pstmt = conn.prepareStatement("INSERT INTO inventory (item_id, item_name, item_amount, item_measurement_type) VALUES (?, ?, ?, ?)");
                            pstmt.setInt(1, newId);
                            pstmt.setString(2, itemName);
                            pstmt.setInt(3, Integer.parseInt(amountStr));
                            pstmt.setString(4, measurementType);
                            pstmt.executeUpdate();

                            pstmt.close();
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

                        // Refresh the inventory table
                        inventoryButton.doClick();
                    }
                });
                // Create a panel for the "Delete Item" button
                JPanel deletePanel = new JPanel();
                JButton deleteButton = new JButton("Delete Item");
                deletePanel.add(deleteButton);

                // Add the "Delete Item" button to the panel
                add(deletePanel);

                deleteButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Prompt user to input item_id of the item to be deleted
                        String itemIdStr = JOptionPane.showInputDialog("Enter item ID:");

                        // Delete item from the inventory table with the given item_id
                        PreparedStatement pstmt = null;
                        try {
                            Connection conn = Login.getConnection();
                            pstmt = conn.prepareStatement("DELETE FROM inventory WHERE item_id = ?");
                            pstmt.setInt(1, Integer.parseInt(itemIdStr));
                            pstmt.executeUpdate();

                            pstmt.close();
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

                        // Refresh the inventory table
                        inventoryButton.doClick();
                    }
                });
                // Create a panel for the "Update Item" button
            JPanel updatePanel = new JPanel();
            JButton updateButton = new JButton("Update Item");
            updatePanel.add(updateButton);

            // Add the "Update Item" button to the panel
            add(updatePanel);

            updateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Prompt user to input item_id of the item to be updated
                    String itemIdStr = JOptionPane.showInputDialog("Enter item ID:");

                    // Prompt user to input new values for the item
                    String itemName = JOptionPane.showInputDialog("Enter new item name:");
                    String amountStr = JOptionPane.showInputDialog("Enter new item amount:");
                    String measurementType = JOptionPane.showInputDialog("Enter new item measurement type:");

                    // Update item in the inventory table with the given item_id
                    PreparedStatement pstmt = null;
                    try {
                        Connection conn = Login.getConnection();
                        pstmt = conn.prepareStatement("UPDATE inventory SET item_name = ?, item_amount = ?, item_measurement_type = ? WHERE item_id = ?");
                        pstmt.setString(1, itemName);
                        pstmt.setInt(2, Integer.parseInt(amountStr));
                        pstmt.setString(3, measurementType);
                        pstmt.setInt(4, Integer.parseInt(itemIdStr));
                        pstmt.executeUpdate();

                        pstmt.close();
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

                    // Refresh the inventory table
                    inventoryButton.doClick();
                }
            });


                // Update the panel
                revalidate();
                repaint();
            }
            
        });
        

        buttonPanel.add(inventoryButton);
        add(buttonPanel);
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

                                // Create a panel for the "Add Item" button
                JPanel addPanel = new JPanel();
                JButton addButton = new JButton("Add Item");
                addPanel.add(addButton);

                // Add the "Add Item" button to the panel
                add(addPanel);

                addButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Prompt user to input new item data
                        String itemName = JOptionPane.showInputDialog("Enter item name:");
                        String priceStr = JOptionPane.showInputDialog("Enter item price:");

                        // Insert new item into the menu table
                        PreparedStatement pstmt = null;
                        Connection conn = null;
                        try {
                            conn = Login.getConnection();

                            // Retrieve the highest menu_item_id value from the menu table
                            Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT MAX(menu_item_id) FROM menu");
                            int newId = 1;
                            if (rs.next()) {
                                newId = rs.getInt(1) + 1;
                            }

                            // Insert the new item with the incremented menu_item_id value
                            pstmt = conn.prepareStatement("INSERT INTO menu (menu_item_id, menu_item_name, menu_item_price) VALUES (?, ?, ?)");
                            pstmt.setInt(1, newId);
                            pstmt.setString(2, itemName);
                            pstmt.setDouble(3, Double.parseDouble(priceStr));
                            pstmt.executeUpdate();

                            pstmt.close();
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

                        // Refresh the menu table
                        menuButton.doClick();
                    }
                });

                // Create a panel for the "Delete Item" button
                JPanel deletePanel = new JPanel();
                JButton deleteButton = new JButton("Delete Item");
                deletePanel.add(deleteButton);

                // Add the "Delete Item" button to the panel
                add(deletePanel);

                deleteButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Prompt user to input menu_item_id of the item to be deleted
                        String itemIdStr = JOptionPane.showInputDialog("Enter menu_item_id:");

                        // Delete item from the menu table with the given menu_item_id
                        PreparedStatement pstmt = null;
                        Connection conn = null;
                        try {
                            conn = Login.getConnection();
                            pstmt = conn.prepareStatement("DELETE FROM menu WHERE menu_item_id = ?");
                            pstmt.setInt(1, Integer.parseInt(itemIdStr));
                            pstmt.executeUpdate();

                            pstmt.close();
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

                        // Refresh the menu table
                        menuButton.doClick();
                    }
                });

                // Create a panel for the "Update Item" button
                JPanel updatePanel = new JPanel();
                JButton updateButton = new JButton("Update Item");
                updatePanel.add(updateButton);

                // Add the "Update Item" button to the panel
                add(updatePanel);

                updateButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Prompt user to input item_id of the item to be updated
                        String itemIdStr = JOptionPane.showInputDialog("Enter item ID:");

                        // Prompt user to input new values for the item
                        String itemName = JOptionPane.showInputDialog("Enter new item name:");
                        String priceStr = JOptionPane.showInputDialog("Enter new item price:");

                        // Update item in the menu table with the given item_id
                        PreparedStatement pstmt = null;
                        Connection conn = null;
                        try {
                            conn = Login.getConnection();
                            pstmt = conn.prepareStatement("UPDATE menu SET menu_item_name = ?, menu_item_price = ? WHERE menu_item_id = ?");
                            pstmt.setString(1, itemName);
                            pstmt.setDouble(2, Double.parseDouble(priceStr));
                            pstmt.setInt(3, Integer.parseInt(itemIdStr));
                            pstmt.executeUpdate();

                            pstmt.close();
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

                        // Refresh the menu table
                        menuButton.doClick();
                    }
                });



        
                // Update the panel
                revalidate();
                repaint();
            }
        });
        buttonPanel.add(menuButton);

        add(buttonPanel);
    }
      
}        