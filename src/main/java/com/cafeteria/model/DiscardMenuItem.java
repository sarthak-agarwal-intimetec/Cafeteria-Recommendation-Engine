package src.main.java.com.cafeteria.model;

public class DiscardMenuItem {
    private int id;
    private int itemId;
    private String itemName;

    public DiscardMenuItem(int id, int itemId, String itemName) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }
}
