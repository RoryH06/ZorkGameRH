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
        Room square, eskimo, jacks, grotto, dollar, cronins, ballintemple, rorys, whelans, nedkellys, clearys, longcourt, agartha;

        // create rooms
        square = new Room("in the square");
        eskimo = new Room("in Eskimo Pizza");//12
        jacks = new Room("in Jacks Pub");//1
        dollar = new Room("in The Silver Dollar");//2
        cronins = new Room("in Cronins Nightclub");//3
        grotto = new Room("in Santa's grotto.");//4
        ballintemple = new Room("in The Ballintemple.");//5
        rorys = new Room("in Rorys house.");//6
        whelans = new Room("in Whelans Pub.");//7
        nedkellys = new Room("in Ned Kellys Bar.");//8
        clearys = new Room("in Clearys.");//9
        longcourt = new Room("in The Longcourt House Hotel.");//10
        agartha = new Room("in Agartha.");//11

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

        Item note = new Item("Note",
                             "\nThere seems to be some text on this bloody note, but you're partially blind so you cant see.",
                             "Welcome to 12 Pubs of christmas, hood edition. \nYou need to drink 12 beers, and complete challenges in all pubs, before all the pubs close. " +
                                            "There will be lots of evil, trying to stop you from completing 12 pubs. You will need to solve puzzles and make correct decisions. \n Best of luck soldier.");

        DrinkItem jager = new DrinkItem(
                "Jager",
                "\nDrinking bottle of Jager:\n+ A bad idea.\n+ May die.\n+ Will never drink Jager again.",
                "There seems to be Sam's blood on this. \"What was he doing?\""
        );

        DrinkItem smithwicks = new DrinkItem(
                "Smithwicks",
                "\nDrinking smithwicks:\n+ Up the Dubs\n+ Dublin accent",
                "A Smithwicks bottle. Classic."
        );

        DrinkItem guinness = new DrinkItem(
                "Guinness",
                "\nDrinking Guinness:\n = Drinking soup",
                "The black shtuff"
        );

        DrinkItem vodka = new DrinkItem(
                "Vodka",
                "\nDrinking vodka:\n+ Burning throat\n+ Instant regret",
                "Good ol' Smirnoff"
        );

        DrinkItem coke = new DrinkItem(
                "Coke",
                "\nDrinking coke:\n+ Refreshment\n+ Look like a pussy\n+ Tastes Good",
                "99% sure your friend put vodka in here"
        );

        DrinkItem cocktail = new DrinkItem(
                "Cocktail",
                "\nDrinking cocktail:\n+ Strawberry required \n+ Feeling a bit zesty",
                "A pornstar martini. \n \"Who the fuck comes up with these names.\""
        );

        DrinkItem whiskey = new DrinkItem(
                "Whiskey",
                "\nDrinking whiskey:\n+ Warm chest\n+ Mumbling trad music imminent",
                "The bottle is pristine, \"Is this LeHenny? I need a honey pack.\""
        );

        DrinkItem jagerBomb = new DrinkItem(
                "Jagerbomb",
                "\nDrinking Jagerbomb:\n+ Strange visions\n+ Slight buzzing sound?\n+ Feeling rejuvinated",
                "The condesnation on the can of RedBull tempts you."
        );

        DrinkItem cider = new DrinkItem(
                "Cider",
                "\nDrinking cider:\n+ Sweetness\n+ Mild buzz\n+ Feel slightly fancy",
                "Orchard Thieves, \"I feel 14 again\""
        );

        DrinkItem sake = new DrinkItem(
                "Sake",
                "\nDrinking sake:\n+ Warm calm feeling\n+ Soft aftertaste\n+ Katana mastery",
                "A delicate ceramic bottle with elegant brush strokes. \n \"Where the hell did this come from\""
        );

        DrinkItem mead = new DrinkItem(
                "Mead",
                "\nDrinking mead:\n+ Blonde hair and blue eyes\n+ Viking spirit\n+ Hairy chest",
                "A mason jar that has the label \"Mead\""
        );

        DrinkItem moonshine = new DrinkItem(
                "Moonshine",
                "\nDrinking moonshine:\n+ Probably illegal\n+ Definitely dangerous\n+ Might go blind",
                "The jar has no label. Just a hand-written warning: \"DONT.\""
        );


        agartha.addItem(mead);
        jacks.addItem(guinness);
        nedkellys.addItem(vodka);
        rorys.addItem(sake);
        eskimo.addItem(cocktail);
        dollar.addItem(moonshine);
        cronins.addItem(jagerBomb);
        whelans.addItem(cider);
        grotto.addItem(whiskey);
        clearys.addItem(coke);
        ballintemple.addItem(guinness);

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
            case "drink":
                doDrink(command);
                if (player.drinkCount() >= 12) {
                    System.out.println("\nYOU COMPLETED THE 12 PUBS CHALLENGE!");
                    return true;
                }
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

    public String go(String direction) { //for da gui
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            return "There is no door!";
        } else {
            player.setCurrentRoom(nextRoom);
            return player.getCurrentRoom().getLongDescription();
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

    private void doDrink (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drink what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = player.findItemInInventory(itemName);

        if (item == null) {
            System.out.println("You dont have that!");
            return;
        }

        if (!(item instanceof DrinkItem)) {
            System.out.println("You cant drink that!");
        }

        Drinkable drink = (Drinkable)  item;
        System.out.println(drink.drink());
        player.removeItem(item);
        player.increaseDrinkCount();

        System.out.println("Drink count:" + player.drinkCount() + "/12");
    }

    public String processGuiCommand(String input) {
        String[] words = input.split(" ", 2);
        String command = words[0].toLowerCase();
        String arg = words.length > 1 ? words[1] : null;

        return switch (command) {
            case "go" -> go(arg);
            default -> "I don't know what you mean...";
        };
    }


    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        game.play();
    }
}


