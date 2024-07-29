package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.MenuItemDAO;

public class RemoveDiscardMenuItemCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public RemoveDiscardMenuItemCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        String itemIdToRemove = in.readLine();
        MenuItemDAO.deleteMenuItem(itemIdToRemove);
        out.println("Item Deleted successfully");
    }
}
