package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ChefCommandHandler extends CommandHandler {

    public ChefCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        switch (command.toLowerCase()) {
            case "1":
                handleShowRecommendation();
                break;
            case "2":
                handleRollOutMenu();
                break;
            case "3":
                handleViewDiscardMenuItem();
                break;
            case "4":
                handleRemoveDiscardMenuItem();
                break;
            case "5":
                handleDiscardMenuItemNotification();
                break;
            default:
                out.println("Unknown command");
                break;
        }
    }

    private void handleShowRecommendation() throws IOException {
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

    private void handleRollOutMenu() throws IOException {
        int itemId = Integer.parseInt(in.readLine());
        Database.addDailyMenuItem(itemId);
        MenuItem menuItem = Database.getMenuItemById(String.valueOf(itemId));
        Database.addNotification("Today's Food Item is: " + menuItem.getName()
                + ", please give vote if you like to have it for next day");
        out.println("Item rolled out successfully");
    }

    private void handleViewDiscardMenuItem() throws IOException {
        List<DiscardMenuItem> discardMenuItems = Database.getDiscardMenuItems();
        out.printf("%-10s%-20s%n", "Item Id", "Item Name");
        for (DiscardMenuItem discardMenuItem : discardMenuItems) {
            out.printf("%-10d%-20s%n", discardMenuItem.getItemId(), discardMenuItem.getItemName());
        }
        out.println("Item fetched successfully");
    }

    private void handleRemoveDiscardMenuItem() throws IOException {
        String itemIdToRemove = in.readLine();
        Database.deleteMenuItem(itemIdToRemove);
        out.println("Item Deleted successfully");
    }

    private void handleDiscardMenuItemNotification() throws IOException {
        String itemIdToDiscardNotification = in.readLine();
        out.println("We are trying to improve your experience with " + itemIdToDiscardNotification
                + ". Please provide your feedback and help us. \n" +
                "Q1. What didn't you like about " + itemIdToDiscardNotification + "?\n" +
                "Q2. How would you like " + itemIdToDiscardNotification + " to taste?\n" +
                "Q3. Share your mom's recipe");
        out.println("Notification sent successfully");
    }
}
