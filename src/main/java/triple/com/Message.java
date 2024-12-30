package triple.com;

public class Message {
    private String title;
    private String content;
    private Instructor instructor;
    private String clientId;

    public Message(String title, String content, Instructor instructor, String clientId) {
        this.title = title;
        this.content = content;
        this.instructor = instructor;
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Instructor getSender() {
        return instructor;
    }

    // public Client getRecipient() {
    // return clientId;
    // }
}
