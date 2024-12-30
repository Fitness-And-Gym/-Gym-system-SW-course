package triple.com;

import java.util.Scanner;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;

//PS C:\Users\dell\OneDrive\Desktop\software\Fitness-Project-SE-course\src\main\java> javac triple/com/*.java
//PS C:\Users\dell\OneDrive\Desktop\software\Fitness-Project-SE-course\src\main\java> java triple.com.Main
public class Main {
    static final String enterUserName = "Enter username: ";
    static final String lineSeparator = "===================================================================================";
    static final String goBack = "<--Go back (enter 0)";
    static final String adminCredentials = "admin";

    public static void main(String[] args) {
        // Initialize the DB
        DatabaseService.createMockData();
        DatabaseService.populateMockPrograms();
        DatabaseService.populateMockArticles();
        DatabaseService.populateMockCompletedPrograms();
        DatabaseService.populateMockFeedbacks();
        DatabaseService.populateMockPlans();

        System.out.println("\nWelcome to our Fitness Gym!");
        Scanner scanner = new Scanner(System.in);

        // Display login options
        System.out.println("Sign up(1) or Login(2):");
        int login = scanner.nextInt();
        if (login == 1) {
            System.out.println("Choose account type:");
            System.out.println("1 - User");
            System.out.println("2 - Instructor");
            System.out.print("Enter your choice (1/2): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    clientSignUp(scanner);
                    break;
                case 2:
                    instructorSignUp(scanner);
                    break;

                default:
                    System.out.println("Invalid choice. Please restart the program and try again.");
            }
        } else if (login == 2) {
            System.out.println("Please choose your login type:");
            System.out.println("1 - User");
            System.out.println("2 - Instructor");
            System.out.println("3 - Admin");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    clientLogin(scanner);
                    break;
                case 2:
                    instructorLogin(scanner);
                    break;
                case 3:
                    adminLogin(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please restart the program and try again.");
            }
        } else {
            System.out.println("Not valid option try again");
        }

        scanner.close();
    }

    private static void clientSignUp(Scanner scanner) {

        System.out.print(enterUserName);
        String username = scanner.next();

        System.out.print("Enter  password: ");
        String password = scanner.next();

        Client client = new Client(username, password);
        System.out.print("New account successful! \n Login to your account");
        clientLogin(scanner);

    }

    private static void clientLogin(Scanner scanner) {

        // System.out.print(enterUserName);
        // String username = scanner.next();

        // System.out.print("Enter password: ");
        // String password = scanner.next();

        Client client = DatabaseService.getClientByName("tom");
        // if (client.getPassword().equals(password)) {
        if (true) {
            System.out.println("Your login successful!");

            while (true) {
                System.out
                        .println(lineSeparator);
                System.out.println("Start your journey :");
                System.out.println("1 - change user Name");
                System.out.println("2 - Enroll in program");
                System.out.println("3 - Set Goal today");
                System.out.println("4 - update Goal Progress");
                System.out.println("5 - see my goals");
                System.out.println("6 - change Subscription");

                System.out.println(goBack);

                // almost every thing is ready
                // add benefit for a plan
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        clientOption1(scanner, client);
                        break;
                    case 2:
                        clientOption2(scanner, client);
                        break;
                    case 3:
                        clientOption3(scanner, client);
                        break;
                    case 4:
                        clientOption4(scanner, client);
                        break;
                    case 5:
                        clientOption5(scanner, client);
                        break;
                    case 6:
                        clientOption6(scanner, client);
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;

                    default:
                        main(null);
                        break;
                }
            }
        } else

        {
            System.out.println("Invalid client credentials. Please try again.");
        }

    }

    private static void instructorSignUp(Scanner scanner) {

        System.out.print(enterUserName);
        String username = scanner.next();

        System.out.print("Enter  password: ");
        String password = scanner.next();

        Instructor instructor = new Instructor(username, password);// automatically sends request to admin
        System.out.println(" Pending request sent to admin........");
        System.out.println(goBack);
        int goBack = scanner.nextInt();
        if (goBack == 0)
            main(null);
        return;
    }

    private static void instructorLogin(Scanner scanner) {

        System.out.print(enterUserName);
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        Instructor instructor = DatabaseService.getInstructorByName(username);
        if (instructor.getPassword().equals(password)) {
            System.out.println("Your Logged In");

            while (true) {
                System.out
                        .println(lineSeparator);
                System.out.println("Start your journey :");
                System.out.println("1 - create program");

                System.out.println(goBack);

                // almost every thing is ready
                // add benefit for a plan
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;

                    default:
                        main(null);
                        break;
                }
            }
        } else

        {
            System.out.println("Invalid client credentials. Please try again.");
        }

    }

    private static void adminLogin(Scanner scanner) {

        Admin admin = new Admin();

        System.out.print("Enter admin username: ");
        String username = scanner.next();

        System.out.print("Enter admin password: ");
        String password = scanner.next();

        admin.login(username, password);
        admin.login(adminCredentials, adminCredentials);

        if (admin.getLogin()) {
            System.out.println("Admin login successful!");

            while (true) {
                System.out
                        .println(lineSeparator);

                System.out.println("Please choose action :");
                System.out.println("1 - createClientAccount");
                System.out.println("2 - see the most popular programs");
                System.out.println("3 - updateClient");
                System.out.println("4 - deactivateClient");
                System.out.println("5 - check instructors requests box!");
                System.out.println("6 - createInstructorAccount");
                System.out.println("7 - TrackCompletedPrograms");
                System.out.println("8 - Track on going programs");
                System.out.println("9 - read Feed backs");
                System.out.println("10 - seePlansForClient");
                System.out.println("11 - seePlansForInstructors");
                System.out.println("12 - see instructors");
                System.out.println("13 - see  Instructors I declined");
                System.out.println("14 - getPendingArticle");
                System.out.println("15 - see clients");
                System.out.println("16 - see pending Articles");
                System.out.println("17 - see Articles");
                System.out.println("18 - create new plan for Instructors");
                System.out.println("19 - create new plan for clients");
                System.out.println(goBack);

                // almost every thing is ready
                // add benefit for a plan
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        adminOption1(scanner, admin);
                        break;
                    case 2:
                        adminOption2(admin);
                        break;
                    case 3:
                        adminOption3(scanner, admin);

                        break;
                    case 4:
                        adminOption4(scanner, admin);
                        break;
                    case 5:
                        adminOption5(scanner, admin);
                        break;
                    case 6:
                        adminOption6(scanner, admin);
                        break;
                    case 7:
                        adminOption7(admin);
                        break;
                    case 8:
                        adminOption8(admin);
                        break;
                    case 9:
                        adminOption9(admin);
                        break;
                    case 10:
                        adminOption10(admin);
                        break;
                    case 11:
                        adminOption11(admin);

                        break;
                    case 12:
                        adminOption12();

                        break;
                    case 13:
                        adminOption13();

                        break;
                    case 14:
                        adminOption14(scanner, admin);

                        break;
                    case 15:
                        adminOption15();
                        break;
                    case 16:
                        adminOption16();
                        break;
                    case 17:
                        adminOption17();
                        break;
                    case 18:
                        adminOption18(admin, false);
                        break;
                    case 19:
                        adminOption19(scanner, admin);
                        break;
                    default:
                        main(null);
                        break;
                }
            }

        } else {
            System.out.println("Invalid admin credentials. Please try again.");
        }
    }

    public static void adminOption1(Scanner scanner, Admin admin) {
        System.out.print("Enter Client username: ");
        String username = scanner.next();

        System.out.print("Enter Client default password: ");
        String password = scanner.next();
        Client client = admin.createClientAccount(username, password);
        if (client.getStatus() == "valid") {
            System.out.print("A new account for " + client.getClientName() + " is created");
        }
    }

    public static void adminOption2(Admin admin) {
        List<Program> programs = admin.seeStatistics();
        if (programs.isEmpty()) {
            System.out.println("No programs available.");
            return;
        }

        // Print table header
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s%n",
                "Program ID", "Title", "Instructor", "Enrollments", "Duration", "Fees");

        // Print a separator line
        System.out.println("-------------------------------------------------------------------------------");

        // Print each program's details
        for (Program program : programs) {
            System.out.printf("%-10s %-30s %-15s %-10d %-10d $%-9d%n",
                    program.getProgramId(),
                    program.getTitle(),
                    program.getInstructor().getName(),
                    program.getEnrollments(),
                    program.getDuration(),
                    program.fees);
        }
    }

    public static void adminOption3(Scanner scanner, Admin admin) {
        DatabaseService.printClientsTable();
        System.out.println("enter client Id to update");
        String clientId = scanner.next();
        System.out.println("New user Name:");
        String updatedName = scanner.next();
        System.out.println("New password");
        String updatedPassword = scanner.next();
        Client updatedClient = admin.updateClient(clientId, updatedName, updatedPassword);
        System.out.println("Client successfully updated !");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println(updatedClient.toString());
        System.out.println("-------------------------------------------------------------------------------");

    }

    public static void adminOption4(Scanner scanner, Admin admin) {
        DatabaseService.printClientsTable();
        System.out.println("Are you sure to deactivate client with Id=");
        String clientId = scanner.next();
        admin.deactivateClient(clientId);
        System.out.println("Client with Id =" + clientId + " deactivated ?");
    }

    public static void adminOption5(Scanner scanner, Admin admin) {
        Instructor instructor = admin.pullInstructorRequest();
        if (instructor != null) {
            int requestsNumber = DatabaseService.getAllRequests().size();
            System.out.println("You have " + requestsNumber + "pending requests = >");
            System.out.println("REQUEST: Instructor Id:" + instructor.getId() + "| Instructor Name:"
                    + instructor.getName() + " | Status" + instructor.getStatus());
            System.err.println("Accept (1) / Reject (0)");
            int accept = scanner.nextInt();
            if (accept == 1) {
                admin.acceptInstructor(instructor.getId());
                System.out.println(
                        "congratulations new instructor joined! \n remaining requests: " + (requestsNumber - 1));
            } else if (accept == 0) {
                admin.rejectInstructor(instructor.getId());
                System.out.println("declined one request remaining requests: " + (requestsNumber - 1));
            } else
                System.out.println("still pending requests=> " + (requestsNumber));

        }
    }

    public static void adminOption6(Scanner scanner, Admin admin) {

        System.out.print("Enter Instructor username: ");
        String username = scanner.next();

        System.out.print("Enter Instructor default password: ");
        String password = scanner.next();
        Instructor instructor = admin.createInstructorAccount(username, password);
        System.out.println("Successfully created Instructor account for : " + instructor.getName() + " with Id : "
                + instructor.getId());
    }

    public static void adminOption7(Admin admin) {
        admin.TrackCompletedPrograms();
    }

    public static void adminOption8(Admin admin) {
        admin.TrackActivePrograms();
    }

    public static void adminOption9(Admin admin) {
        admin.readFeedbacks();
    }

    public static void adminOption10(Admin admin) {
        admin.seePlansForClient();
    }

    public static void adminOption11(Admin admin) {
        admin.seePlansForInstructors();
    }

    public static void adminOption12() {
        DatabaseService.printInstructorsTable();
    }

    public static void adminOption13() {
        DatabaseService.printDeclinedInstructorsTable();
    }

    public static void adminOption14(Scanner scanner, Admin admin) {
        Article article = admin.getPendingArticle();
        if (article != null) {
            int articlesNumber = DatabaseService.getArticleRequests().size() + 1;// why the number of articles less by 1
            System.out.println("You have " + articlesNumber + "pending articles = >");
            System.out.println("Article: Article Id:" + article.getId() + "\n Author:"
                    + article.getAuthor().getName() + " \n creation date" + article.getSubmissionDate() + "\nTitle: "
                    + article.getTitle() + " (" + article.getType() + ")\n" + article.getContent());
            System.err.println("Approve (1) / Decline (0)");
            int approve = scanner.nextInt();
            if (approve == 1) {
                admin.acceptArticle(article.getId());
                System.out.println(
                        "Article publish successfully! \n pending articles: " + (articlesNumber - 1));
            } else if (approve == 0) {
                admin.rejectArticle(article.getId());
                System.out.println("declined one article pending articles: " + (articlesNumber - 1));
            } else
                System.out.println("still pending articles=> " + (articlesNumber));
        } else
            System.out.println("No pending articles!");

    }

    public static void adminOption15() {
        DatabaseService.printClientsTable();

    }

    public static void adminOption16() {
        DatabaseService.printArticlesRequests();

    }

    public static void adminOption17() {
        DatabaseService.printArticles();

    }

    public static void adminOption18(Admin admin, boolean flagClient) {
        Scanner scanner = new Scanner(System.in);// here I created a new scanner because the passed one is not working
                                                 // properly
        System.out.println("Enter the plan name:");
        String name = scanner.nextLine();

        System.out.println("Enter the plan type (Paid, Free):");
        String type = scanner.nextLine();

        System.out.println("Enter the plan price:");
        double price = 0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price input. Please enter a valid number.");
            return;
        }

        System.out.println("Enter the basic benefit of the plan:");
        String basicBenefit = scanner.nextLine();

        if (flagClient) {
            admin.createClientPlan(name, type, price, basicBenefit);
        } else
            admin.createInstructorPlan(name, type, price, basicBenefit);

        System.out.println("Plan " + name + " created successfully!");
    }

    public static void adminOption19(Scanner scanner, Admin admin) {
        adminOption18(admin, true);// choose plan for client

    }

    public static void clientOption1(Scanner scanner, Client client) {

        System.out.print("Enter new username: ");
        String username = scanner.next();
        client.updateName(username);
        System.out.println("Updated userName successfully!");
        System.out.print(client.toString());

    }

    public static void clientOption2(Scanner scanner, Client client) {
        Admin admin = new Admin();
        admin.login(adminCredentials, adminCredentials);
        admin.TrackActivePrograms();
        System.out.print("Enter Program ID= ");
        String programId = scanner.next();
        client.enrollInProgram(programId);
        System.out.print(client.toString());

    }

    public static void clientOption3(Scanner scanner, Client client) {
        System.out.print("Enter Goal title: ");
        String title = scanner.next();

        System.out.print("Enter your current state value: ");
        double current = scanner.nextDouble();

        System.out.print("Enter your Target value: ");
        double target = scanner.nextDouble();

        client.setGoal(title, current, target);
        System.out.println("===YOUR GOALS=== ");
        client.displayGoalProgress();
    }

    public static void clientOption4(Scanner scanner, Client client) {
        client.displayGoalProgress();
        if (client.getGoals().size() != 0) {
            System.out.print("Enter Goal Id= ");
            int progressNumber = scanner.nextInt();

            System.out.print("Enter status update value: ");
            double updateValue = scanner.nextDouble();

            client.updateGoalProgress(progressNumber, updateValue);
            client.displayGoalProgress();
        } else
            System.out.println("set Goals first");

    }

    public static void clientOption5(Scanner scanner, Client client) {
        client.displayGoalProgress();
    }

    public static void clientOption6(Scanner scanner, Client client) {
        Admin admin = new Admin();
        admin.login(adminCredentials, adminCredentials);
        admin.seePlansForClient();

        System.out.print("Enter Plan number= ");
        int planNumber = scanner.nextInt();

        PlanClient plan = DatabaseService.getClientPlanByNumber(planNumber - 1);

        client.changeSubscription(plan);

        System.out.print(client.toString());
    }
}