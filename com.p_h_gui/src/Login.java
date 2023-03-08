// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.sql.*;

// public class Login extends JFrame implements ActionListener {
//     private JTextField usernameField;
//     private JPasswordField passwordField;
//     private JButton loginButton;
//     private Connection conn;

//     public Login() {
//         super("Login");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(300, 150);
//         setResizable(false);

//         JPanel panel = new JPanel(new GridLayout(3, 2));

//         JLabel userLabel = new JLabel("Username:");
//         usernameField = new JTextField(20);
//         JLabel passLabel = new JLabel("Password:");
//         passwordField = new JPasswordField(20);
//         loginButton = new JButton("Login");
//         loginButton.addActionListener(this);

//         panel.add(userLabel);
//         panel.add(usernameField);
//         panel.add(passLabel);
//         panel.add(passwordField);
//         panel.add(new JLabel()); // empty cell
//         panel.add(loginButton);
//         setLocationRelativeTo(null);

//         getContentPane().add(panel);

//         pack();
//         setVisible(true);

        

//         // establish database connection
//     }

//     public void actionPerformed(ActionEvent e) {
//         String url = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon";
//         String username = usernameField.getText();
//         String password = new String(passwordField.getPassword());

//         try {
//             conn = DriverManager.getConnection(url, username, password);
//         } catch (SQLException ex) {
//             System.out.println("Invalid Username and Password Entered");
//             return;
//         }
//         System.out.println("Opened database successfully");
//         JOptionPane.showMessageDialog(null, "Login successful!");
//         setVisible(false); // hide the login window
//         // create a new instance of the dashboard window
//         new GUI();
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
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
            try {
                String query = "SELECT * FROM employees WHERE employee_id = " + password;
                // System.out.println(query);
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                if (!resultSet.next()) {
                    System.out.println("User not found, please try again");
                    password = "";
                    passwordField.setText("");
                    return;
                }else{
                    JOptionPane.showMessageDialog(null, "Login successful, welcome " + resultSet.getString("employeename"));
                    setVisible(false); // hide the login window
                }
            }catch(Exception ex){
                ex.printStackTrace();
                System.err.println(ex.getClass().getName()+": "+ex.getMessage());
                System.exit(0);
            }
            // create a new instance of the dashboard window
            new GUI();
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon", "csce315331_epsilon_master", "EPSILON");
        return conn;
    }
    
}