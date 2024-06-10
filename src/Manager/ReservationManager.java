package Manager;

import Objects.Account;
import Objects.Room;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationManager {
    private static final String LOG_FILE = "room_log.txt";
    private static final String USER_FILE = "users.txt";
    private List<Room> rooms;
    private Account currentUser;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ReservationManager(Account account) {
        rooms = new ArrayList<>();
        currentUser = account;
        initializeRooms();
    }

    private void initializeRooms() {
        // Add rooms
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i));
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int roomNumber = Integer.parseInt(parts[0]);
                boolean isVacant = parts[1].equals("isVacant");
                String reservedBy = parts[2].equals("none") ? "none" : parts[2];
                String reservedUntil = parts[3].equals("none") ? "none" : parts[3];
                Room room = rooms.get(roomNumber - 1);
                room.setVacant(isVacant);
                room.setReservedBy(reservedBy);
                room.setReservedUntil(reservedUntil);
            }
        } catch (IOException e) {
            System.out.println("Error loading log file: " + e.getMessage());
        }
    }

    public void showMenu() {
        Scanner read = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n----------- Menu -----------");
                System.out.println("1. Reserve Room");
                System.out.println("2. Return Room");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = read.nextInt();
                read.nextLine();

                switch (choice) {
                    case 1 -> reserveRoom();
                    case 2 -> returnRoom();
                    case 3 -> {
                        saveRoom();
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Choose between 1 and 3 only.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid Input!");
                read.nextLine();
            }
        }
    }

    private void returnRoom() {
        Scanner read = new Scanner(System.in);

        System.out.println("\n----------- Your Reserved Rooms -----------");
        boolean hasReservedRooms = false;
        for (Room room : rooms) {
            if (room.getReservedBy() != null && room.getReservedBy().equals(currentUser.getFullName())) {
                System.out.println("Room " + room.getRoomNumber() + " reserved until " + room.getReservedUntil() + ".");
                hasReservedRooms = true;
            }
        }

        if (!hasReservedRooms) {
            System.out.println("You have no rooms reserved.");
            return;
        }

        System.out.print("Enter room number to return: ");
        int roomNumber = read.nextInt();
        read.nextLine(); // Consume newline

        if (roomNumber < 1 || roomNumber > rooms.size()) {
            System.out.println("Invalid room number.");
            return;
        }

        Room room = rooms.get(roomNumber - 1);
        if (room.getReservedBy() == null || !room.getReservedBy().equals(currentUser.getFullName())) {
            System.out.println("You have not reserved room " + roomNumber + ".");
            return;
        }

        room.setVacant(true);
        room.setReservedBy(null);
        room.setReservedUntil(null);
        System.out.println("Room " + roomNumber + " returned successfully.");
        read.nextLine();
    }

    private void reserveRoom() {
        Scanner read = new Scanner(System.in);

        System.out.println("\n----------- Room Availability -----------");
        for (Room room : rooms) {
            String availability = room.isVacant() ? "Available" : "Not available until " + room.getReservedUntil();
            System.out.println("Room " + room.getRoomNumber() + " is " + availability + ".");
        }

        System.out.print("\nEnter room number to reserve: ");
        try {
            int roomNumber = read.nextInt();
            read.nextLine(); // Consume newline

            if (roomNumber < 1 || roomNumber > rooms.size()) {
                System.out.println("Invalid room number.");
                return;
            }

            Room room = rooms.get(roomNumber - 1);
            if (!room.isVacant()) {
                System.out.println("Room " + roomNumber + " is already occupied until " + room.getReservedUntil() + ".");
                return;
            }

            System.out.print("Enter reservation start time (yyyy-MM-dd HH:mm): ");
            String reservedStart = read.nextLine();
            System.out.print("Enter reservation end time (yyyy-MM-dd HH:mm): ");
            String reservedUntil = read.nextLine();

            Date startDate = dateFormat.parse(reservedStart);
            Date endDate = dateFormat.parse(reservedUntil);

            if (startDate.after(endDate)) {
                System.out.println("End time must be after start time.");
                return;
            }

            room.setVacant(false);
            room.setReservedBy(currentUser.getFullName());
            room.setReservedUntil(reservedUntil);
            System.out.println("Room " + roomNumber + " reserved successfully from " + reservedStart + " to " + reservedUntil + ".");
        } catch (InputMismatchException ex) {
            System.out.println("Invalid room number. Please enter a valid number.");
            read.nextLine(); // Consume the invalid input
        } catch (Exception e) {
            System.out.println("Error parsing date. Please use the correct format.");
        }
    }

    private void saveRoom() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            for (Room room : rooms) {
                writer.write(room.getRoomNumber() + "," + (room.isVacant() ? "isVacant" : "notVacant") + "," +
                        (room.getReservedBy() == null ? "none" : room.getReservedBy()) + "," +
                        (room.getReservedUntil() == null ? "none" : room.getReservedUntil()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving log file: " + e.getMessage());
        }
    }
}
