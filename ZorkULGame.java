/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g., "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognizes a limited set of commands like "go", "help", and "quit".
Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting,
emphasizing exploration and simple command-driven gameplay
*/

import java.io.*;

public class ZorkULGame {
    private Parser parser;
    public static Character player;

    public ZorkULGame() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room square, eskimo, jacks, grotto, dollar, cronins, ballintemple, rorys, whelans, nedkellys, clearys, longcourt, rathkeale;

        // create rooms
        square = new Room("in the square");
        eskimo = new Room("in Eskimo Pizza");
        jacks = new Room("in Jacks Pub");
        dollar = new Room("in The Silver Dollar");
        cronins = new Room("in Cronins Nightclub");
        grotto = new Room("in Santa's grotto.");
        ballintemple = new Room("in The Ballintemple.");
        rorys = new Room("in Rorys house.");
        whelans = new Room("in Whelans Pub.");
        nedkellys = new Room("in Ned Kellys Bar.");
        clearys = new Room("in Clearys.");
        longcourt = new Room("in The Longcourt House Hotel.");
        rathkeale = new Room("in Rathkeale.");

        square.setExit("west", eskimo);
        square.setExit("south", ballintemple);
        square.setExit("east", nedkellys);
        square.setExit("north", dollar);

        eskimo.setExit("west", jacks);
        eskimo.setExit("east", square);

        jacks.setExit("west", grotto);
        jacks.setExit("east", eskimo);

        grotto.setExit("east", jacks);

        dollar.setExit("north", cronins);
        dollar.setExit("south", square);
        dollar.setExit("east", whelans);

        cronins.setExit("south", dollar);

        whelans.setExit("south", nedkellys);
        whelans.setExit("west", dollar);

        ballintemple.setExit("south", rorys);
        ballintemple.setExit("north", square);

        rorys.setExit("north", ballintemple);

        nedkellys.setExit("north", whelans);
        nedkellys.setExit("west", square);
        nedkellys.setExit("east", clearys);

        clearys.setExit("south", longcourt);
        clearys.setExit("west", nedkellys);

        longcourt.setExit("north", clearys);

        Item smithwicks = new Item("Beer", "\nDrinking beer: \n+Nausea\n+Twistedness", "Smithwicks" );
        Item note = new Item("Note", "\nThere seems to be some text on this bloody note, but you're partially blind so you cant see."
                            , "Welcome to 12 Pubs of christmas, hood edition. \nYou need to drink 12 beers, and complete challenges in all pubs, before all the pubs close. " +
                                "There will be lots of evil, trying to stop you from completing 12 pubs. You will need to solve puzzles and make correct decisions. \n Best of luck soldier.");
        Item jager = new Item("Jager", "\n Drinking bottle of Jager: \n+A bad idea. \n+May die. \n+Will never drink Jager again", "There seems to be Sams blood on this. \"What was he doing?\"");

        rathkeale.addItem(smithwicks);
        square.addItem(note);

        player = new Character("player", square);

        FriendlyNPC santa = new FriendlyNPC("Santa", "A grotesquely overweight, yet jolly man. \n\"Is Santa binge drinking Hennessy?\"", grotto, "*Burps*");
        MerchantNPC sam =  new MerchantNPC("Sam", " He is on the floor, and doesn't seem to be too responsive. \n\"He seems to have drank WAY too much Jager, he couldn't finish the bottle in his hand. Poor guy.\""
                , longcourt, "Lets get litty in New Junk CITAY!", "Note", "Hey I just got scammed, I WANT MY JAGER BACK!");

        grotto.addNPC(santa);
        longcourt.addNPC(sam);
        sam.addItem(jager);
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to 12 pubs of Christmas 'Hood Edition'!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            System.out.println("I don't understand your command...");
            return false;
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "interact":
                doInteract(command);
                break;
            case "look":
                player.getCurrentRoom().look();
                break;
            case "take":
                takeItem(command);
                break;
            case "drop":
                dropItem(command);
                break;
            case "examine":
                examineItem(command);
                break;
            case "inventory":
                player.showInventory();
                break;
            case "save":
                saveGame("filename");
                break;
            case "resume":
                resumeGame("filename");
                break;
            case "trade":
                doTrade(command);
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    System.out.println("Quit what?");
                    return false;
                } else {
                    return true;
                }
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }

    private void printHelp() {
        System.out.println("You are in the square. There's an ominous timer above your head. You have a strong sense of survival, beer...");
        System.out.print("Your command words are: ");
        parser.showCommands();
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what? ");
            return;
        }

        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Item czechItem = null;

        for (Item item : currentRoom.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                czechItem = item;
                break;
            }
        }

        if (czechItem == null) {
            System.out.println("There is no item here!");
        } else {
            player.grabItem(czechItem, player.getCurrentRoom());
            System.out.println("You have taken this item" + czechItem.getName());
        }
    }



    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what? ");
            return;
        }

        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Item czechItem = null;

        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                czechItem = item;
                break;
            }
        }

        if (czechItem == null) {
            System.out.println("This is not in your inventory!");
        } else {
            player.leaveItem(czechItem, player.getCurrentRoom());
            System.out.println("You dropped" + czechItem.getName());
        }
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }

    private void doInteract(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Interact with who?");
            return;
        }

        String targetName = command.getSecondWord();
        Room room = player.getCurrentRoom();

        for (NPC npc : room.getNPCs()) {
            if (npc.getName().equalsIgnoreCase(targetName)) {
                System.out.println(npc.interact());
                return;
            }
        }

        System.out.println("There’s no one named " + targetName + " here.");
    }

    private void examineItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Examine what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item targetItem = null;

        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                targetItem = item;
                break;
            }
        }

        if (targetItem == null) {
            System.out.println("You don’t have any item called '" + itemName + "'.");
        } else {
            System.out.println(targetItem.getHiddenDescription());
        }
    }

    public void saveGame(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(player);
            System.out.println("Object has been serialized to" + fileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void resumeGame(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            player = (Character) in.readObject();
            System.out.println("Object has been serialized to" + fileName);
        } catch (IOException |  ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void doTrade(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Trade with who?");
            return;
        }

        String targetName = command.getSecondWord();
        Room room = player.getCurrentRoom();

        for (NPC npc : room.getNPCs()) {
            if (npc.getName().equalsIgnoreCase(targetName)) {

                if (npc instanceof MerchantNPC merchant) {
                    System.out.println(merchant.trade(player, room));
                    return;
                } else {
                    System.out.println(npc.getName() + " is not a merchant.");
                    return;
                }
            }
        }

        System.out.println("There is no one named " + targetName + " here.");
    }


    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        game.play();
    }
}


