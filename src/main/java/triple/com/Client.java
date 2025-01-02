package triple.com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a client in the system, with functionality to manage programs,
 * goals, feedback, dietary preferences,
 * and subscription plans.
 */
public class Client {
    /**
     * A unique counter to generate unique client IDs.
     */
    private static int idCounter = 1;

    /**
     * The unique identifier for the client.
     */
    private String clientId;

    /**
     * The name of the client.
     */
    private String clientName;

    /**
     * The password of the client.
     */
    private String password;

    /**
     * A list of programs the client is enrolled in.
     */
    private ArrayList<Program> clientPrograms = new ArrayList<>();

    /**
     * A list of feedback provided by the client.
     */
    private ArrayList<Feedback> clientFeeds = new ArrayList<>();

    /**
     * The inbox containing messages for the client.
     */
    private ArrayList<Message> inbox = new ArrayList<>();

    /**
     * A list of dietary preferences for the client.
     */
    private ArrayList<String> dietaryPreferences = new ArrayList<>();

    /**
     * A list of the client's progress, organized by goal.
     */
    private List<Progress> progressByGoal;

    /**
     * The current status of the client, which can be either 'valid' or 'invalid'.
     */
    private String status; // Might have only the value 'invalid'/'valid'

    /**
     * The subscription plan of the client.
     */
    private PlanClient plan;

    // Dietary restrictions would be managed later

    /**
     * Constructor to create a new Client object with the given client name and
     * password.
     * This constructor initializes the client ID, sets the initial status as
     * 'valid',
     * subscribes the client to the basic plan, and adds the client to the database.
     *
     * @param clientName the name of the client
     * @param password   the password for the client
     */
    public Client(String clientName, String password) {
        this.clientName = clientName;
        this.password = password;
        this.progressByGoal = new ArrayList<>();
        this.clientId = "C" + idCounter++; // Generate unique client ID
        this.status = "valid"; // Set initial status to 'valid'
        this.plan = DatabaseService.getBasicPlanClient(); // Set the default plan
        DatabaseService.getBasicPlanClient().subscribeClient(this); // Subscribe client to the plan
        DatabaseService.addClient(this); // Add client to the database
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

    /**
     * Enrolls the client in a program by its program ID.
     * If the program is valid and the client is not already enrolled,
     * the client will be added to the program's list of enrolled clients.
     *
     * @param programId the ID of the program to enroll the client in
     */
    public void enrollInProgram(String programId) {
        Program selectedProgram = DatabaseService.getProgramById(programId);
        if (selectedProgram != null && !clientPrograms.contains(selectedProgram)) {
            clientPrograms.add(selectedProgram);
            selectedProgram.enrollClient(this);
        }
    }

    /**
     * Sets a goal for the client with a specific goal type, starting value, and
     * target value.
     * The new goal is added to the client's progress list.
     *
     * @param goalType    the type of the goal (e.g., weight loss, muscle gain)
     * @param startValue  the starting value for the goal
     * @param targetValue the target value to achieve
     * @return the created Progress object
     */
    public Progress setGoal(String goalType, double startValue, double targetValue) {
        Progress progress = new Progress(goalType, startValue, targetValue);
        progressByGoal.add(progress);
        return progress;
    }

    /**
     * Adds a progress goal to the client's list of goals for a specific program.
     *
     * @param progress the Progress object to be added
     */
    public void setGoalForAProgram(Progress progress) {
        progressByGoal.add(0, progress);
    }

    /**
     * Displays the progress summary of a specified goal based on its index in the
     * goal list.
     *
     * @param goalNumber the index of the goal in the list
     */
    public String displayGaolProgressIn(int goalNumber) {
        Progress progress = progressByGoal.get(goalNumber);
        String summery = progress.getProgressSummary();
        System.out.println(summery);
        return summery;
    }

    /**
     * Displays the progress summary for all of the client's goals.
     * If no goals are set, a message indicating this is printed.
     */
    public void displayGoalProgress() {
        if (progressByGoal.isEmpty()) {
            System.out.println("No goals set for this client.");
        } else {
            for (int i = 0; i < progressByGoal.size(); i++) {
                System.out.println("ID:" + i + "\t" + progressByGoal.get(i).getProgressSummary());
            }
        }
    }

    /**
     * Updates the progress of a specified goal by its index in the list.
     * The new value is applied to the selected goal.
     *
     * @param goalNumber the index of the goal in the list
     * @param newValue   the new value to update the goal's progress
     * @return the updated Progress object
     */
    public Progress updateGoalProgress(int goalNumber, double newValue) {
        progressByGoal.get(goalNumber).updateProgress(newValue);
        return progressByGoal.get(goalNumber);
    }

    /**
     * Allows the client to provide feedback with a specified feedback type and
     * message.
     * The feedback is stored in the client's feedback list and also added to the
     * database.
     *
     * @param feedbackType the type of feedback (e.g., complaint, suggestion, etc.)
     * @param message      the feedback message
     */
    public void writeFeedback(String feedbackType, String message) {
        Feedback temp = new Feedback(feedbackType, message, this);
        clientFeeds.add(temp);
        DatabaseService.addFeed(temp);
    }

    /**
     * Changes the client's subscription plan. The client's current plan is
     * canceled, and the new plan is applied.
     *
     * @param plan the new PlanClient to subscribe the client to
     */
    public void changeSubscription(PlanClient plan) {
        this.plan.cancelClientSubscription(this);
        this.plan = plan;
        plan.subscribeClient(this);
    }

    /**
     * Cancels the client's current subscription and resets their plan to the basic
     * plan.
     */
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

    public Message sendMessage(String recipientId, String title, String content) {// can send to all instructors
        Message message = new Message(title, content, clientId, recipientId);// display list of instructors to choose

        System.out.println("Message sent to " + recipientId + ": " + content);
        return message;
    }

    public void viewMessages() {
        if (inbox.isEmpty()) {
            System.out.println("No messages received.");
        } else {
            System.out.println("Messages for " + clientName + ":");
            int i = 1;
            for (Message message : inbox) {
                Instructor instructor = DatabaseService.getInstructorById(message.getSender());
                System.out
                        .println("message " + i++ + " : from " + instructor.getName() + ": Title'" + message.getTitle()
                                + "' , content '"
                                + message.getContent() + "'");
            }
        }
    }

    public void receiveMessage(Message message) {
        this.inbox.add(message);
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }

    /**
     * Replies to an original message with the specified reply content.
     * The reply message is sent to the original message sender ( instructor).
     *
     * @param originalMessage the message to reply to
     * @param replyContent    the content of the reply
     * @return the reply message
     */
    public Message replyToMessage(Message originalMessage, String replyContent) {
        Message reply = new Message("Re: " + originalMessage.getTitle(), replyContent,
                clientId, originalMessage.getSender());
        inbox.remove(originalMessage);
        System.out.println("Reply sent to " + originalMessage.getSender() + ": " + replyContent);

        return reply;
    }

    /**
     * Adds a dietary preference to the client's dietary preferences list.
     * If the preference already exists, it will not be added again.
     *
     * @param preference the dietary preference to add
     */
    public void addDietaryPreference(String preference) {
        if (!dietaryPreferences.contains(preference)) {
            dietaryPreferences.add(preference);
            System.out.println("Dietary preference added: " + preference);
        } else {
            System.out.println("This dietary preference already exists.");
        }
    }

    /**
     * Removes a dietary preference from the client's list.
     * If the preference does not exist, a message will be displayed.
     *
     * @param preference the dietary preference to remove
     */
    public void deleteDietaryPreference(String preference) {
        if (dietaryPreferences.contains(preference)) {
            dietaryPreferences.remove(preference);
            System.out.println("Dietary preference removed: " + preference);
        } else {
            System.out.println("Dietary preference not found.");
        }
    }

    /**
     * Returns the list of the client's dietary preferences.
     *
     * @return the dietary preferences list
     */
    public ArrayList<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    /**
     * Prints all of the client's dietary preferences if exist.
     */
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

    /**
     * Filters and returns a list of programs based on the specified difficulty.
     *
     * @param difficulty the difficulty level ( Beginner Intermediate Advanced)
     * @return a list of programs with the specified difficulty
     */
    public ArrayList<Program> filterProgramsByDifficulty(String difficulty) {
        ArrayList<Program> filteredPrograms = new ArrayList<>();

        for (Program program : DatabaseService.getPrograms()) {
            if (program.getDifficulty().equalsIgnoreCase(difficulty)) {
                filteredPrograms.add(program);
            }
        }

        return filteredPrograms;
    }

    /**
     * Filters and returns a list of programs based on the specified goal type.
     *
     * @param goal the goal type (e.g., "weight loss", "muscle gain")
     * @return a list of programs with the specified goal
     */
    public ArrayList<Program> filterProgramsByGoal(String goal) {
        ArrayList<Program> filteredPrograms = new ArrayList<>();

        for (Program program : DatabaseService.getPrograms()) {
            for (Progress progress : program.getProgramGoals()) {
                if (progress.getGoalType().equalsIgnoreCase(goal)) {
                    filteredPrograms.add(program);
                    break;
                }
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
