import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/** 
 * A class used to satisfy the X&Z Report Requirement. 
 * @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class XReportFeature extends JPanel {

    private JTable xReportTable;
    private JTable zReportTable;
    private JScrollPane xReportScrollPane;
    private JScrollPane zReportScrollPane;

    /**
     * Constructor for the XReportPanel
     * @return JPanel
     */
    public XReportFeature() {
        initComponents();
    }

    /** 
     * Used to set the paramaters of 'XReportPanel()'. 
     * @param none
     * @return JPanel
    */
    private void initComponents() {
        setLayout(new BorderLayout());

        // Set up X_Report
        xReportTable = createXReportTable();
        xReportScrollPane = new JScrollPane(xReportTable);
        JPanel xReportPanel = new JPanel(new BorderLayout());
        xReportPanel.setBorder(BorderFactory.createTitledBorder("X_Report"));
        xReportPanel.add(xReportScrollPane, BorderLayout.CENTER);

        // Set up Z_Report
        zReportTable = createZReportTable();
        zReportScrollPane = new JScrollPane(zReportTable);
        JPanel zReportPanel = new JPanel(new BorderLayout());
        zReportPanel.setBorder(BorderFactory.createTitledBorder("Z_Report"));
        zReportPanel.add(zReportScrollPane, BorderLayout.CENTER);

        // Combine X_Report and Z_Report
        JPanel reportsPanel = new JPanel(new GridLayout(2, 1));
        reportsPanel.add(xReportPanel);
        reportsPanel.add(zReportPanel);
        add(reportsPanel, BorderLayout.CENTER);

        // Add Generate Z_Report button
        JButton generateZReportButton = createGenerateZReportButton();
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateZReportButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the JTable that will display the X Report
     * @param none
     * @return JTable
     */
    private JTable createXReportTable() {
        DefaultTableModel xReportModel = new DefaultTableModel(new Object[]{"Transaction ID", "Date", "Sales Subtotal", "Tax Amount", "Total Sales", "Total Transactions"}, 0);
        JTable xReportTable = new JTable(xReportModel);
        xReportTable.setAutoCreateRowSorter(true);
    
        try (Connection connection = connectDatabase()) {
            String query = "SELECT * FROM x_report";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                int transactionId = resultSet.getInt("report_id");
                Date date = resultSet.getDate("date");
                double salesSubtotal = resultSet.getDouble("sales_subtotal");
                double taxAmount = resultSet.getDouble("tax_amount");
                double totalSales = resultSet.getDouble("total_sales");
                int totalTransactions = resultSet.getInt("total_transactions");
    
                xReportModel.addRow(new Object[]{transactionId, date, salesSubtotal, taxAmount, totalSales, totalTransactions});
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return xReportTable;
    }
    
     /**
     * Creates the JTable that will display the Z Report
     * @param none
     * @return JTable
     */
    private JTable createZReportTable() {
        DefaultTableModel zReportModel = new DefaultTableModel(new Object[]{"Report ID", "Start Date", "End Date", "Sales Subtotal", "Tax Amount", "Total Sales", "Total Transactions"}, 0);
        JTable zReportTable = new JTable(zReportModel);
        zReportTable.setAutoCreateRowSorter(true);
    
        try (Connection connection = connectDatabase()) {
            String query = "SELECT * FROM z_report";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
    
            while (resultSet.next()) {
                int reportId = resultSet.getInt("report_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                double salesSubtotal = resultSet.getDouble("sales_subtotal");
                double taxAmount = resultSet.getDouble("tax_amount");
                double totalSales = resultSet.getDouble("total_sales");
                int totalTransactions = resultSet.getInt("total_transactions");
    
                zReportModel.addRow(new Object[]{reportId, startDate, endDate, salesSubtotal, taxAmount, totalSales, totalTransactions});
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return zReportTable;
    }
    
     /**
     * Creates the JButton that will display the Z Report
     * @param none
     * @return JTable
     */
    private JButton createGenerateZReportButton() {
        JButton generateZReportButton = new JButton("Generate Z_Report");
    
        generateZReportButton.addActionListener(e -> {
            DefaultTableModel zReportModel = (DefaultTableModel) zReportTable.getModel();
            DefaultTableModel xReportModel = (DefaultTableModel) xReportTable.getModel();
    
            if (xReportTable.getRowCount() > 0) {
                int lastReportId = zReportTable.getRowCount() > 0
                        ? (int) zReportTable.getValueAt(zReportTable.getRowCount() - 1, 0)
                        : 0;
                int reportId = lastReportId + 1;
                Date startDate = (Date) xReportTable.getValueAt(0, 1);
                Date endDate = (Date) xReportTable.getValueAt(xReportTable.getRowCount() - 1, 1);
    
                double salesSubtotal = 0;
                double taxAmount = 0;
                double totalSales = 0;
                int totalTransactions = xReportTable.getRowCount();
    
                for (int i = 0; i < totalTransactions; i++) {
                    salesSubtotal += (double) xReportTable.getValueAt(i, 2);
                    taxAmount += (double) xReportTable.getValueAt(i, 3);
                    totalSales += (double) xReportTable.getValueAt(i, 4);
                }
    
                zReportModel.addRow(new Object[]{reportId, startDate, endDate, salesSubtotal, taxAmount, totalSales, totalTransactions});
    
                // Save the generated Z-Report in the database
                try (Connection connection = connectDatabase()) {
                    saveZReportToDatabase(connection, reportId, startDate, endDate, salesSubtotal, taxAmount, totalSales, totalTransactions);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
    
                // Clear the X-Report table in the GUI
                clearXReportTable(xReportModel);
    
                // Clear the X-Report table in the database
                try (Connection connection = connectDatabase()) {
                    clearXReportTableInDatabase(connection);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
    
            } else {
                JOptionPane.showMessageDialog(null, "X_Report is empty. Please add data to X_Report first.");
            }
        });
    
        return generateZReportButton;
    }
    
    /**
     * Clears the X Report in the current table
     * @param xReportModel
     * @return none
     */
    private void clearXReportTable(DefaultTableModel xReportModel) {
        xReportModel.setRowCount(0);
    } 

    /**
     * Establishes a conenction to the database
     * @param none
     * @return Connection
     */
    private Connection connectDatabase() {
        Connection connection = null;
        try {
            connection = Login.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Clears the X Report in the Database
     * @param connection
     * @return none
     */
    private void clearXReportTableInDatabase(Connection connection) {
        String query = "DELETE FROM x_report";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the Z Report to the Database
     * @param connection
     * @param reportId
     * @param startDate
     * @param endDate
     * @param salesSubtotal
     * @param taxAmount
     * @param totalSales
     * @param totalTransactions
     * @return none
     */
    private void saveZReportToDatabase(Connection connection, int reportId, Date startDate, Date endDate, double salesSubtotal, double taxAmount, double totalSales, int totalTransactions) {
        String query = "INSERT INTO z_report (report_id, start_date, end_date, sales_subtotal, tax_amount, total_sales, total_transactions) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reportId);
            preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));
            preparedStatement.setDouble(4, salesSubtotal);
            preparedStatement.setDouble(5, taxAmount);
            preparedStatement.setDouble(6, totalSales);
            preparedStatement.setInt(7, totalTransactions);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
}
