package src.main.java.com.cafeteria;

import java.util.*;

public class RecommendationEngine {
    private List<Feedback> feedbacks;
    private Map<Integer, Integer> itemFeedbacks;
    public Map<Integer, String> itemFeedbackSentiments;
    public Map<Integer, Double> itemRatings;
    private Map<Integer, Double> itemRatingCounts;
    private Map<Integer, Double> itemRatingScores;

    public RecommendationEngine(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        this.itemFeedbacks = new HashMap<>();
        this.itemFeedbackSentiments =  new HashMap<>();
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
            String[] words = feedback.getComment().split("\\s+");
            int positiveScore = 0;
            int negativeScore = 0;
            boolean negation = false;

            for(String word : words) {
                String lowerCaseWord = word.toLowerCase();

                if (NegationWords.contains(lowerCaseWord)) {
                    negation = true;
                    continue;
                }

                if (PositiveWords.contains(lowerCaseWord)) {
                    if (negation) {
                        negativeScore++;
                        negation = false;
                    } else {
                        positiveScore++;
                    }
                } else if (NegativeWords.contains(lowerCaseWord)) {
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

        for(Integer itemId : itemFeedbacks.keySet()) {
            if(itemFeedbacks.get(itemId) > 0) {
                itemFeedbackSentiments.put(itemId, "Postive");
            } else if(itemFeedbacks.get(itemId) < 0) {
                itemFeedbackSentiments.put(itemId, "Negative");
            } else {
                itemFeedbackSentiments.put(itemId, "Neutral");
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

    public Map<Integer, String> getItemFeedbackSentiments() {
        return itemFeedbackSentiments;
    }

    public Map<Integer, Double> getItemRatings() {
        return itemRatings;
    }
}

class PositiveWords {
    private static final List<String> POSITIVE_WORDS = Arrays.asList(
        "Delicious", "Tasty", "Savory", "Flavorful", "Appetizing", 
        "Delectable", "Scrumptious", "Yummy", "Mouthwatering", "Luscious", 
        "Succulent", "Exquisite", "Heavenly", "Tempting", "Gourmet", "Rich", 
        "Satisfying", "Juicy", "Nutritious", "Fresh", "Aromatic", "Zesty", 
        "Flavor-packed", "Divine", "Tantalizing", "Lip-smacking", "Indulgent", 
        "Gratifying", "Delightful", "Palatable", "Bursting with flavor", 
        "Sumptuous", "Decadent", "Mouth-filling", "Melt-in-your-mouth", "Irresistible", 
        "Crispy", "Tender", "Velvety", "Bright", "Fragrant", "Clean", "Comforting", 
        "Homemade", "Authentic", "Freshly baked", "Wholesome", "Energizing", 
        "Nourishing", "Excellent", "Outstanding", "Superb", "Fantastic", "Brilliant",
        "Exceptional", "Impressive", "Marvelous", "Remarkable", "Wonderful", 
        "Great", "Amazing", "Terrific", "Fabulous", "Awesome", "Stellar", "Extraordinary",
        "Magnificent", "Perfect", "Commendable", "good"
    );

    public static boolean contains(String word) {
        return POSITIVE_WORDS.contains(word);
    }
}

class NegativeWords {
    private static final List<String> NEGATIVE_WORDS = Arrays.asList(
        "Poor", "Disappointing", "Unsatisfactory", "Inadequate", "Subpar", 
        "Mediocre", "Lacking", "Unacceptable", "Deficient", "Inferior", "Weak", 
        "Flawed", "Ineffective", "Insufficient", "Dismal", "Unimpressive", 
        "Below standard", "Troubling", "Frustrating", "Incomplete", "Bland", 
        "Tasteless", "Soggy", "Stale", "Rancid", "Greasy", "Unappetizing", 
        "Mushy", "Watery", "Overcooked", "Undercooked", "Burnt", "Sour", 
        "Rotten", "Spoiled", "Unpleasant", "Bitter", "Salty", "Dry", "Tough", 
        "Chewy", "Gritty", "Fatty", "Artificial", "Frozen", "Processed", "Stodgy", 
        "Heavy", "Unhealthy", "Unpalatable", "Flat", "Stagnant", "Insipid", 
        "Disappointing", "Mismatched", "Unbalanced", "Off-putting", "Uninspired", 
        "Repulsive", "Sickening", "Stuffy", "Stifling", "Unappealing", "Stinky", 
        "Dull", "Distasteful", "Unimaginative", "Fake", "Lacking", "Ordinary", "bad"
    );

    public static boolean contains(String word) {
        return NEGATIVE_WORDS.contains(word);
    }
}

class NegationWords {
    private static final List<String> NEGATION_WORDS = Arrays.asList(
        "not", "no", "never", "none"
    );

    public static boolean contains(String word) {
        return NEGATION_WORDS.contains(word);
    }
}
