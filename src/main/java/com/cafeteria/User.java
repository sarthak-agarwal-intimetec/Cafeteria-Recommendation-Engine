package src.main.java.com.cafeteria;
public class User {
    private String employeeId;
    private String name;
    private String role;

    public User(String employeeId, String name, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public boolean login(String employeeId, String name) {
        // Validate user credentials by checking with the database
        //System.out.println("Inside Login of User");
        boolean isValidUser = Database.validateUser(employeeId, name);
        //System.out.println("Validation result: " + isValidUser);
        return isValidUser;
    }
}
