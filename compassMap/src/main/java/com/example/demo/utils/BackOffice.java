package com.example.demo.utils;

import com.example.demo.model.AssessmentView;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerProfile;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionAndAnswer;
import com.example.demo.model.RoadMap;
import com.example.demo.service.CustomerService;

import java.util.List;
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
                    roadMapLoop(scan, populatorDB);
                    break;
                case "3":
                    customerProfileLoop(scan, populatorDB);
                    break;
                case "4":
                    questionLoop(scan, populatorDB);
                    break;
                case "5":
                    questionAndAnswerLoop(scan, populatorDB);
                    break;
                case "6":
                    assessmentViewLoop(scan, populatorDB);
                    break;
                case "7":
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
            String custOption = askMenuOption(scan);
            switch (custOption) {
                case "1":
                    System.out.print("How many customers? ");
                    int count = Integer.parseInt(scan.nextLine());
                    populatorDB.createAndSaveCustomer(count);
                    System.out.println(count + " customers created and saved.");
                    break;
                case "2":
                    Iterable<Customer> all = populatorDB.customerService.getAllCustomers();
                    int i = 0;
                    for (Customer c : all) {
                        System.out.print("#" + ++i + ": ");
                        System.out.println(c);
                    }
                    break;
                case "3":
                    System.out.print("Customer ID: ");
                    String getId = scan.nextLine();
                    Customer found = populatorDB.customerService.getCustomerById(getId);
                    System.out.println(found != null ? found : "Not found");
                    break;
                case "4":
                    System.out.print("Customer ID to delete: ");
                    String id = scan.nextLine();
                    populatorDB.customerService.deleteCustomer(id);
                    System.out.println("Customer " + id + " deleted.");
                    break;
                case "5":
                    populatorDB.customerService.deleteAllCustomers();
                    System.out.println("All customers deleted.");
                    break;
                case "6":
                    long total = populatorDB.customerService.countCustomers();
                    System.out.println("Total customers: " + total);
                    break;
                case "7":
                    inCustomerMenu = false;
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void roadMapLoop(Scanner scan, PopulatorDB populatorDB){
        while(true){
            entityMenu("ROADMAP", "Load roadmaps from roadmaps.json");
            String option = askMenuOption(scan);
            try {
                switch (option){
                    // we should implement the option for roadMap entity
                    // populate the roadMap table with synthetic data
                    // and call the roadMap service to delete and count
                    case "1":
                        List<RoadMap> roadMaps = populatorDB.loadAndSaveRoadMaps();
                        System.out.println(roadMaps.size() + " roadmaps loaded and saved.");
                        break;
                    case "2":
                        printAll(populatorDB.roadMapService.getAllRoadMaps());
                        break;
                    case "3":
                        RoadMap found = populatorDB.roadMapService.getRoadMapById(askId(scan));
                        System.out.println(found != null ? found : "Not found");
                        break;
                    case "4":
                        String id = askId(scan);
                        populatorDB.roadMapService.deleteRoadMap(id);
                        System.out.println("RoadMap " + id + " deleted.");
                        break;
                    case "5":
                        populatorDB.roadMapService.deleteAllRoadMaps();
                        System.out.println("All roadmaps deleted.");
                        break;
                    case "6":
                        System.out.println("Total roadmaps: " + populatorDB.roadMapService.countRoadMaps());
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void customerProfileLoop(Scanner scan, PopulatorDB populatorDB){
        while(true){
            entityMenu("CUSTOMER PROFILE", "Load profiles from profiles.json (+ assign roadmaps)");
            System.out.println("8. Show recommended roadmaps of a profile");
            String option = askMenuOption(scan);
            try {
                switch (option){
                    case "1":
                        List<CustomerProfile> profiles = populatorDB.loadAndSaveCustomerProfiles();
                        System.out.println(profiles.size() + " profiles loaded and saved.");
                        break;
                    case "2":
                        printAll(populatorDB.customerProfileService.getAllCustomerProfiles());
                        break;
                    case "3":
                        CustomerProfile found = populatorDB.customerProfileService.getCustomerProfileById(askId(scan));
                        System.out.println(found != null ? found : "Not found");
                        break;
                    case "4":
                        String id = askId(scan);
                        populatorDB.customerProfileService.deleteCustomerProfile(id);
                        System.out.println("CustomerProfile " + id + " deleted.");
                        break;
                    case "5":
                        populatorDB.customerProfileService.deleteAllCustomerProfiles();
                        System.out.println("All customer profiles deleted.");
                        break;
                    case "6":
                        System.out.println("Total customer profiles: " + populatorDB.customerProfileService.countCustomerProfiles());
                        break;
                    case "7":
                        return;
                    case "8":
                        CustomerProfile profile = populatorDB.customerProfileService.getCustomerProfileById(askId(scan));
                        if (profile == null) {
                            System.out.println("Not found");
                        } else {
                            System.out.println(profile);
                            printAll(profile.getRecommendedRoadmaps());
                        }
                        break;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void questionLoop(Scanner scan, PopulatorDB populatorDB){
        while(true){
            entityMenu("QUESTION", "Create questions");
            String option = askMenuOption(scan);
            try {
                switch (option){
                    case "1":
                        System.out.print("How many questions? ");
                        int count = Integer.parseInt(scan.nextLine());
                        populatorDB.createAndSaveQuestions(count);
                        System.out.println(count + " questions created and saved.");
                        break;
                    case "2":
                        printAll(populatorDB.questionService.getAllQuestions());
                        break;
                    case "3":
                        Question found = populatorDB.questionService.getQuestionById(askId(scan));
                        System.out.println(found != null ? found : "Not found");
                        break;
                    case "4":
                        String id = askId(scan);
                        populatorDB.questionService.deleteQuestion(id);
                        System.out.println("Question " + id + " deleted.");
                        break;
                    case "5":
                        populatorDB.questionService.deleteAllQuestions();
                        System.out.println("All questions deleted.");
                        break;
                    case "6":
                        System.out.println("Total questions: " + populatorDB.questionService.countQuestions());
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void questionAndAnswerLoop(Scanner scan, PopulatorDB populatorDB){
        while(true){
            entityMenu("QUESTION AND ANSWER", "Create one random answer per existing question");
            String option = askMenuOption(scan);
            try {
                switch (option){
                    case "1":
                        List<QuestionAndAnswer> created = populatorDB.createAndSaveQuestionAndAnswers();
                        System.out.println(created.size() + " question and answers created and saved.");
                        break;
                    case "2":
                        printAll(populatorDB.questionAndAnswerService.getAllQuestionAndAnswers());
                        break;
                    case "3":
                        QuestionAndAnswer found = populatorDB.questionAndAnswerService.getQuestionAndAnswerById(askId(scan));
                        System.out.println(found != null ? found : "Not found");
                        break;
                    case "4":
                        String id = askId(scan);
                        populatorDB.questionAndAnswerService.deleteQuestionAndAnswer(id);
                        System.out.println("QuestionAndAnswer " + id + " deleted.");
                        break;
                    case "5":
                        populatorDB.questionAndAnswerService.deleteAllQuestionAndAnswers();
                        System.out.println("All question and answers deleted.");
                        break;
                    case "6":
                        System.out.println("Total question and answers: " + populatorDB.questionAndAnswerService.countQuestionAndAnswers());
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void assessmentViewLoop(Scanner scan, PopulatorDB populatorDB){
        while(true){
            entityMenu("ASSESSMENT VIEW", "Create one sample assessment (random profile)");
            String option = askMenuOption(scan);
            try {
                switch (option){
                    case "1":
                        AssessmentView created = populatorDB.createAndSaveAssessmentView();
                        System.out.println("Assessment created: " + created);
                        break;
                    case "2":
                        printAll(populatorDB.assessmentViewRepository.findAll());
                        break;
                    case "3":
                        AssessmentView found = populatorDB.assessmentViewRepository.findById(askId(scan)).orElse(null);
                        System.out.println(found != null ? found : "Not found");
                        break;
                    case "4":
                        String id = askId(scan);
                        populatorDB.assessmentViewRepository.deleteById(id);
                        System.out.println("AssessmentView " + id + " deleted.");
                        break;
                    case "5":
                        populatorDB.assessmentViewRepository.deleteAll();
                        System.out.println("All assessments deleted.");
                        break;
                    case "6":
                        System.out.println("Total assessments: " + populatorDB.assessmentViewRepository.count());
                        break;
                    case "7":
                        return;
                    default:
                        System.out.println("Invalid option, try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // same 7 CRUD options for every entity, only the title and the populate option change
    public static void entityMenu(String entity, String populateOption) {
        System.out.println("\n===== " + entity + " MENU =====");
        System.out.println("1. " + populateOption);
        System.out.println("2. Get all");
        System.out.println("3. Get by Id");
        System.out.println("4. Delete by Id");
        System.out.println("5. Delete all");
        System.out.println("6. Count");
        System.out.println("7. Back");
    }

    public static <T> void printAll(Iterable<T> items) {
        int i = 0;
        for (T item : items) {
            System.out.print("#" + ++i + ": ");
            System.out.println(item);
        }
        if (i == 0) {
            System.out.println("(empty)");
        }
    }

    public static String askId(Scanner scan) {
        System.out.print("Id: ");
        return scan.nextLine();
    }

    public static void customerMenu() {
        System.out.println("\n===== CUSTOMER MENU =====");
        System.out.println("1. Create customers");
        System.out.println("2. Get all customers");
        System.out.println("3. Get customer by Id");
        System.out.println("4. Delete customer");
        System.out.println("5. Delete all customers");
        System.out.println("6. Count customers");
        System.out.println("7. Quit");
    }

    public static void mainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Customer");
        System.out.println("2. Roadmap");
        System.out.println("3. Customer Profile");
        System.out.println("4. Question");
        System.out.println("5. Question and Answer");
        System.out.println("6. Assessment View");
        System.out.println("7. Quit");

    }

    public static String askMenuOption(Scanner scan){
        System.out.print("Select an option: ");
        String option = scan.nextLine();
        return option;
    }


}
