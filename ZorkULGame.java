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

public class ZorkULGame {
    private Parser parser;
    private Character player;

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

        Item beer = new Item("Beer", "\nDrinking beer: \n+Nausea\n+Twistedness");
        Item note = new Item("Note", "\nThere seems to be some text on this bloody note, but you're partially blind so you cant see.");

        rathkeale.addItem(beer);
        square.addItem(note);

        player = new Character("player", square);

        FriendlyNPC santa = new FriendlyNPC("Santa", "A grotesquely overweight, yet jolly man", grotto, "*Burps*");

        grotto.addNPC(santa);
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
            case "inventory":
                player.showInventory();
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

        for (Item item : currentRoom.getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                czechItem = item;
                break;
            }
        }

        if (czechItem == null) {
            System.out.println("This is not in your inventory!");
        } else {
            player.grabItem(czechItem, player.getCurrentRoom());
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


    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        game.play();
    }
}
