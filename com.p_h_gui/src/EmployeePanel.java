import java.text.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
* This class is used to create the Employee Panel
* @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/

public class EmployeePanel extends JPanel {

    private JTable employeeTable;
    private JScrollPane scrollPane;

    /**
    * constructor for the EmployeePanel class, calls initComponents()
    * @param none
    * @return JTable
    */
    public EmployeePanel() {
        initComponents();
    }

    /**
    * Creates the display and buttons for the employee table panel
    * @param none
    * @return JTable
    */
    private void initComponents() {
        setLayout(new BorderLayout());

        employeeTable = createEmployeeTable();
        scrollPane = new JScrollPane(employeeTable);
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
    * creates the employee table containing the employee information from our database to be displayed on the Employee Panel
    * @param none
    * @return JTable
    */
    private JTable createEmployeeTable() {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        // Add columns to the employee table
        model.addColumn("Employee ID");
        model.addColumn("Employee Name");
        model.addColumn("Employee DOB");
        model.addColumn("Is Manager");
        model.addColumn("Employee Salary");

        // Retrieve data from the employee table
        try {
            Connection conn = Login.getConnection();
            Statement stmt = conn.createStatement();
            // ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
            ResultSet rs = stmt.executeQuery("SELECT employee_id, employeename, employeedob, ismanager, employeesalary FROM employees");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String employeeName = rs.getString("employeename");
                Date employeeDobb = rs.getDate("employeedob");
                String employeeDob = sdf.format(employeeDobb);
                boolean isManager = rs.getBoolean("ismanager");
                double employeeSalary = rs.getDouble("employeesalary");
                model.addRow(new Object[]{employeeId, employeeName, employeeDob, isManager, employeeSalary});
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
        JButton addButton = new JButton("Add Employee");
    
        addButton.addActionListener(e -> {
            // Prompt user to input new employee data
            String employeeIdStr = JOptionPane.showInputDialog("Enter employee ID:");
            String employeeName = JOptionPane.showInputDialog("Enter employee name:");
            String employeeDob = JOptionPane.showInputDialog("Enter employee DOB (YYYY-MM-DD):");
            String isManagerStr = JOptionPane.showInputDialog("Enter true if employee is a manager, false otherwise:");
            String employeeSalary = JOptionPane.showInputDialog("Enter employee salary:");

    
            // Insert new employee into the employee table
            PreparedStatement pstmt = null;
            Connection conn = null;

            try {
                conn = Login.getConnection();
                System.out.println("Working Stage 3");
    
                // Insert the new employee with the given employee_id value
                pstmt = conn.prepareStatement("INSERT INTO employees (employee_id, employeename, employeedob, ismanager, employeesalary) VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(employeeIdStr));
                pstmt.setString(2, employeeName);
                System.out.println("Working Stage 6");
                pstmt.setDate(3, Date.valueOf(employeeDob));
                System.out.println("Working Stage 7");
                pstmt.setBoolean(4, Boolean.parseBoolean(isManagerStr));
                pstmt.setDouble(5, Double.parseDouble(employeeSalary));
                pstmt.executeUpdate();
                System.out.println("Working Stage 8");
    
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
    
            // Refresh the employee table
            refreshEmployeeTable();
        });
    
        return addButton;
    }
    
    


    /**
    * code for creating the Delete button, called by initComponents()
    * @param none
    * @return deleteButton
    */
    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete Employee");
    
        deleteButton.addActionListener(e -> {
            // Implement code for deleting an employee
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
    
                try {
                    Connection conn = Login.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement("DELETE FROM employees WHERE employee_id = ?");
                    pstmt.setInt(1, employeeId);
                    pstmt.executeUpdate();
    
                    pstmt.close();
                    conn.close();
    
                    // Refresh the employee table
                    DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    System.out.println("Error connecting to database: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an employee to delete.");
            }
            // Refresh the employee table
            refreshEmployeeTable();
        });
    
        return deleteButton;
    }
    

    /**
    * code for creating the Update button, called by initComponents()
    * @param none
    * @return updateButton
    */
    private JButton createUpdateButton() {
        JButton updateButton = new JButton("Update Employee");
    
        updateButton.addActionListener(e -> {
            String employeeIdStr = JOptionPane.showInputDialog("Enter employee ID:");
            String employeeName = JOptionPane.showInputDialog("Enter new employee name:");
            String employeeDob = JOptionPane.showInputDialog("Enter new employee date of birth (YYYY-MM-DD):");
            String isManagerStr = JOptionPane.showInputDialog("Is the employee a manager? (yes/no):");
            String employeeSalaryStr = JOptionPane.showInputDialog("Enter new employee salary:");
    
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = Login.getConnection();
                pstmt = conn.prepareStatement("UPDATE employees SET employeename = ?, employeedob = ?, ismanager = ?, employeesalary = ? WHERE employee_id = ?");
                pstmt.setString(1, employeeName);
                pstmt.setDate(2, java.sql.Date.valueOf(employeeDob));
                pstmt.setBoolean(3, "yes".equalsIgnoreCase(isManagerStr));
                pstmt.setDouble(4, Double.parseDouble(employeeSalaryStr));
                pstmt.setInt(5, Integer.parseInt(employeeIdStr));
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
    
            // Refresh the employee table
            refreshEmployeeTable();
        });
    
        return updateButton;
    }
    

    /**
    * refreshes the employee table panel to contain the most up-to-date information from the database table, called when an employee is added, deleted, or updated
    * @param none
    * @return none
    */
    private void refreshEmployeeTable() {
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);
    
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = Login.getConnection();
            stmt = conn.createStatement();
            // rs = stmt.executeQuery("SELECT * FROM employees ORDER BY employee_id ASC");
            rs = stmt.executeQuery("SELECT employee_id, employeename, employeedob, ismanager, employeesalary FROM employees");

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            while (rs.next()) {
                int employeeId = rs.getInt("employee_id");
                String employeeName = rs.getString("employeename");
                Date employeeDobb = rs.getDate("employeedob");
                String employeeDob = sdf.format(employeeDobb);
                boolean isManager = rs.getBoolean("ismanager");
                double employeeSalary = rs.getDouble("employeesalary");
    
                model.addRow(new Object[]{employeeId, employeeName, employeeDob, isManager, employeeSalary});
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
