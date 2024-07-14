package src.main.java.com.cafeteria.factory;

import java.io.BufferedReader;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.command.AddMenuItemCommand;
import src.main.java.com.cafeteria.command.Command;
import src.main.java.com.cafeteria.command.DeleteMenuItemCommand;
import src.main.java.com.cafeteria.command.DiscardMenuItemNotificationCommand;
import src.main.java.com.cafeteria.command.RemoveDiscardMenuItemCommand;
import src.main.java.com.cafeteria.command.ShowMenuCommand;
import src.main.java.com.cafeteria.command.UpdateMenuItemCommand;
import src.main.java.com.cafeteria.command.ViewDiscardMenuItemCommand;

public class AdminCommandFactory {
    public static Command createCommand(String command, BufferedReader in, PrintWriter out) {
        switch (command.toLowerCase()) {
            case "1":
                return new ShowMenuCommand(out);
            case "2":
                return new AddMenuItemCommand(in, out);
            case "3":
                return new UpdateMenuItemCommand(in, out);
            case "4":
                return new DeleteMenuItemCommand(in, out);
            case "5":
                return new ViewDiscardMenuItemCommand(out);
            case "6":
                return new RemoveDiscardMenuItemCommand(in, out);
            case "7":
                return new DiscardMenuItemNotificationCommand(in, out);
            default:
                throw new IllegalArgumentException("Unknown command");
        }
    }
}
