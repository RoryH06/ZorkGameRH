import java.util.ArrayList;

public class MerchantNPC extends NPC{
    private String dialogue;
    private ArrayList<Item> inventory = new ArrayList<>();
    private String requiredItemName;
    private String  merchantDialogue;

    public MerchantNPC(String name, String description, Room startingRoom, String dialogue, String requiredItemName, String merchantDialogue) {
        super(name, description, startingRoom);
        this.dialogue = dialogue;
        this.requiredItemName = requiredItemName;
        this.merchantDialogue = merchantDialogue;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public String trade(Character player, Room room) {
        Item requiredItem = player.findItemInInventory(requiredItemName);

        if (requiredItem == null) {
            return name + " : You dont have a " + requiredItemName + ", come back with it.";
        }

        if (inventory.isEmpty()) {
            System.out.println("\"They have nothing to trade\"");
        }

        Item merchantItem = inventory.get(0);

        player.leaveItem(requiredItem, player.getCurrentRoom());
        inventory.add(requiredItem);

        room.getItems().add(merchantItem);
        player.grabItem(merchantItem, player.getCurrentRoom());
        inventory.remove(merchantItem);

        return "\"You have traded " + requiredItem.getName() + " for " + merchantItem.getName() + "\"\n" + name + ": " + merchantDialogue;
    }

    @Override
    public String interact() {
        return name + ": " + dialogue + "\n" + name + " has : " + inventory.size() + " items.'";
                }
    }

