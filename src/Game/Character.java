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

    private boolean croninsstamp = false;

    public boolean hasStamp() {
        return croninsstamp;
    }

    public void setCroninsstamp(boolean croninsstamp) {
        this.croninsstamp = croninsstamp;
    }

    private boolean elfhat = false;

    public boolean isWearingElfHat() {
        return elfhat;
    }

    public void setElfHat(boolean elfhat) {
        this.elfhat = elfhat;
    }

    private boolean roryskeys = false;

    public boolean isHoldingKeys() {
        return roryskeys;
    }

    public void setRoryskeys(boolean roryskeys) {
        this.roryskeys = roryskeys;
    }

    private boolean bosschain = false;

    public boolean isWearingBOSSChain() {
        return bosschain;
    }

    public void setBosschain(boolean bosschain) {
        this.bosschain = bosschain;
    }

    public String getName() {return name;}

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

    public void lookAround() {
        System.out.println(currentRoom.getLongDescription());
    }

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

            if (drinkCount == 12) {
                System.out.println("\nYOU DID IT! You finished the 12 pubs of Christmas!");
                System.out.println("You collapse on the ground, victorious and drunk beyond reason...");
            }
        }
    }


    public int getDrinkCount() {
        return drinkCount;
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }
}
