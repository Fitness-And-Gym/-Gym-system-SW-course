package triple.com;

import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

public class Instructor {
    private static int idCounter = 1;
    private String instructorId;
    private String name;
    private String password;
    private boolean loggedIn;
    private String status;// Might have the values "pending","valid","invalid"
    private PlanInstructor plan;
    private ArrayList<Message> inbox = new ArrayList<>();

    private List<Program> programs;

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

    // Be carful when creating a new instructor account it will be invalid you must
    // send a request to admin.
    public void sendRequest() {
        DatabaseService.sendRequest(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getId() {
        return this.instructorId;
    }

    // TO DO: check if the account is valid
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

    public Program updateProgramTitle(String title, String programId) {
        Program program = DatabaseService.getProgramById(programId);
        program.updateTitle(title);
        return program;
    }

    public Program updateProgramFees(int fees, String programId) {
        Program program = DatabaseService.getProgramById(programId);
        program.updateFees(fees);
        return program;
    }

    public void deleteMyProgram(String programId) {
        Program program = DatabaseService.getProgramById(programId);
        if (program.getInstructor().equals(this))
            DatabaseService.deleteProgram(program);
        else {
            System.out.println("didn't call the delete program func");
        }
    }

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

    public void addGoalToProgram(String programId, String type, double startValue, double targetValue) {
        Program program = DatabaseService.getProgramById(programId);
        program.addProgramGoal(new Progress(type, startValue, targetValue));
    }

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

    public String getStatus() {
        return status;
    }

    public PlanInstructor getPlan() {
        return plan;
    }

    public void setStatus(String status) {
        if (status == "valid" || status == "invalid")
            DatabaseService.getInstructorRequestById(this.instructorId);// to remove request from pending
        this.status = status;
    }

    public void updateName(String newName) {
        this.name = newName;
        System.out.println("Name updated successfully!");

    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully!");

    }

    public String getPassword() {
        return password;
    }

    public void changeSubscription(PlanInstructor plan) {
        this.plan.cancelInstructorSubscription(this);
        this.plan = plan;
        plan.subscribeInstructor(this);
    }

    public void deleteSubscription() {
        this.plan.cancelInstructorSubscription(this);
        this.plan = DatabaseService.getBasicPlanInstructor();
    }

    // public Article createArticle(String title,String content){
    // if(status=="valid")
    // {
    // Article article=new Article(title,content);
    // DatabaseService.addPendingArticle(article);
    // return article;
    // }
    // return null;
    // }

    public void viewMessages() {
        if (inbox.isEmpty()) {
            System.out.println("No messages received.");
        } else {
            System.out.println("Messages for " + name + ":");
            for (Message message : inbox) {
                System.out.println(message);
            }
        }
    }

    public void receiveMessage(Message message) {
        this.inbox.add(message);
    }

    public Message sendMessage(Client recipient, String title, String content) {
        Message message = new Message(title, content, this, recipient);
        recipient.receiveMessage(message);
        return message;
    }

    public void deleteMessage(Message message) {
        inbox.remove(message);
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }

}
