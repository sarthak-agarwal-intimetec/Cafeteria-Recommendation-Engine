package src.main.java.com.cafeteria.util;

import src.main.java.com.cafeteria.model.Feedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingCalculator {
    private List<Feedback> feedbacks;
    private Map<Integer, Double> itemRatings;
    private Map<Integer, Double> itemRatingCounts;
    private Map<Integer, Double> itemRatingScores;
    private Map<Integer, String> itemIdToItemNameMap;

    public RatingCalculator(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        this.itemRatings = new HashMap<>();
        this.itemRatingCounts = new HashMap<>();
        this.itemRatingScores = new HashMap<>();
        this.itemIdToItemNameMap = new HashMap<>();
    }

    public void calculate() {
        for (Feedback feedback : feedbacks) {
            int itemId = feedback.getItemId();
            itemIdToItemNameMap.putIfAbsent(itemId, feedback.getItemName());

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

    public Map<Integer, Double> getItemRatings() {
        return itemRatings;
    }

    public Map<Integer, String> getItemIdToItemNameMap() {
        return itemIdToItemNameMap;
    }
}
