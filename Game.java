import java.util.ArrayList;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game {
    Room Regia, Aedes_Vestae, Castor_Tempel, Basilica_Sempronia, Basilica_Amilia, Templum_Saturni, Triumphbogen, Curia,
            Templum_Concordia, Vor_der_Curia;
    Item Carti, Gladius_Brevis;
    Amicus amicus;
    ArrayList<Item> inventory = new ArrayList<Item>();

    // ~~~ initialization ~~~
    public static void main(String args[]) {
        Game g = new Game();
        g.play();
    }

    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        createRooms();
        createItems();
        amicus = new Amicus("Crassus", Templum_Concordia);
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {

        // create the rooms
        Regia = new Room("An dem Tempel Regia von Julius Cäsar");
        Aedes_Vestae = new Room("im Tempel der Vesta");
        Castor_Tempel = new Room("Am Tempel des Castor");
        Basilica_Sempronia = new Room("zwischen den Händlern in der Basilica Sempronia");
        Basilica_Amilia = new Room("bei den Händlern der Basilica Amilia");
        Templum_Saturni = new Room("am Tempel des Gottes Saturn");
        Triumphbogen = new Room("unter dem Triumphbogen");
        Curia = new Room("unbefugt im Rathaus");
        Templum_Concordia = new Room("vor dem Tempel der Concordia");
        Vor_der_Curia = new Room("vor dem römischen Rathaus"); // initialise room exits

        Regia.setExit("south", Aedes_Vestae);
        Regia.setExit("north", Basilica_Amilia);
        Regia.setExit("northwest", Triumphbogen);
        Regia.setExit("southwest", Castor_Tempel);

        Aedes_Vestae.setExit("north", Regia);
        Aedes_Vestae.setExit("west", Castor_Tempel);
        Aedes_Vestae.setExit("northwest", Triumphbogen);

        Castor_Tempel.setExit("west", Basilica_Sempronia);
        Castor_Tempel.setExit("east", Aedes_Vestae);
        Castor_Tempel.setExit("north", Triumphbogen);

        Basilica_Sempronia.setExit("east", Castor_Tempel);
        Basilica_Sempronia.setExit("northwest", Templum_Saturni);
        Basilica_Sempronia.setExit("northeast", Triumphbogen);
        Basilica_Sempronia.setExit("north", Templum_Concordia);

        Templum_Saturni.setExit("southeast", Basilica_Sempronia);
        Templum_Saturni.setExit("north", Templum_Concordia);
        Templum_Saturni.setExit("northeast", Vor_der_Curia);
        Templum_Saturni.setExit("east", Triumphbogen);

        Templum_Concordia.setExit("south", Templum_Saturni);
        Templum_Concordia.setExit("northeast", Vor_der_Curia);
        Templum_Concordia.setExit("southeast", Triumphbogen);

        Vor_der_Curia.setExit("southwest", Templum_Concordia);
        Vor_der_Curia.setExit("south", Triumphbogen);
        Vor_der_Curia.setExit("southeast", Basilica_Amilia);
        Vor_der_Curia.setExit("north", Curia);

        Curia.setExit("south", Vor_der_Curia);

        Basilica_Amilia.setExit("northwest", Vor_der_Curia);
        Basilica_Amilia.setExit("southwest", Triumphbogen);
        Basilica_Amilia.setExit("south", Regia);

        Triumphbogen.setExit("north", Vor_der_Curia);
        Triumphbogen.setExit("west", Templum_Saturni);
        Triumphbogen.setExit("northwest", Templum_Concordia);
        Triumphbogen.setExit("southwest", Basilica_Sempronia);
        Triumphbogen.setExit("south", Castor_Tempel);
        Triumphbogen.setExit("southeast", Aedes_Vestae);
        Triumphbogen.setExit("east", Regia);
        Triumphbogen.setExit("northeast", Basilica_Amilia);

        currentRoom = Regia; // start game outside
    }

    private void createItems(){
        Carti = new Item("Carti");
        Triumphbogen.setItem(Carti);
        Gladius_Brevis = new Item("Gladius Brevis");
        Castor_Tempel.setItem(Gladius_Brevis);
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Wilkommen auf dem Forum Romanum!");
        System.out.println("finde deinen amicus");
        System.out.println("wenn du Hilfe benötigst drücke help");
        System.out.println();
        System.out.println("Du befindest dich " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if (currentRoom.getExit("north") != null)
            System.out.print("north ");
        if (currentRoom.getExit("east") != null)
            System.out.print("east ");
        if (currentRoom.getExit("south") != null)
            System.out.print("south ");
        if (currentRoom.getExit("west") != null)
            System.out.print("west ");
        if (currentRoom.getExit("southwest") != null)
            System.out.print("southwest ");
        if (currentRoom.getExit("northwest") != null)
            System.out.print("northwest ");
        if (currentRoom.getExit("southeast") != null)
            System.out.print("southeast ");
        if (currentRoom.getExit("northeast") != null)
            System.out.print("northeast ");
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("HÄÄÄÄÄÄÄÄÄÄÄ");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("look"))
            wantToQuit = look();
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if (commandWord.equals("inventory")) {
            printInventory();
        }
        if (currentRoom == Curia) {
            System.out.print("Du hast die Curia unbefugt betreten und wirst nun in den Kerker gesteckt!");
            return true;
        }

        return wantToQuit;
    }

    private void printInventory() {
        String output = " ";
        for (int i = 0; i < inventory.size(); i++) {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("du hast bei dir:");
        System.out.print(output);
    }

    // implementations of user commands:
    private boolean look() {
        if (currentRoom == amicus.getLocation()) {
            System.out.println("du hast deinen Amicus gefunden, du kannst nun das Forum verlassen!");
            return true;
        } else {
            printLocationInfo();
            return false;
        }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /**
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.

        Room nextRoom = currentRoom.getExit(direction);
        System.out.println("direction: " + direction);
        System.out.println("currentRoom.getExits(): ");
        currentRoom.printExits();

        if (nextRoom == null)
            System.out.println("hier geht es nicht entlang!!!");
        else {
            currentRoom = nextRoom;
            System.out.println();
            System.out.println("Du befindest dich " + currentRoom.getDescription());
            System.out.println("Exits: ");
            if (currentRoom.getExit("north") != null)
                System.out.print("north ");
            if (currentRoom.getExit("east") != null)
                System.out.print("east ");
            if (currentRoom.getExit("south") != null)
                System.out.print("south ");
            if (currentRoom.getExit("west") != null)
                System.out.print("west ");
            if (currentRoom.getExit("southwest") != null)
                System.out.print("southwest ");
            if (currentRoom.getExit("northwest") != null)
                System.out.print("northwest ");
            if (currentRoom.getExit("southeast") != null)
                System.out.print("southeast ");
            if (currentRoom.getExit("northeast") != null)
                System.out.print("northeast ");
            System.out.println();
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else
            return true; // signal that we want to quit
    }

    private void printLocationInfo() {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if (currentRoom.getExit("north") != null) {
            System.out.print("north ");
        }
        if (currentRoom.getExit("east") != null) {
            System.out.print("east ");
        }
        if (currentRoom.getExit("south") != null) {
            System.out.print("south ");
        }
        if (currentRoom.getExit("west") != null) {
            System.out.print("west ");
        }
        System.out.println();
        System.out.print("Items: ");

        for (Item i : currentRoom.getItems()) {
            System.out.print(i.getDescription());
        }
        System.out.println();
    }

}
