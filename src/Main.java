import java.util.*;

// Add-On Service class
class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String toString() {
        return name + " (₹" + cost + ")";
    }
}

// Manager class for handling services
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (AddOnService service : getServices(reservationId)) {
            total += service.getCost();
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation IDs (from UC6)
        String reservation1 = "RES-101";
        String reservation2 = "RES-102";

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800);

        // Guest selects services
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, spa);

        manager.addService(reservation2, airportPickup);
        manager.addService(reservation2, breakfast);

        // Display results
        System.out.println("\n--- Reservation 1 ---");
        manager.displayServices(reservation1);

        System.out.println("\n--- Reservation 2 ---");
        manager.displayServices(reservation2);
    }
}