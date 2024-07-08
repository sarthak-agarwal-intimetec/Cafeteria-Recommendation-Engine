package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AdminCommandHandler extends CommandHandler {

    public AdminCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        switch (command.toLowerCase()) {
            case "1":
                handleShowMenu();
                break;
            case "2":
                handleAddMenuItem();
                break;
            case "3":
                handleUpdateMenuItem();
                break;
            case "4":
                handleDeleteMenuItem();
                break;
            case "5":
                handleViewDiscardMenuItem();
                break;
            case "6":
                handleRemoveDiscardMenuItem();
                break;
            case "7":
                handleDiscardMenuItemNotification();
                break;
            default:
                out.println("Unknown command");
                break;
        }
    }

    private void handleShowMenu() throws IOException {
        List<MenuItem> menuItems = Database.getAllMenuItems();
        out.printf("%-10s%-20s%-15s%-20s%n", "Id", "Name", "Price", "Availability");
        for (MenuItem item : menuItems) {
            out.printf("%-10d%-20s%-15.2f%-20s%n", item.getId(), item.getName(), item.getPrice(),
                    (item.isAvailable() ? "Available" : "Not Available"));
        }
        out.println("Item fetched successfully");
    }

    private void handleAddMenuItem() throws IOException {
        String itemName = in.readLine();
        double itemPrice = Double.parseDouble(in.readLine());
        String itemIsAvailable = in.readLine();

        Database.addMenuItem(itemName, itemPrice, itemIsAvailable);
        Database.addNotification("New Food Item is added, check this out - " + itemName + " -> Price: " + itemPrice);
        out.println("Item added successfully");
    }

    private void handleUpdateMenuItem() throws IOException {
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

    private void handleDeleteMenuItem() throws IOException {
        String itemIdToDelete = in.readLine();
        Database.deleteMenuItem(itemIdToDelete);
        out.println("Item deleted successfully");
    }

    private void handleViewDiscardMenuItem() throws IOException {
        List<DiscardMenuItem> discardMenuItems = Database.getDiscardMenuItems();
        out.printf("%-10s%-20s%n", "Item Id", "Item Name");
        for (DiscardMenuItem discardMenuItem : discardMenuItems) {
            out.printf("%-10d%-20s%n", discardMenuItem.getItemId(), discardMenuItem.getItemName());
        }
        out.println("Item fetched successfully");
    }

    private void handleRemoveDiscardMenuItem() throws IOException {
        String itemIdToRemove = in.readLine();
        Database.deleteMenuItem(itemIdToRemove);
        out.println("Item Deleted successfully");
    }

    private void handleDiscardMenuItemNotification() throws IOException {
        String itemIdToDiscardNotification = in.readLine();
        out.println("We are trying to improve your experience with " + itemIdToDiscardNotification
                + ". Please provide your feedback and help us. \n" +
                "Q1. What didn't you like about " + itemIdToDiscardNotification + "?\n" +
                "Q2. How would you like " + itemIdToDiscardNotification + " to taste?\n" +
                "Q3. Share your mom's recipe");
        out.println("Notification sent successfully");
    }
}
