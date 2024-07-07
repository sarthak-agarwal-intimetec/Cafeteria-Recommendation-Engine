package src.main.java.com.cafeteria;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            initializeStreams();
            while (true) {
                handleClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void initializeStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void handleClient() throws IOException {
        String employeeId = in.readLine();
        String name = in.readLine();
        String userRole = Database.getUserRole(employeeId);
        out.println(userRole);

        System.out.println("Received login request: " + userRole + ", " + employeeId + ", " + name);

        User user = createUser(employeeId, name, userRole);

        if (user != null && user.login(employeeId, name)) {
            out.println("Login successful as " + user.getRole());
            processCommands(user);
        } else {
            out.println("Invalid credentials");
        }
    }

    private User createUser(String employeeId, String name, String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(employeeId, name, "Admin");
            case "chef":
                return new Chef(employeeId, name, "Chef");
            case "employee":
                return new Employee(employeeId, name, "Employee");
            default:
                return null;
        }
    }

    private void processCommands(User user) throws IOException {
        String command;
        while ((command = in.readLine()) != null) {
            if(command.toLowerCase().equals("logout")){
                break;
            }
            handleCommand(user, command);
        }
    }

    private void handleCommand(User user, String command) throws IOException {
        switch (command.toLowerCase()) {
            case "showmenu":
                handleShowMenu();
                break;
            case "addmenuitem":
                handleAddMenuItem();
                break;
            case "updatemenuitem":
                handleUpdateMenuItem();
                break;
            case "deletemenuitem":
                handleDeleteMenuItem();
                break;
            case "showrecommendation":
                handleShowRecommendation();
                break;
            case "rolloutmenu":
                handleRollOutMenu();
                break;
            case "dailymenuitem":
                handleDailyMenuItem(user);
                break;
            case "vote":
                handleVote();
                break;
            case "feedback":
                handleFeedback(user);
                break;
            case "notification":
                handleNotification();
                break;
            case "viewdiscardmenuitem":
                handleViewDiscardMenuItem();
                break;
            case "removediscardmenuitem":
                handleRemoveDiscardMenuItem();
                break;
            case "discardmenuitemnotification":
                handleDiscardMenuItemNotification();
                break;
            case "profile":
                handleProfile(user);
                break;
            default:
                out.println("Unknown command");
                break;
        }
    }

    private void handleShowMenu() throws IOException {
        List<MenuItem> menuItems = Database.getAllMenuItems();
        for (MenuItem item : menuItems) {
            out.println(item.getId() + ". " + item.getName() + " - " + item.getPrice() + " - "
                    + (item.isAvailable() ? "Available" : "Not Available"));
        }
        out.println("Item fetched successfully");
    }

    private void handleAddMenuItem() throws IOException {
        String itemName = in.readLine();
        double itemPrice = Double.parseDouble(in.readLine());
        String itemIsAvailable = in.readLine();

        Database.addMenuItem(itemName, itemPrice, itemIsAvailable);
        Database.addNotification("New Food Item is added, check this out - " + itemName + " -> Price: " + itemPrice);
        out.println("Item added successfully");
    }

    private void handleUpdateMenuItem() throws IOException {
        String itemIdToUpdate = in.readLine();
        String itemNameToUpdate = in.readLine();
        double itemPriceToUpdate = Double.parseDouble(in.readLine());
        String itemIsAvailableToUpdate = in.readLine();
        MenuItem itemToUpdate = Database.getMenuItemById(itemIdToUpdate);

        Database.updateMenuItem(itemIdToUpdate, itemNameToUpdate, itemPriceToUpdate, itemIsAvailableToUpdate);
        if (String.valueOf(itemToUpdate.isAvailable()) != itemIsAvailableToUpdate) {
            String message = "Availability status of this food item is changed - " + itemNameToUpdate + " -> "
                    + (itemIsAvailableToUpdate.equals("true") ? "Available" : "Not Available");
            Database.addNotification(message);
        }
        out.println("Item updated successfully");
    }

    private void handleDeleteMenuItem() throws IOException {
        String itemIdToDelete = in.readLine();
        Database.deleteMenuItem(itemIdToDelete);
        out.println("Item Deleted successfully");
    }

    private void handleShowRecommendation() throws IOException {
        List<Feedback> feedbacks = Database.getFeedbacks();
        RecommendationEngine recommendationEngine = new RecommendationEngine(feedbacks);
        Database.updateRatingSentiment(recommendationEngine);
        Database.updateDiscardMenuItemList(recommendationEngine);
        for (Integer itemId : recommendationEngine.itemRatings.keySet()) {
            out.println(itemId + " - Ratings - " + (recommendationEngine.itemRatings.get(itemId) + ", Feedback - "
                    + recommendationEngine.itemFeedbackSentiments.get(itemId)));
        }
        out.println("Item fetched successfully");
    }

    private void handleRollOutMenu() throws IOException {
        int itemId = Integer.parseInt(in.readLine());
        Database.addDailyMenuItem(itemId);
        MenuItem menuItem = Database.getMenuItemById(String.valueOf(itemId));
        Database.addNotification("Today's Food Item is: " + menuItem.getName()
                + ", please give vote if you like to have it for next day");
        out.println("Item added successfully");
    }

    private void handleDailyMenuItem(User user) throws IOException {
        User userDetail = Database.getUserPreferenceDetail(user.getEmployeeId());
        List<DailyMenuItem> dailyMenuItems = Database.getDailyMenuItems(userDetail);
        for (DailyMenuItem dailyMenuItem : dailyMenuItems) {
            out.println(dailyMenuItem.getId() + ". " + dailyMenuItem.getDate() + " - " + dailyMenuItem.getItemId()
                    + " - " + dailyMenuItem.getAverageRating() + " - " + dailyMenuItem.getSentiment());
        }
        out.println("Item Fetched Succesfully");
    }

    private void handleVote() throws IOException {
        int itemIdToVote = Integer.parseInt(in.readLine());
        Database.updateVoteCount(itemIdToVote);
        out.println("Item Voted Successfully");
    }

    private void handleFeedback(User user) throws IOException {
        int itemIdForFeedback = Integer.parseInt(in.readLine());
        int itemRating = Integer.parseInt(in.readLine());
        String itemComment = in.readLine();
        String userId = user.getEmployeeId();

        Database.addFeedback(itemIdForFeedback, itemRating, itemComment, userId);
        out.println("Item Feedbacked Successfully");
    }

    private void handleNotification() throws IOException {
        List<Notification> notifications = Database.getNotifications();
        for (Notification notification : notifications) {
            out.println(notification.getTimestamp() + " - " + notification.getMessage());
        }
        out.println("Notification Fetched Succesfully");
    }

    private void handleViewDiscardMenuItem() throws IOException {
        List<DiscardMenuItem> discardMenuItems = Database.getDiscardMenuItems();
        for (DiscardMenuItem discardMenuItem : discardMenuItems) {
            out.println(discardMenuItem.getId() + " - " + discardMenuItem.getItemId());
        }
        out.println("Item fetched successfully");
    }

    private void handleRemoveDiscardMenuItem() throws IOException {
        String itemIdToRemove = in.readLine();
        Database.deleteMenuItem(itemIdToRemove);
        Database.removeDiscardMenuItem(itemIdToRemove);
        out.println("Item Deleted successfully");
    }

    private void handleDiscardMenuItemNotification() throws IOException {
        String itemIdToDiscardNotification = in.readLine();
        out.println("We are trying to improve your experience with " + itemIdToDiscardNotification
                + ". Please provide your feedback and help us. \n" +
                "Q1. What didn't you like about " + itemIdToDiscardNotification + "?\n" +
                "Q2. How would you like " + itemIdToDiscardNotification + " to taste?\n" +
                "Q3. Share your mom's recipe");
        out.println("Notification sent successfully");
    }

    private void closeConnection() {
        try {
            System.out.println("Closing connection with " + socket.getRemoteSocketAddress());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleProfile(User user) throws IOException {
        int dietaryPreference = Integer.parseInt(in.readLine());
        int spiceLevel = Integer.parseInt(in.readLine());
        int cuisineType = Integer.parseInt(in.readLine());
        int isSweetTooth = Integer.parseInt(in.readLine());
        String userId = user.getEmployeeId();

        Database.updateProfile(dietaryPreference, spiceLevel, cuisineType, isSweetTooth, userId);
        out.println("Item fetched successfully");
    }
}
