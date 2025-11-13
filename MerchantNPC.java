import java.util.ArrayList;

public class MerchantNPC extends NPC{
    private String dialogue;
    private ArrayList<Item> inventory = new ArrayList<>();

    public MerchantNPC(String name, String description, Room startingRoom, String dialogue) {
        super(name, description, startingRoom);
        this.dialogue = dialogue;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    @Override
    public String interact() {
        return name + ": " + dialogue + "\n" + name + "has : " + inventory.size() + " items.'";
    }
}
