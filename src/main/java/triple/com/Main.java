package triple.com;

import java.util.Scanner;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;

//PS C:\Users\dell\OneDrive\Desktop\software\Fitness-Project-SE-course> mvn compile
//PS C:\Users\dell\OneDrive\Desktop\software\Fitness-Project-SE-course> mvn exec:java
//mvn clean verify

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
        // if (client!=null&&client.getPassword().equals(password)) {
        if (true) {
            boolean exit = true;
            System.out.println("Your login successful!");
            DatabaseService.sendMockMessages(client);
            while (exit) {
                System.out.println(lineSeparator);
                System.out.println("Start your journey :");
                System.out.println("1 - change user Name");
                System.out.println("2 - Enroll in program");
                System.out.println("3 - Set Goal today");
                System.out.println("4 - update Goal Progress");
                System.out.println("5 - see my goals");
                System.out.println("6 - change Subscription");
                System.out.println("7 - Check inbox");
                System.out.println("8 - Read published articles");
                System.out.println("9 - Add Dietary Preference");
                System.out.println("10- Delete Dietary Preference");
                System.out.println("11 - Write Feedback");
                System.out.println("12 - Filter Programs By Difficulty");
                System.out.println("13 - Filter Programs ByGoal");
                System.out.println("14 -  View my Feeds");
                System.out.println("15 -  Open conversation with Instructor");

                // write feedback about an instructor
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
                        clientOption7(scanner, client);

                        break;
                    case 8:
                        break;
                    case 15:
                        clientOption15(scanner, client);
                        break;

                    default:
                        exit = false;
                        break;
                }

            }
            main(null);
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

        // For testing->
        // userName:ins
        // Password:123

        System.out.print(enterUserName);
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        Instructor instructor = DatabaseService.getInstructorByName(username);

        if (instructor != null && instructor.getPassword().equals(password)) {
            instructor.login(username, password);

            boolean exit = true;
            DatabaseService.sendMockMessages(instructor);
            System.out.println("Your Logged In");
            while (exit) {
                System.out.println(lineSeparator);
                System.out.println("Start your journey :");
                System.out.println("1 - create program");
                System.out.println("2 - Check inbox");
                System.out.println("3 - Send messages to clients");
                System.out.println("4 - My Programs");
                System.out.println("5 - Enter attendance");
                System.out.println("6 - Write and Publish  Article ,Tip ,Recipe");
                System.out.println("7 - Change Subscription");
                System.out.println("8 - Cancel Subscription ");
                // System.out.println("9 - ");

                System.out.println(goBack);

                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        instructorOption1(scanner, instructor);

                        break;
                    case 2:
                        instructorOption2(scanner, instructor);
                        break;
                    case 3:
                        instructorOption3(scanner, instructor);
                        break;
                    case 4:
                        instructorOption4(scanner, instructor);

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
                        exit = false;
                        break;
                }
            }

        } else {
            System.out.println("Invalid client credentials. Please try again.");
        }
        main(null);

    }

    private static void adminLogin(Scanner scanner) {

        Admin admin = new Admin();

        System.out.print("Enter admin username: ");
        String username = scanner.next();

        System.out.print("Enter admin password: ");
        String password = scanner.next();
        System.out.println(username + password);

        admin.login(username, password);

        if (admin.getLogin()) {
            boolean exit = true;
            System.out.println("Admin login successful!");

            while (exit) {

                System.out.println(lineSeparator);
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
                System.out.println("20 - Add benefit for a plan");

                System.out.println(goBack);

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
                    case 20:
                        adminOption20(scanner, admin);
                        break;
                    default:
                        exit = false;
                        break;
                }
            }
            main(null);
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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of the plan you want to add a benefit to(-1 to go back):");
        int planNumber;

        try {
            planNumber = scanner.nextInt();
            if (planNumber == -1)
                return;
            PlanClient planClient = DatabaseService.getPlansClient().get(planNumber - 1);
            scanner.nextLine();
            addBenefit(admin, planClient);
            System.out.println(planClient.getPlanDetails());

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid plan number.");
        }

    }

    public static void adminOption11(Admin admin) {
        admin.seePlansForInstructors();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of the plan you want to add a benefit to(-1 to go back):");
        int planNumber;

        try {
            planNumber = scanner.nextInt();
            if (planNumber == -1)
                return;
            PlanInstructor planInstructor = DatabaseService.getPlansInstructors().get(planNumber - 1);
            scanner.nextLine();
            addBenefit(admin, planInstructor);
            System.out.println(planInstructor.getPlanDetails());
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid plan number.");
        }
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

    public static void adminOption20(Scanner scanner, Admin admin) {
        System.out.print("Plan For Client(1)/Instructor(2) :");
        Integer isClient = scanner.nextInt();
        scanner.nextLine();
        if (isClient == 1) {
            adminOption10(admin);
        } else {
            adminOption11(admin);
        }
    }

    public static void addBenefit(Admin admin, Plan plan) {
        Scanner scanner = new Scanner(System.in);
        Boolean shouldAddMore = true;
        while (shouldAddMore) {

            System.out.print("Enter benefit description (# to stop):");
            String benefit = scanner.nextLine();

            if (benefit.equals("#")) {
                shouldAddMore = false;
                continue;
            }

            plan.addBenefit(benefit);
        }
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

    public static void clientOption7(Scanner scanner, Client client) {

        client.viewMessages();
        System.out.println("1-Reply to a message ");
        System.out.println("2-Go back");
        int option = scanner.nextInt();

        if (option == 1) {
            System.out.print("Message Number= ");
            int messageNumber = scanner.nextInt();
            if (messageNumber > 0) {
                Message message = client.getInbox().get(messageNumber - 1);
                Instructor instructor = DatabaseService.getInstructorById(message.getSender());
                System.out.println("Replying to : " + instructor.getName());
                scanner.nextLine(); // Clear the buffer

                System.out.print("Title:  ");
                String title = scanner.nextLine();

                System.out.print("Content:  ");
                String content = scanner.nextLine();

                client.replyToMessage(message, content);
            }

        }
    }

    public static void clientOption15(Scanner scanner, Client client) {
        scanner.nextLine(); // Clear the buffer

        DatabaseService.printInstructorsTable();

        System.out.print("Enter instructor Id to open conversation with: ");
        String instructorId = scanner.next();

        try {
            Instructor instructor = DatabaseService.getInstructorById(instructorId);
            if (instructor != null) {
                System.out.println("Open conversation with " + instructor.getName() + " : ");

                System.out.print("Enter Message title: ");
                String title = scanner.next();

                System.out.print("Enter Message content: ");
                String content = scanner.next();

                client.sendMessage(instructorId.toUpperCase(), title, content);
            }

        } catch (Exception e) {
            System.out.println("Enter Valid Id " + e.getMessage());
        }
    }

    // INSTRUCTOR OPTIONS
    public static void instructorOption1(Scanner scanner, Instructor instructor) {
        scanner.nextLine();// clear buffer

        System.out.println("Enter Program Name: ");
        String title = scanner.nextLine();

        System.out.println("Enter Program fees: ");
        int fees = scanner.nextInt();

        System.out.println("Enter Program duration(in weeks): ");
        int duration = scanner.nextInt();

        scanner.nextLine();// clear buffer

        System.out.println("Enter Program difficulty(Beginner, Intermediate, Advanced): ");
        String difficulty = scanner.nextLine();

        Program program = instructor.createProgram(fees, title, duration, difficulty);
        System.out.println(program.generateReport());

    }

    public static void instructorOption2(Scanner scanner, Instructor instructor) {

        instructor.viewMessages();
        System.out.println("1-Reply to a message ");
        System.out.println("2-Go back");
        int option = scanner.nextInt();

        if (option == 1) {
            System.out.print("Message Number= ");
            int messageNumber = scanner.nextInt();
            if (messageNumber > 0) {
                Message message = instructor.getInbox().get(messageNumber - 1);
                Client client = DatabaseService.getClientById(message.getSender());
                System.out.println("Replying to : " + client.getClientName());
                scanner.nextLine(); // Clear the buffer

                System.out.print("Title:  ");
                String title = scanner.nextLine();

                System.out.print("Content:  ");
                String content = scanner.nextLine();

                instructor.replyToMessage(message, content);
            }

        }
    }

    public static void instructorOption3(Scanner scanner, Instructor instructor) {

        scanner.nextLine(); // Clear the buffer

        DatabaseService.printClientsTable();

        System.out.print("Enter client Id to open conversation with: ");
        String clientId = scanner.next();

        try {
            Client client = DatabaseService.getClientById(clientId);
            if (client != null) {
                System.out.println("Open conversation with " + client.getClientName() + " : ");

                System.out.print("Enter Message title: ");
                String title = scanner.next();

                System.out.print("Enter Message content: ");
                String content = scanner.next();

                instructor.sendMessage(clientId.toUpperCase(), title, content);
            }

        } catch (Exception e) {
            System.out.println("Enter Valid Id " + e.getMessage());
        }
    }

    public static void instructorOption4(Scanner scanner, Instructor instructor) {
        instructor.viewPrograms();

        System.out.print("Details of Program Number= ");
        int option = scanner.nextInt();
        Program program;
        // get Program
        if (option > 0 && option < instructor.getPrograms().size()) {
            program = instructor.getPrograms().get(option - 1);
        } else {
            return;
        }

        System.out.println(program.getTitle() + "program :");
        System.out.print("1 - Update Program Title");
        System.out.print("2 - Update Program fees");
        System.out.print("3 - Enter Attendence");// same as above
        System.out.print("4 - Add addGoalToProgram");
        System.out.print("5 - delete Program");
        option = scanner.nextInt();

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

            default:
                break;
        }

    }

}