package triple.com;

import java.util.Scanner;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//PS C:\Users\dell\OneDrive\Desktop\software\Fitness-Project-SE-course> mvn compile
//PS C:\Users\dell\OneDrive\Desktop\software\Fitness-Project-SE-course> mvn exec:java
//mvn clean verify

public class Main {
    static final String enterUserName = "Enter username: ";
    static final String lineSeparator = "===================================================================================";
    static final String goBack = "<--Go back (enter 0)";
    static final String adminCredentials = "admin";
    static boolean Initialize = true;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (Initialize) {
            // Initialize the DB
            DatabaseService.createMockData();
            DatabaseService.populateMockPrograms();
            DatabaseService.populateMockArticles();
            DatabaseService.populateMockCompletedPrograms();
            DatabaseService.populateMockFeedbacks();
            DatabaseService.populateMockPlans();
            Initialize = false;
        }
        logger.info("\nWelcome to our Fitness Gym!");
        Scanner scanner = new Scanner(System.in);

        // Display login options
        try {
            logger.info("Sign up(1) or Login(2):");
            int login = scanner.nextInt();
            if (login == 1) {
                logger.info("Choose account type:");
                logger.info("1 - User");
                logger.info("2 - Instructor");
                logger.info("Enter your choice (1/2): ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        clientSignUp(scanner);
                        break;
                    case 2:
                        instructorSignUp(scanner);
                        break;

                    default:
                        logger.info("Invalid choice. Please restart the program and try again.");
                }
            } else if (login == 2) {
                logger.info("Please choose your login type:");
                logger.info("1 - User");
                logger.info("2 - Instructor");
                logger.info("3 - Admin");
                logger.info("Enter your choice (1/2/3): ");
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
                        logger.info("Invalid choice. Please restart the program and try again.");
                }
            } else {
                logger.info("Not valid option try again");
            }
        } catch (Exception e) {
            logger.info("Not valid option try again" + e.getMessage());

        }
        scanner.close();
    }

    private static void clientSignUp(Scanner scanner) {

        logger.info(enterUserName);
        String username = scanner.next();

        logger.info("Enter  password: ");
        String password = scanner.next();

        Client client = new Client(username, password);
        logger.info("New account successful! \n Login to your account");
        clientLogin(scanner);

    }

    private static void clientLogin(Scanner scanner) {

        logger.info(enterUserName);
        String username = scanner.next();

        logger.info("Enter password: ");
        String password = scanner.next();

        Client client = DatabaseService.getClientByName(username);
        if (client != null && client.getPassword().equalsIgnoreCase(password)) {
            boolean exit = true;
            logger.info("Your login successful!");
            DatabaseService.sendMockMessages(client);
            while (exit) {
                logger.info(lineSeparator);
                logger.info("Start your journey:");
                logger.info("1 - Update Your Username");
                logger.info("2 - Enroll in a New Program");
                logger.info("3 - Set Month's Goal");
                logger.info("4 - Update Goal Progress");
                logger.info("5 - View My Goals");
                logger.info("6 - Modify Subscription Plan");
                logger.info("7 - Check Your Inbox");
                logger.info("8 - Chat with an Instructor");
                logger.info("9 - Update Dietary Preferences");
                logger.info("10 - Share Your Feedback");
                logger.info("11 - Search Programs by Difficulty or Goal");
                logger.info("12 - Explore My Feeds");

                // write feedback about an instructor
                logger.info(goBack);

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
                        clientOption8(scanner, client);
                        break;
                    case 9:
                        clientOption9(scanner, client);
                        break;
                    case 10:
                        clientOption10(scanner, client);
                        break;
                    case 11:
                        clientOption11(scanner, client);
                        break;
                    case 12:
                        clientOption12(scanner, client);
                        break;
                    default:
                        exit = false;
                        break;
                }

            }
        } else

        {
            logger.info("Invalid client credentials. Please try again.");
        }
        main(null);

    }

    private static void instructorSignUp(Scanner scanner) {

        logger.info(enterUserName);
        String username = scanner.next();

        logger.info("Enter  password: ");
        String password = scanner.next();

        Instructor instructor = new Instructor(username, password);// automatically sends request to admin
        logger.info(" Pending request sent to admin........");
        logger.info(goBack);
        int goBack = scanner.nextInt();
        if (goBack == 0)
            main(null);
        return;
    }

    private static void instructorLogin(Scanner scanner) {

        // For testing->
        // userName:ins
        // Password:123

        logger.info(enterUserName);
        String username = scanner.next();

        logger.info("Enter password: ");
        String password = scanner.next();

        Instructor instructor = DatabaseService.getInstructorByName(username);

        if (instructor != null && instructor.getPassword().equals(password)) {
            instructor.login(username, password);

            boolean exit = true;
            DatabaseService.sendMockMessages(instructor);
            logger.info("Your Logged In");
            while (exit) {
                logger.info(lineSeparator);
                logger.info("Begin your professional journey:");
                logger.info("1 - Create a New Program for Clients");
                logger.info("2 - Access Your Inbox");
                logger.info("3 - Communicate with Clients");
                logger.info("4 - View and Manage My Programs");// ins 123
                logger.info("5 - Compose and Publish Articles, Tips, or Recipes");
                logger.info("6 - Modify Subscription Details");
                logger.info("7 - Cancel Your Subscription Plan");
                logger.info("8 - Review My Published Articles");

                // logger.info("9 - ");

                logger.info(goBack);

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
                        instructorOption5(scanner, instructor);
                        break;
                    case 6:
                        instructorOption6(scanner, instructor);

                        break;
                    case 7:
                        instructorOption7(scanner, instructor);

                        break;
                    case 8:
                        instructorOption8(instructor);

                        break;

                    default:
                        exit = false;
                        break;
                }
            }

        } else {
            logger.info("Invalid client credentials. Please try again.");
        }
        main(null);

    }

    private static void adminLogin(Scanner scanner) {

        Admin admin = new Admin();

        logger.info("Enter admin username: ");
        String username = scanner.next();

        logger.info("Enter admin password: ");
        String password = scanner.next();
        logger.info(username + password);

        admin.login(username, password);

        if (admin.getLogin()) {
            boolean exit = true;
            logger.info("Admin login successful!");

            while (exit) {

                logger.info(lineSeparator);
                logger.info("Please choose an action:");
                logger.info("1 - Create a New Client Account");
                logger.info("2 - View Most Popular Programs");
                logger.info("3 - Update Client Information");
                logger.info("4 - Deactivate a Client Account");
                logger.info("5 - Check Instructor Requests");
                logger.info("6 - Create a New Instructor Account");
                logger.info("7 - Track Completed Programs");
                logger.info("8 - Monitor Ongoing Programs");
                logger.info("9 - Read Client Feedback");
                logger.info("10 - View Client-Specific Plans");
                logger.info("11 - Explore Instructor Plans");
                logger.info("12 - View All Instructors");
                logger.info("13 - Review Declined Instructor Applications");
                logger.info("14 - Retrieve Pending Articles");
                logger.info("15 - View All Clients");
                logger.info("16 - Review Pending Articles");
                logger.info("17 - Browse Published Articles");
                logger.info("18 - Design a New Plan for Instructors");
                logger.info("19 - Create a Custom Plan for Clients");
                logger.info("20 - Add Benefits to an Existing Plan");

                logger.info(goBack);

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
        } else {
            logger.info("Invalid admin credentials. Please try again.");
        }
        main(null);

    }

    public static void adminOption1(Scanner scanner, Admin admin) {
        logger.info("Enter Client username: ");
        String username = scanner.next();

        logger.info("Enter Client default password: ");
        String password = scanner.next();
        Client client = admin.createClientAccount(username, password);
        if (client.getStatus() == "valid") {
            logger.info("A new account for " + client.getClientName() + " is created");
        }
    }

    public static void adminOption2(Admin admin) {
        List<Program> programs = admin.seeStatistics();
        if (programs.isEmpty()) {
            logger.info("No programs available.");
            return;
        }

        // Print table header
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s%n",
                "Program ID", "Title", "Instructor", "Enrollments", "Duration", "Fees");

        // Print a separator line
        logger.info("-------------------------------------------------------------------------------");

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
        logger.info("enter client Id to update");
        String clientId = scanner.next();
        logger.info("New user Name:");
        String updatedName = scanner.next();
        logger.info("New password");
        String updatedPassword = scanner.next();
        Client updatedClient = admin.updateClient(clientId, updatedName, updatedPassword);
        logger.info("Client successfully updated !");
        logger.info("-------------------------------------------------------------------------------");
        logger.info(updatedClient.toString());
        logger.info("-------------------------------------------------------------------------------");

    }

    public static void adminOption4(Scanner scanner, Admin admin) {
        DatabaseService.printClientsTable();
        logger.info("Are you sure to deactivate client with Id=");
        String clientId = scanner.next();
        admin.deactivateClient(clientId);
        logger.info("Client with Id =" + clientId + " deactivated ?");
    }

    public static void adminOption5(Scanner scanner, Admin admin) {
        Instructor instructor = admin.pullInstructorRequest();
        if (instructor != null) {
            int requestsNumber = DatabaseService.getAllRequests().size();
            logger.info("You have " + requestsNumber + "pending requests = >");
            logger.info("REQUEST: Instructor Id:" + instructor.getId() + "| Instructor Name:"
                    + instructor.getName() + " | Status" + instructor.getStatus());
            System.err.println("Accept (1) / Reject (0)");
            int accept = scanner.nextInt();
            if (accept == 1) {
                admin.acceptInstructor(instructor.getId());
                logger.info(
                        "congratulations new instructor joined! \n remaining requests: " + (requestsNumber - 1));
            } else if (accept == 0) {
                admin.rejectInstructor(instructor.getId());
                logger.info("declined one request remaining requests: " + (requestsNumber - 1));
            } else
                logger.info("still pending requests=> " + (requestsNumber));

        }
    }

    public static void adminOption6(Scanner scanner, Admin admin) {

        logger.info("Enter Instructor username: ");
        String username = scanner.next();

        logger.info("Enter Instructor default password: ");
        String password = scanner.next();
        Instructor instructor = admin.createInstructorAccount(username, password);
        logger.info("Successfully created Instructor account for : " + instructor.getName() + " with Id : "
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

        logger.info("Enter the number of the plan you want to add a benefit to(-1 to go back):");
        int planNumber;

        try {
            planNumber = scanner.nextInt();
            if (planNumber == -1)
                return;
            PlanClient planClient = DatabaseService.getPlansClient().get(planNumber - 1);
            scanner.nextLine();
            addBenefit(admin, planClient);
            logger.info(planClient.getPlanDetails());

        } catch (Exception e) {
            logger.info("Invalid input. Please enter a valid plan number.");
        }

    }

    public static void adminOption11(Admin admin) {
        admin.seePlansForInstructors();
        Scanner scanner = new Scanner(System.in);

        logger.info("Enter the number of the plan you want to add a benefit to(-1 to go back):");
        int planNumber;

        try {
            planNumber = scanner.nextInt();
            if (planNumber == -1)
                return;
            PlanInstructor planInstructor = DatabaseService.getPlansInstructors().get(planNumber - 1);
            scanner.nextLine();
            addBenefit(admin, planInstructor);
            logger.info(planInstructor.getPlanDetails());
        } catch (Exception e) {
            logger.info("Invalid input. Please enter a valid plan number.");
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
            logger.info("You have " + articlesNumber + "pending articles = >");
            logger.info("Article: Article Id:" + article.getId() + "\n Author:"
                    + article.getAuthor().getName() + " \n creation date" + article.getSubmissionDate() + "\nTitle: "
                    + article.getTitle() + " (" + article.getType() + ")\n" + article.getContent());
            System.err.println("Approve (1) / Decline (0)");
            int approve = scanner.nextInt();
            if (approve == 1) {
                admin.acceptArticle(article.getId());
                logger.info(
                        "Article publish successfully! \n pending articles: " + (articlesNumber - 1));
            } else if (approve == 0) {
                admin.rejectArticle(article.getId());
                logger.info("declined one article pending articles: " + (articlesNumber - 1));
            } else
                logger.info("still pending articles=> " + (articlesNumber));
        } else
            logger.info("No pending articles!");

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
        logger.info("Enter the plan name:");
        String name = scanner.nextLine();

        logger.info("Enter the plan type (Paid, Free):");
        String type = scanner.nextLine();

        logger.info("Enter the plan price:");
        double price = 0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.info("Invalid price input. Please enter a valid number.");
            return;
        }

        logger.info("Enter the basic benefit of the plan:");
        String basicBenefit = scanner.nextLine();

        if (flagClient) {
            admin.createClientPlan(name, type, price, basicBenefit);
        } else
            admin.createInstructorPlan(name, type, price, basicBenefit);

        logger.info("Plan " + name + " created successfully!");
    }

    public static void adminOption19(Scanner scanner, Admin admin) {
        adminOption18(admin, true);// choose plan for client

    }

    public static void adminOption20(Scanner scanner, Admin admin) {
        logger.info("Plan For Client(1)/Instructor(2) :");
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

            logger.info("Enter benefit description (# to stop):");
            String benefit = scanner.nextLine();

            if (benefit.equals("#")) {
                shouldAddMore = false;
                continue;
            }

            plan.addBenefit(benefit);
        }
    }

    public static void clientOption1(Scanner scanner, Client client) {

        logger.info("Enter new username: ");
        String username = scanner.next();
        client.updateName(username);
        logger.info("Updated userName successfully!");
        logger.info(client.toString());

    }

    public static void clientOption2(Scanner scanner, Client client) {
        Admin admin = new Admin();
        admin.login(adminCredentials, adminCredentials);
        admin.TrackActivePrograms();
        logger.info("Enter Program ID= ");
        String programId = scanner.next();
        client.enrollInProgram(programId);
        logger.info(client.toString());

    }

    public static void clientOption3(Scanner scanner, Client client) {
        logger.info("Enter Goal title: ");
        String title = scanner.next();

        logger.info("Enter your current state value: ");
        double current = scanner.nextDouble();

        logger.info("Enter your Target value: ");
        double target = scanner.nextDouble();

        client.setGoal(title, current, target);
        logger.info("===YOUR GOALS=== ");
        client.displayGoalProgress();
    }

    public static void clientOption4(Scanner scanner, Client client) {
        client.displayGoalProgress();
        if (client.getGoals().size() != 0) {
            logger.info("Enter Goal Id= ");
            int progressNumber = scanner.nextInt();

            logger.info("Enter status update value: ");
            double updateValue = scanner.nextDouble();

            client.updateGoalProgress(progressNumber, updateValue);
            client.displayGoalProgress();
        } else
            logger.info("set Goals first");

    }

    public static void clientOption5(Scanner scanner, Client client) {
        client.displayGoalProgress();
    }

    public static void clientOption6(Scanner scanner, Client client) {
        Admin admin = new Admin();
        admin.login(adminCredentials, adminCredentials);
        admin.seePlansForClient();

        logger.info("Enter Plan number= ");
        int planNumber = scanner.nextInt();

        PlanClient plan = DatabaseService.getClientPlanByNumber(planNumber - 1);

        client.changeSubscription(plan);

        logger.info(client.toString());
    }

    public static void clientOption7(Scanner scanner, Client client) {

        client.viewMessages();
        logger.info("1-Reply to a message ");
        logger.info("2-Go back");
        int option = scanner.nextInt();

        if (option == 1) {
            logger.info("Message Number= ");
            int messageNumber = scanner.nextInt();
            if (messageNumber > 0) {
                Message message = client.getInbox().get(messageNumber - 1);
                Instructor instructor = DatabaseService.getInstructorById(message.getSender());
                logger.info("Replying to : " + instructor.getName());
                scanner.nextLine(); // Clear the buffer

                logger.info("Title:  ");
                String title = scanner.nextLine();

                logger.info("Content:  ");
                String content = scanner.nextLine();

                client.replyToMessage(message, content);
            }

        }
    }

    public static void clientOption8(Scanner scanner, Client client) {
        scanner.nextLine(); // Clear the buffer

        DatabaseService.printInstructorsTable();

        logger.info("Enter instructor Id to open conversation with: ");
        String instructorId = scanner.next();

        try {
            Instructor instructor = DatabaseService.getInstructorById(instructorId);
            if (instructor != null) {
                logger.info("Open conversation with " + instructor.getName() + " : ");

                scanner.nextLine(); // Clear the buffer
                logger.info("Title : ");
                String title = scanner.nextLine();

                logger.info("Content : ");
                String content = scanner.nextLine();

                client.sendMessage(instructorId.toUpperCase(), title, content);
            }

        } catch (Exception e) {
            logger.info("Enter Valid Id " + e.getMessage());
        }
    }

    public static void clientOption9(Scanner scanner, Client client) {
        scanner.nextLine(); // Clear the buffer
        boolean exit = true;
        try {
            while (exit) {

                client.printDietaryPreferences();

                logger.info("1- Add Dietary Preference: ");
                logger.info("2- Delete Dietary Preference: ");
                logger.info(goBack);

                int option = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                switch (option) {
                    case 1:
                        logger.info("Dietary Preference: ");
                        String dietaryPreference = scanner.nextLine();

                        client.addDietaryPreference(dietaryPreference);
                        break;
                    case 2:
                        logger.info("Dietary Preference: ");
                        dietaryPreference = scanner.nextLine();

                        client.deleteDietaryPreference(dietaryPreference);

                        break;

                    default:
                        exit = false;
                        break;
                }
            }
        } catch (Exception e) {
            logger.info("Sorry Can't set preference now" + e.getMessage());
        }
    }

    public static void clientOption10(Scanner scanner, Client client) {
        scanner.nextLine(); // Clear the buffer
        try {

            logger.info("Feedback Type(e.g., complaint, suggestion): ");
            String feedbackType = scanner.nextLine();

            logger.info("Content: ");
            String feedbackContent = scanner.nextLine();

            client.writeFeedback(feedbackType, feedbackContent);

        } catch (Exception e) {
            logger.info("Sorry Can't send feedback now" + e.getMessage());
        }

    }

    public static void clientOption11(Scanner scanner, Client client) {
        scanner.nextLine(); // Clear the buffer
        boolean exit = true;

        try {

            while (exit) {

                logger.info("1- Filter Programs By Difficulty");
                logger.info("2- Filter Programs By Goals ");
                logger.info(goBack);

                int option = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                switch (option) {
                    case 1:
                        logger.info("Search(eg. Beginner,Intermediate,Advanced) : ");
                        String searchText = scanner.nextLine();
                        ArrayList<Program> programs = client.filterProgramsByDifficulty(searchText);
                        client.printPrograms(programs);
                        break;

                    case 2:
                        logger.info("Search(eg. Lose weight) : ");
                        searchText = scanner.nextLine();
                        programs = client.filterProgramsByGoal(searchText);
                        client.printPrograms(programs);
                        break;

                    default:
                        exit = false;
                        break;
                }
            }
        } catch (Exception e) {
            logger.info("Sorry Can't search programs now " + e.getMessage());
        }

    }

    public static void clientOption12(Scanner scanner, Client client) {
        scanner.nextLine(); // Clear the buffer
        try {
            ArrayList<Feedback> feedbacks = client.getClientFeeds();
            for (Feedback feedback : feedbacks) {
                logger.info(feedback.printFeedback());
            }

        } catch (Exception e) {
            logger.info("Sorry Can't search programs now " + e.getMessage());
        }

    }

    // INSTRUCTOR OPTIONS
    public static void instructorOption1(Scanner scanner, Instructor instructor) {
        scanner.nextLine();// clear buffer

        logger.info("Enter Program Name: ");
        String title = scanner.nextLine();

        logger.info("Enter Program fees: ");
        int fees = scanner.nextInt();

        logger.info("Enter Program duration(in weeks): ");
        int duration = scanner.nextInt();

        scanner.nextLine();// clear buffer

        logger.info("Enter Program difficulty(Beginner, Intermediate, Advanced): ");
        String difficulty = scanner.nextLine();

        Program program = instructor.createProgram(fees, title, duration, difficulty);
        logger.info(program.generateReport());

    }

    public static void instructorOption2(Scanner scanner, Instructor instructor) {

        instructor.viewMessages();
        logger.info("1-Reply to a message ");
        logger.info("2-Go back");
        int option = scanner.nextInt();

        if (option == 1) {
            logger.info("Message Number= ");
            int messageNumber = scanner.nextInt();
            if (messageNumber > 0) {
                Message message = instructor.getInbox().get(messageNumber - 1);
                Client client = DatabaseService.getClientById(message.getSender());
                logger.info("Replying to : " + client.getClientName());
                scanner.nextLine(); // Clear the buffer

                logger.info("Title:  ");
                String title = scanner.nextLine();

                logger.info("Content:  ");
                String content = scanner.nextLine();

                instructor.replyToMessage(message, content);
            }

        }
    }

    public static void instructorOption3(Scanner scanner, Instructor instructor) {

        scanner.nextLine(); // Clear the buffer

        DatabaseService.printClientsTable();

        logger.info("Enter client Id to open conversation with: ");
        String clientId = scanner.next();

        try {
            Client client = DatabaseService.getClientById(clientId);
            if (client != null) {
                logger.info("Open conversation with " + client.getClientName() + " : ");

                scanner.nextLine(); // Clear the buffer

                logger.info("Enter Message title: ");
                String title = scanner.nextLine();

                logger.info("Enter Message content: ");
                String content = scanner.nextLine();

                instructor.sendMessage(clientId.toUpperCase(), title, content);
            }

        } catch (Exception e) {
            logger.info("Enter Valid Id " + e.getMessage());
        }
    }

    public static void instructorOption4(Scanner scanner, Instructor instructor) {

        boolean exit = true;
        while (exit) {
            instructor.viewPrograms();

            logger.info("Details of Program Number(0 go back)= ");
            int option = scanner.nextInt();
            scanner.nextLine();// clear buffer

            Program program;
            if (option > 0 && option <= instructor.getPrograms().size()) {

                try {
                    program = instructor.getPrograms().get(option - 1);
                    boolean shouldDelete = handleProgram(program);
                    try {
                        if (shouldDelete) {
                            instructor.deleteMyProgram(program.getProgramId());
                            logger.info("Deleting operation was successful");
                        }
                    } catch (Exception e) {
                        logger.info("Program does not exist to be deleted  " + e.getMessage());
                    }

                } catch (Exception e) {

                    logger.info("Program does not exist " + e.getMessage());
                }

            } else if (option == 0) {
                exit = false;// just for infinite while loop analysis
                return;
            }
        }

    }

    public static boolean handleProgram(Program program) {
        // used by instructorOption4
        Scanner scanner = new Scanner(System.in);
        while (program != null) {
            logger.info(program.generateReport());

            logger.info(program.getTitle() + "program :");
            logger.info("1 - Update Program Title");
            logger.info("2 - Update Program fees");
            logger.info("3 - Enter Attendance");// same as above
            logger.info("4 - Add New Goal To Program");
            logger.info("5 - delete Program");
            logger.info(goBack);

            int option = scanner.nextInt();
            scanner.nextLine();// clear buffer

            switch (option) {
                case 0:
                    program = null;
                    break;
                case 1:
                    logger.info("New Program Title: ");
                    String title = scanner.next();

                    program.updateTitle(title);
                    break;
                case 2:
                    logger.info("New Program fees: ");
                    int fees = scanner.nextInt();

                    program.updateFees(fees);
                    break;
                case 3:

                    logger.info("Enter attendance for one week (Y for present, N for absent):");

                    Map<Client, Boolean> attendance = new HashMap<>();

                    for (Client client : program.getEnrolledClients()) {
                        logger.info("Attendance for " + client.getClientName() + ": ");
                        String yesNo = scanner.next();

                        boolean attended;

                        if (yesNo.equalsIgnoreCase("y"))
                            attended = true;
                        else
                            attended = false;

                        attendance.put(client, attended);
                    }

                    program.insertAttendance(attendance);

                    logger.info("Attendance recorded successfully.");

                    logger.info("\nAttendance Records:");
                    for (Map.Entry<Client, List<Boolean>> entry : program.getAttendanceRecords().entrySet()) {
                        logger.info(entry.getKey().getClientName() + ": " + entry.getValue());
                    }

                    break;
                case 4:
                    logger.info("Enter Goal title: ");
                    title = scanner.next();

                    logger.info("Enter your current state value: ");
                    double current = scanner.nextDouble();

                    logger.info("Enter your Target value: ");
                    double target = scanner.nextDouble();

                    Progress progress = new Progress(title, current, target);
                    program.addProgramGoal(progress);
                    break;
                case 5:
                    return true;

                default:
                    return false;

            }

        }
        scanner.nextLine();// clear buffer

        return false;

    }

    public static void instructorOption5(Scanner scanner, Instructor instructor) {

        scanner.nextLine(); // Clear the buffer

        logger.info("Enter title: ");
        String title = scanner.nextLine();

        logger.info("1)ARTICLE    2)TIP   3)RECIPE");
        int option = scanner.nextInt();

        scanner.nextLine(); // Clear the buffer

        ArticleType type;

        switch (option) {
            case 1:
                type = ArticleType.ARTICLE;
                break;
            case 2:
                type = ArticleType.TIP;
                break;
            case 3:
                type = ArticleType.RECIPE;
                break;
            default:
                type = ArticleType.ARTICLE;
                break;
        }

        logger.info("Start Writing here-: ");
        String text = scanner.nextLine();

        Article article = instructor.writeArticle(title, text, type);

        logger.info(article.toString());

    }

    public static void instructorOption6(Scanner scanner, Instructor instructor) {

        Admin admin = new Admin();
        admin.login(adminCredentials, adminCredentials);
        admin.seePlansForInstructors();

        try {
            logger.info("Enter Plan number= ");
            int planNumber = scanner.nextInt();

            // we are here converte to instructor
            PlanInstructor plan = DatabaseService.getInstructorPlanByNumber(planNumber - 1);

            instructor.changeSubscription(plan);

            logger.info(instructor.toString());

        } catch (Exception e) {
            logger.info("couldn't change subscription try again later " + e.getMessage());
        }

    }

    public static void instructorOption7(Scanner scanner, Instructor instructor) {
        try {
            instructor.changeSubscription(DatabaseService.getBasicPlanInstructor());
        } catch (Exception e) {
            logger.info("couldn't cancel subscription try again later " + e.getMessage());
        }
        logger.info(instructor.toString());

    }

    public static void instructorOption8(Instructor instructor) {
        try {
            for (Article article : DatabaseService.getArticles()) {
                if (article.getAuthor().getId().equalsIgnoreCase(instructor.getId())) {
                    logger.info(article.toString());
                }
            }
        } catch (Exception e) {
            logger.info("No articles yet " + e.getMessage());
        }

    }

}