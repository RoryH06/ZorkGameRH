import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits; // Map direction to neighboring Room
    private ArrayList<Item> items;

    public Room(String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (String direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString().trim();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void look() {
        if (items.isEmpty()) {
            System.out.println("No items in this room");
        } else {
            System.out.println("Items in this room:");
            for (Item item : items) {
                System.out.println(item.getName());
            }
        }

    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }
}
