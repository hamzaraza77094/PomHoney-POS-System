import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RestockReport extends JPanel {

    private JTable restockTable;
    private JScrollPane restockScrollPane;

    public RestockReport() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Set up Restock Report table
        restockTable = createRestockTable();
        restockScrollPane = new JScrollPane(restockTable);
        JPanel restockPanel = new JPanel(new BorderLayout());
        restockPanel.setBorder(BorderFactory.createTitledBorder("Restock Report"));
        restockPanel.add(restockScrollPane, BorderLayout.CENTER);

        // Add Restock Report table to the panel
        add(restockPanel, BorderLayout.CENTER);
    }

    private JTable createRestockTable() {
        DefaultTableModel restockModel = new DefaultTableModel(new Object[]{"Item ID", "Item Name", "Item Amount", "Item Measurement Type", "Minimum"}, 0);
        JTable restockTable = new JTable(restockModel);
        restockTable.setAutoCreateRowSorter(true);

        try (Connection connection = connectDatabase()) {
            String query = "SELECT * FROM inventory WHERE item_amount < minimum";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                double itemAmount = resultSet.getDouble("item_amount");
                String itemMeasurementType = resultSet.getString("item_measurement_type");
                double minimum = resultSet.getDouble("minimum");

                restockModel.addRow(new Object[]{itemId, itemName, itemAmount, itemMeasurementType, minimum});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restockTable;
    }

    private Connection connectDatabase() {
        Connection connection = null;
        try {
            connection = Login.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
