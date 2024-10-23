import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ProjectsPanel extends JPanel {
    private JTextField projectNameField;
    private JTextField projectDescriptionField;
    private JButton saveButton;
    private String collegeRegistrationNumber;

    public ProjectsPanel(String collegeRegistrationNumber) {
        this.collegeRegistrationNumber = collegeRegistrationNumber;

        setLayout(new GridLayout(3, 2));

        projectNameField = new JTextField();
        projectDescriptionField = new JTextField();
        saveButton = new JButton("Save");

        add(new JLabel("Project Name:"));
        add(projectNameField);
        add(new JLabel("Project Description:"));
        add(projectDescriptionField);
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
                    "SELECT * FROM projects WHERE college_registration_number = '" + collegeRegistrationNumber + "'");

            if (rs.next()) {
                projectNameField.setText(rs.getString("project_name"));
                projectDescriptionField.setText(rs.getString("project_description"));
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

                if (projectNameField.getText().isEmpty() || projectDescriptionField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    stmt.executeUpdate(
                            "INSERT INTO projects (college_registration_number, project_name, project_description) VALUES ('"
                                    + collegeRegistrationNumber + "', '" + projectNameField.getText() + "', '"
                                    + projectDescriptionField.getText() + "') ON DUPLICATE KEY UPDATE project_name = '"
                                    + projectNameField.getText() + "', project_description = '"
                                    + projectDescriptionField.getText() + "'");
                    JOptionPane.showMessageDialog(null, "Data saved successfully!");
                }

                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage());
            }
        }
    }
}