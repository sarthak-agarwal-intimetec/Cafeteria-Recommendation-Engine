package src.main.java.com.cafeteria;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewDiscardMenuItemCommand implements Command {
    private PrintWriter out;

    public ViewDiscardMenuItemCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        List<DiscardMenuItem> discardMenuItems = Database.getDiscardMenuItems();
        out.printf("%-10s%-20s%n", "Item Id", "Item Name");
        for (DiscardMenuItem discardMenuItem : discardMenuItems) {
            out.printf("%-10d%-20s%n", discardMenuItem.getItemId(), discardMenuItem.getItemName());
        }
        out.println("Item fetched successfully");
    }
}