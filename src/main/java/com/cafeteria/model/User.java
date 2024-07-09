package src.main.java.com.cafeteria.model;

import src.main.java.com.cafeteria.Database;

public class User {
    private String employeeId;
    private String name;
    private String role;
    private String dietaryPreference;
    private String spiceLevel;
    private String cuisineType;
    private String isSweetTooth;

    public User(String employeeId, String name, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public User(String dietaryPreference, String spiceLevel, String cuisineType, String isSweetTooth) {
        this.dietaryPreference = dietaryPreference;
        this.spiceLevel = spiceLevel;
        this.cuisineType = cuisineType;
        this.isSweetTooth = isSweetTooth;
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

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getIsSweetTooth() {
        return isSweetTooth;
    }

    public boolean login(String employeeId, String name) {
        boolean isValidUser = Database.validateUser(employeeId, name);
        return isValidUser;
    }
}
