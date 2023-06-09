import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
* This class defines the Inventory Panel used to display the inventory table on our application
* @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class InventoryPanel extends JPanel {

    private JTable inventoryTable;
    private JScrollPane scrollPane;

    /**
    * constructor for the InventoryPanel class, calls initComponents()
    * @param none
    * @return JPanel
    */
    public InventoryPanel() {
        initComponents();
    }

    /**
    * Creates the display and buttons for the inventory table panel
    * @param none
    * @return JPanel
    */
    private void initComponents() {
        setLayout(new BorderLayout());

        inventoryTable = createInventoryTable();
        scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton addButton = createAddButton();
        JButton deleteButton = createDeleteButton();
        JButton updateButton = createUpdateButton();
        JButton resetInventoryCountButton = createResetInventoryCountButton(); // Not made yet

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(resetInventoryCountButton); // Not made yet


        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
    * Creates the actual table containing the information from the inventory table in the database, to be displayed on the Inventory Panel
    * @param none
    * @return JTable
    */
    private JTable createInventoryTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
    
        // Add columns to the inventory table
        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Item Amount");
        model.addColumn("Item Measurement Type");
        model.addColumn("Minimum"); // Added the new 'Minimum' column
        model.addColumn("Maximum");

    
        // Retrieve data from the inventory table
        try {
            Connection conn = Login.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory ORDER BY item_ID ASC");
    
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int amount = rs.getInt("item_amount");
                String measurementType = rs.getString("item_measurement_type");
                int minimum = rs.getInt("minimum"); // Retrieve the minimum value from the database
                int maximum = rs.getInt("maximum");
                model.addRow(new Object[]{itemId, itemName, amount, measurementType, minimum, maximum});
            }
    
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        }
    
        return table;
    }
    
    /**
    * code for creating the add button, called by initComponents()
    * @param none
    * @return addButton
    */
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Item");
    
        addButton.addActionListener(e -> {
            // Prompt user to input new item data
            String itemName = JOptionPane.showInputDialog("Enter item name:");
            String amountStr = JOptionPane.showInputDialog("Enter item amount:");
            String measurementType = JOptionPane.showInputDialog("Enter item measurement type:");
            String minimumStr = JOptionPane.showInputDialog("Enter minimum item amount:"); // Add input dialog for minimum value
            String maximumStr = JOptionPane.showInputDialog("Enter maximum item amount:");

    
            // Insert new item into the inventory table
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
    
                // Retrieve the highest item_id value from the inventory table
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(item_id) FROM inventory");
                int newId = 1;
                if (rs.next()) {
                    newId = rs.getInt(1) + 1;
                }
    
                // Insert the new item with the incremented item_id value
                pstmt = conn.prepareStatement("INSERT INTO inventory (item_id, item_name, item_amount, item_measurement_type, minimum, maximum) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, newId);
                pstmt.setString(2, itemName);
                pstmt.setInt(3, Integer.parseInt(amountStr));
                pstmt.setString(4, measurementType);
                pstmt.setInt(5, Integer.parseInt(minimumStr)); // Add the minimum value to the prepared statement
                pstmt.setInt(6, Integer.parseInt(maximumStr));
                pstmt.executeUpdate();
    
                pstmt.close();
                stmt.close();
                rs.close();
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
            refreshInventoryTable();
        });
    
        return addButton;
    }


    /**
    * code for creating the Delete button, called by initComponents()
    * @param none
    * @return deleteButton
    */
    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete Item");

        deleteButton.addActionListener(e -> {
            // Implement code for deleting an item
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) inventoryTable.getValueAt(selectedRow, 0);

                try {
                    Connection conn = Login.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM inventory WHERE item_id = ?");
                    pstmt.setInt(1, itemId);
                    pstmt.executeUpdate();

                    pstmt.close();
                    conn.close();

                    // Refresh the inventory table
                    DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    System.out.println("Error connecting to database: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to delete.");
            }
            // Refresh the inventory table
            refreshInventoryTable();
        });

        return deleteButton;
    }

    /**
    * code for creating the Update button, called by initComponents()
    * @param none
    * @return updateButton
    */
    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Update Item");
    
        updateButton.addActionListener(e -> {
            String itemIdStr = JOptionPane.showInputDialog("Enter item ID:");
            String itemName = JOptionPane.showInputDialog("Enter new item name:");
            String amountStr = JOptionPane.showInputDialog("Enter new item amount:");
            String measurementType = JOptionPane.showInputDialog("Enter new item measurement type:");
            String minimumStr = JOptionPane.showInputDialog("Enter new minimum item amount:"); // Add input dialog for minimum value
            String maximumStr = JOptionPane.showInputDialog("Enter new maximum item amount:");

    
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("UPDATE inventory SET item_name = ?, item_amount = ?, item_measurement_type = ?, minimum = ?, maximum = ? WHERE item_id = ?");
                pstmt.setString(1, itemName);
                pstmt.setInt(2, Integer.parseInt(amountStr));
                pstmt.setString(3, measurementType);
                pstmt.setInt(4, Integer.parseInt(minimumStr)); // Add the minimum value to the prepared statement
                pstmt.setInt(5, Integer.parseInt(maximumStr));
                pstmt.setInt(6, Integer.parseInt(itemIdStr));
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error connecting to database: " + ex.getMessage());
            } finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException ex) {
                        System.out.println("Error closing PreparedStatement: " + ex.getMessage());
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        System.out.println("Error closing database connection: " + ex.getMessage());
                    }
                }
            }
    
            // Refresh the inventory table
            refreshInventoryTable();
        });
    
        return updateButton;
    }

    /**
    * code for creating the inventory reset button, called by initComponents()
    * @param none
    * @return rInvCountButton
    */
    private JButton createResetInventoryCountButton() {
        JButton rInvCountButton = new JButton("RST INV #s");
    
        rInvCountButton.addActionListener(e -> {
            try {
                Connection conn = Login.getConnection();
                Statement stmt = conn.createStatement();
    
                conn.createStatement().executeQuery(
                    "UPDATE inventory SET item_amount = CASE " +
                            "WHEN item_measurement_type = 'count' THEN 150" +
                            "WHEN item_measurement_type = 'gallons' THEN 55" +
                            "WHEN item_measurement_type = 'pounds' THEN 75" +
                            "END"
                ); // Reset the count amount
    
                conn.createStatement().executeQuery(
                    "UPDATE inventory SET minimum = CASE" +
                            "WHEN item_measurement_type = 'count' THEN 15" +
                            "WHEN item_measurement_type = 'gallons' THEN 5" +
                            "WHEN item_measurement_type = 'pounds' THEN 7" +
                            "END"
                ); // Update the minimum value to be 10% of item_amount
    
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                if (ex.getMessage().compareTo("No results were returned by the query.") != 0) {
                    // handle the exception in the usual way
                    System.out.println(ex.getMessage());
                }
            }
    
            refreshInventoryTable();
        });
    
        return rInvCountButton;
    }

    /**
    * function that resets inventory levels to a specific level, called when rInvCountButton is pressed, used to simulate a restock
    * @param none
    * @return none
    */
    private void refreshInventoryTable() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0);
    
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Login.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventory ORDER BY item_ID ASC");
    
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                int amount = rs.getInt("item_amount");
                String measurementType = rs.getString("item_measurement_type");
                int minimum = rs.getInt("minimum"); // Add the minimum value here
                int maximum = rs.getInt("maximum");

    
                model.addRow(new Object[]{itemId, itemName, amount, measurementType, minimum, maximum});
            }
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing ResultSet: " + ex.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing Statement: " + ex.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing database connection: " + ex.getMessage());
                }
            }
        }
    }

    
    
    
}
