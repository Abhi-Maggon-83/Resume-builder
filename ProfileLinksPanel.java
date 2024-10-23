import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ProfileLinksPanel extends JPanel {
    private JTextField githubField;
    private JTextField linkedInField;
    private JTextField portfolioField;
    private JButton saveButton;
    private String collegeRegistrationNumber;

    public ProfileLinksPanel(String collegeRegistrationNumber) {
        this.collegeRegistrationNumber = collegeRegistrationNumber;

        setLayout(new GridLayout(4, 2));

        githubField = new JTextField();
        linkedInField = new JTextField();
        portfolioField = new JTextField();
        saveButton = new JButton("Save");

        add(new JLabel("GitHub:"));
        add(githubField);
        add(new JLabel("LinkedIn:"));
        add(linkedInField);
        add(new JLabel("Portfolio:"));
        add(portfolioField);
        add(saveButton);

        saveButton.addActionListener(new SaveButtonListener());

        loadData();
    }

    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root",
                    "password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM profile_links WHERE college_registration_number = '"
                    + collegeRegistrationNumber + "'");

            if (rs.next()) {
                githubField.setText(rs.getString("github"));
                linkedInField.setText(rs.getString("linkedin"));
                portfolioField.setText(rs.getString("portfolio"));
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + ex.getMessage());
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root",
                        "password");
                Statement stmt = conn.createStatement();

                if (githubField.getText().isEmpty() || linkedInField.getText().isEmpty()
                        || portfolioField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    stmt.executeUpdate(
                            "INSERT INTO profile_links (college_registration_number, github, linkedin, portfolio) VALUES ('"
                                    + collegeRegistrationNumber + "', '" + githubField.getText() + "', '"
                                    + linkedInField.getText() + "', '" + portfolioField.getText()
                                    + "') ON DUPLICATE KEY UPDATE github = '" + githubField.getText()
                                    + "', linkedin = '" + linkedInField.getText() + "', portfolio = '"
                                    + portfolioField.getText() + "'");
                    JOptionPane.showMessageDialog(null, "Data saved successfully!");
                }

                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage());
            }
        }
    }
}