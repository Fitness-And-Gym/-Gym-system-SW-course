package triple.com;

import java.util.Date;

//stored in the program
import triple.com.Client;
import triple.com.Program;
public class Review {
    private static int idCounter = 1;
    private String id;
    private Client author;        // The ID of the user who submitted the review
    private Program program;     // The ID of the program being reviewed
    private int rating;           // Rating out of 5
    private String comment;       // Optional comment
    private Date timestamp;       // The time when the review was submitted

    // Constructor
    public Review(Client client, Program program, int rating, String comment) {
        this.id = "R" + idCounter++;
        this.program = program;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = new Date();
    }
    public String getId() {
        return id;
    }

    public Client getAuthor() {
        return author;
    }

    public Program getProgram() {
        return program;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Review by " + author.getClientName() + " for program " + program.getTitle() + ": " + rating + " stars. " + comment + " on " + timestamp;
    }
}
