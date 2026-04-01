import java.util.*;

// Reservation class (from UC6 concept)
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public String toString() {
        return "ReservationID=" + reservationId +
                ", RoomType=" + roomType +
                ", RoomID=" + roomId;
    }
}

// Add-On Service (from UC7)
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return name + "(₹" + cost + ")";
    }
}

// Add-On Manager (UC7)
class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        servicesMap.putIfAbsent(reservationId, new ArrayList<>());
        servicesMap.get(reservationId).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return servicesMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double getTotalCost(String reservationId) {
        double total = 0;
        for (AddOnService s : getServices(reservationId)) {
            total += s.getCost();
        }
        return total;
    }
}

// Booking History (UC8)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r); // maintains order
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service (UC8)
class BookingReportService {

    public void generateReport(List<Reservation> history, AddOnServiceManager manager) {

        System.out.println("\n📊 BOOKING REPORT");
        System.out.println("===================================");

        for (Reservation r : history) {
            System.out.println(r);

            List<AddOnService> services = manager.getServices(r.getReservationId());

            if (!services.isEmpty()) {
                System.out.println("  Services: " + services);
                System.out.println("  Add-On Cost: ₹" + manager.getTotalCost(r.getReservationId()));
            } else {
                System.out.println("  Services: None");
            }

            System.out.println("-----------------------------------");
        }
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        // Simulating UC6 confirmed bookings
        Reservation r1 = new Reservation("RES-101", "Single", "S-1A");
        Reservation r2 = new Reservation("RES-102", "Double", "D-2B");
        Reservation r3 = new Reservation("RES-103", "Suite", "SU-1C");

        // Booking History (UC8)
        BookingHistory history = new BookingHistory();
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Add-On Services (UC7)
        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService("RES-101", new AddOnService("Breakfast", 500));
        manager.addService("RES-101", new AddOnService("Spa", 1500));

        manager.addService("RES-102", new AddOnService("Airport Pickup", 800));

        // Reporting (UC8)
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllReservations(), manager);
    }
}