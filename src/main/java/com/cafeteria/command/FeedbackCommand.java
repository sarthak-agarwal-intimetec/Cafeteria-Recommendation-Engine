package src.main.java.com.cafeteria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import src.main.java.com.cafeteria.dao.FeedbackDAO;
import src.main.java.com.cafeteria.model.User;

public class FeedbackCommand implements Command {
    private BufferedReader in;
    private PrintWriter out;
    private User user;

    public FeedbackCommand(BufferedReader in, PrintWriter out, User user) {
        this.in = in;
        this.out = out;
        this.user = user;
    }

    @Override
    public void execute() throws IOException {
        int itemIdForFeedback = Integer.parseInt(in.readLine());
        int itemRating = Integer.parseInt(in.readLine());
        String itemComment = in.readLine();
        String userId = user.getEmployeeId();

        FeedbackDAO.addFeedback(itemIdForFeedback, itemRating, itemComment, userId);
        out.println("Feedback submitted successfully");
    }
}
