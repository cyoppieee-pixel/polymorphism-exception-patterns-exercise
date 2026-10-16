import java.util.Scanner;

// menu
public class Main {
    static Scanner sc = new Scanner(System.in);
    static ParcelManager manager = ParcelManager.getInstance();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== PARCEL DELIVERY SYSTEM ===");
            System.out.println("1. Add Parcel");
            System.out.println("2. View All Parcels");
            System.out.println("3. Change Delivery Service");
            System.out.println("4. Mark as Delivered");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": addParcel(); break;
                case "2": viewParcels(); break;
                case "3": changeService(); break;
                case "4": markDelivered(); break;
                case "5":
                    System.out.println("Goodbye!");
                    exit = true;
                    break;
                default: System.out.println("Invalid choice! Please enter 1 to 5.");
            }
        }
    }

    // 1. Add Parcel
    static void addParcel() {
        int type = readInt("Parcel type (1-Document, 2-Box, 3-Fragile): ", 1, 3);

        // check format and duplicates
        String tracking = "";
        boolean valid = false;
        while (!valid) {
            tracking = readTracking();
            if (!tracking.matches("ABC\\d{4}")) {
                System.out.println("Invalid format! Use ABC followed by 4 digits (e.g. ABC0001).");
            } else if (manager.exists(tracking)) {
                System.out.println("Tracking Number already exists!");
            } else {
                valid = true;
            }
        }

        String receiver = readText("Receiver Name: ");

        Parcel parcel;
        if (type == 1) {
            double weight = readWeight("0.5");
            int pages = readInt("Number of Pages (1-100): ", 1, 100);
            parcel = new Document(tracking, receiver, weight, pages);
        } else {
            double weight = readWeight(type == 2 ? "50" : "30");
            int length = readPositiveInt("Length (cm): ");
            int width = readPositiveInt("Width (cm): ");
            int height = readPositiveInt("Height (cm): ");
            if (type == 2) {
                parcel = new Box(tracking, receiver, weight, length, width, height);
            } else {
                String note = readText("Handling Note: ");
                parcel = new Fragile(tracking, receiver, weight, length, width, height, note);
            }
        }

        boolean serviceSet = false;
        while (!serviceSet) {
            try {
                parcel.setService(chooseService());
                serviceSet = true;
            } catch (ParcelException e) {
                System.out.println(e.getMessage());
            }
        }

        manager.addParcel(parcel);
        System.out.printf("Parcel added successfully! Delivery fee: Php %.2f%n", parcel.getDeliveryFee());
    }

    // 2. View All Parcels
    static void viewParcels() {
        if (manager.getParcels().isEmpty()) {
            System.out.println("No parcels yet.");
            return;
        }
        String format = "%-10s %-10s %-15s %-12s %-10s %-14s %-10s%n";
        System.out.printf(format, "Tracking", "Type", "Receiver", "Chargeable", "Service", "Delivery Fee", "Status");
        for (Parcel p : manager.getParcels()) {
            System.out.printf(format,
                    p.getTrackingNumber(),
                    p.getType(),
                    p.getReceiverName(),
                    String.format("%.2f kg", p.getChargeableWeight()),
                    p.getService().getName(),
                    String.format("Php %.2f", p.getDeliveryFee()),
                    p.getStatus());
        }
    }

    // 3. Change Delivery Service
    static void changeService() {
        try {
            Parcel parcel = manager.findParcel(readTracking());
            if (parcel.getStatus().equals("Delivered")) {
                throw new ParcelException("Parcel already delivered!");
            }
            parcel.setService(chooseService());
            System.out.printf("Delivery service changed! New fee: Php %.2f%n", parcel.getDeliveryFee());
        } catch (ParcelException e) {
            System.out.println(e.getMessage());
        }
    }

    // 4. Mark as Delivered
    static void markDelivered() {
        try {
            Parcel parcel = manager.findParcel(readTracking());
            if (parcel.getStatus().equals("Delivered")) {
                throw new ParcelException("Parcel was already delivered.");
            }
            parcel.setStatus("Delivered");
            System.out.println("Parcel delivered successfully!");
        } catch (ParcelException e) {
            System.out.println(e.getMessage());
        }
    }

    static DeliveryService chooseService() {
        int choice = readInt("Delivery service (1-Standard, 2-Express, 3-Same Day): ", 1, 3);
        if (choice == 1) return new StandardDelivery();
        if (choice == 2) return new ExpressDelivery();
        return new SameDayDelivery();
    }

    static String readTracking() {
        return readText("Tracking Number (format ABC0000, e.g. ABC0001): ").toUpperCase();
    }

    static String readText(String prompt) {
        String text = "";
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            text = sc.nextLine().trim();
            if (!text.isEmpty()) {
                valid = true;
            } else {
                System.out.println("Input must not be empty!");
            }
        }
        return text;
    }

    static int readInt(String prompt, int min, int max) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            try {
                value = Integer.parseInt(readText(prompt));
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Please enter a number from " + min + " to " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number!");
            }
        }
        return value;
    }

    static int readPositiveInt(String prompt) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            try {
                value = Integer.parseInt(readText(prompt));
                if (value > 0) {
                    valid = true;
                } else {
                    System.out.println("Value must be greater than 0!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number!");
            }
        }
        return value;
    }

    static double readWeight(String max) {
        double weight = 0;
        boolean valid = false;
        while (!valid) {
            try {
                weight = Double.parseDouble(readText("Weight in kg (max " + max + "): "));
                if (weight > 0 && weight <= Double.parseDouble(max)) {
                    valid = true;
                } else {
                    System.out.println("Weight must be greater than 0 and at most " + max + " kg!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
        return weight;
    }
}