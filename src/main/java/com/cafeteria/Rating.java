package src.main.java.com.cafeteria;
// Represents a rating given by a user to a menu item
public class Rating {
    private String userId;
    private String itemId;
    private double rating;

    public String getItemId() {

        return itemId;
    }

    public double getRating() {
        
        return rating;
    }

    public String getUserId() {

        return userId;
    }

    // Constructor, getters, setters
}