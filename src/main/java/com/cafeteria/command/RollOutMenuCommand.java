package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.DailyMenuItemDAO;
import src.main.java.com.cafeteria.dao.MenuItemDAO;
import src.main.java.com.cafeteria.dao.NotificationDAO;
import src.main.java.com.cafeteria.model.MenuItem;

public class RollOutMenuCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public RollOutMenuCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        int itemId = Integer.parseInt(in.readLine());
        DailyMenuItemDAO.addDailyMenuItem(itemId);
        MenuItem menuItem = MenuItemDAO.getMenuItemById(String.valueOf(itemId));
        NotificationDAO.addNotification("Today's Food Item is: " + menuItem.getName()
                + ", please give vote if you like to have it for next day");
        out.println("Item rolled out successfully");
    }
}