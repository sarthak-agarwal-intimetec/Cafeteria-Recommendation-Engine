package src.main.java.com.cafeteria.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.model.User;

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
