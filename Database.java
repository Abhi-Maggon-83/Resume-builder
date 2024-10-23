import java.sql.*;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/resume_builder", "root", "12345");
    }
}