// import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ManagerPanel extends JPanel{
    
    public JTable menuTable;
    
    public ManagerPanel(){
    // Allow managers to View, Add, & Update Menu Items and Prices
        //1) Display Menu Items and prices
        String columnNames[] = {"Test1", "Test2", "Test3", "Test4"};
        String data[][] = {
            {"Test5", "Test6", "Test7", "Test8"},
            {"Test9", "Test10", "Test11", "Test12"},
            {"Test9", "Test10", "Test11", "Test12"}
        }; // Tests Data for the table

        JTable menuTable = new JTable(data, columnNames);
        // menuTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        menuTable.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(menuTable);

        add(scrollPane);


        //2) Add Menu Items and Prices
        //3) Edit Menu Items and Prices
    }
    
    // Allow managers to View & Update inventory

    
}
