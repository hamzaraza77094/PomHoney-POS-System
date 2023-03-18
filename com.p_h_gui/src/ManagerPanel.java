
import javax.swing.*;
import java.awt.*;

public class ManagerPanel extends JPanel {
    private JPanel cards;
    private CardLayout cardLayout;
    private InventoryPanel inventoryPanel;
    private MenuPanel menuPanel;
    private EmployeePanel employeePanel;
    private TotalSalesPanel salesPanel;
    private DailySalesPanel totalSalesPanel;

    public ManagerPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1)); // Change grid layout to 5 rows
        JButton inventoryButton = new JButton("Inventory");
        JButton menuButton = new JButton("Menu");
        JButton dailySalesButton = new JButton("Total Sales");
        JButton totalSalesButton = new JButton("Daily Sales"); // Add the Total Sales button
        JButton employeesButton = new JButton("Employees");

        inventoryButton.addActionListener(e -> {
            if (inventoryPanel == null) {
                inventoryPanel = new InventoryPanel();
                cards.add(inventoryPanel, "InventoryPanel");
            }
            cardLayout.show(cards, "InventoryPanel");
            revalidate();
            repaint();
        });

        menuButton.addActionListener(e -> {
            if (menuPanel == null) {
                menuPanel = new MenuPanel();
                cards.add(menuPanel, "MenuPanel");
            }
            cardLayout.show(cards, "MenuPanel");
            revalidate();
            repaint();
        });

        dailySalesButton.addActionListener(e -> {
            if (salesPanel == null) {
                salesPanel = new TotalSalesPanel();
                cards.add(salesPanel, "SalesPanel");
            }
            cardLayout.show(cards, "SalesPanel");
            revalidate();
            repaint();
        });

        // Add the action listener for the Total Sales button
        totalSalesButton.addActionListener(e -> {
            if (totalSalesPanel == null) {
                totalSalesPanel = new DailySalesPanel(); // Replace this line with the appropriate panel for total sales
                cards.add(totalSalesPanel, "TotalSalesPanel");
            }
            cardLayout.show(cards, "TotalSalesPanel");
            revalidate();
            repaint();
        });

        employeesButton.addActionListener(e -> {
            if (employeePanel == null) {
                employeePanel = new EmployeePanel();
                cards.add(employeePanel, "EmployeePanel");
            }
            cardLayout.show(cards, "EmployeePanel");
            revalidate();
            repaint();
        });

        buttonPanel.add(inventoryButton);
        buttonPanel.add(menuButton);
        buttonPanel.add(dailySalesButton);
        buttonPanel.add(totalSalesButton); // Add the Total Sales button to the panel
        buttonPanel.add(employeesButton);

        add(buttonPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards, BorderLayout.CENTER);
    }
}
