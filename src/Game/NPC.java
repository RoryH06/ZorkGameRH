package Game;

import java.io.Serializable;

public abstract class NPC implements Serializable {
    protected String name;
    protected String description;
    protected Room currentRoom;

    public NPC(String name, String description, Room startingRoom) {
        this.name = name;
        this.description = description;
        this.currentRoom = startingRoom;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public abstract String interact();

}

