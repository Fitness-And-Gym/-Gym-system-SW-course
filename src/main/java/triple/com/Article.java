package triple.com;

import java.util.Date;

import triple.com.Database;
import triple.com.Instructor;

public class Article {
    private static int idCounterArticle = 1;
    private String id; // Unique identifier for the article
    private String title; // Title of the article
    private String content; // Main content of the article (text, recipes, or tips)
    private Instructor author; // Name or ID of the instructor or user who submitted the article
    private Date submissionDate; // Date when the article was submitted
    private boolean isApproved; // Indicates whether the article is approved by an admin
    private ArticleType type; // Enum for categorizing (e.g., ARTICLE, TIP, RECIPE)

    // Constructor
    public Article(String title, String content, Instructor author, ArticleType type) {
        this.id = "A" + idCounterArticle++;
        this.title = title;
        this.content = content;
        this.author = author;
        this.submissionDate = new Date();
        this.isApproved = false; // Default to false until admin approval
        this.type = type;
        Database.articleRequests.add(this);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instructor getAuthor() {
        return author;
    }

    public void setAuthor(Instructor author) {
        this.author = author;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        if (approved ) {
            Database.articleRequests.remove(this);
            Database.addArticle(this);
        }
        isApproved = approved;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    // // Utility Method
    // @Override
    // public String toString() {
    // return "Article{" +
    // "id='" + id + '\'' +
    // ", title='" + title + '\'' +
    // ", content='" + content + '\'' +
    // ", author='" + author + '\'' +
    // ", submissionDate=" + submissionDate +
    // ", isApproved=" + isApproved +
    // ", type=" + type +
    // '}';
    // }
}
