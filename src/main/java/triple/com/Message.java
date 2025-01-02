package triple.com;

/**
 * The Message class represents a message sent by an instructor to a client.
 * It contains the title, content, sender (instructor), and the recipient's
 * client ID.
 */
public class Message {
    private String title;
    private String content;
    private String sender;// ID
    private String receiver;// ID

    /**
     * Constructs a new message with the specified title, content, sender, and
     * recipient ID.
     *
     * @param title    the title of the message
     * @param content  the content of the message
     * @param sender   the ID of the sender who is sending the message
     * @param receiver the ID of the receiver receiving the message
     */
    public Message(String title, String content, String sender, String receiver) {// clients Id start with c and
        // instructor id start with i
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;// messages are not stored in the database they are temp messages once deleted
                                 // from client and instructor it never can be retrived
        putInbox();
    }

    public void putInbox() {

        Boolean isRecipientClient = receiver.charAt(0) == 'C' ? true : false;
        System.out.println(
                "sender ID=" + sender + "recip ID=" + receiver + "isReciptionetClient=" + isRecipientClient.toString());
        if (isRecipientClient)// add message to client inbox
        {
            Client client = DatabaseService.getClientById(receiver);
            // if (!client.getInbox().contains(this))// if not in the inbox add it
            client.receiveMessage(this);
        } else {
            Instructor instructor = DatabaseService.getInstructorById(receiver);
            // if (!instructor.getInbox().contains(this))
            instructor.receiveMessage(this);
        }
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
     * Returns the sender who sent the message.
     *
     * @return the sender who sent the message
     */
    public String getSender() {
        return sender;
    }

    public String getRecipient() {

        return receiver;
    }
}
