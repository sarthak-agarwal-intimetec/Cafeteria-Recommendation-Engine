package src.main.java.com.cafeteria.dao;

import src.main.java.com.cafeteria.model.User;
import src.main.java.com.cafeteria.server.Database;
import src.main.java.com.cafeteria.util.Constant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static boolean validateUser(String employeeId, String name) {
        String query = "SELECT * FROM User WHERE employeeId = ? AND name = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, name);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return false;
    }

    public static String getUserRole(String employeeId) {
        String query = "SELECT role FROM User WHERE employeeId = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return "";
    }

    public static User getUserPreferenceDetail(String employeeId) {
        User user = null;
        String query = "SELECT * FROM User WHERE employeeId = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("DietaryPreference"),
                        rs.getString("SpiceLevel"),
                        rs.getString("CuisineType"),
                        rs.getString("SweetTooth"));
            }
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
        return user;
    }

    public static void updateProfile(Integer dietaryPreference, Integer spiceLevel, Integer cuisineType,
            Integer isSweetTooth, String userId) {
        String query = "UPDATE User SET dietaryPreference = ?, spiceLevel = ?, cuisineType = ?, sweetTooth = ? WHERE employeeId = ?";
        try (Connection conn = Database.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Constant.dietaryPreferenceMap.get(dietaryPreference));
            stmt.setString(2, Constant.spiceLeveleMap.get(spiceLevel));
            stmt.setString(3, Constant.cuisineTypeMap.get(cuisineType));
            stmt.setString(4, ((isSweetTooth == 1) ? "Yes" : "No"));
            stmt.setString(5, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Database.handleSQLException(e);
        }
    }

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
