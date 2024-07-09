package src.main.java.com.cafeteria.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import src.main.java.com.cafeteria.Database;
import src.main.java.com.cafeteria.model.DailyMenuItem;
import src.main.java.com.cafeteria.model.User;

public class DailyMenuItemCommand implements Command {
    private PrintWriter out;
    private User user;

    public DailyMenuItemCommand(PrintWriter out, User user) {
        this.out = out;
        this.user = user;
    }

    @Override
    public void execute() throws IOException {
        User userDetail = Database.getUserPreferenceDetail(user.getEmployeeId());
        List<DailyMenuItem> dailyMenuItems = Database.getDailyMenuItems(userDetail);
        out.printf("%-10s%-20s%-20s%-20s%n", "Item Id", "Item Name", "Average Rating", "Feedback");
        for (DailyMenuItem dailyMenuItem : dailyMenuItems) {
            out.printf("%-10d%-20s%-20.2f%-20s%n", dailyMenuItem.getItemId(), dailyMenuItem.getItemName(),
                    dailyMenuItem.getAverageRating(), dailyMenuItem.getSentiment());
        }
        out.println("Items fetched successfully");
    }
}
