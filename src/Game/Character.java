package Game;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    private String name;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private int drinkCount = 0;

    public int drinkCount() {
        return drinkCount;
    }

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    }

    private boolean hungry = false;

    public boolean isHungry() {
        return hungry;
    }

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }


    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

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

    public Item findItemInInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void increaseDrinkCount() {
        if (drinkCount < 12) {
            drinkCount++;
        }
        if (ZorkULGame.player.drinkCount() >= 12) {
            System.out.println("\nYOU DID IT! You finished the 12 pubs of Christmas!");
            System.out.println("You collapse on the ground, victorious and drunk beyond reason...");
            return;
        }


    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }
}
