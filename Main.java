import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Register JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // Create schema
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "password")) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS resume_builder");
            }
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Create tables
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                java.sql.Statement stmt = conn.createStatement();
                stmt.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS profile_links (college_registration_number VARCHAR(255) PRIMARY KEY, github VARCHAR(255), linkedin VARCHAR(255), portfolio VARCHAR(255))");
            }
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Start GUI
        SwingUtilities.invokeLater(() -> {
            try {
                new ResumeBuilderGUI();
            } catch (IOException e) {
                System.out.println("Error starting GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}