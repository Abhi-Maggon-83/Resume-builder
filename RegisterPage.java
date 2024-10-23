import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField mobileNumberField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField collegeRegistrationNumberField;
    private JButton registerButton;

    public RegisterPage() {
        super("Register");
        setLayout(new GridLayout(7, 2));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        mobileNumberField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        collegeRegistrationNumberField = new JTextField();

        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Mobile Number:"));
        add(mobileNumberField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("College Registration Number:"));
        add(collegeRegistrationNumberField);

        registerButton = new JButton("Register");
        add(registerButton);

        registerButton.addActionListener(new RegisterListener());

        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String mobileNumber = mobileNumberField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String collegeRegistrationNumber = collegeRegistrationNumberField.getText();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root",
                        "password");
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                        "INSERT INTO users (first_name, last_name, mobile_number, email, password, college_registration_number) VALUES ('"
                                + firstName + "', '" + lastName + "', '" + mobileNumber + "', '" + email + "', '"
                                + password + "', '" + collegeRegistrationNumber + "')");
                conn.close();
                JOptionPane.showMessageDialog(null, "Registration successful!");
                new ResumeBuilderPage(collegeRegistrationNumber);
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error registering user: " + ex.getMessage());
            }
        }
    }
}