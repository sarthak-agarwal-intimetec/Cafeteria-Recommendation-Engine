package src.main.java.com.cafeteria.util;

import src.main.java.com.cafeteria.model.Feedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackAnalyzer {
    private List<Feedback> feedbacks;
    private Map<Integer, Integer> itemFeedbacks;

    public FeedbackAnalyzer(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        this.itemFeedbacks = new HashMap<>();
    }

    public Map<Integer, String> analyze() {
        for (Feedback feedback : feedbacks) {
            int itemId = feedback.getItemId();
            itemFeedbacks.putIfAbsent(itemId, 0);
            String[] words = feedback.getComment().split("\\s+");
            int positiveScore = 0;
            int negativeScore = 0;
            boolean negation = false;

            for (String word : words) {
                String lowerCaseWord = word.toLowerCase();

                if (Constant.negationWords.contains(lowerCaseWord)) {
                    negation = true;
                    continue;
                }

                if (Constant.positiveWords.contains(lowerCaseWord)) {
                    if (negation) {
                        negativeScore++;
                        negation = false;
                    } else {
                        positiveScore++;
                    }
                } else if (Constant.negativeWords.contains(lowerCaseWord)) {
                    if (negation) {
                        positiveScore++;
                        negation = false;
                    } else {
                        negativeScore++;
                    }
                }
            }

            if (positiveScore > negativeScore) {
                itemFeedbacks.put(itemId, itemFeedbacks.get(itemId) + 1);
            } else if (negativeScore > positiveScore) {
                itemFeedbacks.put(itemId, itemFeedbacks.get(itemId) - 1);
            }
        }

        Map<Integer, String> itemFeedbackSentiments = new HashMap<>();
        for (Integer itemId : itemFeedbacks.keySet()) {
            if (itemFeedbacks.get(itemId) > 0) {
                itemFeedbackSentiments.put(itemId, "Positive");
            } else if (itemFeedbacks.get(itemId) < 0) {
                itemFeedbackSentiments.put(itemId, "Negative");
            } else {
                itemFeedbackSentiments.put(itemId, "Neutral");
            }
        }

        return itemFeedbackSentiments;
    }
}
