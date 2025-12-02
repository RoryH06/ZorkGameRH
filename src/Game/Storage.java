package Game;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Storage implements Serializable {
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

    public Item lootItem(String targetName, Character character) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(targetName)) {
                items.remove(item);
                character.addItem(item);
                System.out.println("You loot " + item.getName() + " from the " + name + ".");
                return item;
            }
        }
        System.out.println("That item is not in the " + name + ".");
        return null;
    }
}