import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EducationPanel extends JPanel {
    private JTextField collegeNameField;
    private JTextField degreeField;
    private JTextField graduationYearField;
    private JButton saveButton;
    private String collegeRegistrationNumber;

    public EducationPanel(String collegeRegistrationNumber) {
        this.collegeRegistrationNumber = collegeRegistrationNumber;

        setLayout(new GridLayout(4, 2));

        collegeNameField = new JTextField();
        degreeField = new JTextField();
        graduationYearField = new JTextField();
        saveButton = new JButton("Save");

        add(new JLabel("College Name:"));
        add(collegeNameField);
        add(new JLabel("Degree:"));
        add(degreeField);
        add(new JLabel("Graduation Year:"));
        add(graduationYearField);
        add(saveButton);

        saveButton.addActionListener(new SaveButtonListener());

        loadData();
    }

    private void loadData() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root",
                    "password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM education WHERE college_registration_number = '" + collegeRegistrationNumber + "'");

            if (rs.next()) {
                collegeNameField.setText(rs.getString("college_name"));
                degreeField.setText(rs.getString("degree"));
                graduationYearField.setText(rs.getString("graduation_year"));
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

                if (collegeNameField.getText().isEmpty() || degreeField.getText().isEmpty()
                        || graduationYearField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    stmt.executeUpdate(
                            "INSERT INTO education (college_registration_number, college_name, degree, graduation_year) VALUES ('"
                                    + collegeRegistrationNumber + "', '" + collegeNameField.getText() + "', '"
                                    + degreeField.getText() + "', '" + graduationYearField.getText()
                                    + "') ON DUPLICATE KEY UPDATE college_name = '" + collegeNameField.getText()
                                    + "', degree = '" + degreeField.getText() + "', graduation_year = '"
                                    + graduationYearField.getText() + "'");
                    JOptionPane.showMessageDialog(null, "Data saved successfully!");
                }

                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage());
            }
        }
    }
}