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
        //System.out.println("Inside validateUser Method");
        String query = "SELECT * FROM Users WHERE employeeId = ? AND name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            //System.out.println("Inside validateUser Try");
            //String query = "SELECT * FROM Users WHERE employeeId = ? AND name = ?";
            //System.out.println("Query Executed");
            //PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, employeeId);
            stmt.setString(2, name);
            //System.out.println("Executing Query: " + stmt);
            ResultSet rs = stmt.executeQuery();
            //System.out.println("Result : "+rs);
            boolean isValid = rs.next();
            //System.out.println("User validation result: " + isValid);
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static MenuItem getMenuItemById(String itemId) {
        MenuItem menuItem = null;
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM MenuItems WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                boolean isAvailable = rs.getBoolean("isAvailable");
                menuItem = new MenuItem(id, name, price, isAvailable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItem;
    }

    public static List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM MenuItems";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                boolean isAvailable = rs.getBoolean("isAvailable");
                menuItems.add(new MenuItem(id, name, price, isAvailable));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    public static void addMenuItem(String itemName, double itemPrice, String itemIsAvailable) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO MenuItems (name, price, isAvailable) VALUES(?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemName);
            stmt.setDouble(2, itemPrice);
            stmt.setString(3, itemIsAvailable);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMenuItem(String itemid, String itemName, double itemPrice, String itemIsAvailable) {
        //List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "UPDATE MenuItems SET Name = ?, Price = ?, IsAvailable = ? WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemName);
            stmt.setDouble(2, itemPrice);
            stmt.setString(3, itemIsAvailable);
            stmt.setString(4, itemid);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMenuItem(String itemid) {
        //List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM MenuItems WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemid);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUserRole(String empId) {
        String userRole = null;
        try (Connection conn = getConnection()) {
            String query = "Select Role FROM Users WHERE employeeId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, empId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userRole = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRole;
    }

    public static List<Feedback> getFeedbacks() {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM Feedbacks";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String comment = rs.getString("comment");
                int rating = rs.getInt("rating");
                Date feedbackDate = rs.getDate("feedbackDate");
                int itemId = rs.getInt("itemId");
                String userId = rs.getString("userId");
                feedbacks.add(new Feedback(id, comment, rating, feedbackDate, itemId, userId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    public static void addDailyMenuItem(int itemId) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO DailyMenuItem (Date, ItemId) VALUES(CURDATE(),?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemId);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<DailyMenuItem> getDailyMenuItem() {
        List<DailyMenuItem> dailyMenuItem = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT DailyMenuItem.Id, DailyMenuItem.Date, DailyMenuItem.ItemId, MenuItems.AverageRating, MenuItems.SentimentScore,MenuItems.Id FROM DailyMenuItem INNER JOIN MenuItems ON DailyMenuItem.ItemId = MenuItems.Id";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("DailyMenuItem.Id");
                Date date = rs.getDate("DailyMenuItem.Date");
                int itemId = rs.getInt("DailyMenuItem.ItemId");
                double averageRating = rs.getDouble("MenuItems.AverageRating");
                double sentimentScore = rs.getDouble("MenuItems.SentimentScore");
                dailyMenuItem.add(new DailyMenuItem(id, date, itemId, averageRating, sentimentScore));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dailyMenuItem;
    }

    public static void updateRatingAndSentimentScore(RecommendationEngine recommendationEngine) {
        try (Connection conn = getConnection()) {
            for(Integer itemId : recommendationEngine.itemFeedbacks.keySet()){
                String query = "UPDATE MenuItems SET SentimentScore = ?, AverageRating = ? WHERE Id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, recommendationEngine.itemFeedbacks.get(itemId));
                stmt.setDouble(2, recommendationEngine.itemRatings.get(itemId));
                stmt.setInt(3, itemId);
                stmt.executeUpdate();;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* public static void updateVoteCount(String itemid) {
        //List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM MenuItems WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemid);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */

    public static void updateVoteCount(int itemIdToVote) {
        //List<MenuItem> menuItems = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM DailyMenuItem WHERE ItemId = ? AND Date = CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemIdToVote);
            ResultSet rs = stmt.executeQuery();
            int vote = 0;
            while (rs.next()) {
                vote = rs.getInt("vote");
                //dailyMenuItem.add(new DailyMenuItem(id, date, itemId, averageRating, sentimentScore));
            }
            vote++;
            query = "UPDATE DailyMenuItem SET Vote = ? WHERE ItemId = ? AND Date = CURDATE()";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, vote);
            stmt.setInt(2, itemIdToVote);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addFeedback(int itemIdForFeedback, int itemRating, String itemComment, String userId) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO Feedbacks (itemid, rating, comment, userId, feedbackDate) VALUES(?,?,?,?,(CURDATE()-1))";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemIdForFeedback);
            stmt.setInt(2, itemRating);
            stmt.setString(3, itemComment);
            stmt.setString(4, userId);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNotification(String message){
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO Notifications (message) VALUES(?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, message);
            stmt.executeUpdate();;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Notification> getNotifications(){
        List<Notification> notifications = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM Notifications WHERE DATE(`timestamp`) = CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String message = rs.getString("Message");
                Timestamp timeStamp = rs.getTimestamp("Timestamp");
                notifications.add(new Notification(id, message, timeStamp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }
}