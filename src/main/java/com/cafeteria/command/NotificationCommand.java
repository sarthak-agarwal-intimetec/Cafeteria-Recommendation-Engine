package src.main.java.com.cafeteria.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import src.main.java.com.cafeteria.dao.NotificationDAO;
import src.main.java.com.cafeteria.model.Notification;

public class NotificationCommand implements Command {
    private PrintWriter out;

    public NotificationCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        List<Notification> notifications = NotificationDAO.getNotifications();
        out.printf("%-100s%-10s%n", "Message", "Time");
        for (Notification notification : notifications) {
            out.printf("%-100s%-20s%n", notification.getMessage(), notification.getTimestamp());
        }
        out.println("Notifications fetched successfully");
    }
    
}
