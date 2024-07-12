package src.main.java.com.cafeteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import src.main.java.com.cafeteria.server.Database;

public class LoginActivityDAO {
    public static void addLoginActivity(String userId, String activityMessage) {
        String query = "INSERT INTO LoginActivity (userId, activityMessage) VALUES (?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, activityMessage);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }
}
