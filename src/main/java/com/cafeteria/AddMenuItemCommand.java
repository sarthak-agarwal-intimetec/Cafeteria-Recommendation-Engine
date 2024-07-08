package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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

        Database.addMenuItem(itemName, itemPrice, itemIsAvailable);
        Database.addNotification("New Food Item is added, check this out - " + itemName + " -> Price: " + itemPrice);
        out.println("Item added successfully");
    }
}
