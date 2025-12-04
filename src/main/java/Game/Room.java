package Game;

import Conditions.RoomCondition;

import java.io.Serializable;
import java.util.*;

public class Room implements Serializable {

    private String name;
    private String description;

    private Map<String, Room> exits = new HashMap<>();
    private Container<Item> items;
    private Container<NPC> npcs;
    private List<RoomCondition> conditions = new ArrayList<>();
    private Map<String, Storage> storages = new HashMap<>();

    private TeleportDestination teleportDestination;
    private int mapX;
    private int mapY;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new Container<>();
        this.npcs = new Container<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction.toLowerCase(), neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    public String getExitString() {
        if (exits.isEmpty()) return "No exits";

        StringBuilder sb = new StringBuilder();
        for (String dir : exits.keySet()) {
            sb.append(dir).append(" ");
        }
        return sb.toString().trim();
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

    public void addItem(Item item) {
        items.add(item);
    }

    public Container<Item> getItems() {
        return items; }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public Container<NPC> getNPCs() { return npcs; }

    public void look() {
        if (items.isEmpty()) {
            System.out.println("No items in this room.");
        } else {
            System.out.println("Items in this room:");
            items.forEach(i -> System.out.println(i.getName()));
        }

        if (npcs.isEmpty()) {
            System.out.println("There's nobody here...");
        } else {
            System.out.println("NPCs in this room:");
            npcs.forEach(n -> System.out.println(n.getName() + ": " + n.getDescription()));
        }

        if (storages.isEmpty()) {
            System.out.println("There's no storage unit here.");
        } else {
            System.out.println("Available storage units:");
            storages.values().forEach(s -> System.out.println(" - " + s.getName()));
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

    public TeleportDestination getTeleportDestination() {
        return teleportDestination;
    }

    public void setTeleportDestination(TeleportDestination teleportDestination) {
        this.teleportDestination = teleportDestination;
    }
}
