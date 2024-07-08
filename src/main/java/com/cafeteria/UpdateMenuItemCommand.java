package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateMenuItemCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public UpdateMenuItemCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        String itemIdToUpdate = in.readLine();
        String itemNameToUpdate = in.readLine();
        double itemPriceToUpdate = Double.parseDouble(in.readLine());
        String itemIsAvailableToUpdate = in.readLine();
        MenuItem itemToUpdate = Database.getMenuItemById(itemIdToUpdate);

        Database.updateMenuItem(itemIdToUpdate, itemNameToUpdate, itemPriceToUpdate, itemIsAvailableToUpdate);
        if (String.valueOf(itemToUpdate.isAvailable()) != itemIsAvailableToUpdate) {
            String message = "Availability status of this food item is changed - " + itemNameToUpdate + " -> "
                    + (itemIsAvailableToUpdate.equals("true") ? "Available" : "Not Available");
            Database.addNotification(message);
        }
        out.println("Item updated successfully");
    }
}
