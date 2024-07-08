package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
        Database.deleteMenuItem(itemIdToRemove);
        out.println("Item Deleted successfully");
    }
}
