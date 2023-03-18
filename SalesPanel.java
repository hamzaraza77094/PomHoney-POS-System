import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SalesPanel extends JPanel {

    private JTable salesTable;
    private JScrollPane scrollPane;

    public SalesPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
    
        salesTable = createSalesTable();
        scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton addButton = createAddButton();
        JButton deleteButton = createDeleteButton();
        JButton updateButton = createUpdateButton();
        JButton compileButton = createCompileButton(); // Add this line
    
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(compileButton); // Add this line
    
        add(buttonPanel, BorderLayout.SOUTH);
    }
    

    private JTable createSalesTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        // Add columns to the sales table
        model.addColumn("Order ID");
        model.addColumn("Day ID");
        model.addColumn("Date");
        model.addColumn("Order Contents");
        model.addColumn("Order Subtotal");
        model.addColumn("Sales Tax");
        model.addColumn("Total");

        // Retrieve data from the dailySales table
        try {
            Connection conn = Login.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM salesbridge ORDER BY orderid ASC");

            while (rs.next()) {
                int orderId = rs.getInt("orderid");
                int dayId = rs.getInt("dayid");
                Date date = rs.getDate("date");
                String orderContents = rs.getString("ordercontents");
                double orderSubtotal = rs.getDouble("ordersubtotal");
                double salesTax = rs.getDouble("salestax");
                double total = rs.getDouble("total");

                model.addRow(new Object[]{orderId, dayId, date, orderContents, orderSubtotal, salesTax, total});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        }

        return table;
    }

    private JButton createAddButton() {
        JButton addButton = new JButton("Add Order");
    
        addButton.addActionListener(e -> {
            // Prompt user to input new order data
            String dayIdStr = JOptionPane.showInputDialog("Enter Day ID:");
            String dateStr = JOptionPane.showInputDialog("Enter Date (YYYY-MM-DD):");
            String orderContents = JOptionPane.showInputDialog("Enter Order Contents:");
            String orderSubtotalStr = JOptionPane.showInputDialog("Enter Order Subtotal:");
            String salesTaxStr = JOptionPane.showInputDialog("Enter Sales Tax:");
            String totalStr = JOptionPane.showInputDialog("Enter Total:");
    
            // Insert new order into the salesbridge table
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
    
                // Insert the new order
                pstmt = conn.prepareStatement("INSERT INTO salesbridge (dayid, date, ordercontents, ordersubtotal, salestax, total) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(dayIdStr));
                pstmt.setDate(2, Date.valueOf(dateStr));
                pstmt.setString(3, orderContents);
                pstmt.setDouble(4, Double.parseDouble(orderSubtotalStr));
                pstmt.setDouble(5, Double.parseDouble(salesTaxStr));
                pstmt.setDouble(6, Double.parseDouble(totalStr));
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
        });
    
        return addButton;
    }
    


    // Create and configure the Delete button
private JButton createDeleteButton() {
    JButton deleteButton = new JButton("Delete Order");

    deleteButton.addActionListener(e -> {
        // Implement code for deleting an order
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow != -1) {
            int orderId = (int) salesTable.getValueAt(selectedRow, 0);

            try {
                Connection conn = Login.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM dailySales WHERE orderid = ?");
                pstmt.setInt(1, orderId);
                pstmt.executeUpdate();

                pstmt.close();
                conn.close();

                // Refresh the sales table
                DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
                model.removeRow(selectedRow);
            } catch (SQLException ex) {
                System.out.println("Error connecting to database: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an order to delete.");
        }
        // Refresh the sales table
        refreshSalesTable();
    });

    return deleteButton;
}

// Create and configure the Update button
    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Update Order");

        updateButton.addActionListener(e -> {
            String orderIdStr = JOptionPane.showInputDialog("Enter order ID:");
            String date = JOptionPane.showInputDialog("Enter new date (YYYY-MM-DD):");
            String orderContents = JOptionPane.showInputDialog("Enter new order contents:");
            String orderSubtotalStr = JOptionPane.showInputDialog("Enter new order subtotal:");
            String salesTaxStr = JOptionPane.showInputDialog("Enter new sales tax:");
            String totalStr = JOptionPane.showInputDialog("Enter new total:");

            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("UPDATE dailySales SET date = ?, ordercontents = ?, ordersubtotal = ?, salestax = ?, total = ? WHERE orderid = ?");
                pstmt.setString(1, date);
                pstmt.setString(2, orderContents);
                pstmt.setBigDecimal(3, new BigDecimal(orderSubtotalStr));
                pstmt.setBigDecimal(4, new BigDecimal(salesTaxStr));
                pstmt.setBigDecimal(5, new BigDecimal(totalStr));
                pstmt.setInt(6, Integer.parseInt(orderIdStr));
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

            // Refresh the sales table
            refreshSalesTable();
        });

        return updateButton;
    }


    private void refreshSalesTable() {
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        model.setRowCount(0);
    
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Login.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM salesbridge ORDER BY orderid ASC");
    
            while (rs.next()) {
                int orderId = rs.getInt("orderid");
                int dayId = rs.getInt("dayid");
                Date date = rs.getDate("date");
                String orderContents = rs.getString("ordercontents");
                BigDecimal orderSubtotal = rs.getBigDecimal("ordersubtotal");
                BigDecimal salesTax = rs.getBigDecimal("salestax");
                BigDecimal total = rs.getBigDecimal("total");
    
                model.addRow(new Object[]{orderId, dayId, date, orderContents, orderSubtotal, salesTax, total});
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
    

    private JButton createCompileButton() {
        JButton compileButton = new JButton("Compile");
    
        compileButton.addActionListener(e -> {
            Connection conn = null;
            try {
                conn = Login.getConnection();
    
                // 1. Insert the data from the salesbridge table to the dailySales table
                String insertDailySalesSql = "INSERT INTO dailySales (orderid, dayid, date, ordercontents, ordersubtotal, salestax, total) " +
                        "SELECT row_number() OVER (ORDER BY salesbridge.orderid) + (SELECT COALESCE(MAX(orderid), 0) FROM dailySales), COALESCE((SELECT dayid FROM dailySales WHERE dailySales.date = salesbridge.date LIMIT 1), (SELECT COALESCE(MAX(dayid), 0) + 1 FROM dailySales)) AS dayid, date, ordercontents, ordersubtotal, salestax, total " +
                        "FROM salesbridge;";
                PreparedStatement insertDailySalesPstmt = conn.prepareStatement(insertDailySalesSql);
                insertDailySalesPstmt.executeUpdate();
    
                // 2. Aggregate and append the data from the salesbridge table to the sales table
                String appendSalesSql = "WITH aggregated_sales AS ( " +
                        "SELECT date, SUM(ordersubtotal) AS ordersubtotal, SUM(salestax) AS salestax, SUM(total) AS total " +
                        "FROM salesbridge " +
                        "GROUP BY date " +
                        ") " +
                        "INSERT INTO sales (dayid, date, salesubtotal, saletax, totalsales) " +
                        "SELECT COALESCE((SELECT dayid FROM sales WHERE sales.date = aggregated_sales.date LIMIT 1), (SELECT COALESCE(MAX(dayid), 0) + 1 FROM sales)) AS dayid, date, ordersubtotal, salestax, total " +
                        "FROM aggregated_sales " +
                        "ON CONFLICT (dayid, date) DO UPDATE " +
                        "SET salesubtotal = sales.salesubtotal + EXCLUDED.salesubtotal, " +
                        "    saletax = sales.saletax + EXCLUDED.saletax, " +
                        "    totalsales = sales.totalsales + EXCLUDED.totalsales;";
    
                PreparedStatement appendSalesPstmt = conn.prepareStatement(appendSalesSql);
                appendSalesPstmt.executeUpdate();
    
                // 3. Clear the salesbridge table for the next use
                String truncateSalesbridgeSql = "TRUNCATE salesbridge;";
                PreparedStatement truncateSalesbridgePstmt = conn.prepareStatement(truncateSalesbridgeSql);
                truncateSalesbridgePstmt.executeUpdate();
    
                insertDailySalesPstmt.close();
                appendSalesPstmt.close();
                truncateSalesbridgePstmt.close();
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
    
            // Refresh the sales table and the dailySales table
            refreshSalesTable();
        });
        return compileButton;
    }
    

             
          


}