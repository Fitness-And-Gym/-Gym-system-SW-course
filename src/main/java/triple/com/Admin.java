package triple.com;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * The Admin class is responsible for managing administrative operations,
 * including user login, client management, program tracking, instructor
 * management,
 * and article approval/rejection.
 * <p>
 * It requires the admin to be logged in to perform most actions.
 * </p>
 */
public class Admin {
    private String userName; // Admin's username
    private String password; // Admin's password
    private boolean loggedIn; // Indicates whether the admin is logged in

    /**
     * Returns whether the admin is logged in.
     *
     * @return true if the admin is logged in, false otherwise
     */
    public boolean getLogin() {
        return loggedIn;
    }

    /**
     * Logs out the admin, marking the admin as not logged in.
     */
    public void logout() {
        loggedIn = false;
    }

    /**
     * Logs in the admin with a given username and password.
     * Only "admin" username and password will grant access.
     *
     * @param userName the username for login
     * @param password the password for login
     */
    public void login(String userName, String password) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml")) {
            Yaml yaml = new Yaml();

            Map<String, String> obj = yaml.load(inputStream);

            System.out.println(obj);

            String adminName = obj.get("user");
            String adminPassword = obj.get("password");

            if (userName.equals(adminName) && password.equals(adminPassword)) {
                loggedIn = true;
                this.userName = userName;
                this.password = password;
            } else {
                loggedIn = false;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Creates a new client account if the admin is logged in.
     *
     * @param name     the name of the client
     * @param password the password for the client account
     * @return the created Client object, or null if the admin is not logged in
     */
    public Client createClientAccount(String name, String password) {
        if (loggedIn) {
            Client client = new Client(name, password);
            DatabaseService.addClient(client);
            return client;
        } else {
            return null;
        }
    }

    /**
     * Retrieves statistics of popular programs, if the admin is logged in.
     *
     * @return a list of popular programs or null if the admin is not logged in
     */
    public ArrayList<Program> seeStatistics() {
        if (loggedIn) {
            return DatabaseService.getPopularPrograms();
        } else {
            return null;
        }
    }

    /**
     * Updates the details of a client based on their ID.
     *
     * @param clientId        the ID of the client to update
     * @param updatedName     the new name of the client
     * @param updatedPassword the new password of the client
     * @return the updated Client object, or null if no client found with the given
     *         ID
     */
    public Client updateClient(String clientId, String updatedName, String updatedPassword) {
        Client myClient = DatabaseService.getClientById(clientId);
        if (myClient != null) {
            myClient.updateName(updatedName);
            myClient.updatePassword(updatedPassword);
        }
        return myClient; // returns null if no client found
    }

    /**
     * Deactivates a client's account by setting its status to "invalid".
     *
     * @param clientId the ID of the client to deactivate
     */
    public void deactivateClient(String clientId) {
        Client myClient = DatabaseService.getClientById(clientId);
        if (myClient != null) {
            myClient.setStatus("invalid");
        } else {
            System.out.println("client is not deactivated**");
        }
    }

    /**
     * Accepts an instructor's request and sets their status to "valid".
     *
     * @param instructorId the ID of the instructor to accept
     */
    public void acceptInstructor(String instructorId) {
        Instructor myInstructor = DatabaseService.getInstructorRequestById(instructorId);
        if (myInstructor != null) {
            myInstructor.setStatus("valid");
            DatabaseService.addInstructor(myInstructor);
        }
    }

    /**
     * Rejects an instructor's request by setting their status to "invalid".
     *
     * @param instructorId the ID of the instructor to reject
     */
    public void rejectInstructor(String instructorId) {
        Instructor myInstructor = DatabaseService.getInstructorRequestById(instructorId);
        if (myInstructor != null) {
            myInstructor.setStatus("invalid");
            DatabaseService.addDeclinedInstructors(myInstructor);
        }
    }

    /**
     * Retrieves the first pending instructor request.
     *
     * @return the oldest instructor request, or null if none exists
     */
    public Instructor pullInstructorRequest() {
        if (loggedIn) {
            return DatabaseService.processRequest();
        }
        System.out.println("Admin must login first**");
        return null;
    }

    /**
     * Creates a new instructor account by sending a request and accepting the
     * instructor.
     *
     * @param InstructorName     the name of the instructor
     * @param InstructorPassword the password of the instructor
     * @return the created Instructor object
     */
    public Instructor createInstructorAccount(String InstructorName, String InstructorPassword) {
        Instructor myInstructor = new Instructor(InstructorName, InstructorPassword);
        myInstructor.sendRequest();
        acceptInstructor(myInstructor.getId());
        return myInstructor;
    }

    /**
     * Tracks and generates a report of active programs.
     *
     * @return the generated report as a String
     */
    public String TrackActivePrograms() {
        StringBuilder report = new StringBuilder("Track Active Programs\n");

        for (Program program : DatabaseService.getPrograms()) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

    /**
     * Tracks and generates a report of completed programs.
     *
     * @return the generated report as a String
     */
    public String TrackCompletedPrograms() {
        StringBuilder report = new StringBuilder("Track Completed Programs\n");

        for (Program program : DatabaseService.getCompletedPrograms()) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

    /**
     * Retrieves the oldest pending article request.
     *
     * @return the oldest pending Article object, or null if no article requests
     *         exist
     */
    public Article getPendingArticle() {
        if (DatabaseService.getArticleRequests().size() != 0) {
            Article oldestRequest = DatabaseService.getLastArticleRequest();
            return oldestRequest;
        }
        return null;
    }

    /**
     * Accepts an article request by marking it as approved and adding it to the
     * database.
     *
     * @param articleId the ID of the article to accept
     */
    public void acceptArticle(String articleId) {
        Article article = DatabaseService.getArticleRequestById(articleId);
        article.setApproved(true);
        DatabaseService.removeArticleRequest(article);
        DatabaseService.addArticle(article);
    }

    /**
     * Creates a plan for instructors.
     *
     * @param name         the name of the plan
     * @param type         the type of the plan
     * @param price        the price of the plan
     * @param basicBenefit the basic benefit of the plan
     */
    public void createInstructorPlan(String name, String type, double price, String basicBenefit) {
        if (loggedIn) {
            PlanInstructor planInstructor = new PlanInstructor(name, type, price, basicBenefit);
            DatabaseService.addPlanToInstructors(planInstructor);
        }
    }

    /**
     * Creates a plan for clients.
     *
     * @param name         the name of the plan
     * @param type         the type of the plan
     * @param price        the price of the plan
     * @param basicBenefit the basic benefit of the plan
     */
    public void createClientPlan(String name, String type, double price, String basicBenefit) {
        if (loggedIn) {
            PlanClient planClient = new PlanClient(name, type, price, basicBenefit);
            DatabaseService.addPlanToClient(planClient);
        }
    }

    /**
     * Rejects an article request by marking it as disapproved.
     *
     * @param articleId the ID of the article to reject
     */
    public void rejectArticle(String articleId) {
        Article article = DatabaseService.getArticleRequestById(articleId);
        article.setApproved(false);
        DatabaseService.removeArticleRequest(article);
    }

    /**
     * Reads and prints all feedbacks from clients and instructors.
     *
     * @return a string of all feedbacks
     */
    public String readFeedbacks() {
        ArrayList<Feedback> feeds = DatabaseService.getFeeds();
        StringBuilder allFeedback = new StringBuilder();

        for (Feedback feed : feeds) {
            String temp = feed.printFeedback();
            allFeedback.append(temp).append("\n");
            System.out.println(temp);
        }

        return allFeedback.toString();
    }

    /**
     * Displays the available plans for clients.
     *
     * @return a string of available plans for clients
     */
    public String seePlansForClient() {
        StringBuilder allPlanDetails = new StringBuilder();

        for (PlanClient plan : DatabaseService.getPlansClient()) {
            allPlanDetails.append(plan.getPlanDetails()).append("\n");
        }
        System.out.println("\nPlans available for clients\n" + "------------------------------------\n");
        System.out.println(allPlanDetails.toString());
        return allPlanDetails.toString();
    }

    /**
     * Displays the available plans for instructors.
     *
     * @return a string of available plans for instructors
     */
    public String seePlansForInstructors() {
        StringBuilder allPlanDetails = new StringBuilder();

        for (PlanInstructor plan : DatabaseService.getPlansInstructors()) {
            allPlanDetails.append(plan.getPlanDetails()).append("\n");
        }
        System.out.println("\nPlans available for Instructors\n" + "------------------------------------\n");
        System.out.println(allPlanDetails.toString());
        return allPlanDetails.toString();
    }
}
