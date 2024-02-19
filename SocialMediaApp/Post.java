import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Post {
    private String id;
    private String content;
    private String author;
    private LocalDateTime timestamp;
    private List<Comment> comments;
    private int likes;
    private int dislikes;
    private List<String> tags;
    private List<String> editHistory;

    public Post(String content, String author, List<String> tags) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.author = author;
        this.timestamp = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
        this.tags = new ArrayList<>(tags);
        this.editHistory = new ArrayList<>();
    }

    // Add a comment to the post
    public void addComment(Comment comment) {
        comments.add(comment);
        // Future: Notify the post author of the new comment
    }

    // Increment the likes count
    public void likePost() {
        likes++;
        // Future: Notify the post author of the new like
    }

    // Increment the dislikes count
    public void dislikePost() {
        dislikes++;
        // Future: Notify the post author of the new dislike
    }

    // Edit the post content
    public void editPost(String newContent) {
        editHistory.add(this.content); // Add current content to edit history
        this.content = newContent;
        // Future: Notify followers of the post update
    }

    // Getters
    public String getId() { return id; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public List<Comment> getComments() { return new ArrayList<>(comments); }
    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }
    public List<String> getTags() { return new ArrayList<>(tags); }
    public List<String> getEditHistory() { return new ArrayList<>(editHistory); }

    @Override
    public String toString() {
        return String.format("%s: %s (Posted on %s) - Likes: %d, Dislikes: %d, Comments: %d, Tags: %s", author, content, timestamp, likes, dislikes, comments.size(), String.join(", ", tags));
    }

    // Unique ID generation for the post (uses UUID for simplicity here)
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
