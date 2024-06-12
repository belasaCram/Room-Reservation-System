package Main;

import Manager.LoginManager;

import java.util.Scanner;

public class RoomReservationMain {


    public static void main(String[] args) {
        RoomReservationMain main = new RoomReservationMain();
        main.start();
    }

    public void start(){
        Scanner read = new Scanner(System.in);
        LoginManager loginManager = new LoginManager();

        while (true) {
            System.out.println("\n----------- Main Menu -----------");
            System.out.println("1. Register\n2. Login\n3. Exit\n");
            System.out.print("Enter your choice: ");
            int choice = read.nextInt();
            read.nextLine(); // Consumes line

            switch (choice) {
                case 1 -> loginManager.registerUser();
                case 2 -> loginManager.loginUser();
                case 3 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}