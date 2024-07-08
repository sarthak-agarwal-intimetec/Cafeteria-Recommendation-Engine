package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class CommandHandler {
    protected BufferedReader in;
    protected PrintWriter out;
    protected User user;

    public CommandHandler(BufferedReader in, PrintWriter out, User user) {
        this.in = in;
        this.out = out;
        this.user = user;
    }

    public abstract void handleCommand(String command) throws IOException;
}
