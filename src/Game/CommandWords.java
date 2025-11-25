package Game;

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
        validCommands.put("interact", "Interact with the NPCs in the room");
        validCommands.put("examine", "Examine an item in your inventory");
        validCommands.put("save", "Save the game");
        validCommands.put("resume", "Resume the game");
        validCommands.put("trade", "Trade with an Game.NPC");
        validCommands.put("drink", "Drink a beer");
        validCommands.put("open", "Open storage");
        validCommands.put("code", "Enter a code");
        validCommands.put("loot", "Loot an item from storage");
        validCommands.put("run", "Go for a run");
        validCommands.put("pose", "Pretend to be something you're not");
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

