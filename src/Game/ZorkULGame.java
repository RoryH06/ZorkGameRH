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

import Conditions.*;

import java.io.*;
import java.util.ArrayList;

public class ZorkULGame implements Serializable {
    private final Parser parser;
    public Game.Character player;
    private ArrayList<Room> allRooms = new ArrayList<>();
    private LockedStorage chestAwaitingCode = null;

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
                "You ascended to Agartha.");
        agartha.setTeleportDestination(TeleportDestination.AGARTHA);

        eskimo.addCondition(new HungerCondition());
        grotto.addCondition(new ElfHatCondition());
        rorys.addCondition(new RorysKeysCondition());
        cronins.addCondition(new CroninsStampCondition());
        ballintemple.addCondition(new BeerRequirementCondition(1));
        whelans.addCondition(new BeerRequirementCondition(2));
        longcourt.addCondition(new BeerRequirementCondition(9));
        cronins.addCondition(new BeerRequirementCondition(10));
        nedkellys.addCondition(new EasyPickinsCondition());

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

        OpenStorage cellar= new OpenStorage("Cellar",
                "A dark mysterious door that seems to be part of the wall is cracked open.");
        ballintemple.addStorage("Cellar", cellar);

        ballintemple.getStorage("Cellar").addItem(new Item("BOSSChain",
                "\nA blinged out chain, 14kt gold with lots of diamonds encrusted on it.",
                "If you wear this, they will not want to mess with you."));

        OpenStorage rorysLocker = new OpenStorage("Locker",
                "A tall metal locker with lots of stickers which spell \"RORY\"");
        rorys.addStorage("Locker", rorysLocker);

        rorys.getStorage("locker").addItem(new Item("NikeZoom",
                "\nA nice turqoise and orange pair of running shoes.",
                "These shoes are needed for running"));

        LockedStorage Chest = new LockedStorage("0410",
                "Chest",
                "\"The code is somebody's birthday who you hold dear to you.\"");
        square.addStorage("Chest", Chest);

        square.getStorage("chest").addItem(new Item("MonopolyMoney",
                "\n10 Monopoly Money. \n\"What the fuck am I supposed to do with Monopoly Money\"",
                "You fondly remember the time you scammed Sam on monopoly."));

        square.getStorage("chest").addItem(new Item("Teleporter",
                "\nThis seems to be some kind of teleporter.",
                "\"'AscensionToAgartha-4000', thats a peculiar name\""));

        square.getStorage("chest").addItem(new Item("Tenner",
                "\n\"Mmmmmm 2 more pints.\"",
                "It is definitely forged. \"FUCK\""));

        OpenStorage dollarDrawer = new OpenStorage("Drawer",
                "Theres a framed photo on the top sayin 'Free Pints tomorrow', \"I have a feeling tomorrow never comes...\"");
        dollar.addStorage("Drawer", dollarDrawer);

        dollar.getStorage("drawer").addItem(new Item("Elfhat",
                "\nThere was a nice ElfHat on a cardboard cutout of Terry Crews in the dollar. It's green and red with a nice golden bobble",
                "\"This really does make me look like an elf\""));

        OpenStorage shed = new OpenStorage("Shed",
                "An old fashioned shed, the door seems to be cracked.");
        jacks.addStorage("Shed", shed);

        jacks.getStorage("shed").addItem(new Item("AussieFootie",
                "\nA nice, round, slightly deflated pigskin.",
                "You see a faded name on the side, \"HAYDEN\""));

        Item note = new Item("Note",
                "\nThere seems to be some text on this bloody note, but you're partially blind so you cant see.",
                "Welcome to 12 Pubs of christmas, hood edition. You need to drink 12 beers, and complete challenges in all pubs, before all the pubs close. "
                        + "There will be lots of evil, trying to stop you from completing 12 pubs. You will need to solve puzzles and make correct decisions. \n Best of luck soldier.");

        Item teleporter = new Item("Teleporter",
                "\nThis seems to be some kind of teleporter.",
                "\"'AscensionToAgartha-4000', thats a peculiar name\"");

        Item rorysKeys = new Item("RorysKeys",
                "\nA dirty set of keys you found on the floor. There is a lot of porsche car keys for some reason.",
                "A set of keys with the writing on a tag saying: \"If lost please return to V42 W722\"");

        Item stamp = new Item("CroninsStamp",
                "\nA ticket to get yourself a stamp from Cronins",
                "It just says *Non-Refundable*");

        Item boogersugar = new Item("BoogerSugar",
                "\n\"You'd think Santa would at least try to hide his addictions from Mrs.Claus, she should leave him.\"",
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
        jacks.addItem(coke);
        nedkellys.addItem(vodka);
        rorys.addItem(sake);
        dollar.addItem(moonshine);
        cronins.addItem(jagerBomb);
        whelans.addItem(cider);
        grotto.addItem(whiskey);
        clearys.addItem(smithwicks);
        ballintemple.addItem(guinness);
        square.addItem(note);
        grotto.addItem(boogersugar);
        clearys.addItem(rorysKeys);

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
                        "\n I think I need a break. Let's go for a run, it will build up your appetite and sober you up anyways, but first we gotta drink some Sake.");

        MerchantNPC hayden = new MerchantNPC("Hayden",
                "Hes working hard, cheffing up some pizza in the brick over, what a GOAT",
                eskimo,
                "Gday mate, OI Dec! How're you getting on my son. If you see Sen will you tell him he has to feed the animals?",
                "AussieFootie",
                "Cheers matey, here's a drink to add to the tally, feelin' a little zesty today");

        MerchantNPC johnny = new MerchantNPC(
                "Johnny",
                "\"How the fuck did he get in here and I nearly wasn't allowed. At least he has a Christmas jumper I guess.\"",
                cronins,
                "Oi bro, can I have a tenner for a hostel.",
                "Tenner",
                "Thanks, pal. That'll get me warm for the night. Take this yoke, it tells me I am not welcome for ascension."
        );

        grotto.addNPC(santa);
        longcourt.addNPC(sam);
        sam.addItem(jager);
        rorys.addNPC(rory);
        eskimo.addNPC(hayden);
        hayden.addItem(cocktail);
        dollar.addNPC(croninsBouncer);
        croninsBouncer.addItem(stamp);
        cronins.addNPC(johnny);
        johnny.addItem(teleporter);
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

    public boolean processCommand(Command command) {
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
            case "teleport":
                doTeleport(command);
                break;
            case "inventory":
                player.showInventory();
                break;
            case "save":
                saveGame("filename");
                break;
            case "resume":
                loadGame("filename");
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
            case "stamp":
                getStamp(command);
                break;
            case "use":
                doUse(command);
                break;
            case "equip":
                doEquip(command);
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

    public void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room current = player.getCurrentRoom();
        Room nextRoom = current.getExit(direction);

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
        System.out.println("You move " + direction + " into " + nextRoom.getName());
        System.out.println("Coordinates: (" + nextRoom.getMapX() + "," + nextRoom.getMapY() + ")");
        System.out.println(nextRoom.getLongDescription());
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

    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(player);
            out.writeObject(allRooms);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            player = (Character) in.readObject();
            allRooms = (ArrayList<Room>) in.readObject();

            Room loadedRoom = findRoomByName(player.getCurrentRoom().getName(), allRooms);
            if (loadedRoom != null) {
                player.setCurrentRoom(loadedRoom);
            }

            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }

    private Room findRoomByName(String name, ArrayList<Room> rooms) {
        for (Room r : rooms) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
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
                } else {
                    System.out.println(npc.getName() + " is not a merchant.");
                }
                return;
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
        Parser parser = new Parser();
        Command command = parser.getCommand(input);

        boolean finished = processCommand(command);

        if (finished) {
            return "Game Over.";
        }
        return "";
    }

    public void openStorage(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Open what?");
            return;
        }

        String targetName = command.getSecondWord().toLowerCase();
        Storage storage = player.getCurrentRoom().getStorage(targetName);

        if (storage == null) {
            System.out.println("There is no storage here to open.");
            return;
        }

        System.out.println("You see a " + storage.getName() + ".");
        System.out.println(storage.getDescription());

        if (storage instanceof LockedStorage chest) {
            if (chest.isLocked()) {
                chestAwaitingCode = chest;
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
            LockedStorage opened = chestAwaitingCode;
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
        Room currentRoom = player.getCurrentRoom();

        if (currentRoom.getStorages().isEmpty()) {
            System.out.println("There is nothing here to loot.");
            return;
        }

        Storage storage = currentRoom.getStorages().values().iterator().next();

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
            if (item.getName().equalsIgnoreCase("CroninsStamp")) {
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

    public void doUse(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Unlock with what?");
            return;
        }

        if (!command.getSecondWord().equalsIgnoreCase("roryskeys")) {
            System.out.println("You can't unlock with " + command.getSecondWord() + ".");
            return;
        }

        Room currentRoom = player.getCurrentRoom();

        if (!currentRoom.getName().contains("Ballintemple")) {
            System.out.println("You can only unlock Rory's front door from the ballintemple.");
            return;
        }

        Item rorysKeys = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase("RorysKeys")) {
                rorysKeys = item;
                break;
            }
        }

        if (rorysKeys == null) {
            System.out.println("You need RorysKeys to open the door.");
            return;
        }

        player.getInventory().remove(rorysKeys);

        player.setRoryskeys(true);

        System.out.println("You put the keys in the door and twist..");
        System.out.println("It opens with ease and as you open the door you're blessed with the beautiful aroma of JPG Elixir.");
        System.out.println("You return RorysKeys to him");
    }

    public void doEquip(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Equip what?");
            return;
        }

        if (!command.getSecondWord().equalsIgnoreCase("bosschain")) {
            System.out.println("You cant equip a " + command.getSecondWord() + ".");
            return;
        }

        Room currentRoom = player.getCurrentRoom();

        if (!currentRoom.getName().contains("Whelan")) {
            System.out.println("You can only wear the chain before you go into Ned Kellys or else you look like a twat.");
            return;
        }

        Item bossChain = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase("BOSSChain")) {
                bossChain = item;
                break;
            }
        }

        if (bossChain == null) {
            System.out.println("You need a BOSSChain to equip.");
            return;
        }

        player.getInventory().remove(bossChain);

        player.setBosschain(true);

        System.out.println("You put on the BOSSChain and all of a sudden your hair is slipped back...");
        System.out.println("Everyone flinches as you enter because your aura is overwhelming. Nobody wants to mess with you.");
    }

    private Room findRoomByTeleport(TeleportDestination dest) {
        for (Room room : allRooms) {
            if (room.getTeleportDestination() == dest) {
                return room;
            }
        }
        return null;
    }


    public void doTeleport(Command command) {
        Item teleporter = null;
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase("Teleporter")) {
                teleporter = item;
                break;
            }
        }

        if (teleporter == null) {
            System.out.println("You don't have the Teleporter. You cannot teleport.");
            return;
        }

        if (command.hasSecondWord()) {
            System.out.println("Just say 'teleport' to use the Teleporter.");
            return;
        }

        Room agartha = findRoomByTeleport(TeleportDestination.AGARTHA);

        if (agartha == null) {
            System.out.println("Agartha room does not exist!");
            return;
        }

        player.getInventory().remove(teleporter);

        player.setCurrentRoom(agartha);

        new Thread(() -> {
            try {
                System.out.println("At last… after all this time… you finally stand here.");
                Thread.sleep(3000);

                System.out.println("Agartha.");
                Thread.sleep(2000);

                System.out.println("The final area. The end of the journey...");
                Thread.sleep(2800);

                System.out.println("Your head is spinning. Your legs feel like they were forged from wobbly noodles. You’ve drunk things no sane adventurer would ever combine in one lifetime, let alone in one night.");
                Thread.sleep(3500);

                System.out.println("You’ve tasted glory, despair, mild poisoning, and that drink with the umbrella in it that definitely should not have had an umbrella in it.");
                Thread.sleep(3500);

                System.out.println("You should have passed out hours ago. By all known laws of biology, physics, and good decision-making, you should currently be unconscious in a ditch behind Pub Seven.");
                Thread.sleep(4000);

                System.out.println("And yet… somehow… you endure.");
                Thread.sleep(2500);

                System.out.println("The air here is thick with mystery. Or maybe that’s just the smell of spilt ale baked into the ancient stone. It’s getting hard to tell.");
                Thread.sleep(3500);

                System.out.println("Your vision swims. The walls shimmer. The floor breathes. You’re not entirely convinced you haven’t imagined this place.");
                Thread.sleep(3500);

                System.out.println("It's possible you're lying facedown in a hallway somewhere hallucinating a secret realm.");
                Thread.sleep(3300);

                System.out.println("But even if you are… what a hallucination.");
                Thread.sleep(2500);

                System.out.println("Only one pub remains. One final drink. One last heroic, idiotic, magnificent step before you complete the legendary Twelve Pubs Challenge and ascend into the pantheon of absolute madlads.");
                Thread.sleep(5000);

                System.out.println("Every muscle trembles. Your heartbeat sounds like a tavern drum being played by someone who has had far too much mead.");
                Thread.sleep(3500);

                System.out.println("Your thoughts are foggy—thick as the stout from Pub Four. But deep inside, beneath the exhaustion, beneath the dizziness, beneath the questionable decisions… a fire still burns.");
                Thread.sleep(4500);

                System.out.println("You’re going to make it.");
                Thread.sleep(2000);

                System.out.println("Even if the ground is currently tilting. Even if you’re seeing two doors when there should be one.");
                Thread.sleep(3000);

                System.out.println("Even if you’re not entirely sure Agartha is real and not the product of whatever was in that glowing bottle from Pub Ten.");
                Thread.sleep(4000);

                System.out.println("You press forward, because heroes don’t quit. Heroes don’t fall. Heroes may stumble, trip, argue with furniture, and briefly forget their own name… but they do not quit.");
                Thread.sleep(5000);

                System.out.println("And tonight—whether this is reality, a dream, or a hallucination written by a drunk wizard—you are a hero.");
                Thread.sleep(3500);

                System.out.println("The final drink awaits.");
                Thread.sleep(2500);

                System.out.println("May your stomach hold firm. May your liver forgive you. May you remain conscious long enough to see the credits roll.");
                Thread.sleep(4000);

                System.out.println("Welcome… to Agartha.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public static void main(String[] args) {
        ZorkULGame game = new ZorkULGame();
        game.play();
    }
}


