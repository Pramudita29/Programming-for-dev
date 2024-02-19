import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    // Maps a user to the set of users they are following.
    private Map<User, Set<User>> following;

    // Maps a user to the set of users they are friends with.
    private Map<User, Set<User>> friends;

    public Graph() {
        this.following = new HashMap<>();
        this.friends = new HashMap<>();
    }

    /**
     * Ensures a user is added to the graph with empty sets of following and friends.
     * @param user The user to add.
     */
    public void addUser(User user) {
        following.putIfAbsent(user, new HashSet<>());
        friends.putIfAbsent(user, new HashSet<>());
    }

    /**
     * Removes a user from the graph, including all references in following and friends mappings.
     * @param user The user to remove.
     */
    public void removeUser(User user) {
        // Remove the user from others' follow lists and friend lists
        following.values().forEach(followList -> followList.remove(user));
        friends.values().forEach(friendList -> friendList.remove(user));
        
        // Remove the user's own entries from the maps
        following.remove(user);
        friends.remove(user);
    }

    /**
     * Records that one user follows another.
     * @param user1 The follower.
     * @param user2 The followed.
     */
    public void followUser(User user1, User user2) {
        following.getOrDefault(user1, new HashSet<>()).add(user2);
    }

    /**
     * Stops one user from following another.
     * @param user1 The follower.
     * @param user2 The followed to unfollow.
     */
    public void unfollowUser(User user1, User user2) {
        following.getOrDefault(user1, new HashSet<>()).remove(user2);
    }

    /**
     * Checks if one user is following another.
     * @param user1 The potential follower.
     * @param user2 The potential followed.
     * @return True if user1 is following user2, false otherwise.
     */
    public boolean isFollowing(User user1, User user2) {
        return following.getOrDefault(user1, new HashSet<>()).contains(user2);
    }

    /**
     * Establishes a mutual friendship between two users.
     * @param user1 One user.
     * @param user2 The other user.
     */
    public void addFriendship(User user1, User user2) {
        friends.getOrDefault(user1, new HashSet<>()).add(user2);
        friends.getOrDefault(user2, new HashSet<>()).add(user1);
    }

    /**
     * Removes a mutual friendship between two users.
     * @param user1 One user.
     * @param user2 The other user.
     */
    public void removeFriendship(User user1, User user2) {
        friends.getOrDefault(user1, new HashSet<>()).remove(user2);
        friends.getOrDefault(user2, new HashSet<>()).remove(user1);
    }

    /**
     * Checks if two users are friends.
     * @param user1 One user.
     * @param user2 The other user.
     * @return True if the users are friends, false otherwise.
     */
    public boolean areFriends(User user1, User user2) {
        return friends.getOrDefault(user1, new HashSet<>()).contains(user2);
    }

    /**
     * Retrieves the set of users that a given user is following.
     * @param user1 The user in question.
     * @return A set of users user1 is following.
     */
    public Set<User> getFollowing(User user1) {
        return new HashSet<>(following.getOrDefault(user1, new HashSet<>()));
    }

    /**
     * Retrieves the set of users that a given user is friends with.
     * @param user1 The user in question.
     * @return A set of user1's friends.
     */
    public Set<User> getFriends(User user1) {
        return new HashSet<>(friends.getOrDefault(user1, new HashSet<>()));
    }
}
