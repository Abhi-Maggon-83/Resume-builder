import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResumeBuilderPage extends JFrame {
    private JPanel buttonPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JButton educationButton;
    private JButton projectsButton;
    private JButton profileLinksButton;
    private JButton resumeFormat1Button;
    private JButton resumeFormat2Button;
    private EducationPanel educationPanel;
    private ProjectsPanel projectsPanel;
    private ProfileLinksPanel profileLinksPanel;
    private String collegeRegistrationNumber;

    public ResumeBuilderPage(String collegeRegistrationNumber) {
        super("Resume Builder");
        this.collegeRegistrationNumber = collegeRegistrationNumber;

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        educationButton = new JButton("Education");
        projectsButton = new JButton("Projects");
        profileLinksButton = new JButton("Profile Links");
        resumeFormat1Button = new JButton("Resume Format 1");
        resumeFormat2Button = new JButton("Resume Format 2");

        educationPanel = new EducationPanel(collegeRegistrationNumber);
        projectsPanel = new ProjectsPanel(collegeRegistrationNumber);
        profileLinksPanel = new ProfileLinksPanel(collegeRegistrationNumber);

        buttonPanel.add(educationButton);
        buttonPanel.add(projectsButton);
        buttonPanel.add(profileLinksButton);
        buttonPanel.add(resumeFormat1Button);
        buttonPanel.add(resumeFormat2Button);

        cardPanel.add(educationPanel, "education");
        cardPanel.add(projectsPanel, "projects");
        cardPanel.add(profileLinksPanel, "profileLinks");

        add(buttonPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        educationButton.addActionListener(new EducationButtonListener());
        projectsButton.addActionListener(new ProjectsButtonListener());
        profileLinksButton.addActionListener(new ProfileLinksButtonListener());

        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class EducationButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "education");
        }
    }

    private class ProjectsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "projects");
        }
    }

    private class ProfileLinksButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "profileLinks");
        }
    }
}