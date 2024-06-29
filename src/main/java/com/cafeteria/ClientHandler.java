package src.main.java.com.cafeteria;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //String userRole = in.readLine();
            String employeeId = in.readLine();
            String name = in.readLine();

            String userRole = Database.getUserRole(employeeId);
            
            out.println(userRole);
            
            System.out.println("Received login request: " + userRole + ", " + employeeId + ", " + name);

            User user = null;
            if ("Admin".equalsIgnoreCase(userRole)) {
                user = new Admin(employeeId, name, "Admin");
            } else if ("Chef".equalsIgnoreCase(userRole)) {
                user = new Chef(employeeId, name, "Chef");
            } else if ("Employee".equalsIgnoreCase(userRole)) {
                user = new Employee(employeeId, name, "Employee");
            }
           // System.out.println("Inside ClientHandler");
            Boolean isValidUser = user.login(employeeId, name);
            if (user != null && isValidUser) {
                out.println("Login successful as " + user.getRole());
                String command;
                while ((command = in.readLine()) != null) {
                    processCommand(user, command);
                }
            } else {
                out.println("Invalid credentials");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing connection with " + socket.getRemoteSocketAddress());
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processCommand(User user, String command) throws IOException {
        switch (command) {
            case "ShowMenu":
                List<MenuItem> menuItems = Database.getAllMenuItems();
                for (MenuItem item : menuItems) {
                    out.println(item.getId() + ". " + item.getName() + " - " + item.getPrice() + " - " + (item.isAvailable() ? "Available" : "Not Available"));
                }
                out.println("Item fetched successfully");
                break;

            case "AddMenuItem":
                //System.out.println("Reading item details from client");
                String itemName = in.readLine();
                //System.out.println("Received itemName: " + itemName);

                String itemPriceStr = in.readLine();
                //System.out.println("Received itemPrice: " + itemPriceStr);
                double itemPrice = Double.parseDouble(itemPriceStr);

                String itemIsAvailable = in.readLine();
                //System.out.println("Received itemIsAvailable: " + itemIsAvailable);

                Database.addMenuItem(itemName, itemPrice, itemIsAvailable);
                String message = "New Food Item is added, check this out - " + itemName + " -> Price: "+itemPrice;
                Database.addNotification(message);
                out.println("Item added successfully");
                break;

            case "UpdateMenuItem":
                //System.out.println("Reading item details from client");
                String itemIdToUpdate = in.readLine();
                //System.out.println("Received itemName: " + itemIdToUpdate);

                String itemNameToUpdate = in.readLine();
                //System.out.println("Received itemName: " + itemNameToUpdate);

                String itemPriceToUpdateStr = in.readLine();
                //System.out.println("Received itemPrice: " + itemPriceToUpdateStr);
                double itemPriceToUpdate = Double.parseDouble(itemPriceToUpdateStr);

                String itemIsAvailableToUpdate = in.readLine();
                //System.out.println("Received itemIsAvailable: " + itemIsAvailableToUpdate);
                MenuItem itemToUpdate = Database.getMenuItemById(itemIdToUpdate);
                Database.updateMenuItem(itemIdToUpdate, itemNameToUpdate, itemPriceToUpdate,itemIsAvailableToUpdate);
                if(String.valueOf(itemToUpdate.isAvailable()) != itemIsAvailableToUpdate){
                    message = "Availability status of this food item is changed - " + itemNameToUpdate + " -> " + (itemIsAvailableToUpdate == "true" ? "Available" : "Not Available");
                    Database.addNotification(message);
                }
                out.println("Item updated successfully");
                break;

            case "DeleteMenuItem":
                //System.out.println("Reading item details from client");
                String itemIdToDelete = in.readLine();
                //System.out.println("Received itemName: " + itemIdToDelete);

                Database.deleteMenuItem(itemIdToDelete);
                out.println("Item updated successfully");
                break;
            
            case "ShowRecommendation":
                List<Feedback> feedbacks = Database.getFeedbacks();
                RecommendationEngine recommendationEngine = new RecommendationEngine(feedbacks);
                Database.updateRatingAndSentimentScore(recommendationEngine);
                for (Integer itemId : recommendationEngine.itemRatings.keySet()) {
                    //System.out.println("@@@");
                    out.println(itemId + " - " + (recommendationEngine.itemRatings.get(itemId) + recommendationEngine.itemFeedbacks.get(itemId)));
                }
                out.println("Item fetched successfully");
                break;

            case "RollOutMenu":
                //System.out.println("Reading item details from client");
                String itemIdStr = in.readLine();
                int itemId = Integer.parseInt(itemIdStr);
                //System.out.println("Received itemName: " + itemId);
                
                Database.addDailyMenuItem(itemId);
                MenuItem menuItem = Database.getMenuItemById(itemIdStr);
                message = "Today's Food Item is: " + menuItem.getName() + ", please give vote if you like to have it for next day";
                Database.addNotification(message);
                out.println("Item added successfully");
                break;

            case "DailyMenuItem":
                List<DailyMenuItem> dailyMenuItems = Database.getDailyMenuItem();
                for (DailyMenuItem dailyMenuItem : dailyMenuItems) {
                    out.println(dailyMenuItem.getId() + ". " + dailyMenuItem.getDate() + " - " + dailyMenuItem.getItemId() + dailyMenuItem.getAverageRating() + " - " + dailyMenuItem.getSentimentScore());
                }
                out.println("Item fetched successfully");
                break;

            case "Vote":
                //System.out.println("Reading item details from client");
                
                String itemIdToVoteStr = in.readLine();
                //System.out.println("Received itemPrice: " + itemIdToVoteStr);
                int itemIdToVote = Integer.parseInt(itemIdToVoteStr);
                
                //System.out.println("Received itemName: " + itemIdToVote);

                Database.updateVoteCount(itemIdToVote);
                out.println("Item voted successfully");
                break;
            
            case "Feedback":
                //System.out.println("Reading item details from client");
                
                String itemIdForFeedbackStr = in.readLine();
                //System.out.println("Received itemPrice: " + itemIdForFeedbackStr);
                int itemIdForFeedback = Integer.parseInt(itemIdForFeedbackStr);
                
                String itemRatingStr = in.readLine();
                //System.out.println("Received itemPrice: " + itemRatingStr);
                int itemRating = Integer.parseInt(itemRatingStr);
                //System.out.println("Received itemName: " + itemRating);

                String itemComment = in.readLine();
                //System.out.println("Received itemPrice: " + itemComment);

                String userId = user.getEmployeeId();

                Database.addFeedback(itemIdForFeedback, itemRating, itemComment, userId);
                out.println("Item feedbacked successfully");
                break;
            
            case "Notification":
                List<Notification> notifications = Database.getNotifications();
                for (Notification notification : notifications) {
                    out.println(notification.getTimestamp() + " - " + notification.getMessage());
                }
                out.println("Item fetched successfully");
                break;

            default:
                out.println("Unknown command");
                break;
        }
    }
}