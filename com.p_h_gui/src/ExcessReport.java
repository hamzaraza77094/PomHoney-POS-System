import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* This class is used to generate the excess report based on sales performance
* @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/

public class ExcessReport extends JPanel {

    private JTable restockTable;
    private JScrollPane restockScrollPane;

    /**
    * constructor for the ExcessReport class, calls initComponents()
    * @param none
    * @return JPanel
    */
    public ExcessReport() {
        initComponents();
    }

    /**
    * creates the display for the restock table
    * @param none
    * @return JPanel
    */
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

    /**
    * This function creates a restock table showing how many of each inventory item have been used so the correct amount is
    * purchased when the manager orders supplies for a restock
    * @param none
    * @return none
    */
    private JTable createRestockTable() {
        DefaultTableModel restockModel = new DefaultTableModel(new Object[]{"Item ID", "Item Name", "Item Amount", "Item Measurement Type", "Minimum", "Maximum"}, 0);
        JTable restockTable = new JTable(restockModel);
        restockTable.setAutoCreateRowSorter(true);

        try (Connection connection = connectDatabase()) {
            String query = "SELECT * FROM inventory WHERE item_amount <= (maximum * 0.9)";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String itemName = resultSet.getString("item_name");
                double itemAmount = resultSet.getDouble("item_amount");
                String itemMeasurementType = resultSet.getString("item_measurement_type");
                double minimum = resultSet.getDouble("minimum");
                double maximum = resultSet.getDouble("maximum");

                restockModel.addRow(new Object[]{itemId, itemName, itemAmount, itemMeasurementType, minimum, maximum});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restockTable;
    }

    /**
    * function used to connect to the database so the program can communicate with it
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
}
