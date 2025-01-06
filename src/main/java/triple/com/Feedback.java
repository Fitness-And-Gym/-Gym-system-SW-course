package triple.com;

import java.util.Date;

/**
 * The Feedback class represents feedback submitted by clients. It stores information
 * about the type of feedback, the content of the message, the client who submitted it,
 * and the timestamp of when the feedback was provided.
 */
public class Feedback {

    private static int idCounter = 1;
    private String id;
    private Client author;
    private String feedbackType;
    private String message;
    private Date timestamp;

    /**
     * Constructs a Feedback object with the specified type, message, and author.
     * The feedback is automatically added to the database.
     *
     * @param feedbackType the type of feedback (e.g., complaint, suggestion)
     * @param message the content of the feedback message
     * @param author the client who is submitting the feedback
     */
    public Feedback(String feedbackType, String message, Client author) {
        this.id = "F" + idCounter++; // Generate unique ID for feedback
        this.feedbackType = feedbackType;
        this.message = message;
        this.timestamp = new Date(); // Set the current date and time as timestamp
        this.author = author;
        DatabaseService.addFeed(this); // Add the feedback to the database
    }




    /**
     * Returns the type of feedback (e.g., complaint, suggestion).
     *
     * @return the feedback type
     */
    public String getFeedbackType() {
        return feedbackType;
    }

    /**
     * Returns the message content of the feedback.
     *
     * @return the feedback message
     */
    public String getMessage() {
        return message;
    }

  

    /**
     * Formats and prints the feedback details as a string.
     *
     * @return the formatted feedback details
     */
    public String printFeedback() {
        return "------------------------------------\n" +
                "Feedback Type  : " + feedbackType + "\n" +
                "Client Name    : " + author.getClientName() + "\n" +
                "Message        : " + message + "\n" +
                "Timestamp      : " + timestamp + "\n" +
                "------------------------------------";
    }
}
