
import javax.swing.*;
import java.awt.*;

/** 
 * This class sets up the panel containing the anaylsis features accesible by users
 * @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/

public class AnalysisPanel extends JPanel {
    private JPanel cards;
    private CardLayout cardLayout;
    private SalesReport salesReport;
    private XReportFeature XReport;
    private RestockReport restockReport;
    private ExcessReport excessReportPanel;

    /**
    * constructor for the AnalysisPanel class which calls the initComponents() function 
    * @param none
    * @return JPanel
    */
    public AnalysisPanel() {
        initComponents();
    }

    /**
    * creates the display and buttons within the analysis panel, defines ActionListeners for each button
    * @param none
    * @return JPanel
    */
    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1)); // Change grid layout to 5 rows
        JButton salesReportButton = new JButton("Sales Report");
        JButton xReportButton = new JButton("X & Z Report");
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

        // Add the action listener for the Total Sales button
        excessReportButton.addActionListener(e -> {
            if (excessReportPanel == null) {
                excessReportPanel = new ExcessReport(); // Replace this line with the appropriate panel for total sales
                cards.add(excessReportPanel, "ExcessReport");
            }
            cardLayout.show(cards, "ExcessReport");
            revalidate();
            repaint();
        });

        restockReportButton.addActionListener(e -> {
            if (restockReport == null) {
                restockReport = new RestockReport();
                cards.add(restockReport, "RestockReport");
            }
            cardLayout.show(cards, "RestockReport");
            revalidate();
            repaint();
        });

        buttonPanel.add(salesReportButton);
        buttonPanel.add(xReportButton);
        buttonPanel.add(excessReportButton); // Add the Total Sales button to the panel
        buttonPanel.add(restockReportButton);

        add(buttonPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards, BorderLayout.CENTER);
    }
}
