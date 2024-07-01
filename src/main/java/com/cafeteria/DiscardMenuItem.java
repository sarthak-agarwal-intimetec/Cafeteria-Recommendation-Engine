package src.main.java.com.cafeteria;

public class DiscardMenuItem {
    private int id;
    private int itemId;

    public DiscardMenuItem(int id, int itemId) {
        this.id = id;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public int getItemId() {
        return itemId;
    }
}
