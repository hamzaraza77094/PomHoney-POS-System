import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class Login extends JFrame implements ActionListener {
    /**
    * Login is a class used to display a screen which will take an employees 'employee_id', query the csce315331_epsilon database,
    * if the employee_id is correct, then the user is allowed access. Else, is denied to try again.
    *
    * @author Cameron Yoffe
    * @return void
    * @param void
    * @throws SQLException if the connection to the database fails
    * @throws Exception if the query to the database fails
    */

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection conn;
    private JPanel numberPad;
    private String password = "";
    

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
    * actionPerformed is a override from the ActionEvent class which gives functionallity to the 'loginButton'. This is where 'Login'
    * gains access to the 'csce315331_epsilon' database.

    * @param ActionEvent e: This is the trigger for the users action on a button on the 'Login' Frame.
    * @return void: This function does not return anything, however, it does start a GUI() instance.
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
            System.out.println("Opened database successfully");
            //SQL statement for finding the employee ID
            boolean isManager = false;
            try {
                String query = "SELECT * FROM employees WHERE employee_id = " + password;
                //System.out.println(query);
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                if (!resultSet.next()) {
                    System.out.println("User not found, please try again");
                    password = "";
                    passwordField.setText("");
                    return;
                }else{
                    String employee_name = resultSet.getString("employeename");
                    if (employee_name.equals("Karen")) {
                        isManager = true;
                    }

                    JOptionPane.showMessageDialog(null, "Login successful, welcome " + resultSet.getString("employeename"));
                    setVisible(false); // hide the login window
                }
            } catch(Exception ex){
                ex.printStackTrace();
                System.err.println(ex.getClass().getName()+": "+ex.getMessage());
                System.exit(0);
            }
            // create a new instance of the dashboard window
            if (isManager == true) {
                // manager view
                System.out.println("Manager has logged in");
                System.exit(0);
            } else {
                new GUI();
            }
        }
    }
}
