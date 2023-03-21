import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/** 
 * A class used to house the subPanels of the Menu Features
 * @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class MenuPanel extends JPanel {

    private JTable MenuTable;
    private JScrollPane scrollPane;

    /** 
     * Cosntructor for the MenuPanel Class. Calls `initComponents()` to set the parameters of the panel.  
     * @param none 
     * @return JPanel
    */
    public MenuPanel() {
        initComponents();
    }

    /** 
     * Used to set the paramaters of 'MenuPanel()'. 
     * @param none
     * @return JPanel
    */
    private void initComponents() {
        setLayout(new BorderLayout());

        MenuTable = createMenuTable();
        scrollPane = new JScrollPane(MenuTable);
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

    /**
     * createMenuTable(): Creates the Menu Table to house the items from the `menu` table in the `csce315331_epsilon` database.
     * @param none
     * @return JTable
     */
    private JTable createMenuTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
    
        // Add columns to the Menu table
        model.addColumn("Menu Item ID");
        model.addColumn("Menu Item Name");
        model.addColumn("Menu Item Price");
    
        // Retrieve data from the Menu table
        try {
            Connection conn = Login.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from menu Order by menu_item_id");
    
            while (rs.next()) {
                int itemId = rs.getInt("menu_item_id");
                String itemName = rs.getString("menu_item_name");
                double amount = rs.getDouble("menu_item_price");
                model.addRow(new Object[]{itemId, itemName, amount});
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
     * Create and configure the Add button for the MenuPanel. Has `addActionListener()` functionality to add a menu item.
     * @param none
     * @return JButton
     */
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Item");

        addButton.addActionListener(e -> {
            // Prompt user to input new item data
            String itemName = JOptionPane.showInputDialog("Enter item name:");
            String amountStr = JOptionPane.showInputDialog("Enter item price:");

            // Insert new item into the Menu table
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();

                // Retrieve the highest item_id value from the Menu table
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(menu_item_id) FROM Menu");
                int newId = 1;
                if (rs.next()) {
                    newId = rs.getInt(1) + 1;
                }

                // Insert the new item with the incremented item_id value
                pstmt = conn.prepareStatement("INSERT INTO Menu (menu_item_id, menu_item_name, menu_item_price) VALUES (?, ?, ?)");
                pstmt.setInt(1, newId);
                pstmt.setString(2, itemName);
                pstmt.setDouble(3, Double.parseDouble(amountStr));
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

            // Refresh the Menu table
            refreshMenuTable();
        });

        return addButton;
    }


    /**
     * Create and configure the Delete button for MenuPanel. Has `addActionListener()` functionality to add a menu item.
     * @param none
     * @return JButton
     */
    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete Item");

        deleteButton.addActionListener(e -> {
            // Implement code for deleting an item
            int selectedRow = MenuTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) MenuTable.getValueAt(selectedRow, 0);

                try {
                    Connection conn = Login.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Menu WHERE menu_item_id = ?");
                    pstmt.setInt(1, itemId);
                    pstmt.executeUpdate();

                    pstmt.close();
                    conn.close();

                    // Refresh the Menu table
                    DefaultTableModel model = (DefaultTableModel) MenuTable.getModel();
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    System.out.println("Error connecting to database: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to delete.");
            }
            // Refresh the Menu table
            refreshMenuTable();
        });

        return deleteButton;
    }

    
    /**
     * Create and configure the Update button for the MenuPanel. Has `addActionListener()` functionality to add a menu item.
     * @param none
     * @return JButton
     */
    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Update Item");

        updateButton.addActionListener(e -> {
            String itemIdStr = JOptionPane.showInputDialog("Enter item ID:");
            String itemName = JOptionPane.showInputDialog("Enter new item name:");
            String amountStr = JOptionPane.showInputDialog("Enter new item price:");

            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("UPDATE Menu SET menu_item_name = ?, menu_item_price = ? WHERE menu_item_id = ?");
                pstmt.setString(1, itemName);
                pstmt.setDouble(2, Double.parseDouble(amountStr));
                pstmt.setInt(3, Integer.parseInt(itemIdStr));
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

            // Refresh the Menu table
            refreshMenuTable();
        });

        return updateButton;
    }

    /**
     * Used to update the table w/ new information if needed. Updates the current MenuPanel.
     * @param none
     * @return none
     */
    private void refreshMenuTable() {
        DefaultTableModel model = (DefaultTableModel) MenuTable.getModel();
        model.setRowCount(0);
    
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Login.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from menu Order by menu_item_id");
    
            while (rs.next()) {
                int itemId = rs.getInt("menu_item_id");
                String itemName = rs.getString("menu_item_name");
                double amount = rs.getDouble("menu_item_price");
    
                model.addRow(new Object[]{itemId, itemName, amount});
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
