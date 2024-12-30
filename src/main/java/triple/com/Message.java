package triple.com;

/**
 * The Message class represents a message sent by an instructor to a client.
 * It contains the title, content, sender (instructor), and the recipient's client ID.
 */
public class Message {
    private String title;
    private String content;
    private Instructor instructor;
    private String clientId;

    /**
     * Constructs a new message with the specified title, content, instructor, and recipient client ID.
     *
     * @param title the title of the message
     * @param content the content of the message
     * @param instructor the instructor who is sending the message
     * @param clientId the ID of the client receiving the message
     */
    public Message(String title, String content, Instructor instructor, String clientId) {
        this.title = title;
        this.content = content;
        this.instructor = instructor;
        this.clientId = clientId;
    }

    /**
     * Returns the title of the message.
     *
     * @return the title of the message
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the content of the message.
     *
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the instructor who sent the message.
     *
     * @return the instructor who sent the message
     */
    public Instructor getSender() {
        return instructor;
    }

    // public Client getRecipient() {
    //     return clientId;
    // }
}
