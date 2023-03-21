import java.util.Map;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ExcessReportPanel extends JPanel {

    private JTable dailySalesTable;
    private JScrollPane scrollPane;

    public ExcessReportPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
    
        dailySalesTable = createDailySalesTable();
        scrollPane = new JScrollPane(dailySalesTable);
        add(scrollPane, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton excessReportButton = createExcessReportButton();

        buttonPanel.add(excessReportButton);
    
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
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
    
    // private JButton createExcessReportButton() {
    //     JButton exButt = new JButton(); //Man I never want see this again.

    //     exButt.addActionListener(e -> {
    //         // 1) Prompt the user for a start date.

    //         // 2) For every date in the timeframe: start date to -> current date...
    //         //    i) get order const str
    //         //   ii) Parse for item
    //         //  iii) Tally item

    //         // 3) Get minimum amount for every value from Inventory Minium amounts, store the value.

    //         // 4) Run the calculations for the itemQuantity > 90%, Display them in a new 'inventory like' menu
    //         //  Note: This may require have another function that's like `createNewInventoryPanel`, where it takes in a array of the item ids to be displayed and displays them.

    //     });
        

    //     return exButt;
    // }
    // private JButton createExcessReportButton() {
    //     JButton excessReportButton = new JButton("Excess Report");
    
    //     excessReportButton.addActionListener(e -> {
    //         String startDateStr = JOptionPane.showInputDialog("Enter start date (YYYY-MM-DD):");
    //         Date startDate = Date.valueOf(startDateStr);
    
    //         Map<String, Integer> itemTallies = new HashMap<>();
    //         Map<String, Integer> itemMinimums = new HashMap<>();
    
    //         try {
    //             Connection conn = Login.getConnection();
    
    //             // Get item tallies
    //             Statement stmt = conn.createStatement();
    //             ResultSet rs = stmt.executeQuery("SELECT * FROM dailySales WHERE date >= '" + startDateStr + "' ORDER BY orderid ASC");
    
    //             while (rs.next()) {
    //                 String orderContents = rs.getString("ordercontents");
    
    //                 // Assuming orderContents is a comma-separated list of item names
    //                 String[] items = orderContents.split(",");
    
    //                 for (String item : items) {
    //                     item = item.trim();
    //                     itemTallies.put(item, itemTallies.getOrDefault(item, 0) + 1);
    //                 }
    //             }
    
    //             // Get minimum amounts
    //             stmt = conn.createStatement();
    //             rs = stmt.executeQuery("SELECT item_name, minimum FROM inventory");
    
    //             while (rs.next()) {
    //                 String itemName = rs.getString("item_name");
    //                 int minimum = rs.getInt("minimum");
    //                 itemMinimums.put(itemName, minimum);
    //             }
    
    //             // Display excess items
    //             StringBuilder excessItems = new StringBuilder("Excess items:\n");
    //             for (String itemName : itemTallies.keySet()) {
    //                 int tally = itemTallies.get(itemName);
    //                 int minimum = itemMinimums.getOrDefault(itemName, 0);
    
    //                 if (tally >= 0.9 * minimum) {
    //                     excessItems.append(itemName).append(": ").append(tally).append("\n");
    //                 }
    //             }
    
    //             JOptionPane.showMessageDialog(null, excessItems.toString());
    
    //             rs.close();
    //             stmt.close();
    //             conn.close();
    //         } catch (SQLException ex) {
    //             System.out.println("Error connecting to database: " + ex.getMessage());
    //         }
    //     });
    
    //     return excessReportButton;
    // }
    

    private JButton createExcessReportButton() {
        JButton excessReportButton = new JButton("Excess Report");
    
        excessReportButton.addActionListener(e -> {
            String startDateStr = JOptionPane.showInputDialog("Enter start date (YYYY-MM-DD):");
            Date startDate = Date.valueOf(startDateStr);
    
            Map<String, Integer> itemTallies = new HashMap<>();
            Map<String, Integer> itemMinimums = new HashMap<>();
            Map<String, Integer> itemInventory = new HashMap<>();
    
            try {
                Connection conn = Login.getConnection();
    
                // Get item minimums and inventory
                Statement inventoryStmt = conn.createStatement();
                ResultSet inventoryRs = inventoryStmt.executeQuery("SELECT * FROM inventory");
    
                while (inventoryRs.next()) {
                    String itemId = inventoryRs.getString("item_id");
                    int minimum = inventoryRs.getInt("minimum");
                    int inventory = inventoryRs.getInt("item_amount");
    
                    itemMinimums.put(itemId, minimum);
                    itemInventory.put(itemId, inventory);
                }
    
                // Get orders from the given start date to the current date
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM dailySales WHERE date >= ?");
                pstmt.setDate(1, startDate);
                ResultSet rs = pstmt.executeQuery();
    
                while (rs.next()) {
                    String orderContents = rs.getString("ordercontents");
                    String[] items = orderContents.split(", ");

                    for (String item : items) {
                        String[] itemParts = item.trim().split(" ");
                        int quantity = 1;
                        String itemName;

                        if (itemParts.length > 1 && isNumeric(itemParts[0])) {
                            quantity = Integer.parseInt(itemParts[0]);
                            itemName = item.substring(item.indexOf(' ') + 1).trim();
                        } else {
                            itemName = item;
                        }

                        // Update item tallies
                        itemTallies.put(itemName, itemTallies.getOrDefault(itemName, 0) + quantity);
                    }


                    
                }
    
                rs.close();
                pstmt.close();
                inventoryRs.close();
                inventoryStmt.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Error connecting to database: " + ex.getMessage());
            }
    
            // Display items that sold less than 10% of their inventory
            DefaultTableModel model = new DefaultTableModel();
            JTable excessTable = new JTable(model);
    
            model.addColumn("Item ID");
            model.addColumn("Item Sold");
            model.addColumn("Inventory");
            model.addColumn("Percentage");
    
            for (String itemId : itemTallies.keySet()) {
                int sold = itemTallies.getOrDefault(itemId, 0);
                int inventory = itemInventory.get(itemId);
                double percentage = (double) sold / inventory * 100;
    
                if (percentage < 10) {
                    model.addRow(new Object[]{itemId, sold, inventory, String.format("%.2f", percentage) + "%"});
                }
            }
    
            // Show the excess report in a new window
            JFrame excessReportFrame = new JFrame("Excess Report");
            excessReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            excessReportFrame.setSize(600, 400);
    
            JScrollPane scrollPane = new JScrollPane(excessTable);
            excessReportFrame.add(scrollPane);
    
            excessReportFrame.setVisible(true);
        });
    
        return excessReportButton;
    }
    

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

    private Map<String, Integer> parseOrderContents(String orderContents) {
        Map<String, Integer> itemQuantities = new HashMap<>();
    
        String[] items = orderContents.split(", ");
        for (String item : items) {
            String[] itemParts = item.trim().split(" ");
            int quantity = 1;
            String itemName;
    
            if (itemParts.length > 1 && isNumeric(itemParts[0])) {
                quantity = Integer.parseInt(itemParts[0]);
                itemName = item.substring(item.indexOf(' ') + 1).trim();
            } else {
                itemName = item;
            }
    
            itemQuantities.put(itemName, itemQuantities.getOrDefault(itemName, 0) + quantity);
        }
    
        return itemQuantities;
    }
    
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
}
