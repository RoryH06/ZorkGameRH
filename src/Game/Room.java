package Game;

import Conditions.RoomCondition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Room implements Serializable {
    private String name;
    private String description;
    private Map<String, Room> exits;
    private ArrayList<Item> items;
    private ArrayList<NPC> npcs;
    private List<RoomCondition> conditions = new ArrayList<>();
    private Map<String, Storage>  storages;
    private int mapX;
    private int mapY;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        npcs = new ArrayList<>();
        storages = new HashMap<>();
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

    public void addCondition(RoomCondition condition) {
        conditions.add(condition);
    }

    public String checkConditions(Character player) {
        for (RoomCondition c : conditions) {
            if (!c.canEnter(player)) {
                return c.getFailureMessage();
            }
        }
        return null;
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

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
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

        if (npcs.isEmpty()) {
            System.out.println("There's nobody here...");
        } else {
            System.out.println("NPCs in this room:");
            for (NPC npc : npcs) {
                System.out.println(npc.getName() + ": " + npc.getDescription());
            }
        }

        if (storages == null) {
            System.out.println("There's no storage unit here.");
        } else {
            System.out.println("There is: ");
            for (String name : storages.keySet()) {
                System.out.println(" - " + storages.get(name).getName());

            }
        }
    }

    public String getLongDescription() {
        return description + ".\nExits: " + getExitString();
    }

    public void setMapPosition(int x, int y) {
        this.mapX = x;
        this.mapY = y;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void addStorage(String name, Storage storage) {
        storages.put(name.toLowerCase(), storage);
    }

    public Storage getStorage(String name) {
        return storages.get(name.toLowerCase());
    }

    public Map<String, Storage> getStorages() {
        return storages;
        }
}