package src.main.java.com.cafeteria.server;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/cafeteria";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Additional logging or error handling can be added here if needed
    }
}
