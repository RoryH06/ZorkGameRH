package Game;
/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g., "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
Game.Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognizes a limited set of commands like "go", "help", and "quit".
Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting,
emphasizing exploration and simple command-driven gameplay
*/

import java.io.*;
import java.util.ArrayList;

public class ZorkULGame {
    private Parser parser;
    public static Game.Character player;
    private ArrayList<Room> allRooms = new ArrayList<>();
    private Chest chestAwaitingCode = null;

    public ZorkULGame() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {

        Room square        = new Room("the town square",
                "You are standing in the bustling town square, the center of everything.");
        Room eskimo        = new Room("Eskimo Pizza",
                "You are inside Eskimo Pizza. The smell of fresh dough and melted cheese surrounds you.");
        Room jacks         = new Room("Jack's Pub",
                "You enter Jack’s Pub, a lively spot filled with chatter and clinking glasses.");
        Room dollar        = new Room("The Silver Dollar",
                "You are inside The Silver Dollar, a cosy pub with dim lights and friendly faces.");
        Room cronins       = new Room("Cronin's Nightclub",
                "You step into Cronin’s Nightclub, where the music pounds and lights flash wildly.");
        Room grotto        = new Room("Santa’s Grotto",
                "You find yourself in Santa’s Grotto, warm and festive with twinkling lights everywhere.");
        Room ballintemple  = new Room("The Ballintemple",
                "You are in The Ballintemple, a traditional bar with wooden stools and a roaring fire.");
        Room rorys         = new Room("Rory’s House",
                "You arrive at Rory’s house, a small, tidy home filled with personal touches.");
        Room whelans       = new Room("Whelan’s Pub",
                "You enter Whelan’s Pub, a relaxed place where the locals gather to talk.");
        Room nedkellys     = new Room("Ned Kelly’s Bar",
                "You step into Ned Kelly’s Bar, known for its lively atmosphere and strong drinks.");
        Room clearys       = new Room("Cleary’s",
                "You find yourself inside Cleary’s, a bright and welcoming pub.");
        Room longcourt     = new Room("The Longcourt House Hotel",
                "You are in the Longcourt House Hotel, elegant and comfortable with soft lighting.");
        Room agartha       = new Room("Agartha",
                "You descend into Agartha, a mysterious underground realm filled with ancient secrets.");

        eskimo.addCondition(new HungerCondition());
        grotto.addCondition(new ElfHatCondition());
        ballintemple.addCondition(new BeerRequirementCondition(1));
        cronins.addCondition(new BeerRequirementCondition(10));

        allRooms.add(square);
        allRooms.add(eskimo);
        allRooms.add(jacks);
        allRooms.add(grotto);
        allRooms.add(dollar);
        allRooms.add(cronins);
        allRooms.add(ballintemple);
        allRooms.add(rorys);
        allRooms.add(whelans);
        allRooms.add(nedkellys);
        allRooms.add(clearys);
        allRooms.add(longcourt);
        allRooms.add(agartha);

        square.setMapPosition(3, 2);

        eskimo.setMapPosition(2, 2);
        jacks.setMapPosition(1, 2);
        grotto.setMapPosition(0, 2);

        ballintemple.setMapPosition(3, 1);
        rorys.setMapPosition(3, 0);

        nedkellys.setMapPosition(4, 2);
        clearys.setMapPosition(5, 2);
        longcourt.setMapPosition(5, 1);

        dollar.setMapPosition(3, 3);
        cronins.setMapPosition(3, 4);
        whelans.setMapPosition(4, 3);

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

        Locker rorysLocker = new Locker("Locker",
                "A tall metal locker with lots of stickers which spell \"RORY\"");

        rorysLocker.addItem(new Item("NikeZoom",
                "A nice turqoise and orange pair of running shoes.",
                "These shoes are needed for running"));

        Chest goldChest = new Chest("0410", //need to make it so when i say open locker in the square it doesnt try to open this. Open js does it, doesnt even check the second word.
                                    "Gold Chest",
                                    "\"The code is somebody's birthday who you hold dear to you.\"");

        goldChest.addItem(new Item("MonopolyMoney",
                "\n10 Monopoly Money. \n\"What the fuck am I supposed to do with Monopoly Money\"",
                "You fondly remember the time you scammed Sam on monopoly."));

        Drawer dollarDrawer = new Drawer("Drawer",
                "Theres a framed photo on the top sayin 'Free Pints tomorrow', \"I have a feeling tomorrow never comes...\"");

        dollarDrawer.addItem(new Item("Elfhat",
                "\nThere was a nice ElfHat on a cardboard cutout of Terry Crews in the dollar. It's green and red with a nice golden bobble",
                "\n \"This really does make me look like an elf\""));

        Item elfhat = new Item("Elfhat",
                "\nThere was a nice ElfHat on a cardboard cutout of Terry Crews in the dollar. It's green and red with a nice golden bobble",
                "\n \"This really does make me look like an elf\"");

        Item note = new Item("Note",
                "\nThere seems to be some text on this bloody note, but you're partially blind so you cant see.",
                "Welcome to 12 Pubs of christmas, hood edition. \nYou need to drink 12 beers, and complete challenges in all pubs, before all the pubs close. "
                        + "There will be lots of evil, trying to stop you from completing 12 pubs. You will need to solve puzzles and make correct decisions. \n Best of luck soldier.");

        Item teleporter = new Item("Teleporter",
                "Hayden seems to have developed some kind of teleporter.",
                "\"'AscensionToAgartha-4000', thats a peculiar name\"");

        Item stamp = new Item("CroninsStamp",
                "A ticket to get yourself a stamp from Cronins",
                "It just says *Non-Refundable*");

        Item boogersugar = new Item("BoogerSugar",
                "\"You'd think Santa would at least try to hide his addictions from Mrs.Claus, she should leave him.\"",
                "A bag that has a label saying \"Santas infamous sniff\"");

        DrinkItem jager = new DrinkItem("Jager",
                "\nDrinking bottle of Jager:\n+ A bad idea.\n+ May die.\n+ Will never drink Jager again.",
                "There seems to be Sam's blood on this. \"What was he doing?\"");

        DrinkItem smithwicks = new DrinkItem("Smithwicks",
                "\nDrinking smithwicks:\n+ Up the Dubs\n+ Dublin accent",
                "A Smithwicks bottle. Classic.");

        DrinkItem guinness = new DrinkItem("Guinness",
                "\nDrinking Guinness:\n = Drinking soup",
                "The black shtuff");

        DrinkItem vodka = new DrinkItem("Vodka",
                "\nDrinking vodka:\n+ Burning throat\n+ Instant regret",
                "Good ol' Smirnoff");

        DrinkItem coke = new DrinkItem("Coke",
                "\nDrinking coke:\n+ Refreshment\n+ Look like a pussy\n+ Tastes Good",
                "99% sure your friend put vodka in here");

        DrinkItem cocktail = new DrinkItem("Cocktail",
                "\nDrinking cocktail:\n+ Strawberry required \n+ Feeling a bit zesty",
                "A pornstar martini. \n \"Who the fuck comes up with these names.\"");

        DrinkItem whiskey = new DrinkItem("Whiskey",
                "\nDrinking whiskey:\n+ Warm chest\n+ Mumbling trad music imminent",
                "The bottle is pristine, \"Is this LeHenny? I need a honey pack.\"");

        DrinkItem jagerBomb = new DrinkItem("Jagerbomb",
                "\nDrinking Jagerbomb:\n+ Strange visions\n+ Slight buzzing sound?\n+ Feeling rejuvinated",
                "The condesnation on the can of RedBull tempts you.");

        DrinkItem cider = new DrinkItem("Cider",
                "\nDrinking cider:\n+ Sweetness\n+ Mild buzz\n+ Feel slightly fancy",
                "Orchard Thieves, \"I feel 14 again\"");

        DrinkItem sake = new DrinkItem("Sake",
                "\nDrinking sake:\n+ Warm calm feeling\n+ Soft aftertaste\n+ Katana mastery",
                "A delicate ceramic bottle with elegant brush strokes. \n \"Where the hell did this come from\"");

        DrinkItem mead = new DrinkItem("Mead",
                "\nDrinking mead:\n+ Blonde hair and blue eyes\n+ Viking spirit\n+ Hairy chest",
                "A mason jar that has the label \"Mead\"");

        DrinkItem moonshine = new DrinkItem("Moonshine",
                "\nDrinking moonshine:\n+ Probably illegal\n+ Definitely dangerous\n+ Might go blind",
                "The jar has no label. Just a hand-written warning: \"DONT.\"");

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
        grotto.addItem(boogersugar);

        square.setStorage(goldChest);
        rorys.setStorage(rorysLocker);
        dollar.setStorage(dollarDrawer);

        player = new Game.Character("player", square);

        FriendlyNPC santa = new FriendlyNPC("Santa",
                "A grotesquely overweight, yet jolly man. \n\"Is Santa binge drinking Hennessy?\"",
                grotto,
                "\"*Burps*\"");

        MerchantNPC croninsBouncer = new MerchantNPC("BigJohn",
                "A big brollic man, he stands on the door of Cronins and is your biggest problem.",
                dollar,
                "Look lad I can't let you into here, youre barred, but dont say the anyone, *whispers* - get me some sniff and I'll stamp you in, we'll call it a 'trade'",
                "BoogerSugar",
                "Sound man, have a good night.");

        MerchantNPC sam = new MerchantNPC("Sam",
                " He is on the floor, and doesn't seem to be too responsive. \n\"He seems to have drank WAY too much Jager, he couldn't finish the bottle in his hand. Poor guy.\"",
                longcourt,
                "Lets get litty in New Junk CITAY!",
                "MonopolyMoney",
                "Hey I just got scammed, I WANT MY JAGER BACK!");

        FriendlyNPC rory = new FriendlyNPC("Rory",
                " It's your good friend Rory, studying at his desk so he doesn't fail his exams.",
                rorys,
                "\"Oh my god, what is up Declan! I'm just studying, man I'm so finished.\" " +
                        "\n I think I need a break. Let's go for a run, it will build up your appetite and sober you up anyways.");

        MerchantNPC hayden = new MerchantNPC("Hayden",
                "Hes working hard, cheffing up some pizza in the brick over, what a GOAT",
                eskimo,
                "Gday mate, OI Dec! How're you getting on my son. If you see Sen will you tell him he has to feed the animals?",
                "Note",
                "Cheers matey, here's an old thing I put together myself 14 years ago. It might come in use for your challenge.");

        grotto.addNPC(santa);
        longcourt.addNPC(sam);
        sam.addItem(jager);
        rorys.addNPC(rory);
        eskimo.addNPC(hayden);
        hayden.addItem(teleporter);
        dollar.addNPC(croninsBouncer);
        croninsBouncer.addItem(stamp);
    }


    public ArrayList<Room> getAllRooms() {
        return allRooms;
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
            case "run":
                doRun(command);
                break;
            case "open":
                openStorage(command);
                break;
            case "pose":
                doPose(command);
                break;
            case "code":
                enterCode(command);
                break;
            case "loot":
                doLoot(command);
                break;
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
            return;
        }

        String failMessage = nextRoom.checkConditions(player);
        if (failMessage != null) {
            System.out.println(failMessage);
            return;
        }

        player.setCurrentRoom(nextRoom);
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    public String go(String direction) {
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            return "There is no door!";
        }

        String failMessage = nextRoom.checkConditions(player);
        if (failMessage != null) {
            return failMessage;
        }

        player.setCurrentRoom(nextRoom);
        return player.getCurrentRoom().getLongDescription();
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

    public void openStorage(Command command) {
        Storage storage = player.getCurrentRoom().getStorage();

        if (storage == null) {
            System.out.println("There is no storage here to open.");
            return;
        }

        System.out.println("You see a " + storage.getName() + ".");
        System.out.println(storage.getDescription());

        if (storage instanceof Chest) {
            Chest chest = (Chest) storage;
            if (chest.isLocked()) {
                chestAwaitingCode = chest; // ✅ restore awaiting code
                System.out.println("The chest is locked.");
                System.out.println("Use: code ####");
                return;
            }
        }

        if (storage.getItems().isEmpty()) {
            System.out.println("It is empty.");
        } else {
            System.out.println("Inside you find:");
            for (Item item : storage.getItems()) {
                System.out.println("- " + item.getName());
            }
        }
    }

    public void enterCode(Command command) {
        if (chestAwaitingCode == null) {
            System.out.println("There is nothing waiting for a code.");
            return;
        }

        String attempt = command.getSecondWord();
        if (attempt == null) {
            System.out.println("What code?");
            return;
        }

        if (chestAwaitingCode.tryCode(attempt)) {
            Chest opened = chestAwaitingCode;
            chestAwaitingCode = null;

            System.out.println("You hear a click... the lock opens!");

            if (opened.getItems().isEmpty()) {
                System.out.println("The chest is empty.");
            } else {
                System.out.println("Inside you find:");
                for (Item item : opened.getItems()) {
                    System.out.println("- " + item.getName());
                }
            }
        } else {
            System.out.println("Incorrect code!");
        }
    }

    public void doLoot(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Loot what?");
            return;
        }

        String itemName = command.getSecondWord();
        Storage storage = player.getCurrentRoom().getStorage();

        if (storage == null) {
            System.out.println("There is nothing here to loot.");
            return;
        }

        storage.lootItem(itemName, player);
    }

    public void doRun(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Run with who?");
            return;
        }

        if (!command.getSecondWord().equalsIgnoreCase("rory")) {
            System.out.println("You can't run with " + command.getSecondWord() + ".");
            return;
        }

        Room currentRoom = player.getCurrentRoom();

        if (!currentRoom.getName().contains("Rory")) {
            System.out.println("You can only run with rory from rorys");
            return;
        }

        Item NikeZoom = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase("NikeZoom")) {
                NikeZoom = item;
                break;
            }
        }

        if (NikeZoom == null) {
            System.out.println("You need Running Shoes to run.");
            return;
        }

        player.getInventory().remove(NikeZoom);

        player.setHungry(true);

        System.out.println("You went on a run down the greenway with Rory, and you posted it on Strava.");
        System.out.println("Your Running Shoes are worn out and discarded.");
        System.out.println("You feel hungry after the run...");
    }

    public void doPose(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Pose with what?");
            return;
        }

        if (!command.getSecondWord().equalsIgnoreCase("elfhat")) {
            System.out.println("You can't pose with " + command.getSecondWord() + ".");
            return;
        }

        Room currentRoom = player.getCurrentRoom();

        if (!currentRoom.getName().contains("Jack")) {
            System.out.println("You can only pose from Jack's");
            return;
        }

        Item elfhat = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase("ElfHat")) {
                elfhat = item;
                break;
            }
        }

        if (elfhat == null) {
            System.out.println("You need an ElfHat to pose.");
            return;
        }

        player.getInventory().remove(elfhat);

        player.setElfHat(true);

        System.out.println("You put the ElfHat on your head.");
        System.out.println("You did a cheeky jig for the elf on the door.He moaned as the door open. He looked an awful lot like Senan.");
    }

    public void getStamp(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Stamp what?");
            return;
        }

        if (!command.getSecondWord().equalsIgnoreCase("stamp")) {
            System.out.println("You can't stamp a " + command.getSecondWord() + ".");
            return;
        }

        Room currentRoom = player.getCurrentRoom();

        if (!currentRoom.getName().contains("Dollar")) {
            System.out.println("You can only get stamped outside of Cronins");
            return;
        }

        Item stamp = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase("Stamp")) {
                stamp = item;
                break;
            }
        }

        if (stamp == null) {
            System.out.println("You need a stamp to stamp.");
            return;
        }

        player.getInventory().remove(stamp);

        player.setCroninsstamp(true);

        System.out.println("You got your arm stamped with the Cronins logo in black ink.");
    }

    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        game.play();
    }
}


