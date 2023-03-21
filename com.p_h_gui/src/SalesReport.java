import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/** 
 * A class used to satisfy the Sales Report Requirement. 
 * @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class SalesReport extends JPanel {

    private JTable salesTable;
    private JScrollPane scrollPane;

    /** 
     * Constructor for the SalesReport Class. Calls `initComponents()` to set the parameters of the panel.  
     * @param none 
     * @return JPanel
    */
    public SalesReport() {
        initComponents();
    }

    /** 
     * Used to set the paramaters of 'RestockReport()'. 
     * @param none
     * @return JPanel
    */
    private void initComponents() {
        setLayout(new BorderLayout());

        salesTable = createSalesTable();
        scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        // JButton addButton = createAddButton();
        // JButton deleteButton = createDeleteButton();
        // JButton updateButton = createUpdateButton();
        JButton salesReportButton = createSalesReportButton();

        // buttonPanel.add(addButton);
        // buttonPanel.add(deleteButton);
        // buttonPanel.add(updateButton);
        buttonPanel.add(salesReportButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the JTable that will display the sales report
     * @param none
     * @return JTable
     */
    private JTable createSalesTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("Day ID");
        model.addColumn("Date");
        model.addColumn("Sale Subtotal");
        model.addColumn("Sale Tax");
        model.addColumn("Total Sales");

        try {
            Connection conn = Login.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sales ORDER BY date ASC");

            while (rs.next()) {
                int dayId = rs.getInt("dayid");
                Date date = rs.getDate("date");
                double saleSubtotal = rs.getDouble("salesubtotal");
                double saleTax = rs.getDouble("saletax");
                double totalSales = rs.getDouble("totalsales");
                model.addRow(new Object[]{dayId, date, saleSubtotal, saleTax, totalSales});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        }

        return table;
    }

    // private JButton createAddButton() {
    //     JButton addButton = new JButton("Add Sale");

    //     addButton.addActionListener(e -> {
    //         String dateStr = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):");
    //         String saleSubtotalStr = JOptionPane.showInputDialog("Enter sale subtotal:");
    //         String saleTaxStr = JOptionPane.showInputDialog("Enter sale tax:");
    //         String totalSalesStr = JOptionPane.showInputDialog("Enter total sales:");

    //         PreparedStatement pstmt = null;
    //         Connection conn = null;
    //         try {
    //             conn = Login.getConnection();
    //             pstmt = conn.prepareStatement("INSERT INTO sales (date, salesubtotal, saletax, totalsales) VALUES (?, ?, ?, ?)");
    //             pstmt.setDate(1, Date.valueOf(dateStr));
    //             pstmt.setDouble(2, Double.parseDouble(saleSubtotalStr));
    //             pstmt.setDouble(3, Double.parseDouble(saleTaxStr));
    //             pstmt.setDouble(4, Double.parseDouble(totalSalesStr));
    //             pstmt.executeUpdate();

    //             pstmt.close();
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println("Error connecting to database: " + ex.getMessage());
    //         } finally {
    //             if (conn != null) {
    //                 try {
    //                     conn.close();
    //                 } catch (SQLException ex) {
    //                     System.out.println("Error closing database connection: " + ex.getMessage());
    //                 }
    //             }
    //         }
    //         String a = null;
    //         String b = null;
    //         refreshSalesTable(a, b);
    //     });

    //     return addButton;
    // }

    // private JButton createDeleteButton() {
    //     JButton deleteButton = new JButton("Delete Sale");
    
    //     deleteButton.addActionListener(e -> {
    //         int selectedRow = salesTable.getSelectedRow();
    //         if (selectedRow != -1) {
    //             int dayId = (int) salesTable.getValueAt(selectedRow, 0);
    
    //             try {
    //                 Connection conn = Login.getConnection();
    //                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM sales WHERE dayid = ?");
    //                 pstmt.setInt(1, dayId);
    //                 pstmt.executeUpdate();
    
    //                 pstmt.close();
    //                 conn.close();
                    
    //                 String a = null;
    //                 String b = null;
    //                 refreshSalesTable(a,b);
    //             } catch (SQLException ex) {
    //                 System.out.println("Error connecting to database: " + ex.getMessage());
    //             }
    //         } else {
    //             JOptionPane.showMessageDialog(null, "Please select a sale to delete.");
    //         }
    //     });
    
    //     return deleteButton;
    // }
    
    // private JButton createUpdateButton() {
    //     JButton updateButton = new JButton("Update Sale");
    
    //     updateButton.addActionListener(e -> {
    //         String dayIdStr = JOptionPane.showInputDialog("Enter Day ID:");
    //         String dateStr = JOptionPane.showInputDialog("Enter new date (YYYY-MM-DD):");
    //         String saleSubtotalStr = JOptionPane.showInputDialog("Enter new sale subtotal:");
    //         String saleTaxStr = JOptionPane.showInputDialog("Enter new sale tax:");
    //         String totalSalesStr = JOptionPane.showInputDialog("Enter new total sales:");
    
    //         PreparedStatement pstmt = null;
    //         Connection conn = null;
    //         try {
    //             conn = Login.getConnection();
    //             pstmt = conn.prepareStatement("UPDATE sales SET date = ?, salesubtotal = ?, saletax = ?, totalsales = ? WHERE dayid = ?");
    //             pstmt.setDate(1, Date.valueOf(dateStr));
    //             pstmt.setDouble(2, Double.parseDouble(saleSubtotalStr));
    //             pstmt.setDouble(3, Double.parseDouble(saleTaxStr));
    //             pstmt.setDouble(4, Double.parseDouble(totalSalesStr));
    //             pstmt.setInt(5, Integer.parseInt(dayIdStr));
    //             pstmt.executeUpdate();
    
    //             pstmt.close();
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println("Error connecting to database: " + ex.getMessage());
    //         } finally {
    //             if (conn != null) {
    //                 try {
    //                     conn.close();
    //                 } catch (SQLException ex) {
    //                     System.out.println("Error closing database connection: " + ex.getMessage());
    //                 }
    //             }
    //         }
    //         String a = null;
    //         String b = null;
    //         refreshSalesTable(a,b);
    //     });
    
    //     return updateButton;
    // }

    /**
     * Button used to create the actual report. As an actionListener for functionality
     * @return JButton
     */
    private JButton createSalesReportButton() {
        JButton salesReportButton = new JButton("Sales report");

        salesReportButton.addActionListener(e -> {
            String startDate = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD) or Cancel twice to get Original:");
            String endDate = JOptionPane.showInputDialog("Enter date (YYYY-MM-DD):"); // Pressing cancel 2ce will cause the table to reset
            
            refreshSalesTable(startDate, endDate);
        });

        return salesReportButton;
    }

    /**
     * Refreshes the Sales Table w/ the Sales Report.
     * @param aDate
     * @param bDate
     * @return none
     */
    private void refreshSalesTable(String aDate, String bDate) {
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        model.setRowCount(0);
    
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Login.getConnection();
            stmt = conn.createStatement();
            if (aDate != null && bDate != null) {
                System.out.println("Sales Report button pressed && in refreshTable\n\n: SELECT * FROM sales WHERE date BETWEEN '" + aDate + "' AND '" + bDate + "'");                
                rs = stmt.executeQuery("SELECT * FROM sales WHERE date BETWEEN '" + aDate + "' AND '" + bDate + "'"); // Change this
            }
            else{
                rs = stmt.executeQuery("SELECT * FROM sales ORDER BY date ASC");
            }
            
    
            while (rs.next()) {
                int dayId = rs.getInt("dayid");
                Date date = rs.getDate("date");
                double salesSubtotal = rs.getDouble("salesubtotal");
                double salesTax = rs.getDouble("saletax");
                double totalSales = rs.getDouble("totalsales");
    
                model.addRow(new Object[]{dayId, date, salesSubtotal, salesTax, totalSales});
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
    