import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class XReportFeature extends JPanel {

    private JTable salesTable;
    private JScrollPane scrollPane;
    private String reportType = "X"; // Default report type is X report
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;


    public XReportFeature() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
    
        salesTable = createSalesTable();
        scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);
    
        // Create a parent panel for totalsPanel and buttonPanel
        JPanel southPanel = new JPanel(new BorderLayout());
    
        JPanel totalsPanel = createTotalsPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton xReportButton = createSalesReportButton("X");
        JButton zReportButton = createSalesReportButton("Z");
        buttonPanel.add(xReportButton);
        buttonPanel.add(zReportButton);
    
        southPanel.add(totalsPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        add(southPanel, BorderLayout.SOUTH);
    
        // Refresh the sales table after the totalsPanel is initialized
        refreshSalesTable();
    }
    


    private JPanel createTotalsPanel() {
        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new GridLayout(1, 3));
    
        subtotalLabel = new JLabel("Subtotal: $0.00");
        taxLabel = new JLabel("Tax: $0.00");
        totalLabel = new JLabel("Total: $0.00");
    
        totalsPanel.add(subtotalLabel);
        totalsPanel.add(taxLabel);
        totalsPanel.add(totalLabel);
    
        return totalsPanel;
    }

    private JButton createSalesReportButton(String reportType) {
        JButton salesReportButton = new JButton("Generate " + reportType + " Report");
    
        salesReportButton.addActionListener(e -> {
            if (reportType.equals("Z")) {
                resetXReport();
            }
            this.reportType = reportType;
            refreshSalesTable();
        });
    
        return salesReportButton;
    }

    private JTable createSalesTable() {
        DefaultTableModel model = new DefaultTableModel();
    
        if (salesTable == null) {
            salesTable = new JTable(model);
        } else {
            salesTable.setModel(model);
        }
    
        model.addColumn("Report ID");
        model.addColumn("Date");
        model.addColumn("Sales Subtotal");
        model.addColumn("Tax Amount");
        model.addColumn("Total Sales");
        model.addColumn("Total Transactions");
    
        if (reportType.equals("Z")) {
            model.addColumn("Reset Date");
        }
    
        return salesTable;
    }
    

    private void updateTotals() {
        double subtotal = 0;
        double tax = 0;
        double total = 0;
    
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        int rowCount = model.getRowCount();
    
        for (int i = 0; i < rowCount; i++) {
            subtotal += (double) model.getValueAt(i, 2);
            tax += (double) model.getValueAt(i, 3);
            total += (double) model.getValueAt(i, 4);
        }
    
        subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
        taxLabel.setText(String.format("Tax: $%.2f", tax));
        totalLabel.setText(String.format("Total: $%.2f", total));
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
    
            String query = "SELECT * FROM x_report ORDER BY date ASC";
            if (reportType.equals("Z")) {
                query = "SELECT * FROM z_report ORDER BY start_date ASC";
            }
    
            rs = stmt.executeQuery(query);
    
            while (rs.next()) {
                int reportId = rs.getInt("report_id");
                Date dateFromTable = rs.getDate("date");
                double salesSubtotal = rs.getDouble("sales_subtotal");
                double taxAmount = rs.getDouble("tax_amount");
                double totalSales = rs.getDouble("total_sales");
                int totalTransactions = rs.getInt("total_transactions");
    
                if (reportType.equals("Z")) {
                    Date startDate = rs.getDate("start_date");
                    Date endDate = rs.getDate("end_date");
                    Date resetDate = rs.getDate("reset_date");
                    model.addRow(new Object[]{reportId, startDate, endDate, salesSubtotal, taxAmount, totalSales, totalTransactions, resetDate});
                } else {
                    model.addRow(new Object[]{reportId, dateFromTable, salesSubtotal, taxAmount, totalSales, totalTransactions});
                }
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
        updateTotals();
    }
        

    private void resetXReport() {
        // Code to reset the X report data and insert the totals into the Z report table
    }
}
