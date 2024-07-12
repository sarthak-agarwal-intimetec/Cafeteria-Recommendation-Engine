package src.main.java.com.cafeteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cafeteria.model.Notification;
import src.main.java.com.cafeteria.server.Database;

public class NotificationDAO {
    public static void addNotification(String message) {
        String query = "INSERT INTO Notification (message) VALUES (?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

    public static List<Notification> getNotifications() {
        String query = "SELECT * FROM Notification WHERE DATE(`timestamp`) = CURDATE()";
        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notifications.add(new Notification(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getTimestamp("timestamp")));
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return notifications;
    }
}
