package triple.com;

public class Message {
    private String title;
    private String content;
    private Instructor instructor;
    private Client client;

    public Message(String title, String content, Instructor instructor, Client client) {
        this.title = title;
        this.content = content;
        this.instructor = instructor;
        this.client = client;
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

    public Client getRecipient() {
        return client;
    }
}
