package triple.com;

import java.util.ArrayList;

public class Admin {
    private String userName;// small change for githup
    private String password;
    private boolean loggedIn;

    public boolean getLogin() {
        return loggedIn;
    }

    public void logout() {
        loggedIn = false;
    }

    public void login(String userName, String password) {
        if ("admin".equals(userName) && "admin".equals(password)) {
            loggedIn = true;
            this.userName = userName;
            this.password = password;
        } else {
            loggedIn = false;
        }
    }

    public Client createClientAccount(String name, String password) {
        if (loggedIn) {
            Client client = new Client(name, password);
            DatabaseService.addClient(client);
            return client;
        } else {
            // system.out.println("Admin is not logged in, cannot create client.");
            return null;
        }
    }

    public ArrayList<Program> seeStatistics() {
        if (loggedIn) {
            return DatabaseService.getPopularPrograms();
        } else {
            // system.out.println("Admin is not logged in, cannot see statistics.");
            return null;
        }
    }

    public void checkClientProgress(Client client) {
        client.displayGoalProgress();
    }

    public Client updateClient(String clientId, String updatedName, String updatedPassword) {
        Client myClient = DatabaseService.getClientById(clientId);
        if (myClient != null) {
            myClient.updateName(updatedName);
            myClient.updatePassword(updatedPassword);
        }
        return myClient;// by default returns null when no user found

    }

    public void deactivateClient(String clientId) {
        Client myClient = DatabaseService.getClientById(clientId);
        if (myClient != null) {
            myClient.setStatus("invalid");
        } else {
            System.out.println("client is not deactivated**");
        }
    }

    public void acceptInstructor(String instructorId) {
        Instructor myInstructor = DatabaseService.getInstructorRequestById(instructorId);// how to remove it in
                                                                                         // DatabaseService and
        // then try to read it
        if (myInstructor != null) {
            myInstructor.setStatus("valid");
            DatabaseService.addInstructor(myInstructor);
        }

    }

    public void rejectInstructor(String instructorId) {
        Instructor myInstructor = DatabaseService.getInstructorRequestById(instructorId);
        if (myInstructor != null) {
            myInstructor.setStatus("invalid");
            DatabaseService.addDeclinedInstructors(myInstructor);
        }

    }

    public Instructor pullInstructorRequest() {
        if (loggedIn) {
            return DatabaseService.processRequest();
        }
        System.out.println("Admin must login first**");
        return null;
    }

    public Instructor createInstructorAccount(String InstructorName, String InstructorPassword) {
        Instructor myInstructor = new Instructor(InstructorName, InstructorPassword);
        myInstructor.sendRequest();
        acceptInstructor(myInstructor.getId());
        return myInstructor;
    }

    public String TrackActivePrograms() {
        StringBuilder report = new StringBuilder("Track Active Programs\n");

        for (Program program : DatabaseService.getPrograms()) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

    public String TrackCompletedPrograms() {
        StringBuilder report = new StringBuilder("Track Completed Programs\n");

        for (Program program : DatabaseService.getCompletedPrograms()) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

    public Article getPendingArticle() {// return the oldest request
        if (DatabaseService.getArticleRequests().size() != 0) {
            Article oldestRequest = DatabaseService.getLastArticleRequest();
            return oldestRequest;
        }
        return null;
    }

    public void acceptArticle(String articleId) {
        Article article = DatabaseService.getArticleRequestById(articleId);
        article.setApproved(true);
        DatabaseService.removeArticleRequest(article);
        DatabaseService.addArticle(article);
    }

    public void createInstructorPlan(String name, String type, double price, String basicBenefit) {
        if (loggedIn) {
            PlanInstructor planInstructor = new PlanInstructor(name, type, price, basicBenefit);
            DatabaseService.addPlanToInstructors(planInstructor);
        }
    }

    public void createClientPlan(String name, String type, double price, String basicBenefit) {
        if (loggedIn) {
            PlanClient planClient = new PlanClient(name, type, price, basicBenefit);
            DatabaseService.addPlanToClient(planClient);
        }
    }

    public void rejectArticle(String articleId) {
        Article article = DatabaseService.getArticleRequestById(articleId);
        article.setApproved(false);
        DatabaseService.removeArticleRequest(article);
    }

    public String readFeedbacks() {
        ArrayList<Feedback> feeds = DatabaseService.getFeeds();
        StringBuilder allFeedback = new StringBuilder();

        for (Feedback feed : feeds) {
            String temp = feed.printFeedback();
            allFeedback.append(temp).append("\n");
            System.out.println(temp);
        }

        // Convert StringBuilder to String and return it
        return allFeedback.toString();
    }

    public String seePlansForClient() {
        StringBuilder allPlanDetails = new StringBuilder();

        for (PlanClient plan : DatabaseService.getPlansClient()) {
            allPlanDetails.append(plan.getPlanDetails()).append("\n");
        }
        System.out.println("\nPlans available for clients\n" + "------------------------------------\n");
        System.out.println(allPlanDetails.toString());
        return allPlanDetails.toString();
    }

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