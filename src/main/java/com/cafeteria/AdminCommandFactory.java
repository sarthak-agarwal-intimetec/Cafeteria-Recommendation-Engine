package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class AdminCommandFactory {
    public static Command createCommand(String command, BufferedReader in, PrintWriter out) {
        switch (command.toLowerCase()) {
            case "1":
                return new ShowMenuCommand(out);
            case "2":
                return new AddMenuItemCommand(in, out);
            case "3":
                return new UpdateMenuItemCommand(in,out);
            case "4":
                return new DeleteMenuItemCommand(in,out);
            case "5":
                return new ViewDiscardMenuItemCommand(out);
            case "6":
                return new RemoveDiscardMenuItemCommand(in, out);
            case "7":
                return new DiscardMenuItemNotificationCommand(in, out);
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }
    }
}
