import java.time.LocalDateTime;
import java.util.*;

public class User {
    private String username;
    private String hashedPassword; // Assume this is securely hashed.
    private String email;
    private String bio;
    private Set<String> friends;
    private Set<String> friendRequests;
    private List<Post> posts;
    private List<Message> messages;
    private Map<String, Integer> interests; // Interest tags with interaction counts.
    private Map<String, String> privacySettings; // User's privacy settings.
    private List<String> activityLog; // Logs user's activities for transparency.

    // Constructor
    public User(String username, String hashedPassword, String email, String bio) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.bio = bio;
        this.friends = new HashSet<>();
        this.friendRequests = new HashSet<>();
        this.posts = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.interests = new HashMap<>();
        this.privacySettings = new HashMap<>();
        this.activityLog = new ArrayList<>();
        initializeDefaultPrivacySettings();
    }

    // Initialize default privacy settings for a new user
    private void initializeDefaultPrivacySettings() {
        privacySettings.put("profileVisibility", "Public");
        privacySettings.put("messagePrivacy", "FriendsOnly");
        logActivity("Initialized default privacy settings.");
    }

    // Log an activity with a timestamp
    private void logActivity(String activity) {
        String timestamp = LocalDateTime.now().toString();
        activityLog.add(timestamp + ": " + activity);
    }

    // Add a friend
    public void addFriend(String friendUsername) {
        friends.add(friendUsername);
        logActivity("Added friend: " + friendUsername);
    }

    // Send a friend request
    public void sendFriendRequest(String friendUsername) {
        friendRequests.add(friendUsername);
        logActivity("Sent friend request to: " + friendUsername);
    }

    // Post a message
    public void addPost(Post post) {
        posts.add(post);
        logActivity("Added a new post.");
    }

    // Send a message
    public void sendMessage(String recipient, String content) {
        Message message = new Message(this.username, recipient, content);
        messages.add(message);
        logActivity("Sent a message to: " + recipient);
    }

    // Update interests based on interactions
    public void updateInterests(String interest) {
        interests.merge(interest, 1, Integer::sum);
        logActivity("Updated interest in: " + interest);
    }

    // Update privacy setting
    public void updatePrivacySetting(String setting, String value) {
        privacySettings.put(setting, value);
        logActivity("Updated privacy setting: " + setting + " to " + value);
    }

    // Getters and Setters (Examples)
    public String getUsername() { return username; }
    public String getHashedPassword() { return hashedPassword; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public Set<String> getFriends() { return new HashSet<>(friends); }
    public List<String> getActivityLog() { return new ArrayList<>(activityLog); }
    public void setPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    
    public Set<String> getFriendRequests() {
        return new HashSet<>(friendRequests);
    }
    
    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }
    
    public Map<String, Integer> getInterests() {
        return new HashMap<>(interests);
    }
    
    public Map<String, String> getPrivacySettings() {
        return new HashMap<>(privacySettings);
    }
    
    public void setBio(String bio) {
        this.bio = bio;
        logActivity("Bio updated");
    }
    
    public void setEmail(String email) {
        this.email = email;
        logActivity("Email updated");
    }

    public void removeFriend(String friendUsername) {
        if (friends.remove(friendUsername)) {
            logActivity("Removed friend: " + friendUsername);
        }
    }
    
    public boolean isFriend(String friendUsername) {
        return friends.contains(friendUsername);
    }
    
    public boolean hasFriendRequestFrom(String friendUsername) {
        return friendRequests.contains(friendUsername);
    }
    
    public void acceptFriendRequest(String friendUsername) {
        if (friendRequests.remove(friendUsername)) {
            friends.add(friendUsername);
            logActivity("Accepted friend request from: " + friendUsername);
        }
    }
    
    public void declineFriendRequest(String friendUsername) {
        if (friendRequests.remove(friendUsername)) {
            logActivity("Declined friend request from: " + friendUsername);
        }
    }
    
    public void interactWithPost(Post post) {
        // Increment interest based on post's tags or content
        post.getTags().forEach(this::updateInterests);
        logActivity("Interacted with a post by: " + post.getAuthor());
    }
    
    public void likePost(Post post) {
        post.likePost(); // Assuming Post has a likePost() method
        logActivity("Liked a post by: " + post.getAuthor());
    }
    
    public void commentOnPost(Post post, String comment) {
        post.addComment(new Comment(this.username, comment)); // Assuming Post has an addComment() method
        logActivity("Commented on a post by: " + post.getAuthor());
    }
    
    
    // Example: Receive a message
    public void receiveMessage(Message message) {
        messages.add(message);
        logActivity("Received a message from: " + message.getSender());
    }
}
