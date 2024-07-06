package src.main.java.com.cafeteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean validateUser(String employeeId, String name) {
        String query = "SELECT * FROM Users WHERE employeeId = ? AND name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, name);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return false;
    }

    public static MenuItem getMenuItemById(String itemId) {
        String query = "SELECT * FROM MenuItems WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getBoolean("isAvailable")
                    );
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    public static List<MenuItem> getAllMenuItems() {
        String query = "SELECT * FROM MenuItems";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                menuItems.add(new MenuItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getBoolean("isAvailable")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return menuItems;
    }

    public static void addMenuItem(String itemName, double itemPrice, String itemIsAvailable) {
        String query = "INSERT INTO MenuItems (name, price, isAvailable) VALUES(?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setDouble(2, itemPrice);
            stmt.setString(3, itemIsAvailable);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static void updateMenuItem(String itemId, String itemName, double itemPrice, String itemIsAvailable) {
        String query = "UPDATE MenuItems SET name = ?, price = ?, isAvailable = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setDouble(2, itemPrice);
            stmt.setString(3, itemIsAvailable);
            stmt.setString(4, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static void deleteMenuItem(String itemId) {
        String query = "DELETE FROM MenuItems WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static String getUserRole(String employeeId) {
        String query = "SELECT role FROM Users WHERE employeeId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return null;
    }

    public static List<Feedback> getFeedbacks() {
        String query = "SELECT * FROM Feedbacks";
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                feedbacks.add(new Feedback(
                    rs.getInt("id"),
                    rs.getString("comment"),
                    rs.getInt("rating"),
                    rs.getDate("feedbackDate"),
                    rs.getInt("itemId"),
                    rs.getString("userId")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return feedbacks;
    }

    public static void addDailyMenuItem(int itemId) {
        String query = "INSERT INTO DailyMenuItem (date, itemId) VALUES(CURDATE(), ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static List<DailyMenuItem> getDailyMenuItems() {
        String query = "SELECT d.id, d.date, d.itemId, m.averageRating, m.sentiment " +
                       "FROM DailyMenuItem d " +
                       "INNER JOIN MenuItems m ON d.itemId = m.id";
        List<DailyMenuItem> dailyMenuItems = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dailyMenuItems.add(new DailyMenuItem(
                    rs.getInt("d.id"),
                    rs.getDate("d.date"),
                    rs.getInt("d.itemId"),
                    rs.getDouble("m.averageRating"),
                    rs.getString("m.sentiment")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return dailyMenuItems;
    }

    public static void updateRatingSentiment(RecommendationEngine recommendationEngine) {
        String query = "UPDATE MenuItems SET sentiment = ?, averageRating = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Integer itemId : recommendationEngine.getItemFeedbackSentiments().keySet()) {
                stmt.setString(1, recommendationEngine.getItemFeedbackSentiments().get(itemId));
                stmt.setDouble(2, recommendationEngine.getItemRatings().get(itemId));
                stmt.setInt(3, itemId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static void updateVoteCount(int itemIdToVote) {
        String selectQuery = "SELECT vote FROM DailyMenuItem WHERE itemId = ? AND date = CURDATE()";
        String updateQuery = "UPDATE DailyMenuItem SET vote = ? WHERE itemId = ? AND date = CURDATE()";
        try (Connection conn = getConnection(); PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
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
            handleSQLException(e);
        }
    }

    public static void addFeedback(int itemIdForFeedback, int itemRating, String itemComment, String userId) {
        String query = "INSERT INTO Feedbacks (itemId, rating, comment, userId, feedbackDate) VALUES (?, ?, ?, ?, CURDATE() - 1)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemIdForFeedback);
            stmt.setInt(2, itemRating);
            stmt.setString(3, itemComment);
            stmt.setString(4, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static void addNotification(String message) {
        String query = "INSERT INTO Notifications (message) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static List<Notification> getNotifications() {
        String query = "SELECT * FROM Notifications WHERE DATE(`timestamp`) = CURDATE()";
        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notifications.add(new Notification(
                    rs.getInt("id"),
                    rs.getString("message"),
                    rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return notifications;
    }

    public static void updateDiscardMenuItemList(RecommendationEngine recommendationEngine) {
        String deleteQuery = "DELETE FROM DiscardMenuItem";
        String insertQuery = "INSERT INTO DiscardMenuItem (itemId) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            deleteStmt.executeUpdate();
            for (Integer itemId : recommendationEngine.getItemFeedbackSentiments().keySet()) {
                if (recommendationEngine.getItemRatings().get(itemId) < 2 && recommendationEngine.getItemFeedbackSentiments().get(itemId) == "Negative") {
                    insertStmt.setInt(1, itemId);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static List<DiscardMenuItem> getDiscardMenuItems() {
        String query = "SELECT * FROM DiscardMenuItem";
        List<DiscardMenuItem> discardMenuItems = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                discardMenuItems.add(new DiscardMenuItem(
                    rs.getInt("id"),
                    rs.getInt("itemId")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return discardMenuItems;
    }

    public static void removeDiscardMenuItem(String itemId) {
        String query = "DELETE FROM DiscardMenuItem WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private static void handleSQLException(SQLException e) {
        e.printStackTrace();
        // Additional logging or error handling can be added here if needed
    }
}
