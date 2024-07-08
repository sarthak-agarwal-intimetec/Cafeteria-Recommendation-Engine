package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
        out.println("We are trying to improve your experience with " + itemIdToDiscardNotification
                + ". Please provide your feedback and help us. \n" +
                "Q1. What didn't you like about " + itemIdToDiscardNotification + "?\n" +
                "Q2. How would you like " + itemIdToDiscardNotification + " to taste?\n" +
                "Q3. Share your mom's recipe");
        out.println("Notification sent successfully");
    }
}
