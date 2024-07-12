package src.main.java.com.cafeteria.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        out.printf("%-10s%-20s%-20s%-20s%n", "Item Id", "Item Name", "Average Rating", "Feedback");
        for (Integer itemId : recommendationEngine.itemIdToItemNameMap.keySet()) {
            out.printf("%-10d%-20s%-20.2f%-20s%n", itemId, recommendationEngine.itemIdToItemNameMap.get(itemId),
                    recommendationEngine.itemRatings.get(itemId),
                    recommendationEngine.itemFeedbackSentiments.get(itemId));
        }
        out.println("Recommendations fetched successfully");
    }
}
