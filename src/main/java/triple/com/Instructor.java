package triple.com;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

/**
 * The Instructor class represents an instructor within the system. It stores
 * the
 * instructor's details such as name, password, login status, account status,
 * and
 * associated programs. It also provides functionality for logging in, updating
 * programs, and managing subscriptions.
 */
public class Instructor {

    private static int idCounter = 1; // Counter to generate unique instructor IDs
    private String instructorId; // Unique identifier for the instructor
    private String name; // Name of the instructor
    private String password; // Password for the instructor's account
    private boolean loggedIn; // Flag indicating whether the instructor is logged in
    private String status; // Account status ( "pending", "valid", "invalid")
    private PlanInstructor plan; // The subscription plan assigned to the instructor
    private ArrayList<Message> inbox = new ArrayList<>(); // Inbox for the instructor's messages

    public List<Program> getPrograms() {
        return programs;
    }
    private List<Program> programs; // List of programs associated with the instructor

    /**
     * Constructor to initialize an instructor's account with a name and password.
     * The account is initially set to "pending" status, and the instructor is
     * assigned the basic subscription plan.
     *
     * @param name     the name of the instructor
     * @param password the password for the instructor's account
     */
    public Instructor(String name, String password) {
        this.name = name;
        this.password = password;
        this.loggedIn = false;
        this.programs = new ArrayList<>();
        this.status = "pending";
        this.plan = DatabaseService.getBasicPlanInstructor();
        DatabaseService.getBasicPlanInstructor().subscribeInstructor(this);
        this.instructorId = "I" + idCounter++;
        sendRequest();
    }

    /**
     * Sends a request to the admin for account approval. This is called
     * when an instructor account is created and needs admin validation.
     */
    public void sendRequest() {
        DatabaseService.sendRequest(this);
    }

    // Getters and Setters

    /**
     * Returns the name of the instructor.
     *
     * @return the name of the instructor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the instructor.
     *
     * @param name the new name of the instructor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the login status of the instructor.
     *
     * @return true if the instructor is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the login status of the instructor.
     *
     * @param loggedIn true if the instructor is logged in, false otherwise
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Returns the unique instructor ID.
     *
     * @return the instructor ID
     */
    public String getId() {
        return this.instructorId;
    }

    /**
     * Attempts to log in an instructor with the provided name and password.
     * If the credentials match, the instructor is logged in.
     *
     * @param name     the name of the instructor
     * @param password the password for the instructor's account
     * @return the logged-in instructor if credentials are valid, otherwise null
     */
    public Instructor login(String name, String password) {
        for (Instructor instructor : DatabaseService.getInstructors()) {
            if (instructor.getName().equals(name) && instructor.getPassword().equals(password)) {
                instructor.setLoggedIn(true); // Set the loggedIn flag for the matched instructor

                System.out.println("Instructor logged in successfully!");
                return instructor;
            }
        }
        System.out.println("Invalid credentials!"); // If no match is found
        return null;
    }

    /**
     * Updates the title of an existing program.
     *
     * @param title     the new title for the program
     * @param programId the ID of the program to be updated
     * @return the updated program
     */
    public Program updateProgramTitle(String title, String programId) {
        Program program = DatabaseService.getProgramById(programId);
        program.updateTitle(title);
        return program;
    }

    /**
     * Updates the fees of an existing program.
     *
     * @param fees      the new fees for the program
     * @param programId the ID of the program to be updated
     * @return the updated program
     */
    public Program updateProgramFees(int fees, String programId) {
        Program program = DatabaseService.getProgramById(programId);
        program.updateFees(fees);
        return program;
    }

    /**
     * Deletes a program associated with the instructor.
     *
     * @param programId the ID of the program to be deleted
     */
    public void deleteMyProgram(String programId) {
        Program program = DatabaseService.getProgramById(programId);
        if (program.getInstructor().equals(this)) {
            DatabaseService.deleteProgram(program);
        } else {
            System.out.println("Did not call the delete program function.");
        }
    }

    /**
     * Creates a new program with the specified details (fees, title, duration, and
     * difficulty).
     * The instructor must be logged in to create the program.
     *
     * @param fees       the fees for the program
     * @param title      the title of the program
     * @param duration   the duration of the program in days
     * @param difficulty the difficulty level of the program
     * @return the created program if the instructor is logged in, null otherwise
     */
    public Program createProgram(int fees, String title, int duration, String difficulty) {
        if (loggedIn) {
            Program program = new Program(this, fees, title, duration, difficulty);
            programs.add(program);
            DatabaseService.addProgram(program);
            System.out.println("Program created successfully: " + title);
            return program;
        } else {
            System.out.println("Instructor must be logged in to create programs.");
            return null;
        }
    }

    /**
     * Adds a goal to an existing program. The goal is represented by a Progress
     * object,
     * which contains the type of goal (e.g., weight loss, skill improvement), the
     * starting value,
     * and the target value.
     *
     * @param programId   the ID of the program to which the goal will be added
     * @param type        the type of the goal (e.g., weight loss, skill
     *                    improvement)
     * @param startValue  the starting value of the goal
     * @param targetValue the target value of the goal
     */
    public void addGoalToProgram(String programId, String type, double startValue, double targetValue) {
        Program program = DatabaseService.getProgramById(programId);
        program.addProgramGoal(new Progress(type, startValue, targetValue));
    }

    /**
     * Displays a list of all programs created by the instructor.
     * The instructor must be logged in to view the programs.
     */
    public void viewPrograms() {
        if (loggedIn) {
            System.out.println("Programs by " + name + ":");
            for (Program program : programs) {
                System.out.println(program.getTitle() + " (" + program.getDuration() + " days)");
            }
        } else {
            System.out.println("Instructor must be logged in to view programs.");
        }
    }

    /**
     * Returns the current status of the instructor's account ("pending", "valid",
     * "invalid").
     *
     * @return the current status of the instructor
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the subscription plan of the instructor.
     *
     * @return the plan currently assigned to the instructor
     */
    public PlanInstructor getPlan() {
        return plan;
    }

    /**
     * Sets the status of the instructor's account. If the status is set to "valid"
     * or "invalid",
     * the request to become an instructor is removed from the pending state.
     *
     * @param status the new status to be set (either "valid" or "invalid")
     */
    public void setStatus(String status) {
        if (status == "valid" || status == "invalid")
            DatabaseService.getInstructorRequestById(this.instructorId); // to remove request from pending
        if (status == "valid") {
            DatabaseService.addInstructor(this);
        }
        this.status = status;
    }

    /**
     * Updates the name of the instructor.
     *
     * @param newName the new name to set for the instructor
     */
    public void updateName(String newName) {
        this.name = newName;
        System.out.println("Name updated successfully!");
    }

    /**
     * Updates the password of the instructor.
     *
     * @param newPassword the new password to set for the instructor
     */
    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully!");
    }

    /**
     * Returns the current password of the instructor.
     *
     * @return the current password of the instructor
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the subscription plan of the instructor. The current plan is
     * cancelled,
     * and the new plan is subscribed to.
     *
     * @param plan the new subscription plan to assign to the instructor
     */
    public void changeSubscription(PlanInstructor plan) {
        this.plan.cancelInstructorSubscription(this);
        this.plan = plan;
        plan.subscribeInstructor(this);
    }

    /**
     * Cancels the current subscription plan and assigns the basic plan to the
     * instructor.
     */
    public void deleteSubscription() {
        this.plan.cancelInstructorSubscription(this);
        this.plan = DatabaseService.getBasicPlanInstructor();
    }

    /**
     * Views all the messages in the instructor's inbox. If there are no messages, a
     * message indicating so is shown.
     */

    public void viewMessages() {
        if (inbox.isEmpty()) {
            System.out.println("No messages received.");
        } else {
            System.out.println("Messages for " + name + ":");
            int i = 1;
            for (Message message : inbox) {
                Client client = DatabaseService.getClientById(message.getSender());
                System.out.println(
                        "message " + i++ + " : from " + client.getClientName() + ": Title'" + message.getTitle()
                                + "' , content '"
                                + message.getContent() + "'");
            }
        }
    }

    /**
     * Receives a message and adds it to the instructor's inbox.
     *
     * @param message the message to be received
     */
    public void receiveMessage(Message message) {
        this.inbox.add(message);
    }

    /**
     * Sends a message to a client. The message includes a title, content, and the
     * instructor's ID.
     * The message is added to the recipient's inbox.
     *
     * @param recipient the client to whom the message is being sent
     * @param title     the title of the message
     * @param content   the content of the message
     * @return the sent message
     */

    public Message sendMessage(String recipientId, String title, String content) {// can send to all instructors
        Message message = new Message(title, content, instructorId, recipientId);// display list of instructors to
                                                                                 // choose

        System.out.println("Message sent to " + recipientId + ": " + content);
        return message;
    }

    // /**
    // * Deletes a specific message from the instructor's inbox.
    // *
    // * @param message the message to be deleted
    // */
    // public void deleteMessage(Message message) {
    // inbox.remove(message);
    // }

    /**
     * Returns the inbox of the instructor containing all received messages.
     *
     * @return the list of messages in the instructor's inbox
     */
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
                instructorId, originalMessage.getSender());
        inbox.remove(originalMessage);
        System.out.println("Reply sent to " + originalMessage.getSender() + ": " + replyContent);

        return reply;
    }

}
