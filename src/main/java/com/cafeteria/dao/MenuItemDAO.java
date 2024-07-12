package src.main.java.com.cafeteria.dao;

import src.main.java.com.cafeteria.model.MenuItem;
import src.main.java.com.cafeteria.server.Database;
import src.main.java.com.cafeteria.util.RecommendationEngine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public static MenuItem getMenuItemById(String itemId) {
        String query = "SELECT * FROM MenuItem WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new MenuItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getBoolean("isAvailable"));
                }
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return null;
    }

    public static List<MenuItem> getAllMenuItems() {
        String query = "SELECT * FROM MenuItem";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                menuItems.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("isAvailable")));
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return menuItems;
    }

    public static void addMenuItem(String itemName, double itemPrice, String itemIsAvailable) {
        String query = "INSERT INTO MenuItem (name, price, isAvailable) VALUES(?, ?, ?)";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setDouble(2, itemPrice);
            stmt.setString(3, itemIsAvailable);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

    public static void updateMenuItem(String itemId, String itemName, double itemPrice, String itemIsAvailable) {
        String query = "UPDATE MenuItem SET name = ?, price = ?, isAvailable = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setDouble(2, itemPrice);
            stmt.setString(3, itemIsAvailable);
            stmt.setString(4, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

    public static void deleteMenuItem(String itemId) {
        String query = "DELETE FROM MenuItem WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

    public static void updateRatingSentiment(RecommendationEngine recommendationEngine) {
        String query = "UPDATE MenuItem SET sentiment = ?, averageRating = ? WHERE id = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Integer itemId : recommendationEngine.getItemFeedbackSentiments().keySet()) {
                stmt.setString(1, recommendationEngine.getItemFeedbackSentiments().get(itemId));
                stmt.setDouble(2, recommendationEngine.getItemRatings().get(itemId));
                stmt.setInt(3, itemId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }
}
