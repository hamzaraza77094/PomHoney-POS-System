import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InventoryPanel extends JPanel {

    private JTable inventoryTable;
    private JScrollPane scrollPane;

    public InventoryPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        inventoryTable = createInventoryTable();
        scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton addButton = createAddButton();
        JButton deleteButton = createDeleteButton();
        JButton updateButton = createUpdateButton();

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTable createInventoryTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
    
        // Add columns to the inventory table
        model.addColumn("Item ID");
        model.addColumn("Item Name");
        model.addColumn("Item Amount");
        model.addColumn("Item Measurement Type");
    
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
                model.addRow(new Object[]{itemId, itemName, amount, measurementType});
            }
    
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        }
    
        return table;
    }
    

    // Create and configure the Add button
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Item");

        addButton.addActionListener(e -> {
            // Prompt user to input new item data
            String itemName = JOptionPane.showInputDialog("Enter item name:");
            String amountStr = JOptionPane.showInputDialog("Enter item amount:");
            String measurementType = JOptionPane.showInputDialog("Enter item measurement type:");

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
                pstmt = conn.prepareStatement("INSERT INTO inventory (item_id, item_name, item_amount, item_measurement_type) VALUES (?, ?, ?, ?)");
                pstmt.setInt(1, newId);
                pstmt.setString(2, itemName);
                pstmt.setInt(3, Integer.parseInt(amountStr));
                pstmt.setString(4, measurementType);
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


    // Create and configure the Delete button
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

    // Create and configure the Update button
    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Update Item");

        updateButton.addActionListener(e -> {
            String itemIdStr = JOptionPane.showInputDialog("Enter item ID:");
            String itemName = JOptionPane.showInputDialog("Enter new item name:");
            String amountStr = JOptionPane.showInputDialog("Enter new item amount:");
            String measurementType = JOptionPane.showInputDialog("Enter new item measurement type:");

            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("UPDATE inventory SET item_name = ?, item_amount = ?, item_measurement_type = ? WHERE item_id = ?");
                pstmt.setString(1, itemName);
                pstmt.setInt(2, Integer.parseInt(amountStr));
                pstmt.setString(3, measurementType);
                pstmt.setInt(4, Integer.parseInt(itemIdStr));
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
    
                model.addRow(new Object[]{itemId, itemName, amount, measurementType});
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
