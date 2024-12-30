package triple.com;

import java.util.Date;
import triple.com.DatabaseService;
import triple.com.Instructor;

/**
 * Represents an article that can be submitted by an instructor. Articles can
 * be of various types ( ARTICLE, TIP, RECIPE) and are subject to approval by an admin.
 * The class provides functionality to manage article details, including submission, approval, and categorization.
 */
public class Article {
    private static int idCounterArticle = 1;
    private String id;
    private String title;
    private String content;
    private Instructor author;
    private Date submissionDate;
    private boolean isApproved;
    private ArticleType type;

    /**
     * Constructor to create a new article with the provided title, content, author, and type.
     * Initializes the submission date and sets approval status to false by default.
     * The article is added to the database as a request for approval.
     *
     * @param title the title of the article
     * @param content the main content of the article
     * @param author the instructor who authored the article
     * @param type the type of the article (ARTICLE, TIP, RECIPE)
     */
    public Article(String title, String content, Instructor author, ArticleType type) {
        this.id = "A" + idCounterArticle++;
        this.title = title;
        this.content = content;
        this.author = author;
        this.submissionDate = new Date();
        this.isApproved = false; // Default to false until admin approval
        this.type = type;
        DatabaseService.addArticleRequest(this);
    }

    /**
     * Gets the unique identifier of the article.
     *
     * @return the article's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the article.
     *
     * @param id the article's new ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the article.
     *
     * @return the article's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the article.
     *
     * @param title the new title for the article
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content of the article.
     *
     * @return the article's content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the article.
     *
     * @param content the new content for the article
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the author of the article.
     *
     * @return the article's author
     */
    public Instructor getAuthor() {
        return author;
    }

    /**
     * Sets the author of the article.
     *
     * @param author the new author for the article
     */
    public void setAuthor(Instructor author) {
        this.author = author;
    }

    /**
     * Gets the submission date of the article.
     *
     * @return the article's submission date
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Sets the submission date of the article.
     *
     * @param submissionDate the new submission date for the article
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    /**
     * Gets the approval status of the article.
     *
     * @return true if the article is approved, false otherwise
     */
    public boolean isApproved() {
        return isApproved;
    }

    /**
     * Sets the approval status of the article.
     * If approved, the article is moved from the request list to the approved list in the database.
     *
     * @param approved the new approval status
     */
    public void setApproved(boolean approved) {
        if (approved) {
            DatabaseService.removeArticleRequest(this);
            DatabaseService.addArticle(this);
        }
        isApproved = approved;
    }

    /**
     * Gets the type of the article (e.g., ARTICLE, TIP, RECIPE).
     *
     * @return the article type
     */
    public ArticleType getType() {
        return type;
    }

    /**
     * Sets the type of the article (e.g., ARTICLE, TIP, RECIPE).
     *
     * @param type the new type for the article
     */
    public void setType(ArticleType type) {
        this.type = type;
    }
}
