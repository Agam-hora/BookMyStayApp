import java.util.*;

/**
 * Book My Stay App
 * -----------------------------------------
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * Extends UC4 by introducing a queue-based
 * request intake mechanism without modifying inventory.
 *
 * @version 5.0
 */

// -------- Abstract Room Class (UNCHANGED - UC4) --------
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

// -------- Room Types (UNCHANGED) --------
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

// -------- Inventory (READ-ONLY - UC4) --------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

// -------- Search Service (UNCHANGED - UC4) --------
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\n---- Available Rooms ----\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available : " + available);
                System.out.println("------------------------------------");
            }
        }
    }
}

// ======================================================
// =============== NEW CODE (UC5 STARTS) =================
// ======================================================

// -------- Reservation Class --------
class Reservation {

    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }

    public void displayRequest() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Requested  : " + requestedRoomType);
        System.out.println("------------------------------------");
    }
}

// -------- Booking Request Queue (FIFO) --------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests
    public void displayAllRequests() {
        System.out.println("\n---- Booking Request Queue (FIFO) ----\n");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayRequest();
        }
    }

    // Peek next request (no removal)
    public Reservation peekNext() {
        return queue.peek();
    }
}

// -------- Main Class --------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("        Book My Stay App              ");
        System.out.println("======================================");
        System.out.println("Version: v5.0\n");

        // -------- UC4: Room Search --------
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed. (No inventory was modified)");

        // -------- UC5: Booking Request Queue --------
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        System.out.println("\n--- Adding Booking Requests ---\n");

        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Suite Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Double Room"));

        // Display queue
        requestQueue.displayAllRequests();

        // Peek next request
        System.out.println("\nNext Request to Process:");
        Reservation next = requestQueue.peekNext();

        if (next != null) {
            next.displayRequest();
        }
    }
}