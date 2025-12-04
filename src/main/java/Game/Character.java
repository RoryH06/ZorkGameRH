package Game;

import java.io.Serializable;
import java.util.ArrayList;

public class Character implements Serializable {
    private String name; //instance variables
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private int drinkCount = 0; //initialises drink count

    public int drinkCount() {
        return drinkCount;
    } //getter

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    } //constructor

    private boolean hungry = false; //set hungry condition to false

    public boolean isHungry() {
        return hungry;
    } //getter

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    } //setter

    private boolean croninsstamp = false; //set stamp to false

    public boolean hasStamp() {
        return croninsstamp;
    } //getter

    public void setCroninsstamp(boolean croninsstamp) {
        this.croninsstamp = croninsstamp;
    } //setter

    private boolean elfhat = false; // set elfhat to false

    public boolean isWearingElfHat() {
        return elfhat;
    } //getter

    public void setElfHat(boolean elfhat) {
        this.elfhat = elfhat;
    } //setter

    private boolean roryskeys = false; //set roryskeys to false

    public boolean isHoldingKeys() {
        return roryskeys;
    } //getter

    public void setRoryskeys(boolean roryskeys) {
        this.roryskeys = roryskeys;
    } //setter

    private boolean bosschain = false; //sets bosschain false

    public boolean isWearingBOSSChain() {
        return bosschain;
    } // getter

    public void setBosschain(boolean bosschain) {
        this.bosschain = bosschain;
    } //setter

    public String getName() {
        return name;
    } //getter for name

    public Room getCurrentRoom() {
        return currentRoom;
    } //getter for room

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    } //setter for room

    public ArrayList<Item> getInventory() {
        return inventory;
    } //getter for inventory

    public void move(String direction) { //move method
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        } else {
            System.out.println("You can't go that way!");
        }
    }

    public void showInventory() { //show inventory method
        if (inventory.isEmpty()) {
            System.out.println("You are not carrying anything.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
        }
    }

    public void grabItem(Item item, Room room) { //putting item in inventory method
        if (room.getItems().contains(item)) {
            inventory.add(item);
            room.getItems().remove(item);
            System.out.println("You picked up " + item.getName());
        } else {
            System.out.println("That item is not here");
        }
    }

    public void leaveItem(Item item, Room room) { //dropping item / removing from inventory
        inventory.remove(item);
        room.getItems().add(item);
        System.out.println("You dropped " + item.getName());
    }

    public Item findItemInInventory(String itemName) { //method for finding items in the inventory
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void increaseDrinkCount() { //method for increasing drink count
        if (drinkCount < 12) {
            drinkCount++;

            if (drinkCount == 12) {
                System.out.println("\nYOU DID IT! You finished the 12 pubs of Christmas!");
                System.out.println("You collapse on the ground, victorious and drunk beyond reason...");
            }
        }
    }


    public int getDrinkCount() { //drink count getter
        return drinkCount;
    }

    public boolean removeItem(Item item) { //inventory helpers
        return inventory.remove(item);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }
}
