public class FriendlyNPC extends NPC {
    private String dialogue;

    public FriendlyNPC(String name, String description, Room startingRoom, String dialogue) {
super(name, description, startingRoom);
this.dialogue = dialogue;
    }
    @Override
    public String interact() {
        return name + ": "  + dialogue;
    }
}
