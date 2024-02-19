import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDatabase {
    private List<User> users = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public boolean addUser(User user) {
        if (getUserByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        users.add(user);
        return true;
    }

    public Optional<User> getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public Optional<User> authenticateUser(String username, String hashedPassword) {
        return getUserByUsername(username)
                .filter(user -> user.getHashedPassword().equals(hashedPassword));
    }

    public void saveMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessagesBetweenUsers(String user1, String user2) {
        return messages.stream()
                .filter(message -> (message.getSender().equals(user1) && message.getReceiver().equals(user2)) ||
                                   (message.getSender().equals(user2) && message.getReceiver().equals(user1)))
                .collect(Collectors.toList());
    }

    public List<User> searchUsersByUsername(String username) {
        return users.stream()
                    .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
                    .collect(Collectors.toList());
    }

    public List<User> searchUsersByEmail(String email) {
        return users.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
    }

    // Additional functionality methods

    // Remove a user from the database
    public boolean removeUser(String username) {
        return users.removeIf(user -> user.getUsername().equals(username));
    }

    // Update user information
    public boolean updateUserBio(String username, String newBio) {
        Optional<User> userOpt = getUserByUsername(username);
        userOpt.ifPresent(user -> user.setBio(newBio));
        return userOpt.isPresent();
    }

    // Get all users in the database
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Get all messages in the database
    public List<Message> getAllMessages() {
        return new ArrayList<>(messages);
    }
     /**
     * Updates the information of an existing user in the database.
     * @param userToUpdate The user object containing the updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateUser(User userToUpdate) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            // Assuming the unique identifier is the username, replace this with your unique identifier
            if (user.getUsername().equals(userToUpdate.getUsername())) {
                users.set(i, userToUpdate);
                return true; // Update successful
            }
        }
        return false; // User not found, update failed
    }
}
