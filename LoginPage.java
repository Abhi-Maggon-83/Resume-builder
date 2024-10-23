import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField collegeRegistrationNumberField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPage() {
        super("Login");
        setLayout(new GridLayout(3, 2));

        collegeRegistrationNumberField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        add(new JLabel("College Registration Number:"));
        add(collegeRegistrationNumberField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);

        loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(new LoginListener());

        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String collegeRegistrationNumber = collegeRegistrationNumberField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root",
                        "password");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE college_registration_number = '"
                        + collegeRegistrationNumber + "' OR email = '" + email + "'");

                if (rs.next()) {
                    if (rs.getString("password").equals(password)) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        new ResumeBuilderPage(collegeRegistrationNumber);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User not found!");
                }

                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error logging in: " + ex.getMessage());
            }
        }
    }
}