package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.MenuItemDAO;
import src.main.java.com.cafeteria.dao.NotificationDAO;
import src.main.java.com.cafeteria.model.MenuItem;

public class DiscardMenuItemNotificationCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public DiscardMenuItemNotificationCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        String itemIdToDiscardNotification = in.readLine();
        MenuItem menuItem = MenuItemDAO.getMenuItemById(itemIdToDiscardNotification);
        if (menuItem != null) {
            String message = "We are trying to improve your experience with " + menuItem.getName() +
                    ". Please provide your feedback and help us. " +
                    "Q1. What didn't you like about " + menuItem.getName() + "? " +
                    "Q2. How would you like " + menuItem.getName() + " to taste? " +
                    "Q3. Share your mom's recipe";

            NotificationDAO.addNotification(message);
        } else {
            out.println("Invalid Item Id");
        }
        out.println("Notification sent successfully");
    }
}
