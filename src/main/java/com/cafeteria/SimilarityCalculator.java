package src.main.java.com.cafeteria;
import java.util.*;

public class SimilarityCalculator {
    // Method to calculate cosine similarity between two items
    public double calculateCosineSimilarity(List<Double> item1Ratings, List<Double> item2Ratings) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < item1Ratings.size(); i++) {
            dotProduct += item1Ratings.get(i) * item2Ratings.get(i);
            normA += Math.pow(item1Ratings.get(i), 2);
            normB += Math.pow(item2Ratings.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // Method to get item ratings from the user-item rating matrix
    public Map<String, List<Double>> getItemRatings(List<Rating> ratings) {
        Map<String, List<Double>> itemRatings = new HashMap<>();
        for (Rating rating : ratings) {
            itemRatings.putIfAbsent(rating.getItemId(), new ArrayList<>());
            itemRatings.get(rating.getItemId()).add(rating.getRating());
        }
        return itemRatings;
    }
}
