import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App
 * -----------------------------------------
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how to manage room availability
 * using a centralized HashMap to ensure consistency and scalability.
 *
 * @author YourName
 * @version 3.1
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

// -------- Inventory Class --------
class RoomInventory {

    // Centralized storage
    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\n---- Current Room Inventory ----");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------- Main Class --------
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay App              ");
        System.out.println("======================================");
        System.out.println("Version: v3.1\n");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details with centralized availability
        System.out.println("---- Room Details & Availability ----\n");

        single.displayDetails();
        System.out.println("Available : " + inventory.getAvailability(single.getRoomType()));
        System.out.println("------------------------------------");

        doubleRoom.displayDetails();
        System.out.println("Available : " + inventory.getAvailability(doubleRoom.getRoomType()));
        System.out.println("------------------------------------");

        suite.displayDetails();
        System.out.println("Available : " + inventory.getAvailability(suite.getRoomType()));
        System.out.println("------------------------------------");

        // Display full inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay App!");
    }
}