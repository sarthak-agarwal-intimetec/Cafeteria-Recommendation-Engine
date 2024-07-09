package src.main.java.com.cafeteria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.main.java.com.cafeteria.model.DailyMenuItem;
import src.main.java.com.cafeteria.model.DiscardMenuItem;
import src.main.java.com.cafeteria.model.Feedback;
import src.main.java.com.cafeteria.model.MenuItem;
import src.main.java.com.cafeteria.model.Notification;
import src.main.java.com.cafeteria.model.User;
import src.main.java.com.cafeteria.util.Constant;

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
        String query = "SELECT * FROM User WHERE employeeId = ? AND name = ?";
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
        String query = "SELECT * FROM MenuItem WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
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
            handleSQLException(e);
        }
        return null;
    }

    public static List<MenuItem> getAllMenuItems() {
        String query = "SELECT * FROM MenuItem";
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection();
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
            handleSQLException(e);
        }
        return menuItems;
    }

    public static void addMenuItem(String itemName, double itemPrice, String itemIsAvailable) {
        String query = "INSERT INTO MenuItem (name, price, isAvailable) VALUES(?, ?, ?)";
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
        String query = "UPDATE MenuItem SET name = ?, price = ?, isAvailable = ? WHERE id = ?";
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
        String query = "DELETE FROM MenuItem WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static String getUserRole(String employeeId) {
        String query = "SELECT role FROM User WHERE employeeId = ?";
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
        return "";
    }

    public static List<Feedback> getFeedbacks() {
        String query = "SELECT f.id, f.comment, f.rating, f.feedbackDate, f.itemId, f.userId, m.name FROM Feedback f INNER JOIN MenuItem m ON f.itemId = m.id ";
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection conn = getConnection();
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
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
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
            handleSQLException(e);
        }
        return dailyMenuItems;
    }

    public static void updateRatingSentiment(RecommendationEngine recommendationEngine) {
        String query = "UPDATE MenuItem SET sentiment = ?, averageRating = ? WHERE id = ?";
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
        String query = "INSERT INTO Feedback (itemId, rating, comment, userId, feedbackDate) VALUES (?, ?, ?, ?, CURDATE() - 1)";
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
        String query = "INSERT INTO Notification (message) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static List<Notification> getNotifications() {
        String query = "SELECT * FROM Notification WHERE DATE(`timestamp`) = CURDATE()";
        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notifications.add(new Notification(
                        rs.getInt("id"),
                        rs.getString("message"),
                        rs.getTimestamp("timestamp")));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return notifications;
    }

    public static void updateDiscardMenuItemList(RecommendationEngine recommendationEngine) {
        String deleteQuery = "DELETE FROM DiscardMenuItem";
        String insertQuery = "INSERT INTO DiscardMenuItem (itemId) VALUES (?)";
        try (Connection conn = getConnection();
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
            handleSQLException(e);
        }
    }

    public static List<DiscardMenuItem> getDiscardMenuItems() {
        String query = "SELECT d.id, d.itemId, m.name FROM DiscardMenuItem d INNER JOIN MenuItem m ON d.itemId = m.id";
        List<DiscardMenuItem> discardMenuItems = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                discardMenuItems.add(new DiscardMenuItem(
                        rs.getInt("d.id"),
                        rs.getInt("d.itemId"),
                        rs.getString("m.name")));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return discardMenuItems;
    }

    public static void updateProfile(Integer dietaryPreference, Integer spiceLevel, Integer cuisineType,
            Integer isSweetTooth, String userId) {
        String query = "UPDATE User SET dietaryPreference = ?, spiceLevel = ?, cuisineType = ?, sweetTooth = ? WHERE employeeId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Constant.dietaryPreferenceMap.get(dietaryPreference));
            stmt.setString(2, Constant.spiceLeveleMap.get(spiceLevel));
            stmt.setString(3, Constant.cuisineTypeMap.get(cuisineType));
            stmt.setString(4, ((isSweetTooth == 1) ? "Yes" : "No"));
            stmt.setString(5, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public static User getUserPreferenceDetail(String employeedId) {
        User user = null;
        String query = "SELECT * FROM User WHERE employeeId = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeedId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("DietaryPreference"),
                        rs.getString("SpiceLevel"),
                        rs.getString("CuisineType"),
                        rs.getString("SweetTooth"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return user;
    }

    public static void addLoginActivity(String userId, String activityMessage) {
        String query = "INSERT INTO LoginActivity (userId, activityMessage) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, activityMessage);
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
