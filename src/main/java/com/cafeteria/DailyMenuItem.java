package src.main.java.com.cafeteria;

import java.util.Date;
import java.util.Objects;

public class DailyMenuItem {
    private final int id;
    private final Date date;
    private final int itemId;
    private final String itemName;
    private final double averageRating;
    private final String sentiment;

    public DailyMenuItem(int id, Date date, int itemId, String itemName, double averageRating, String sentiment) {
        if (id <= 0 || itemId <= 0) {
            throw new IllegalArgumentException("ID and Item ID must be positive.");
        }
        if (averageRating < 0 || averageRating > 5) {
            throw new IllegalArgumentException("Average rating must be between 0 and 5.");
        }
        this.id = id;
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.itemId = itemId;
        this.itemName = itemName;
        this.averageRating = averageRating;
        this.sentiment = sentiment;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public String getSentiment() {
        return sentiment;
    }
}
