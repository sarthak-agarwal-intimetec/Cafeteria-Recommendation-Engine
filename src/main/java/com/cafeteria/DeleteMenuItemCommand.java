package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteMenuItemCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public DeleteMenuItemCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        String itemIdToDelete = in.readLine();
        Database.deleteMenuItem(itemIdToDelete);
        out.println("Item deleted successfully");
    }
}
