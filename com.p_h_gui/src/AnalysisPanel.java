
import javax.swing.*;
import java.awt.*;

public class AnalysisPanel extends JPanel {
    private JPanel cards;
    private CardLayout cardLayout;
    private SalesReport salesReport;
    private XReportFeature XReport;
    private EmployeePanel employeePanel;
    private TotalSalesPanel salesPanel;
    private DailySalesPanel totalSalesPanel;

    public AnalysisPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1)); // Change grid layout to 5 rows
        JButton salesReportButton = new JButton("Sales Report");
        JButton xReportButton = new JButton("X & Z Report");
        // JButton zReportButton = new JButton("Z Report");
        JButton excessReportButton = new JButton("Excess Report"); // Add the Total Sales button
        JButton restockReportButton = new JButton("Restock Report");

        salesReportButton.addActionListener(e -> {
            if (salesReport == null) {
                salesReport = new SalesReport();
                cards.add(salesReport, "SalesReport");
            }
            cardLayout.show(cards, "SalesReport");
            revalidate();
            repaint();
        });

        xReportButton.addActionListener(e -> {
            if (XReport == null) {
                XReport = new XReportFeature();
                cards.add(XReport, "XReport");
            }
            cardLayout.show(cards, "XReport");
            revalidate();
            repaint();
        });

        // zReportButton.addActionListener(e -> {
        //     if (salesPanel == null) {
        //         salesPanel = new TotalSalesPanel();
        //         cards.add(salesPanel, "ZReport");
        //     }
        //     cardLayout.show(cards, "ZReport");
        //     revalidate();
        //     repaint();
        // });

        // Add the action listener for the Total Sales button
        excessReportButton.addActionListener(e -> {
            if (totalSalesPanel == null) {
                totalSalesPanel = new DailySalesPanel(); // Replace this line with the appropriate panel for total sales
                cards.add(totalSalesPanel, "ExcessReport");
            }
            cardLayout.show(cards, "ExcessReport");
            revalidate();
            repaint();
        });

        restockReportButton.addActionListener(e -> {
            if (employeePanel == null) {
                employeePanel = new EmployeePanel();
                cards.add(employeePanel, "RestockReport");
            }
            cardLayout.show(cards, "RestockReport");
            revalidate();
            repaint();
        });

        buttonPanel.add(salesReportButton);
        buttonPanel.add(xReportButton);
        // buttonPanel.add(zReportButton);
        buttonPanel.add(excessReportButton); // Add the Total Sales button to the panel
        buttonPanel.add(restockReportButton);

        add(buttonPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards, BorderLayout.CENTER);
    }
}
