package src.main.java.com.cafeteria.dao;

import src.main.java.com.cafeteria.model.DailyMenuItem;
import src.main.java.com.cafeteria.model.DiscardMenuItem;
import src.main.java.com.cafeteria.model.User;
import src.main.java.com.cafeteria.server.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DailyMenuItemDAO {

    public static void addDailyMenuItem(int itemId) {
        String query = "INSERT INTO DailyMenuItem (date, itemId) VALUES(CURDATE(), ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

    public static List<DailyMenuItem> getDailyMenuItems(User user) {
        String query = "SELECT d.id, d.date, d.itemId, m.name, m.averageRating, m.sentiment " +
                "FROM DailyMenuItem d " +
                "INNER JOIN MenuItem m ON d.itemId = m.id " +
                "WHERE d.Date = CURDATE()" +
                "ORDER BY " +
                "CASE WHEN m.DietaryPreference = ? THEN 1 ELSE 0 END DESC, " +
                "CASE WHEN m.SpiceLevel = ? THEN 1 ELSE 0 END DESC, " +
                "CASE WHEN m.CuisineType = ? THEN 1 ELSE 0 END DESC, " +
                "CASE WHEN m.SweetTooth = ? THEN 1 ELSE 0 END DESC";
        List<DailyMenuItem> dailyMenuItems = new ArrayList<>();
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getDietaryPreference());
            stmt.setString(2, user.getSpiceLevel());
            stmt.setString(3, user.getCuisineType());
            stmt.setString(4, user.getIsSweetTooth());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dailyMenuItems.add(new DailyMenuItem(
                        rs.getInt("d.id"),
                        rs.getDate("d.date"),
                        rs.getInt("d.itemId"),
                        rs.getString("m.name"),
                        rs.getDouble("m.averageRating"),
                        rs.getString("m.sentiment")));
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return dailyMenuItems;
    }

    public static void updateVoteCount(int itemIdToVote) {
        String selectQuery = "SELECT vote FROM DailyMenuItem WHERE itemId = ? AND date = CURDATE()";
        String updateQuery = "UPDATE DailyMenuItem SET vote = ? WHERE itemId = ? AND date = CURDATE()";
        try (Connection conn = Database.getConnection(); PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setInt(1, itemIdToVote);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    int vote = rs.getInt("vote") + 1;
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, vote);
                        updateStmt.setInt(2, itemIdToVote);
                        updateStmt.executeUpdate();
                    }
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
