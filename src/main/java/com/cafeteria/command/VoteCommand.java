package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.DailyMenuItemDAO;

public class VoteCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;

    public VoteCommand(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        int itemIdToVote = Integer.parseInt(in.readLine());
        DailyMenuItemDAO.updateVoteCount(itemIdToVote);
        out.println("Item voted successfully");
    }
}
