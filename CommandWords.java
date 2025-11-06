import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private Map<String, String> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", "Move to another room");
        validCommands.put("quit", "End the game");
        validCommands.put("help", "Show help");
        validCommands.put("look", "Look around your current room");
        validCommands.put("take", "Pick up an item from the room");
        validCommands.put("drop", "Drop an item from your inventory into the room");
        validCommands.put("inventory", "Show all items you are carrying");
        validCommands.put("teleport", "Teleport instantly to a specific room");
    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

    public void showAll() {
        System.out.println("Valid commands are:");
        for (Map.Entry<String, String> entry : validCommands.entrySet()) {
            System.out.printf("  %-10s - %s%n", entry.getKey(), entry.getValue());
        }
    }
}

