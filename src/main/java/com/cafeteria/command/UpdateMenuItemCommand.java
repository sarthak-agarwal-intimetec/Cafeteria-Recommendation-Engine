package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.MenuItemDAO;
import src.main.java.com.cafeteria.dao.NotificationDAO;
import src.main.java.com.cafeteria.model.MenuItem;

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
        MenuItem itemToUpdate = MenuItemDAO.getMenuItemById(itemIdToUpdate);

        MenuItemDAO.updateMenuItem(itemIdToUpdate, itemNameToUpdate, itemPriceToUpdate, itemIsAvailableToUpdate);
        if (String.valueOf(itemToUpdate.isAvailable()) != itemIsAvailableToUpdate) {
            String message = "Availability status of this food item is changed - " + itemNameToUpdate + " -> "
                    + (itemIsAvailableToUpdate.equals("true") ? "Available" : "Not Available");
            NotificationDAO.addNotification(message);
        }
        out.println("Item updated successfully");
    }
}
