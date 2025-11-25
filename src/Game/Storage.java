package Game;

import java.util.ArrayList;

public abstract class Storage {
    protected String name;
    protected String description;
    protected ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Item lootItem(String itemName, Character character) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                character.addItem(item); // adds to Character’s inventory
                System.out.println("You loot " + item.getName() + " from the " + name + ".");
                return item;
            }
        }
        System.out.println("That item is not in the " + name + ".");
        return null;
    }
}