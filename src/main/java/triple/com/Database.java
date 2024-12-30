package triple.com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Instructor> registrationRequests = new ArrayList<>();
    private static ArrayList<Instructor> declinedInstructors = new ArrayList<>();
    private static ArrayList<Article> articleRequests = new ArrayList<>();
    private static ArrayList<Program> programs = new ArrayList<>();
    private static ArrayList<Program> CompletedPrograms = new ArrayList<>();
    private static ArrayList<Instructor> instructors = new ArrayList<>();
    private static ArrayList<Article> articles = new ArrayList<>();
    private static ArrayList<Feedback> feeds = new ArrayList<>();
    private static ArrayList<PlanClient> plansClient = new ArrayList<>();
    private static ArrayList<PlanInstructor> plansInstructors = new ArrayList<>();
    private static PlanClient basicPlanClient = new PlanClient("Basic", "free", 0, "Limited access");
    private static PlanInstructor basicPlanInstructor = new PlanInstructor("Basic", "free", 0, "Limited access");

    public static void addArticleRequest(Article article) {
        articleRequests.add(article);
    }

    public static void addCompletedProgram(Program program) {
        CompletedPrograms.add(program);
    }

    public static void removeProgram(Program program) {
        programs.remove(program);
    }

    public static void removeArticleRequest(Article article) {
        articleRequests.remove(article);
    }

    public static Article removeArticleRequest() {
        return articleRequests.remove(0);
    }

    public static Article removeArticle() {
        Article oldestRequest = articleRequests.remove(0);
        return oldestRequest;
    }

    public static PlanClient getBasicPlanClient() {
        return basicPlanClient;
    }

    public static PlanInstructor getBasicPlanInstructor() {
        return basicPlanInstructor;
    }

    public static ArrayList<Article> getArticleRequests() {
        return articleRequests;
    }

    public static ArrayList<Article> getArticles() {
        return articles;
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static ArrayList<Instructor> getDeclinedInstructors() {
        return declinedInstructors;
    }

    public static ArrayList<Instructor> getRegistrationRequests() {
        return registrationRequests;
    }

    public static ArrayList<Program> getCompletedPrograms() {
        return CompletedPrograms;
    }

    public static PlanClient getClientPlanByNumber(int number) {
        return plansClient.get(number);
    }

    public static ArrayList<Program> getPrograms() {
        return programs;
    }

    public static void addDeclinedInstructors(Instructor instructor) {
        if (!declinedInstructors.contains(instructor)) {
            declinedInstructors.add(instructor);
        }

    }

    public static void printInstructorsTable() {
        System.out.printf("%-10s %-20s %-15s %-10s %-15s %-10s%n",
                "ID", "Name", "Password", "Now active", "Status", "Plan");
        System.out.println("------------------------------------------------------------------------------------");

        for (Instructor instructor : instructors) {
            System.out.printf("%-10s %-20s %-15s %-10s %-15s %-10s%n",
                    instructor.getId(),
                    instructor.getName(),
                    instructor.getPassword(),
                    instructor.isLoggedIn(),
                    instructor.getStatus(),
                    instructor.getPlan().getName());
        }
    }

    public static void printDeclinedInstructorsTable() {
        System.out.printf("%-10s %-20s %-15s  %-15s %n",
                "ID", "Name", "Password", "Status");
        System.out.println("------------------------------------------------------------------------------------");

        for (Instructor instructor : declinedInstructors) {
            System.out.printf("%-10s %-20s %-15s  %-15s %n",
                    instructor.getId(),
                    instructor.getName(),
                    instructor.getPassword(),
                    instructor.getStatus());
        }
    }

    public static void deleteProgramById(String programId) {
        Program program = getProgramById(programId);
        programs.remove(program);
        System.out.println("program to delete issssss db by id" + program.getTitle());

    }

    public static void deleteProgram(Program program) {
        if (programs.contains(program)) {
            programs.remove(program);
            System.out.println("Program removed successfully: " + program.getTitle());
        } else {
            System.out.println("Program not found in the list: " + program.getTitle());
        }

    }

    public static ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    public static ArrayList<PlanClient> getPlansClient() {
        return plansClient;
    }

    public static ArrayList<PlanInstructor> getPlansInstructors() {
        return plansInstructors;
    }

    public static void addPlanToClient(PlanClient plan) {
        if (!plansClient.contains(plan))
            plansClient.add(plan);
        System.out.println("Plan added to Client plans: " + plan.getName());
    }

    public static void addPlanToInstructors(PlanInstructor plan) {
        if (!plansInstructors.contains(plan))
            plansInstructors.add(plan);
        System.out.println("Plan added to Instructor plans: " + plan.getName());
    }

    public static void addFeed(Feedback feedback) {
        if (!feeds.contains(feedback))
            feeds.add(feedback);
    }

    public static ArrayList<Feedback> getFeeds() {
        return feeds;
    }

    public static ArrayList<Client> getAllClients() {
        return clients;
    }

    public static void addClient(Client client) {
        if (!clients.contains(client))
            clients.add(client);
        // system.out.println("Added client to database: " + client.getClientName());

    }

    public static Client getClientByName(String clientName) {
        for (Client client : clients) {
            if (client.getClientName().equals(clientName)) {
                return client;
            }
        }
        return null;
    }

    public static Instructor getInstructorByName(String instructorName) {
        for (Instructor instructor : instructors) {
            if (instructor.getName().equals(instructorName))// assuming unique instructor name
                return instructor;
        }
        return null;
    }

    public static Client getClientById(String clientId) {
        for (Client client : clients) {
            if (client.getClientId().toLowerCase().equals(clientId.toLowerCase())) {
                return client;
            }
        }
        return null;
    }

    public static Article getArticleById(String articleId) {
        for (Article article : articles) {
            if (article.getId().toLowerCase().equals(articleId.toLowerCase())) {
                return article;
            }
        }
        return null;
    }

    public static Article getArticleRequestById(String articleId) {
        for (Article article : articleRequests) {
            if (article.getId().toLowerCase().equals(articleId.toLowerCase())) {
                return article;
            }
        }
        return null;
    }

    public static Article getLastArticleRequest() {
        return articleRequests.get(0);
    }

    // Instructor database manipulation

    public static ArrayList<Instructor> getAllRequests() {
        return registrationRequests;
    }

    public static void sendRequest(Instructor req) {
        if (!registrationRequests.contains(req) && req.getStatus() != "valid")
            registrationRequests.add(req);
        // system.out.println("Request sent successfully to admin: " + req.getName());
    }

    public static Instructor processRequest() {
        if (registrationRequests.isEmpty()) {
            System.out.println("Instructors requests box empty!");
            return null;
        }

        Instructor oldestRequest = registrationRequests.get(0);
        return oldestRequest;
    }

    public static Instructor getInstructorRequestById(String instructorId) {
        for (Instructor instructor : registrationRequests) {
            if (instructor.getId().equals(instructorId)) {
                registrationRequests.remove(instructor);
                return instructor;
            }
        }
        return null;
    }

    public static Instructor getInstructorById(String instructorId) {
        for (Instructor instructor : instructors) {
            if (instructor.getId().equals(instructorId)) {
                return instructor;
            }
        }
        return null;
    }

    public static void addInstructor(Instructor newInstructor) {
        if (!instructors.contains(newInstructor))
            instructors.add(newInstructor);
    }

    public static ArrayList<Program> getPopularPrograms() {
        if (programs.isEmpty()) {
            // system.out.println("No programs available.");
            return null;
        }
        ArrayList<Program> sortedPrograms = new ArrayList<>(programs);

        // Sort the programs by enrollments in descending order
        Collections.sort(sortedPrograms, new Comparator<Program>() {
            @Override
            public int compare(Program p1, Program p2) {
                return Integer.compare(p2.popularity(), p1.popularity());
            }
        });

        // system.out.println("Program Statistics (sorted by enrollments):");
        for (Program program : sortedPrograms) {
            // system.out.println("Program Name: " + program.getName());
            // system.out.println("ID: " + program.getId());
            // system.out.println("Instructor: " + program.getInstructor().getName());
            // system.out.println("Enrollments: " + program.popularity());
            // system.out.println("---------------");
        }

        return sortedPrograms;

    }

    // may be deleted not used
    public static Program getProgramById(String programId) {
        for (Program program : programs) {
            if (program.getProgramId().equals(programId)) {
                return program;
            }
        }
        return null;
    }

    public static void addProgram(Program program) {
        if (!programs.contains(program))
            programs.add(program);

    }

    public static Article searchArticleById(String id) {
        for (Article article : articles) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public static Article searchPendingArticleById(String id) {
        for (Article article : articleRequests) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public static void addArticle(Article article) {
        if (!articles.contains(article)) {
            articles.add(article);
        }
    }

    public static void printArticles() {
        // Date formatter for "MMM yyyy" (e.g., "Dec 2024")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM yyyy");

        // Header row with column names
        System.out.printf("%-10s %-20s %-15s %-15s %-30s %-20s %-10s%n",
                "ID", "Author", "Date", "Type", "Title", "Content", "Approved");
        System.out.println(
                "------------------------------------------------------------------------------------------------");

        for (Article article : articles) {
            String formattedDate = dateFormatter.format(article.getSubmissionDate());

            String truncatedContent = article.getContent();
            if (truncatedContent.length() > 20) { // Truncate content to 20 characters
                truncatedContent = truncatedContent.substring(0, 20) + "...";
            }

            System.out.printf("%-10s %-20s %-15s %-15s %-30s %-20s %-10s%n",
                    article.getId(),
                    article.getAuthor().getName(),
                    formattedDate,
                    article.getType(),
                    article.getTitle(),
                    truncatedContent,
                    article.isApproved() ? "Yes" : "No");
        }
    }

    public static void printArticlesRequests() {
        // Date formatter for "MMM yyyy" (e.g., "Dec 2024")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM yyyy");

        // Header row with column names
        System.out.printf("%-10s %-20s %-15s %-15s %-30s %-20s %-10s%n",
                "ID", "Author", "Date", "Type", "Title", "Content", "Approved");
        System.out.println(
                "------------------------------------------------------------------------------------------------");

        for (Article article : articleRequests) {
            String formattedDate = dateFormatter.format(article.getSubmissionDate());

            String truncatedContent = article.getContent();
            if (truncatedContent.length() > 20) { // Truncate content to 20 characters
                truncatedContent = truncatedContent.substring(0, 20) + "...";
            }

            System.out.printf("%-10s %-20s %-15s %-15s %-30s %-20s %-10s%n",
                    article.getId(),
                    article.getAuthor().getName(),
                    formattedDate,
                    article.getType(),
                    article.getTitle(),
                    truncatedContent,
                    article.isApproved() ? "Yes" : "No");
        }
    }

    public static void printClientsTable() {
        ArrayList<Client> clients = DatabaseService.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-15s %-10s %-10s %-10s%n",
                "Client ID", "Name", "Status", "Plan", "Programs", "Goals", "Feedback");

        System.out.println("===================================================================================");

        for (Client cl : clients) {
            System.out.printf("%-10s %-20s %-10s %-15s %-10d %-10d %-10d%n",
                    cl.getClientId(),
                    cl.getClientName(),
                    cl.getStatus(),
                    cl.getPlan().getName(),
                    cl.getClientPrograms().size(),
                    cl.getProgressByGoal().size(),
                    cl.getClientFeeds().size());
        }
    }

    // Mock Data Section//
    public static void createMockData() {
        // Create some instructors
        Instructor instructor1 = new Instructor("John", "12");
        Instructor instructor2 = new Instructor("Jane", "12");
        Instructor instructor3 = new Instructor("Alice", "12");
        Instructor instructor4 = new Instructor("Ali", "121");
        Instructor instructor5 = new Instructor("Ruba", "87");
        Instructor instructor6 = new Instructor("Omar", "444");
        Instructor instructor7 = new Instructor("Ahmad", "56");
        Instructor instructor8 = new Instructor("Alma", "123");
        Instructor instructor9 = new Instructor("Abeer", "555");

        instructor1.setStatus("valid");
        instructor2.setStatus("valid");
        instructor3.setStatus("valid");

        Admin admin = new Admin();
        admin.rejectInstructor(instructor4.getId());
        admin.rejectInstructor(instructor5.getId());
        admin.rejectInstructor(instructor6.getId());

        // Create programs with instructors
        Program program1 = new Program(instructor1, 12, "Full body burn", 12, "mid");// these must be in try catch block
                                                                                     // due to program instructor
        Program program2 = new Program(instructor2, 50, "Old age special program", 6, "hard");
        Program program3 = new Program(instructor3, 10, "Belly fat burn", 3, "easy");

        // Enroll clients to the programs
        Client client1 = new Client("Alice", "password123");
        Client client2 = new Client("Bob", "password456");
        Client client3 = new Client("Charlie", "password789");
        Client client4 = new Client("David", "password321");
        Client client5 = new Client("Eva", "password654");

        // Enroll clients to program1
        program1.enrollClient(client1);
        program1.enrollClient(client2);

        // Enroll clients to program2
        program2.enrollClient(client3);
        program2.enrollClient(client4);
        program2.enrollClient(client5);

        // Enroll clients to program3
        program3.enrollClient(client1);
        program3.enrollClient(client3);

        addProgram(program1);
        addProgram(program2);
        addProgram(program3);

        addEnrollmentsToProgram(program3);
        addEnrollmentsToProgram(program1);
        addEnrollmentsToProgram(program2);

        // The programs list is automatically populated by the constructor
    }

    public static void populateMockPrograms() {
        // Create mock instructors
        Instructor instructor1 = new Instructor("Alice Smith", "I001");
        Instructor instructor2 = new Instructor("Bob Johnson", "I002");
        Instructor instructor3 = new Instructor("Carol Taylor", "I003");

        // Set instructors as valid
        instructor1.setStatus("valid");
        instructor2.setStatus("valid");
        instructor3.setStatus("valid");

        // Add instructors to the database
        addInstructor(instructor1);
        addInstructor(instructor2);
        addInstructor(instructor3);

        // Create mock programs
        Program program1 = new Program(instructor1, 500, "Yoga Basics", 8, "Beginner");
        Program program2 = new Program(instructor2, 700, "Advanced Cardio", 12, "Intermediate");
        Program program3 = new Program(instructor3, 1000, "Weightlifting Pro", 16, "Advanced");
        Program program4 = new Program(instructor3, 1000, "Weightlifting Easy", 16, "Beginner");

        // Add programs to the database (done automatically in Program constructor)

        // Create mock clients
        Client client1 = new Client("John Doe", "password123");
        Client client2 = new Client("Jane Roe", "password456");
        Client client3 = new Client("Sam Smith", "password789");
        Client client4 = new Client("Anna Brown", "password321");
        Client client5 = new Client("Tom White", "password654");

        // Add clients to the database
        addClient(client1);
        addClient(client2);
        addClient(client3);
        addClient(client4);
        addClient(client5);

        // Enroll clients into programs
        program1.enrollClient(client1);
        program1.enrollClient(client2);

        program2.enrollClient(client3);
        program2.enrollClient(client4);

        program3.enrollClient(client5);
        program3.enrollClient(client1);

        Progress progress1 = new Progress("Gain muscles", 10, 20);
        Progress progress2 = new Progress("Lose weight", 15, 25);
        Progress progress3 = new Progress("Increase stamina", 5, 30);
        Progress progress4 = new Progress("Improve flexibility", 8, 18);

        program1.addProgramGoal(progress4);
        program1.addProgramGoal(progress2);
        program1.addProgramGoal(progress3);
        program2.addProgramGoal(progress4);
        program2.addProgramGoal(progress3);
        program2.addProgramGoal(progress2);
        program3.addProgramGoal(progress4);
        program3.addProgramGoal(progress1);
        program4.addProgramGoal(progress1);

        addEnrollmentsToProgram(program3);
        addEnrollmentsToProgram(program2);
        addEnrollmentsToProgram(program1);

        // Log added data
        System.out.println("Mock programs and enrollments added successfully.");
    }

    public static void populateMockCompletedPrograms() {
        // Create mock instructors
        Instructor instructor1 = new Instructor("Alice Smith", "I001");
        Instructor instructor2 = new Instructor("Bob Johnson", "I002");
        Instructor instructor3 = new Instructor("Carol Taylor", "I003");

        // Set instructors as valid
        instructor1.setStatus("valid");
        instructor2.setStatus("valid");
        instructor3.setStatus("valid");

        // Add instructors to the database
        addInstructor(instructor1);
        addInstructor(instructor2);
        addInstructor(instructor3);

        // Create mock programs
        Program program1 = new Program(instructor1, 500, "Yoga Basics", 8, "Beginner");
        Program program2 = new Program(instructor2, 700, "Advanced Cardio", 12, "Intermediate");
        Program program3 = new Program(instructor3, 1000, "Weightlifting Pro", 16, "Advanced");

        // Add programs to the database (done automatically in Program constructor)

        // Create mock clients
        Client client1 = new Client("John Doe", "password123");
        Client client2 = new Client("Jane Roe", "password456");
        Client client3 = new Client("Sam Smith", "password789");
        Client client4 = new Client("Anna Brown", "password321");
        Client client5 = new Client("Tom White", "password654");

        // Add clients to the database
        addClient(client1);
        addClient(client2);
        addClient(client3);
        addClient(client4);
        addClient(client5);

        // Enroll clients into programs
        program1.enrollClient(client1);
        program1.enrollClient(client2);

        program2.enrollClient(client3);
        program2.enrollClient(client4);

        program3.enrollClient(client5);
        program3.enrollClient(client1);

        program1.markAsCompleted();
        program2.markAsCompleted();
        program3.markAsCompleted();
        addEnrollmentsToProgram(program1);
        addEnrollmentsToProgram(program3);
        addEnrollmentsToProgram(program2);

        // Log added data
        System.out.println("Mock completed programs are ready programs and enrollments added successfully.");
    }

    public static void addEnrollmentsToProgram(Program program) {
        // Create mock enrollments
        Client client1 = new Client("John Doe", "password123");
        Client client2 = new Client("Jane Roe", "password456");
        Client client3 = new Client("Sam Smith", "password789");
        Client client4 = new Client("Anna Brown", "password321");
        Client client5 = new Client("Tom White", "password654");
        Client client6 = new Client("tom", "123");

        // Enroll clients into programs
        program.enrollClient(client1);
        program.enrollClient(client2);
        program.enrollClient(client3);
        program.enrollClient(client4);
        program.enrollClient(client5);
        program.enrollClient(client6);

        Random random = new Random();
        for (Client client : program.getEnrolledClients()) {
            for (int week = 1; week <= program.getDuration(); week++) {
                boolean attended = random.nextBoolean(); // Randomly set attendance (true/false)
                program.markAttendance(client, week, attended);
            }
        }

        System.out.println("Mock enrollments and attendance records have been added.");

    }

    public static void populateMockArticles() {
        // Create mock instructors
        Instructor instructor1 = new Instructor("John", "12");
        Instructor instructor2 = new Instructor("Jane", "12");
        Instructor instructor3 = new Instructor("Alice", "12");

        // Set valid status for instructors
        instructor1.setStatus("valid");
        instructor2.setStatus("valid");
        instructor3.setStatus("valid");

        // Add mock articles to articleRequests
        new Article("Fitness Basics", "A beginner's guide to fitness.", instructor1, ArticleType.ARTICLE);
        new Article("Healthy Eating Tips", "Eat more greens and avoid processed foods.", instructor2, ArticleType.TIP);
        new Article("Quick Breakfast Recipe", "Recipe: Oats, yogurt, and fruits.", instructor3, ArticleType.RECIPE);

        // Add approved articles to articles
        Article approvedArticle1 = new Article("Advanced Yoga Techniques", "Learn advanced poses.", instructor1,
                ArticleType.ARTICLE);
        approvedArticle1.setApproved(true);

        Article approvedArticle2 = new Article("Stay Hydrated", "Drink at least 2 liters of water daily.", instructor2,
                ArticleType.TIP);
        approvedArticle2.setApproved(true);

        Article approvedArticle3 = new Article("Protein Smoothie Recipe", "Blend milk, protein powder, and bananas.",
                instructor3, ArticleType.RECIPE);
        approvedArticle3.setApproved(true);
    }

    public static void populateMockFeedbacks() {

        Client client1 = new Client("Alice", "password12");
        Client client2 = new Client("Bob", "password45");
        Client client3 = new Client("Charlie", "password78");
        Client client4 = new Client("David", "password32");
        Client client5 = new Client("Eva", "password65");

        // Mock feedbacks for client1
        client1.writeFeedback("complaint", "Hello, I want to express my sadness for losing only 2kg.");
        client1.writeFeedback("suggestion", "Could you add more personalized diet plans?");
        client1.writeFeedback("feedback", "Great customer service but some products were out of stock.");

        // Mock feedbacks for client2
        client2.writeFeedback("complaint", "The app is very slow sometimes. Please fix it.");
        client2.writeFeedback("feedback", "Overall, I love the progress tracker feature.");
        client2.writeFeedback("suggestion", "Can you integrate a calorie calculator?");

        // Mock feedbacks for client3
        client3.writeFeedback("feedback", "The weekly emails are really motivating!");
        client3.writeFeedback("complaint", "Some of the recipes use ingredients that are hard to find.");
        client3.writeFeedback("suggestion", "Please consider adding a chat option to contact a coach directly.");

        // Mock feedbacks for client4
        // client4.writeFeedback("complaint", "I faced an error while making a payment.
        // Please resolve it.");
        // client4.writeFeedback("feedback", "The support team was quick to respond to
        // my issue. Thank you!");
        // client4.writeFeedback("suggestion", "More exercise routines for beginners
        // would be helpful.");

        // Mock feedbacks for client5
        // client5.writeFeedback("feedback", "Amazing experience so far. I've lost 5kg
        // in 2 months!");
        // client5.writeFeedback("suggestion", "It would be great to see progress charts
        // for more than 3 months.");
        // client5.writeFeedback("complaint", "Some exercises lack proper instructions
        // in the video.");

        System.out.println("Mock feedbacks populated successfully!");
    }

    public static void populateMockPlans() {
        // Define client plans with benefits
        PlanClient clientBasicPlan = new PlanClient("Basic", "Free", 0.00, "Basic access");
        clientBasicPlan.addBenefit("Access to community forums");
        clientBasicPlan.addBenefit("Basic learning resources");

        PlanClient clientPremiumPlan = new PlanClient("Premium", "Paid", 29.99, "Premium features");
        clientPremiumPlan.addBenefit("Priority support");
        clientPremiumPlan.addBenefit("Exclusive content");
        clientPremiumPlan.addBenefit("Advanced learning resources");

        PlanClient clientProPlan = new PlanClient("Pro", "Paid", 49.99, "Pro-level features");
        clientProPlan.addBenefit("All Premium benefits");
        clientProPlan.addBenefit("Personalized training sessions");
        clientProPlan.addBenefit("Discounts on additional services");

        // Define instructor plans with benefits
        PlanInstructor instructorBasicPlan = new PlanInstructor("Basic", "Free", 0.00, "Basic access");
        instructorBasicPlan.addBenefit("Access to teaching platform with limited features");
        instructorBasicPlan.addBenefit("Basic analytics tools");

        PlanInstructor instructorPremiumPlan = new PlanInstructor("Premium", "Paid", 49.99, "Premium teaching tools");
        instructorPremiumPlan.addBenefit("Advanced analytics");
        instructorPremiumPlan.addBenefit("Additional teaching tools");
        instructorPremiumPlan.addBenefit("Marketing support");

        PlanInstructor instructorEnterprisePlan = new PlanInstructor("Enterprise", "Paid", 99.99,
                "Enterprise-grade tools");
        instructorEnterprisePlan.addBenefit("All Premium benefits");
        instructorEnterprisePlan.addBenefit("Team management features");
        instructorEnterprisePlan.addBenefit("Custom branding options");

        // Define clients
        Client client1 = new Client("Alice", "password12");
        Client client2 = new Client("Bob", "password45");
        Client client3 = new Client("Charlie", "password78");
        Client client4 = new Client("David", "password32");
        Client client5 = new Client("Eve", "password99");
        Client client6 = new Client("Frank", "password123");

        // Define instructors
        Instructor instructor1 = new Instructor("John", "password12");
        Instructor instructor2 = new Instructor("Jane", "password12");
        Instructor instructor3 = new Instructor("Michael", "password12");
        Instructor instructor4 = new Instructor("Sarah", "password12");
        Instructor instructor5 = new Instructor("Emma", "password12");
        Instructor instructor6 = new Instructor("Chris", "password12");

        // Set valid status for instructors
        instructor1.setStatus("valid");
        instructor2.setStatus("valid");
        instructor3.setStatus("valid");
        instructor4.setStatus("valid");
        instructor5.setStatus("valid");
        instructor6.setStatus("valid");

        // Add the plans to the respective lists
        addPlanToClient(clientBasicPlan);
        addPlanToClient(clientPremiumPlan);
        addPlanToClient(clientProPlan);
        addPlanToInstructors(instructorBasicPlan);
        addPlanToInstructors(instructorPremiumPlan);
        addPlanToInstructors(instructorEnterprisePlan);

        // Enroll clients to plans
        client1.changeSubscription(clientBasicPlan); // Alice on Basic
        client2.changeSubscription(clientBasicPlan); // Bob on Basic
        client3.changeSubscription(clientPremiumPlan); // Charlie on Premium
        client4.changeSubscription(clientPremiumPlan); // David on Premium
        client5.changeSubscription(clientProPlan); // Eve on Pro
        client6.changeSubscription(clientProPlan); // Frank on Pro

        // Enroll instructors to plans
        instructor1.changeSubscription(instructorPremiumPlan); // John on Premium
        instructor2.changeSubscription(instructorPremiumPlan); // Jane on Premium
        instructor3.changeSubscription(instructorBasicPlan); // Michael on Basic
        instructor4.changeSubscription(instructorBasicPlan); // Sarah on Basic
        instructor5.changeSubscription(instructorEnterprisePlan); // Emma on Enterprise
        instructor6.changeSubscription(instructorEnterprisePlan); // Chris on Enterprise

        System.out.println("Mock plans and subscriptions populated successfully with benefits.");
    }

    public static void populateMockProgramsWithGoals() {
        // Create mock instructors
        Instructor instructor1 = new Instructor("Alice Smith", "I001");
        Instructor instructor2 = new Instructor("Bob Johnson", "I002");
        Instructor instructor3 = new Instructor("Carol Taylor", "I003");

        // Set instructors as valid
        instructor1.setStatus("valid");
        instructor2.setStatus("valid");
        instructor3.setStatus("valid");

        // Add instructors to the database
        addInstructor(instructor1);
        addInstructor(instructor2);
        addInstructor(instructor3);

        // Create mock programs
        Program program1 = new Program(instructor1, 100, "Weight Loss 101", 6, "Beginner");
        Program program2 = new Program(instructor2, 700, "Muscle Gain Pro", 8, "Intermediate");
        Program program3 = new Program(instructor3, 80, "Yoga for Flexibility", 4, "Advanced");

        Progress progress1 = new Progress("Weight Loss", 0, 10);
        Progress progress2 = new Progress("Muscle Building", 0, 20);
        Progress progress3 = new Progress("Increase stamina", 5, 30);
        Progress progress4 = new Progress("Flexibility", 0, 30);

        program1.addProgramGoal(progress4);
        program1.addProgramGoal(progress2);
        program1.addProgramGoal(progress3);
        program2.addProgramGoal(progress4);
        program2.addProgramGoal(progress3);
        program2.addProgramGoal(progress2);
        program3.addProgramGoal(progress4);
        program3.addProgramGoal(progress1);

        addEnrollmentsToProgram(program3);
        addEnrollmentsToProgram(program2);
        addEnrollmentsToProgram(program1);

    }
}
