package triple.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
    private static int idCounter = 1;
    private String clientId;
    private String clientName;
    private String password;
    private ArrayList<Program> clientPrograms = new ArrayList<>();
    private ArrayList<Feedback> clientFeeds = new ArrayList<>();
    private ArrayList<Message> inbox = new ArrayList<>();
    private ArrayList<String> dietaryPreferences = new ArrayList<>();

    private List<Progress> progressByGoal;
    private String status;// Might Has Only the value 'invalid'/'valid'
    private PlanClient plan;
    // Diatry restrictions

    // let the user input his name and password and search for it in the database

    public Client(String clientName, String password) {
        this.clientName = clientName;
        this.password = password;
        this.progressByGoal = new ArrayList<>();
        this.clientId = "C" + idCounter++;
        this.status = "valid";
        this.plan = DatabaseService.getBasicPlanClient();
        DatabaseService.getBasicPlanClient().subscribeClient(this);
        DatabaseService.addClient(this);

    }

    public String getClientName() {
        return clientName;
    }

    public String getPassword() {
        return password;
    }

    public List<Progress> getGoals() {
        return progressByGoal;
    }

    public void updateName(String newName) {
        this.clientName = newName;
    }

    public PlanClient getPlan() {
        return plan;
    }

    public ArrayList<Program> getClientPrograms() {
        return clientPrograms;
    }

    public List<Progress> getProgressByGoal() {
        return progressByGoal;
    }

    public ArrayList<Feedback> getClientFeeds() {
        return clientFeeds;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public String getClientId() {
        return clientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void enrollInProgram(String programId) {
        Program selectedProgram = DatabaseService.getProgramById(programId);
        if (selectedProgram != null && !clientPrograms.contains(selectedProgram)) {
            clientPrograms.add(selectedProgram);
            selectedProgram.enrollClient(this);
        }
    }

    // Enables Client to create progress
    public Progress setGoal(String goalType, double startValue, double targetValue) {
        Progress progress = new Progress(goalType, startValue, targetValue);
        progressByGoal.add(progress);
        return progress;
    }

    // Enables Program to add progress to enrollments
    public void setGoalForAProgram(Progress progress) {
        progressByGoal.add(progress);
    }

    public void displayGaolProgressIn(int goalNumber) {// a specified method
        Progress progress = progressByGoal.get(goalNumber);
        String summery = progress.getProgressSummary();
        System.out.println(summery);
    }

    public void displayGoalProgress() {
        if (progressByGoal.isEmpty()) {
            System.out.println("No goals set for this client.");
        } else {
            for (int i = 0; i < progressByGoal.size(); i++) {
                System.out.println("ID:" + i + "\t" + progressByGoal.get(i).getProgressSummary());
            }
        }
    }

    public Progress updateGoalProgress(int goalNumber, double newValue)// goal number indicates the index of goal in the
    {
        progressByGoal.get(goalNumber).updateProgress(newValue);
        return progressByGoal.get(goalNumber);
    }

    public void writeFeedback(String feedbackType, String message) {
        Feedback temp = new Feedback(feedbackType, message, this);
        clientFeeds.add(temp);
        DatabaseService.addFeed(temp);
    }

    // by index choose the plan to participate in no need for id since only few
    // plans
    public void changeSubscription(PlanClient plan) {
        this.plan.cancelClientSubscription(this);
        this.plan = plan;
        plan.subscribeClient(this);
    }

    public void deleteSubscription() {
        this.plan.cancelClientSubscription(this);
        this.plan = DatabaseService.getBasicPlanClient();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client Details:\n");
        sb.append("Client ID: ").append(clientId).append("\n");
        sb.append("Name: ").append(clientName).append("\n");
        sb.append("Password: ").append("********").append("\n"); // Masked for security
        sb.append("Status: ").append(status).append("\n");
        sb.append("Plan: ").append(plan.getName()).append("\n");

        sb.append("Enrolled Programs:\n");
        if (clientPrograms.isEmpty()) {
            sb.append("  None\n");
        } else {
            for (Program program : clientPrograms) {
                sb.append("  - ").append(program.getTitle()).append("\n");
            }
        }

        sb.append("Goals Progress:\n");
        if (progressByGoal.isEmpty()) {
            sb.append("  No goals set.\n");
        } else {
            for (Progress progress : progressByGoal) {
                sb.append("  - ").append(progress.getProgressSummary()).append("\n");
            }
        }

        sb.append("Feedback:\n");
        if (clientFeeds.isEmpty()) {
            sb.append("  No feedback provided.\n");
        } else {
            for (Feedback feedback : clientFeeds) {
                sb.append("  - Type: ").append(feedback.getFeedbackType())
                        .append(", Message: ").append(feedback.getMessage()).append("\n");
            }
        }

        return sb.toString();
    }

    // public void sendMessage(String recipientId, String content) {
    // Message message = new Message(this.clientId, recipientId, content);
    // DatabaseService.addMessage(message); // Store message in the database
    // System.out.println("Message sent to " + recipientId + ": " + content);
    // }

    public void viewMessages() {
        if (inbox.isEmpty()) {
            System.out.println("No messages received.");
        } else {
            System.out.println("Messages for " + clientName + ":");
            for (Message message : inbox) {
                System.out.println(message);
            }
        }
    }

    public void receiveMessage(Message message) {
        this.inbox.add(message);
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }

    public Message replyToMessage(Message originalMessage, String replyContent) {
        Message reply = new Message("Re: " + originalMessage.getTitle(), replyContent,
                originalMessage.getSender(), clientId);
        originalMessage.getSender().receiveMessage(reply);// sender is instructor
        return reply;
    }

    // Add a dietary preference to the list
    public void addDietaryPreference(String preference) {
        if (!dietaryPreferences.contains(preference)) {
            dietaryPreferences.add(preference);
            System.out.println("Dietary preference added: " + preference);
        } else {
            System.out.println("This dietary preference already exists.");
        }
    }

    // Delete a dietary preference from the list
    public void deleteDietaryPreference(String preference) {
        if (dietaryPreferences.contains(preference)) {
            dietaryPreferences.remove(preference);
            System.out.println("Dietary preference removed: " + preference);
        } else {
            System.out.println("Dietary preference not found.");
        }
    }

    // Get the list of dietary preferences
    public ArrayList<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    // Print all dietary preferences
    public void printDietaryPreferences() {
        if (dietaryPreferences.isEmpty()) {
            System.out.println("No dietary preferences set.");
        } else {
            System.out.println("Dietary Preferences:");
            for (String preference : dietaryPreferences) {
                System.out.println("  - " + preference);
            }
        }
    }

    public ArrayList<Program> filterProgramsByDifficulty(String difficulty) {
        ArrayList<Program> filteredPrograms = new ArrayList<>();

        for (Program program : DatabaseService.getPrograms()) {
            if (program.getDifficulty().equalsIgnoreCase(difficulty)) {
                filteredPrograms.add(program);
            }
        }

        return filteredPrograms;
    }

    public ArrayList<Program> filterProgramsByGoal(String goal) {
        ArrayList<Program> filteredPrograms = new ArrayList<>();

        for (Program program : DatabaseService.getPrograms()) {
            for (Progress progress : program.getProgramGoals())
                if (progress.getGoalType().equalsIgnoreCase(goal)) {
                    filteredPrograms.add(program);
                    break;
                }
        }

        return filteredPrograms;
    }

    public String printPrograms(ArrayList<Program> programs) {
        StringBuilder report = new StringBuilder("Search Result\n");

        for (Program program : programs) {
            report.append(program.generateReportForAdmin()).append("\n");
        }
        System.out.println(report);
        return report.toString();
    }

}
