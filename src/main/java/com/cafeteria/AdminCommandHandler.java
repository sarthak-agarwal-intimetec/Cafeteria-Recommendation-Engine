package src.main.java.com.cafeteria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminCommandHandler extends CommandHandler {

    public AdminCommandHandler(BufferedReader in, PrintWriter out, User user) {
        super(in, out, user);
    }

    @Override
    public void handleCommand(String command) throws IOException {
        try {
            Command cmd = AdminCommandFactory.createCommand(command, in, out);
            cmd.execute();
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }
}
