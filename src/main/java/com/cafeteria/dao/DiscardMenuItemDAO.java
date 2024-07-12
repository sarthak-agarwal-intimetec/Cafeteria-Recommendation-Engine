package src.main.java.com.cafeteria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cafeteria.model.DiscardMenuItem;
import src.main.java.com.cafeteria.server.Database;
import src.main.java.com.cafeteria.util.RecommendationEngine;

public class DiscardMenuItemDAO {
    public static void updateDiscardMenuItemList(RecommendationEngine recommendationEngine) {
        String deleteQuery = "DELETE FROM DiscardMenuItem";
        String insertQuery = "INSERT INTO DiscardMenuItem (itemId) VALUES (?)";
        try (Connection conn = Database.getConnection();
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            deleteStmt.executeUpdate();
            for (Integer itemId : recommendationEngine.getItemFeedbackSentiments().keySet()) {
                if (recommendationEngine.getItemRatings().get(itemId) < 2
                        && recommendationEngine.getItemFeedbackSentiments().get(itemId) == "Negative") {
                    insertStmt.setInt(1, itemId);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

    public static List<DiscardMenuItem> getDiscardMenuItems() {
        String query = "SELECT d.id, d.itemId, m.name FROM DiscardMenuItem d INNER JOIN MenuItem m ON d.itemId = m.id";
        List<DiscardMenuItem> discardMenuItems = new ArrayList<>();
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                discardMenuItems.add(new DiscardMenuItem(
                        rs.getInt("d.id"),
                        rs.getInt("d.itemId"),
                        rs.getString("m.name")));
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return discardMenuItems;
    }
}
