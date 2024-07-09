package src.main.java.com.cafeteria.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import src.main.java.com.cafeteria.Database;
import src.main.java.com.cafeteria.RecommendationEngine;
import src.main.java.com.cafeteria.model.Feedback;

public class ShowRecommendationCommand implements Command {
    private PrintWriter out;

    public ShowRecommendationCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        List<Feedback> feedbacks = Database.getFeedbacks();
        RecommendationEngine recommendationEngine = new RecommendationEngine(feedbacks);
        Database.updateRatingSentiment(recommendationEngine);
        Database.updateDiscardMenuItemList(recommendationEngine);
        out.printf("%-10s%-20s%-20s%-20s%n", "Item Id", "Item Name", "Average Rating", "Feedback");
        for (Integer itemId : recommendationEngine.itemIdToItemNameMap.keySet()) {
            out.printf("%-10d%-20s%-20.2f%-20s%n", itemId, recommendationEngine.itemIdToItemNameMap.get(itemId),
                    recommendationEngine.itemRatings.get(itemId),
                    recommendationEngine.itemFeedbackSentiments.get(itemId));
        }
        out.println("Recommendations fetched successfully");
    }
}
