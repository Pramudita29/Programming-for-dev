import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private String id; // Unique identifier for the message
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp; // Timestamp for when the message was sent
    private boolean isRead; // Message read status
    private String replyToId; // Optional ID for replying to a specific message

    // Constructor updated to include message creation timestamp and optionally a replyToId
    public Message(String sender, String receiver, String content, String replyToId) {
        this.id = UUID.randomUUID().toString(); // Robust ID generation
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now(); // Capture the current time as the message timestamp
        this.isRead = false; // Default to false
        this.replyToId = replyToId; // Can be null if not a reply
    }

    // Additional constructor for messages not replying to another message
    public Message(String sender, String receiver, String content) {
        this(sender, receiver, content, null);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getReplyToId() {
        return replyToId;
    }

    // Optional: Implement method for encryption/decryption if required

    @Override
    public String toString() {
        return "Message from " + sender + " to " + receiver + ": " + content + " [" + timestamp + "]";
    }
}
