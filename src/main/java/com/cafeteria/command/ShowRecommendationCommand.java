package src.main.java.com.cafeteria.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import src.main.java.com.cafeteria.dao.DiscardMenuItemDAO;
import src.main.java.com.cafeteria.dao.FeedbackDAO;
import src.main.java.com.cafeteria.dao.MenuItemDAO;
import src.main.java.com.cafeteria.model.Feedback;
import src.main.java.com.cafeteria.util.RecommendationEngine;

public class ShowRecommendationCommand implements Command {
    private PrintWriter out;

    public ShowRecommendationCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        List<Feedback> feedbacks = FeedbackDAO.getFeedbacks();
        RecommendationEngine recommendationEngine = new RecommendationEngine(feedbacks);

        MenuItemDAO.updateRatingSentiment(recommendationEngine);
        DiscardMenuItemDAO.updateDiscardMenuItemList(recommendationEngine);

        Map<Integer, String> itemFeedbackSentiments = recommendationEngine.getItemFeedbackSentiments();
        Map<Integer, Double> itemRatings = recommendationEngine.getItemRatings();
        Map<Integer, String> itemIdToItemNameMap = recommendationEngine.getItemIdToItemNameMap();

        out.printf("%-10s%-20s%-20s%-20s%n", "Item Id", "Item Name", "Average Rating", "Feedback");
        for (Integer itemId : itemIdToItemNameMap.keySet()) {
            out.printf("%-10d%-20s%-20.2f%-20s%n", itemId, itemIdToItemNameMap.get(itemId),
                    itemRatings.getOrDefault(itemId, 0.0),
                    itemFeedbackSentiments.getOrDefault(itemId, "Neutral"));
        }
        out.println("Recommendations fetched successfully");
    }
}
