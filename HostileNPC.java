public class HostileNPC extends NPC {
    private String dialogue;
    private int health;

    public HostileNPC(String name, String description, Room startingRoom, String dialogue, int health) {
        super(name, description, startingRoom);
        this.dialogue = dialogue;
        this.health = health;
    }
    @Override
    public String interact() {
        return name + "says: "  + dialogue;
    }
}
