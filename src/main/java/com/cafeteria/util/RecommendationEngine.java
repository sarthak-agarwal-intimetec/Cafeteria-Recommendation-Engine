package src.main.java.com.cafeteria.util;

import src.main.java.com.cafeteria.model.Feedback;

import java.util.List;
import java.util.Map;

public class RecommendationEngine {
    private FeedbackAnalyzer feedbackAnalyzer;
    private RatingCalculator ratingCalculator;

    public RecommendationEngine(List<Feedback> feedbacks) {
        this.feedbackAnalyzer = new FeedbackAnalyzer(feedbacks);
        this.ratingCalculator = new RatingCalculator(feedbacks);
        analyzeFeedbacks();
        calculateAverageRatings();
    }

    private void analyzeFeedbacks() {
        feedbackAnalyzer.analyze();
    }

    private void calculateAverageRatings() {
        ratingCalculator.calculate();
    }

    public Map<Integer, String> getItemFeedbackSentiments() {
        return feedbackAnalyzer.analyze();
    }

    public Map<Integer, Double> getItemRatings() {
        return ratingCalculator.getItemRatings();
    }

    public Map<Integer, String> getItemIdToItemNameMap() {
        return ratingCalculator.getItemIdToItemNameMap();
    }
}
