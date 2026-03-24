import java.util.*;

/**
 * Book My Stay App
 * -----------------------------------------
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only access to centralized inventory
 * and filtering available rooms without modifying system state.
 *
 * @author YourName
 * @version 4.0
 */

// -------- Abstract Room Class --------
abstract class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

// -------- Room Types --------
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000.0);
    }

    public void displayDetails() {
        System.out.println("Room Type : " + getRoomType());
        System.out.println("Beds      : " + getBeds());
        System.out.println("Price     : ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500.0);
    }

    public void displayDetails() {
        System.out.println("Room Type : " + getRoomType());
        System.out.println("Beds      : " + getBeds());
        System.out.println("Price     : ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000.0);
    }

    public void displayDetails() {
        System.out.println("Room Type : " + getRoomType());
        System.out.println("Beds      : " + getBeds());
        System.out.println("Price     : ₹" + getPrice());
    }
}

// -------- Inventory (Read-Only Access) --------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // Example: not available
        inventory.put("Suite Room", 2);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Return full map (read-only usage)
    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

// -------- Search Service --------
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\n---- Available Rooms ----\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("------------------------------------");
            }
        }
    }
}

// -------- Main Class --------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay App              ");
        System.out.println("======================================");
        System.out.println("Version: v4.0\n");

        // Room objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform search (READ-ONLY)
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed. (No inventory was modified)");
    }
}