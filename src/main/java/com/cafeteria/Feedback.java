package src.main.java.com.cafeteria;
import java.util.Date;

public class Feedback {
    private int id;
    private String comment;
    private int rating;
    private Date feedbackDate;
    private int itemId;
    private String userId;

    public Feedback(Integer id, String comment, int rating, Date feedbackDate, int itemId, String userId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.feedbackDate = feedbackDate;
        this.itemId = itemId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public int getItemId() {
        return itemId;
    }

    public String getUserId() {
        return userId;
    }
}
