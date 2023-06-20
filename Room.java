import java.util.ArrayList;
import java.util.HashMap;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Room {
    public String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> ausstattung;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        ausstattung = new ArrayList<Item>();
    }

    /**
     * Define the exits of this room. Every direction either leads
     * to another room or is null (no exit there).
     */
    public void setExit(String direction, Room neighbor) {
        System.out.println("Exit added for room " + description + ": " + direction + " - " + neighbor);
        exits.put(direction, neighbor);

        if (getExit(direction) != null)
            System.out.println("Exit gefunden: " + direction);

    }

    public void setItem(Item i) {
        ausstattung.add(i);
    }

    /**
     * Return the description of the room (the one that was defined
     * in the constructor).
     */
    public String getDescription() {
        return description;
    }

    public Room getExit(String direction) {
        /**
         * System.out.println("Room: Key vorhanden: " + direction + " - " +
         * exits.containsKey( direction));
         * System.out.println("Room: Room für Key vorhanden: " + (exits.get(direction)
         * != null));
         */
        return exits.get(direction);
    }

    public void printExits() {
        System.out.println(exits.keySet());
    }

    public ArrayList<Item> getItems() {
        return this.ausstattung;
    }
}
