package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.MenuItemDAO;
import src.main.java.com.cafeteria.dao.NotificationDAO;

public class AddMenuItemCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public AddMenuItemCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        String itemName = in.readLine();
        double itemPrice = Double.parseDouble(in.readLine());
        String itemIsAvailable = in.readLine();

        MenuItemDAO.addMenuItem(itemName, itemPrice, itemIsAvailable);
        NotificationDAO.addNotification("New Food Item is added, check this out - " + itemName + " -> Price: " + itemPrice);
        out.println("Item added successfully");
    }
}
