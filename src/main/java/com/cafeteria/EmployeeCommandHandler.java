package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EmployeeCommandHandler extends CommandHandler {

    public EmployeeCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        switch (command.toLowerCase()) {
            case "1":
                handleDailyMenuItem();
                break;
            case "2":
                handleVote();
                break;
            case "3":
                handleFeedback();
                break;
            case "4":
                handleNotification();
                break;
            case "5":
                handleProfile(user);
                break;
            default:
                out.println("Unknown command");
                break;
        }
    }

    private void handleDailyMenuItem() throws IOException {
        User userDetail = Database.getUserPreferenceDetail(user.getEmployeeId());
        List<DailyMenuItem> dailyMenuItems = Database.getDailyMenuItems(userDetail);
        out.printf("%-10s%-20s%-20s%-20s%n", "Item Id", "Item Name", "Average Rating", "Feedback");
        for (DailyMenuItem dailyMenuItem : dailyMenuItems) {
            out.printf("%-10d%-20s%-20.2f%-20s%n", dailyMenuItem.getItemId(), dailyMenuItem.getItemName(),
                    dailyMenuItem.getAverageRating(), dailyMenuItem.getSentiment());
        }
        out.println("Items fetched successfully");
    }

    private void handleVote() throws IOException {
        int itemIdToVote = Integer.parseInt(in.readLine());
        Database.updateVoteCount(itemIdToVote);
        out.println("Item voted successfully");
    }

    private void handleFeedback() throws IOException {
        int itemIdForFeedback = Integer.parseInt(in.readLine());
        int itemRating = Integer.parseInt(in.readLine());
        String itemComment = in.readLine();
        String userId = user.getEmployeeId();

        Database.addFeedback(itemIdForFeedback, itemRating, itemComment, userId);
        out.println("Feedback submitted successfully");
    }

    private void handleNotification() throws IOException {
        List<Notification> notifications = Database.getNotifications();
        out.printf("%-100s%-10s%n", "Message", "Time");
        for (Notification notification : notifications) {
            out.printf("%-100s%-20s%n", notification.getMessage(), notification.getTimestamp());
        }
        out.println("Notifications fetched successfully");
    }

    private void handleProfile(User user) throws IOException {
        int dietaryPreference = Integer.parseInt(in.readLine());
        int spiceLevel = Integer.parseInt(in.readLine());
        int cuisineType = Integer.parseInt(in.readLine());
        int isSweetTooth = Integer.parseInt(in.readLine());
        String userId = user.getEmployeeId();

        Database.updateProfile(dietaryPreference, spiceLevel, cuisineType, isSweetTooth, userId);
        out.println("Item fetched successfully");
    }
}
