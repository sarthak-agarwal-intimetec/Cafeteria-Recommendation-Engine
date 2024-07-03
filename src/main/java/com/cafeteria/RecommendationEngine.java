package src.main.java.com.cafeteria;

import java.util.*;

public class RecommendationEngine {
    private List<Feedback> feedbacks;
    public Map<Integer, Integer> itemFeedbacks;
    public Map<Integer, Double> itemRatings;
    private Map<Integer, Double> itemRatingCounts;
    private Map<Integer, Double> itemRatingScores;

    public RecommendationEngine(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        this.itemFeedbacks = new HashMap<>();
        this.itemRatings = new HashMap<>();
        this.itemRatingCounts = new HashMap<>();
        this.itemRatingScores = new HashMap<>();
        analyzeFeedbacks();
        calculateAverageRatings();
    }

    private void analyzeFeedbacks() {
        for (Feedback feedback : feedbacks) {
            int itemId = feedback.getItemId();
            itemFeedbacks.putIfAbsent(itemId, 0);

            if (PositiveWords.contains(feedback.getComment())) {
                itemFeedbacks.put(itemId, itemFeedbacks.get(itemId) + 1);
            } else if (NegativeWords.contains(feedback.getComment())) {
                itemFeedbacks.put(itemId, itemFeedbacks.get(itemId) - 1);
            }
        }
    }

    private void calculateAverageRatings() {
        for (Feedback feedback : feedbacks) {
            int itemId = feedback.getItemId();
            itemRatingScores.putIfAbsent(itemId, 0.0);
            itemRatingScores.put(itemId, itemRatingScores.get(itemId) + feedback.getRating());

            itemRatingCounts.putIfAbsent(itemId, 0.0);
            itemRatingCounts.put(itemId, itemRatingCounts.get(itemId) + 1);
        }

        for (Map.Entry<Integer, Double> entry : itemRatingScores.entrySet()) {
            int itemId = entry.getKey();
            double totalScore = entry.getValue();
            double count = itemRatingCounts.get(itemId);
            itemRatings.put(itemId, totalScore / count);
        }
    }

    public Map<Integer, Integer> getItemFeedbacks() {
        return itemFeedbacks;
    }

    public Map<Integer, Double> getItemRatings() {
        return itemRatings;
    }
}

class PositiveWords {
    private static final List<String> POSITIVE_WORDS = Arrays.asList(
        "Excellent", "Outstanding", "Superb", "Fantastic", "Brilliant",
        "Exceptional", "Impressive", "Marvelous", "Remarkable", "Wonderful",
        "Great", "Amazing", "Terrific", "Fabulous", "Awesome",
        "Stellar", "Extraordinary", "Magnificent", "Perfect", "Commendable"
    );

    public static boolean contains(String word) {
        return POSITIVE_WORDS.contains(word);
    }
}

class NegativeWords {
    private static final List<String> NEGATIVE_WORDS = Arrays.asList(
        "Poor", "Disappointing", "Unsatisfactory", "Inadequate", "Subpar",
        "Mediocre", "Lacking", "Unacceptable", "Deficient", "Inferior",
        "Weak", "Flawed", "Ineffective", "Insufficient", "Dismal",
        "Unimpressive", "Below standard", "Troubling", "Frustrating", "Incomplete"
    );

    public static boolean contains(String word) {
        return NEGATIVE_WORDS.contains(word);
    }
}
