import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


/**
* This class is used for the display and functionality of the login window
* @author Hamza Raza, Cameron Yoffe, Jacob Parker, Adam Vick
*/
public class Login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection conn;
    private JPanel numberPad;
    private String password = "";

    /**
    * constructor for the Login page, displays window for password entering
    * @param none
    * @return JPanel
    */
    public Login() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setEditable(false);
        panel.add(passwordField, BorderLayout.NORTH);

        numberPad = new JPanel(new GridLayout(4, 3));
        String[] numberButtons = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "<-", "clear"};
        for (String button : numberButtons) {
            JButton numButton = new JButton(button);
            numButton.addActionListener(this);
            if (button.equals("")) {
                numButton.setEnabled(false);
            }
            numberPad.add(numButton);
        }
        panel.add(numberPad, BorderLayout.CENTER);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton, BorderLayout.SOUTH);

        getContentPane().add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // establish database connection
    }

    /**
    * Used to handle the various button presses that can occur on the login page12
    * @param ActionEvent an action event 'e'
    * @return none
    */
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        if (button.equals("<-")) {
            if (password.length() > 0) {
                password = password.substring(0, password.length() - 1);
                passwordField.setText(password);
            }
        } else if(button.equals("clear")){
            password = "";
            passwordField.setText("");
        }else{
            if(!button.equals("Login")){
                password += button;
                passwordField.setText(password);
            }
        }
        if (button.equals("Login")) {
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon", "csce315331_epsilon_master", "EPSILON");   
            } catch (SQLException ex) {
                System.out.println("Could not open database successfully");
                return;
            }
            boolean isManager = false;

            System.out.println("Opened database successfully");
            // SQL statement for finding the employee ID
            try {
                String query = "SELECT * FROM employees WHERE employee_id = " + password;
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                if (!resultSet.next()) {
                    System.out.println("User not found, please try again");
                    password = "";
                    passwordField.setText("");
                    return;
                } else {
                    isManager = resultSet.getBoolean("ismanager");
                    String employeeName = resultSet.getString("employeename");

                    JOptionPane.showMessageDialog(null, "Login successful, welcome " + employeeName);
                    setVisible(false); // hide the login window
                }
            } catch(Exception ex){
                ex.printStackTrace();
                System.err.println(ex.getClass().getName()+": "+ex.getMessage());
                System.exit(0);
            }
            // create a new instance of the dashboard window
            new GUI(isManager);
        }
    }

    /**
    * This function establishes a connection with our database using the master login
    * @param none
    * @return conn which is a database connection
    */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon", "csce315331_epsilon_master", "EPSILON");
        return conn;
    }
}
