package src.main.java.com.cafeteria;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ShowMenuCommand implements Command {
    private PrintWriter out;

    public ShowMenuCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        List<MenuItem> menuItems = Database.getAllMenuItems();
        out.printf("%-10s%-20s%-15s%-20s%n", "Id", "Name", "Price", "Availability");
        for (MenuItem item : menuItems) {
            out.printf("%-10d%-20s%-15.2f%-20s%n", item.getId(), item.getName(), item.getPrice(),
                    (item.isAvailable() ? "Available" : "Not Available"));
        }
        out.println("Item fetched successfully");
    }
}
