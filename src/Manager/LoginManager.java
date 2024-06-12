package Manager;

import Objects.Account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginManager {
    private static final String USER_FILE = "users.txt";
    private List<Account> users;

    public LoginManager(){
        users = new ArrayList<>();
        initializeUser();
    }

    private void initializeUser() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String email = parts[0];
                String password = parts[1];
                String fullName = parts[2];
                String age = parts[3];
                String telephone = parts[4];

                users.add(new Account (fullName, Integer.parseInt(age),
                        telephone, email, password));
            }
        } catch (IOException e) {
            System.out.println("Error loading user file: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Account user : users) {

                String format  = user.getEmail() + "," + user.getPassword() +
                        "," + user.getFullName() + "," + user.getAge() +
                        "," + user.getPhoneNumber();

                writer.write(format);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user file: " + e.getMessage());
        }
    }

    public void registerUser() {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        System.out.println("\n-----------Register-----------");
        while(true){

            System.out.print("Full Name: ");
            String fullName = scanner.nextLine();
            System.out.print("Age: ");
            int age;
            while (true) {
                try {
                    age = Integer.parseInt(scanner.nextLine());
                    if (age <= 0) {
                        System.out.print("Please enter a valid age: ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number for age: ");
                }
            }

            System.out.print("Phone Number (+63): ");
            String telephone = scanner.nextLine();
            while (!telephone.matches("\\+63\\s?\\d{10}")) {
                System.out.print("Invalid telephone number format (ex: +6395535352333). Please enter a valid Philippine telephone number: ");
                telephone = scanner.nextLine();
            }

            System.out.print("Email: ");
            String email = scanner.nextLine();
            while (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Invalid email format. Please enter a valid email:");
                email = scanner.nextLine();
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Confirm the details
            System.out.println("\nPlease confirm your details:");
            System.out.println("Full Name: " + fullName);
            System.out.println("Age: " + age);
            System.out.println("Telephone: " + telephone);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.print("\nIs the information correct? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                users.add(new Account(fullName, age, telephone, email, password));
                saveUsers();
                System.out.println("Registration successful!");
                break;
            } else {
                System.out.println("Please re-enter your details.\n");
            }
        }
    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nLogin");

        while(true){
            System.out.print("Enter email: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            for (Account user : users) {
                if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
                    System.out.println("Login successful!");
                    ReservationManager rManager = new ReservationManager(user);
                    rManager.showMenu();
                    return;
                }
            }

            System.out.println("Invalid username or password.");
        }
    }
}
