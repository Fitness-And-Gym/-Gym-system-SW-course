package triple.com;

import java.util.ArrayList;

public class Admin {
    private String userName;// small change for githup
    private String password;
    private boolean loggedIn;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean getLogin() {
        return loggedIn;
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
            Database.addClient(client);
            return client;
        } else {
            // system.out.println("Admin is not logged in, cannot create client.");
            return null;
        }
    }

    public ArrayList<Program> seeStatistics() {
        if (loggedIn) {
            return Database.getPopularPrograms();
        } else {
            // system.out.println("Admin is not logged in, cannot see statistics.");
            return null;
        }
    }

    public void checkClientProgress(Client client) {
        client.displayGoalProgress();
    }

    public Client updateClient(String clientId, String updatedName, String updatedPassword) {
        Client myClient = Database.getClientById(clientId);
        if (myClient != null) {
            myClient.updateName(updatedName);
            myClient.updatePassword(updatedPassword);
        }
        return myClient;// by default returns null when no user found

    }

    public void deactivateClient(String clientId) {
        Client myClient = Database.getClientById(clientId);
        if (myClient != null) {
            myClient.setStatus("invalid");
        } else {
            System.out.println("client is not deactivated**");
        }
    }

    public void acceptInstructor(String instructorId) {
        Instructor myInstructor = Database.getInstructorRequestById(instructorId);// how to remove it in database and
                                                                                  // then try to read it
        if (myInstructor != null) {
            myInstructor.setStatus("valid");
            Database.addInstructor(myInstructor);
        }

    }

    public void rejectInstructor(String instructorId) {
        Instructor myInstructor = Database.getInstructorRequestById(instructorId);
        if (myInstructor != null) {
            myInstructor.setStatus("invalid");
            Database.addDeclinedInstructors(myInstructor);
        }

    }

    public Instructor pullInstructorRequest() {
        if (loggedIn) {
            return Database.processRequest();
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

        for (Program program : Database.getPrograms()) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

    public String TrackCompletedPrograms() {
        StringBuilder report = new StringBuilder("Track Completed Programs\n");

        for (Program program : Database.CompletedPrograms) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

    public Article getPendingArticle() {// return the oldest request
        if (Database.articleRequests.size() != 0) {
            Article oldestRequest = Database.articleRequests.remove(0);
            return oldestRequest;
        }
        return null;
    }

    public void acceptArticle(Article article) {
        article.setApproved(true);
        Database.articleRequests.remove(article);
        Database.addArticle(article);
    }

    public void createInstructorPlan(String name, String type, double price, String basicBenefit) {
        if (loggedIn) {
            PlanInstructor planInstructor = new PlanInstructor(name, type, price, basicBenefit);
            Database.addPlanToInstructors(planInstructor);
        }
    }

    public void createClientPlan(String name, String type, double price, String basicBenefit) {
        if (loggedIn) {
            PlanClient planClient = new PlanClient(name, type, price, basicBenefit);
            Database.addPlanToClient(planClient);
        }
    }

    public void rejectArticle(Article article) {
        article.setApproved(false);
        Database.articleRequests.remove(article);
    }

    public String readFeedbacks() {
        ArrayList<Feedback> feeds = Database.getFeeds();
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

        for (PlanClient plan : Database.getPlansClient()) {
            allPlanDetails.append(plan.getPlanDetails()).append("\n");
        }
        System.out.println("\nPlans available for clients\n" + "------------------------------------\n");
        System.out.println(allPlanDetails.toString());
        return allPlanDetails.toString();
    }

    public String seePlansForInstructors() {
        StringBuilder allPlanDetails = new StringBuilder();

        for (PlanInstructor plan : Database.getPlansInstructors()) {
            allPlanDetails.append(plan.getPlanDetails()).append("\n");
        }
        System.out.println("\nPlans available for Instructors\n" + "------------------------------------\n");
        System.out.println(allPlanDetails.toString());
        return allPlanDetails.toString();
    }

    

}