package src.main.java.com.cafeteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cafeteria.model.Feedback;
import src.main.java.com.cafeteria.server.Database;

public class FeedbackDAO {
    public static List<Feedback> getFeedbacks() {
        String query = "SELECT f.id, f.comment, f.rating, f.feedbackDate, f.itemId, f.userId, m.name FROM Feedback f INNER JOIN MenuItem m ON f.itemId = m.id ";
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                feedbacks.add(new Feedback(
                        rs.getInt("f.id"),
                        rs.getString("f.comment"),
                        rs.getInt("f.rating"),
                        rs.getDate("f.feedbackDate"),
                        rs.getInt("f.itemId"),
                        rs.getString("f.userId"),
                        rs.getString("m.name")));
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return feedbacks;
    }

    public static void addFeedback(int itemIdForFeedback, int itemRating, String itemComment, String userId) {
        String query = "INSERT INTO Feedback (itemId, rating, comment, userId, feedbackDate) VALUES (?, ?, ?, ?, CURDATE() - 1)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemIdForFeedback);
            stmt.setInt(2, itemRating);
            stmt.setString(3, itemComment);
            stmt.setString(4, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }
}
