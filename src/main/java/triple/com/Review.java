package triple.com;

import java.util.Date;

//stored in the program
import triple.com.Client;
import triple.com.Program;

/**
 * Represents a review submitted by a client for a specific fitness program.
 * The review contains a rating, optional comment, timestamp, and references to the author and the program.
 */
public class Review {
    private static int idCounter = 1; // Counter for generating unique review IDs
    private String id;                // Unique ID for the review
    private Client author;            // The client who submitted the review
    private Program program;          // The program being reviewed
    private int rating;               // Rating out of 5
    private String comment;           // Optional comment about the program
    private Date timestamp;           // The time when the review was submitted

    /**
     * Constructs a new Review object with the specified client, program, rating, and comment.
     * The review is automatically assigned a unique ID and a timestamp.
     *
     * @param client   The client submitting the review.
     * @param program  The program being reviewed.
     * @param rating   The rating given to the program (out of 5).
     * @param comment  The optional comment about the program.
     */
    public Review(Client client, Program program, int rating, String comment) {
        this.id = "R" + idCounter++; // Generates a unique ID for each review
        this.author = client;
        this.program = program;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = new Date(); // Assigns the current date and time as the timestamp
    }

    /**
     * Gets the unique ID of the review.
     *
     * @return The unique ID of the review.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the client who authored the review.
     *
     * @return The client who submitted the review.
     */
    public Client getAuthor() {
        return author;
    }

    /**
     * Gets the program that was reviewed.
     *
     * @return The program being reviewed.
     */
    public Program getProgram() {
        return program;
    }

    /**
     * Gets the rating given to the program (out of 5).
     *
     * @return The rating of the program.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the optional comment submitted along with the review.
     *
     * @return The comment about the program.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the timestamp when the review was submitted.
     *
     * @return The timestamp of the review submission.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a string representation of the review, including the author's name,
     * the program title, rating, comment, and timestamp.
     *
     * @return A string representing the review.
     */
    @Override
    public String toString() {
        return "Review by " + author.getClientName() + " for program " + program.getTitle() + ": " + rating + " stars. " + comment + " on " + timestamp;
    }
}
