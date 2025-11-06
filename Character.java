import java.util.ArrayList;

public class Character {
    private String name;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    }

    // --- Basic getters/setters ---
    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    // --- Movement ---
    public void move(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        } else {
            System.out.println("You can't go that way!");
        }
    }

    // --- Look around the current room ---
    public void lookAround() {
        System.out.println(currentRoom.getLongDescription());
    }

    // --- Inventory management ---
    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are not carrying anything.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
        }
    }

    public void grabItem(Item item, Room room) {
        if (room.getItems().contains(item)) {
            inventory.add(item);
            room.getItems().remove(item);
            System.out.println("You picked up " + item.getName());
        } else {
            System.out.println("That item is not here");
        }
    }

    public void leaveItem(Item item, Room room) {
        inventory.remove(item);
        room.getItems().add(item);
        System.out.println("You dropped " + item.getName());
    }

    // --- Search your inventory for a specific item ---
    public Item findItemInInventory(String name) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
