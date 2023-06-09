import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
* This class is used to display the daily sales table panel on our application 
* @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class DailySalesPanel extends JPanel {

    private JTable dailySalesTable;
    private JScrollPane scrollPane;

    /**
    * Constructor for the DailySalesPanel class, calls initComponents()
    * @param none
    * @return JTable
    */
    public DailySalesPanel() {
        initComponents();
    }

    /**
    * initializes display and buttons for the daily sales table
    * @param none
    * @return JTable
    */
    private void initComponents() {
        setLayout(new BorderLayout());
    
        dailySalesTable = createDailySalesTable();
        scrollPane = new JScrollPane(dailySalesTable);
        add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton addButton = createAddButton();
        JButton deleteButton = createDeleteButton();
        JButton updateButton = createUpdateButton();
    
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
    
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
    * Creates the actual table containing the information from the daily sales table in the database, to be displayed on the Daily Sales Panel
    * @param none
    * @return JTable
    */
    private JTable createDailySalesTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Order ID");
        model.addColumn("Day ID");
        model.addColumn("Date");
        model.addColumn("Order Contents");
        model.addColumn("Order Subtotal");
        model.addColumn("Sales Tax");
        model.addColumn("Total");

        try {
            Connection conn = Login.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM dailySales ORDER BY orderid ASC");

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

    /**
    * code for creating the add button, called by initComponents()
    * @param none
    * @return addButton
    */
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Order");

        addButton.addActionListener(e -> {
            String dayIdStr = JOptionPane.showInputDialog("Enter Day ID:");
            String dateStr = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
            String orderContents = JOptionPane.showInputDialog("Enter order contents:");
            String orderSubtotalStr = JOptionPane.showInputDialog("Enter order subtotal:");
            String salesTaxStr = JOptionPane.showInputDialog("Enter sales tax:");
            String totalStr = JOptionPane.showInputDialog("Enter total:");

            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("INSERT INTO dailySales (dayid, date, ordercontents, ordersubtotal, salestax, total) VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(dayIdStr));
                pstmt.setDate(2, Date.valueOf(dateStr));
                pstmt.setString(3, orderContents);
                pstmt.setDouble(4, Double.parseDouble(orderSubtotalStr));
                pstmt.setDouble(5, Double.parseDouble(salesTaxStr));
                pstmt.setDouble(6, Double.parseDouble(totalStr));
                pstmt.executeUpdate();

                pstmt.close();
                conn.close();
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

            refreshDailySalesTable();
        });

        return addButton;
    }

    /**
    * code for creating the Delete button, called by initComponents()
    * @param none
    * @return deleteButton
    */
    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete Order");

        deleteButton.addActionListener(e -> {
            int selectedRow = dailySalesTable.getSelectedRow();
            if (selectedRow != -1) {
                int orderId = (int) dailySalesTable.getValueAt(selectedRow, 0);

                try {
                    Connection conn = Login.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM dailySales WHERE orderid = ?");
                    pstmt.setInt(1, orderId);
                    pstmt.executeUpdate();

                    pstmt.close();
                    conn.close();

                    refreshDailySalesTable();
                } catch (SQLException ex) {
                    System.out.println("Error connecting to database: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an order to delete.");
            }
        });

        return deleteButton;
    }

    /**
    * code for creating the Update button, called by initComponents()
    * @param none
    * @return updateButton
    */
    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Update Order");
    
        updateButton.addActionListener(e -> {
            String orderIdStr = JOptionPane.showInputDialog("Enter Order ID:");
            String dayIdStr = JOptionPane.showInputDialog("Enter new Day ID:");
            String dateStr = JOptionPane.showInputDialog("Enter new date (YYYY-MM-DD):");
            String orderContents = JOptionPane.showInputDialog("Enter new order contents:");
            String orderSubtotalStr = JOptionPane.showInputDialog("Enter new order subtotal:");
            String salesTaxStr = JOptionPane.showInputDialog("Enter new sales tax:");
            String totalStr = JOptionPane.showInputDialog("Enter new total:");
    
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("UPDATE dailySales SET dayid = ?, date = ?, ordercontents = ?, ordersubtotal = ?, salestax = ?, total = ? WHERE orderid = ?");
                pstmt.setInt(1, Integer.parseInt(dayIdStr));
                pstmt.setDate(2, Date.valueOf(dateStr));
                pstmt.setString(3, orderContents);
                pstmt.setDouble(4, Double.parseDouble(orderSubtotalStr));
                pstmt.setDouble(5, Double.parseDouble(salesTaxStr));
                pstmt.setDouble(6, Double.parseDouble(totalStr));
                pstmt.setInt(7, Integer.parseInt(orderIdStr));
                pstmt.executeUpdate();
    
                pstmt.close();
                conn.close();
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
    
            refreshDailySalesTable();
        });
    
        return updateButton;
    }
    
    /**
    * function that refreshes display of daily sales table, called when add, delete, or update buttons are pressed
    * @param none
    * @return none
    */
    private void refreshDailySalesTable() {
        DefaultTableModel model = (DefaultTableModel) dailySalesTable.getModel();
        model.setRowCount(0);
    
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Login.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM dailySales ORDER BY orderid ASC");
    
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
