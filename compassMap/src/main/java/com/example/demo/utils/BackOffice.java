package com.example.demo.utils;

import java.util.Scanner;

public class BackOffice {

    //static Scanner scan = new Scanner(System.in);

    // it is the entry-point for the backoffice class
    // and it has the main MENU, main options
    // and CRUD operation for our model and fake data
    public static void startBackOffice(PopulatorDB populatorDB) {
        // We will create just one scanner object
        // and it will pass to another methods

        Scanner scan = new Scanner(System.in);

        while (true) {
            mainMenu();

            String option = askMenuOption(scan);

            switch (option) {
                case "1":
                    customerLoop(scan, populatorDB);
                    break;
                case "2":
                    roadMapLoop(scan);
                    break;
                case "3":
                    System.out.println("Profile - not implemented yet.");
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void customerLoop(Scanner scan, PopulatorDB populatorDB) {
        boolean inCustomerMenu = true;
        while (inCustomerMenu) {
            customerMenu();
            String custOption = scan.nextLine();
            switch (custOption) {
                case "1":
                    System.out.print("How many customers? ");
                    int count = Integer.parseInt(scan.nextLine());
                    populatorDB.createAndSaveCustomer(count);
                    System.out.println(count + " customers created and saved.");
                    break;
                case "2":
                    System.out.print("Customer ID to delete: ");
                    String id = scan.nextLine();
                    populatorDB.customerService.deleteCustomer(id);
                    System.out.println("Customer " + id + " deleted.");
                    break;
                case "3":
                    populatorDB.customerService.deleteAllCustomers();
                    System.out.println("All customers deleted.");
                    break;
                case "4":
                    long total = populatorDB.customerService.countCustomers();
                    System.out.println("Total customers: " + total);
                    break;
                case "5":
                    inCustomerMenu = false;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void roadMapLoop(Scanner scan){
        // we should implement the option for roadMap entity
        // populate the roadMap table with synthetic data
        // and call the roadMap service to delete and count
        boolean inRoadMapMenu = true;
        while (inRoadMapMenu) {
            roadMapMenu();
            String roadOption = askMenuOption(scan);
            switch (roadOption) {
                case "1":
                    System.out.print("How many roadMaps? ");
                    int count = Integer.parseInt(scan.nextLine());
                    ///////populatorDB.createAndSaveCustomer(count);
                    System.out.println(count + " customers created and saved.");
                    break;
                case "2":
                    System.out.print("RoadMap ID to delete: ");
                    String id = scan.nextLine();
                    ///////populatorDB.roadMapService.deleteRoadMap(id);
                    System.out.println("RoadMap " + id + " deleted.");
                    break;
                case "3":
                    //////populatorDB.roadMapService.deleteAllRoadMaps();
                    System.out.println("All roadMaps deleted.");
                    break;
                case "4":
                    //////long total = populatorDB.roadMapService.countRoadMaps();
                    //////System.out.println("Total roadMaps: " + total);
                    break;
                case "5":
                    inRoadMapMenu = false;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void customerMenu() {
        System.out.println("\n===== CUSTOMER MENU =====");
        System.out.println("1. Create customers");
        System.out.println("2. Delete customer");
        System.out.println("3. Delete all customers");
        System.out.println("4. Count customers");
        System.out.println("5. Quit");
    }

    public static void roadMapMenu() {
        System.out.println("\n===== ROADMAP MENU =====");
        System.out.println("1. Create roadMaps");
        System.out.println("2. Delete roadMap");
        System.out.println("3. Delete all roadMaps");
        System.out.println("4. Count roadmaps");
        System.out.println("5. Quit");
    }

    public static void mainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Customer");
        System.out.println("2. Roadmap");
        System.out.println("3. Profile");
        System.out.println("4. Quit");

    }

    public static String askMenuOption(Scanner scan){
        System.out.print("Select an option: ");
        String option = scan.nextLine();
        return option;
    }


}
