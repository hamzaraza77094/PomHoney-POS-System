import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Connection conn;

    public Login() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // empty cell
        panel.add(loginButton);
        setLocationRelativeTo(null);

        getContentPane().add(panel);

        pack();
        setVisible(true);

        

        // establish database connection
    }

    public void actionPerformed(ActionEvent e) {
        String url = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_epsilon";
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("Invalid Username and Password Entered");
            return;
        }
        System.out.println("Opened database successfully");
        JOptionPane.showMessageDialog(null, "Login successful!");
        setVisible(false); // hide the login window
        // create a new instance of the dashboard window
        new GUI();
    }
}

