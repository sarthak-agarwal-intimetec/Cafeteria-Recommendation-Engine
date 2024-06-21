package src.main.java.com.cafeteria;
import java.util.Date;

public class DailyMenuItem {
    private int id;
    private Date date;
    private int itemId;
    private double averageRating;
    private double sentimentScore;

    public DailyMenuItem(int id, Date date, int itemId, double averageRating, double sentimentScore) {
        this.id = id;
        this.date = date;
        this.itemId = itemId;
        this.averageRating = averageRating;
        this.sentimentScore = sentimentScore;
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

    public double getAverageRating() {
        return averageRating;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }
}
