package src.main.java.com.cafeteria.factory;

import java.io.BufferedReader;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.command.Command;
import src.main.java.com.cafeteria.command.DailyMenuItemCommand;
import src.main.java.com.cafeteria.command.VoteCommand;
import src.main.java.com.cafeteria.command.FeedbackCommand;
import src.main.java.com.cafeteria.command.NotificationCommand;
import src.main.java.com.cafeteria.command.ProfileCommand;
import src.main.java.com.cafeteria.model.User;

public class EmployeeCommandFactory {
    public static Command createCommand(String command, BufferedReader in, PrintWriter out, User user) {
        switch (command.toLowerCase()) {
            case "1":
                return new DailyMenuItemCommand(out, user);
            case "2":
                return new VoteCommand(in, out);
            case "3":
                return new FeedbackCommand(in, out, user);
            case "4":
                return new NotificationCommand(out);
            case "5":
                return new ProfileCommand(in, out, user);
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }
}
