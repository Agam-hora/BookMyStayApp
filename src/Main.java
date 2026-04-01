import java.util.*;

class UseCase6RoomAllocationService {

    // Queue for booking requests (FIFO)
    private Queue<String> requestQueue = new LinkedList<>();

    // Inventory: room type -> available count
    private Map<String, Integer> inventory = new HashMap<>();

    // Allocated rooms: room type -> set of room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness of room IDs
    private Set<String> allRoomIds = new HashSet<>();

    public static void main(String[] args) {
        UseCase6RoomAllocationService service = new UseCase6RoomAllocationService();
        service.initializeInventory();
        service.addRequests();
        service.processBookings();
    }

    // Initialize room inventory
    private void initializeInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    // Add sample booking requests
    private void addRequests() {
        requestQueue.add("Single");
        requestQueue.add("Double");
        requestQueue.add("Single");
        requestQueue.add("Suite");
        requestQueue.add("Suite"); // should fail (only 1 available)
    }

    // Process booking requests
    private void processBookings() {
        while (!requestQueue.isEmpty()) {
            String roomType = requestQueue.poll();

            System.out.println("\nProcessing request for: " + roomType);

            // Check availability
            if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
                System.out.println("❌ No rooms available for " + roomType);
                continue;
            }

            // Generate unique room ID
            String roomId = generateUniqueRoomId(roomType);

            // Allocate room (atomic logic)
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);
            allRoomIds.add(roomId);

            // Update inventory
            inventory.put(roomType, inventory.get(roomType) - 1);

            // Confirm reservation
            System.out.println("✅ Booking Confirmed!");
            System.out.println("Room Type: " + roomType);
            System.out.println("Room ID: " + roomId);
            System.out.println("Remaining " + roomType + ": " + inventory.get(roomType));
        }

        printSummary();
    }

    // Generate unique room ID
    private String generateUniqueRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
        } while (allRoomIds.contains(roomId));

        return roomId;
    }

    // Print final allocation summary
    private void printSummary() {
        System.out.println("\n📊 Allocation Summary:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}