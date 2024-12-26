package triple.com;

import java.util.Date;

import triple.com.Client;
import triple.com.Database;

public class Feedback {
    private static int idCounter = 1;
    private String id;
    private Client author;                      // The ID of the user who provided the feedback
    private String feedbackType;  // Type of feedback (e.g., complaint, suggestion, etc.)
    private String message;       // The actual feedback message
    private Date timestamp;       // The time when the feedback was submitted

    // Constructor
    public Feedback(String feedbackType, String message,Client author) {
        this.id = "F" + idCounter++;
        this.feedbackType = feedbackType;
        this.message = message;
        this.timestamp = new Date();
        this.author=author;
        Database.addFeed(this);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String printFeedback() {
        return "------------------------------------\n" +
               "Feedback Type  : " + feedbackType + "\n" +
               "Client Name    : " + author.getClientName() + "\n" +
               "Message        : " + message + "\n" +
               "Timestamp      : " + timestamp + "\n" +
               "------------------------------------";
    }

}
