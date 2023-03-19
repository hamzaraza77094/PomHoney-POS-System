import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CartFeature extends JPanel {

    private JTable MenuTable;
    private JTable CartTable;
    private JScrollPane menuScrollPane;
    private JScrollPane cartScrollPane;
    private JLabel salesSubtotalLabel;
    private JLabel taxLabel;
    private JLabel grandTotalLabel;
    private ArrayList<Integer> menuItemIds;
    Integer ingredients_ID_Array[];
    Double ingredients_Amount_Array[];

    public CartFeature() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        MenuTable = createMenuTable();
        menuScrollPane = new JScrollPane(MenuTable);
        add(menuScrollPane, BorderLayout.NORTH);

        CartTable = createCartTable();
        cartScrollPane = new JScrollPane(CartTable);
        add(cartScrollPane, BorderLayout.CENTER);

        JPanel totalsPanel = new JPanel(new GridLayout(3, 1));
        salesSubtotalLabel = new JLabel("Sales Subtotal: $0.00");
        taxLabel = new JLabel("Tax: $0.00");
        grandTotalLabel = new JLabel("Grand Total: $0.00");
        totalsPanel.add(salesSubtotalLabel);
        totalsPanel.add(taxLabel);
        totalsPanel.add(grandTotalLabel);
        add(totalsPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton addButton = createAddButton();
        JButton deleteButton = createDeleteButton();
        JButton resetCartButton = createResetCartButton();
        JButton submitOrderButton = createSubmitOrderButton();

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetCartButton);
        buttonPanel.add(submitOrderButton);

        add(buttonPanel, BorderLayout.SOUTH);

        menuItemIds = new ArrayList<>();
    }

    // ... (rest of the code for createMenuTable remains unchanged)
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

    private JTable createCartTable() {
        DefaultTableModel cartModel = new DefaultTableModel();
        JTable table = new JTable(cartModel);

        cartModel.addColumn("Menu Item ID");
        cartModel.addColumn("Menu Item Name");
        cartModel.addColumn("Menu Item Price");

        return table;
    }

    // Create and configure the Add button
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Item");

        addButton.addActionListener(e -> {
            int selectedRow = MenuTable.getSelectedRow();
            if (selectedRow != -1) {
                int itemId = (int) MenuTable.getValueAt(selectedRow, 0);
                String itemName = (String) MenuTable.getValueAt(selectedRow, 1);
                double itemPrice = (double) MenuTable.getValueAt(selectedRow, 2);

                DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();
                cartModel.addRow(new Object[]{itemId, itemName, itemPrice});
                updateTotals();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to add.");
            }
        });

        return addButton;
    }

    // Create and configure the Delete button
    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete Item");

        deleteButton.addActionListener(e -> {
            int selectedRow = CartTable.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();
                cartModel.removeRow(selectedRow);
                updateTotals();
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to delete.");
            }
        });

        return deleteButton;
    }


    private void updateTotals() {
        double salesSubtotal = 0;
        double taxRate = 0.0625;
    
        for (int i = 0; i < CartTable.getRowCount(); i++) {
            salesSubtotal += (double) CartTable.getValueAt(i, 2);
        }
    
        double tax = salesSubtotal * taxRate;
        double grandTotal = salesSubtotal + tax;
    
        salesSubtotalLabel.setText(String.format("Sales Subtotal: $%.2f", salesSubtotal));
        taxLabel.setText(String.format("Tax: $%.2f", tax));
        grandTotalLabel.setText(String.format("Grand Total: $%.2f", grandTotal));
    }

    private JButton createSubmitOrderButton() {
        JButton submitOrderButton = new JButton("Submit Order");
    
        submitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected menu_item_ids
                ArrayList<Integer> selectedItemIds = new ArrayList<>();
                for (int i = 0; i < CartTable.getRowCount(); i++) {
                    selectedItemIds.add((Integer) CartTable.getValueAt(i, 0));
                }
    
                // Print the selected menu_item_ids
                System.out.println("Selected menu_item_ids: " + selectedItemIds);
    
                ArrayList<String> selectedItemNames = new ArrayList<>();
                for (int i = 0; i < CartTable.getRowCount(); i++) {
                    selectedItemNames.add((String) CartTable.getValueAt(i, 1));
                }
    
                // Calculate the order subtotal, tax, and total
                double orderSubtotal = Double.parseDouble(salesSubtotalLabel.getText().replaceAll("[^\\d.]", ""));
                double salesTax = Double.parseDouble(taxLabel.getText().replaceAll("[^\\d.]", ""));
                double total = Double.parseDouble(grandTotalLabel.getText().replaceAll("[^\\d.]", ""));
    
                // Insert the order into the salesBridge table
                String orderContents = String.join(", ", selectedItemNames);
                try {
                    Connection conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon", "csce315331_epsilon_master", "EPSILON");
                    Statement stmt = conn.createStatement();
                    String insertQuery = "INSERT INTO salesBridge (orderid, dayid, date, ordercontents, ordersubtotal, salestax, total) " +
                            "VALUES (COALESCE((SELECT MAX(orderid) FROM salesBridge), 0) + 1, " +
                            "COALESCE((SELECT MAX(dayid) FROM salesBridge WHERE date = CURRENT_DATE), " +
                            "(SELECT COALESCE(MAX(dayid), 0) + 1 FROM salesBridge)), " +
                            "CURRENT_DATE, " +
                            "'" + orderContents + "', " +
                            "'" + orderSubtotal + "', " +
                            "'" + salesTax + "', " +
                            "'" + total + "'" +
                            ")";
                    stmt.executeUpdate(insertQuery);
                } catch (SQLException ex) {
                    System.out.println("Could not open database successfully" + ex.getMessage());
                    return;
                }
                System.out.println("Opened database successfully");
    
                detractIngredients(selectedItemIds);
    
                menuItemIds.clear();

                clearCart(); // Clear the cart after the order is submitted
                // refreshCartTable();
                updateTotals();
    
                // TODO: showMainPanel("checkoutPanel");
            }
        });
    
        return submitOrderButton;
    }



    // function for detracting ingredients for each item ordered
    public void detractIngredients(ArrayList<Integer> order_Items) { 
        for (int i = 0; i < order_Items.size(); ++i) {
            if (order_Items.get(i) == 1) { // chicken gyro
                ingredients_ID_Array = new Integer[] {1, 3, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            } else if (order_Items.get(i) == 2) { // chicken bowl
                ingredients_ID_Array = new Integer[] {19, 3, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            } else if (order_Items.get(i) == 3) { // hummus and pita
                ingredients_ID_Array = new Integer[] {1, 15, 23};
                ingredients_Amount_Array = new Double[] {1.0, 0.02, 2.0};
            } else if (order_Items.get(i) == 4) { // 2 falalfels
                ingredients_ID_Array = new Integer[] {23};
                ingredients_Amount_Array = new Double[] {2.0};
            } else if (order_Items.get(i) == 5) { // extra protein
                if ((order_Items.get(i-1) == 1) || (order_Items.get(i-1) == 2)) {
                    ingredients_ID_Array = new Integer[] {3};
                    ingredients_Amount_Array = new Double[] {0.25};
                } else if ((order_Items.get(i-1) == 8) || (order_Items.get(i-1) == 9)) {
                    ingredients_ID_Array = new Integer[] {2};
                    ingredients_Amount_Array = new Double[] {0.25};
                }
            } else if (order_Items.get(i) == 6) { // extra dressing
                ingredients_ID_Array = new Integer[] {16};
                ingredients_Amount_Array = new Double[] {0.05};
            } else if (order_Items.get(i) == 7) { // fountain drink
                ingredients_ID_Array = new Integer[] {24};
                ingredients_Amount_Array = new Double[] {1.0};
            } else if (order_Items.get(i) == 8) { // lamb gyro
                ingredients_ID_Array = new Integer[] {1, 2, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            } else if (order_Items.get(i) == 9) { // lamb bowl
                ingredients_ID_Array = new Integer[] {19, 2, 4, 5, 7, 16, 17};
                ingredients_Amount_Array = new Double[] {1.0, 0.25, 0.25, 0.5, 0.01, 0.05, 0.1};
            }

            for (int j = 0; j < ingredients_ID_Array.length; ++j) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon", "csce315331_epsilon_master", "EPSILON");
                    String query = "UPDATE inventory SET item_amount = item_amount - " + ingredients_Amount_Array[j] + " WHERE item_id = " + ingredients_ID_Array[j];
                    Statement stmt = conn.createStatement();
                    ResultSet resultSet = stmt.executeQuery(query);
                } catch(Exception ex){}
            }
        }
    }

    private void clearCart() {
        DefaultTableModel cartModel = (DefaultTableModel) CartTable.getModel();
        cartModel.setRowCount(0);
    }

    // Create and configure the Reset Cart button
    private JButton createResetCartButton() {
        JButton resetCartButton = new JButton("Reset Cart");

        resetCartButton.addActionListener(e -> {
            clearCart();
            updateTotals();
        });

        return resetCartButton;
    }


    
    
}



