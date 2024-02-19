import java.time.LocalDateTime;

public class Comment {
    private String author;
    private String content;
    private LocalDateTime timestamp; // Timestamp for when the comment was posted
    private int likes; // Number of likes for the comment

    // Constructor
    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now(); // Set the timestamp to the current time
        this.likes = 0; // Initialize likes to 0
    }

    // Getters
    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getLikes() {
        return likes;
    }

    // Method to increment the number of likes
    public void likeComment() {
        this.likes++;
    }

    // toString method for displaying comment information
    @Override
    public String toString() {
        return author + ": " + content + " (Posted on " + timestamp + ", Likes: " + likes + ")";
    }
}
