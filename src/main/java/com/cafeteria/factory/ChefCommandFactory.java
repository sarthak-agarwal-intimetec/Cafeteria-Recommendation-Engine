package src.main.java.com.cafeteria.factory;

import java.io.BufferedReader;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.command.Command;
import src.main.java.com.cafeteria.command.ShowRecommendationCommand;
import src.main.java.com.cafeteria.command.RollOutMenuCommand;
import src.main.java.com.cafeteria.command.ViewDiscardMenuItemCommand;
import src.main.java.com.cafeteria.command.RemoveDiscardMenuItemCommand;
import src.main.java.com.cafeteria.command.DiscardMenuItemNotificationCommand;

public class ChefCommandFactory {
    public static Command createCommand(String command, BufferedReader in, PrintWriter out) {
        switch (command.toLowerCase()) {
            case "1":
                return new ShowRecommendationCommand(out);
            case "2":
                return new RollOutMenuCommand(in, out);
            case "3":
                return new ViewDiscardMenuItemCommand(out);
            case "4":
                return new RemoveDiscardMenuItemCommand(in, out);
            case "5":
                return new DiscardMenuItemNotificationCommand(in, out);
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }
}
