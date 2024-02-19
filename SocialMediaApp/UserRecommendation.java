import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class UserRecommendation {

    private Graph userConnections;
    private static final int MAX_RECOMMENDATIONS = 100; // Define a constant for the maximum number of recommendations

    public UserRecommendation(Graph userConnections) {
        this.userConnections = userConnections;
    }

    // Optimized recommendation algorithm using breadth-first search (BFS)
    public Set<User> recommendUsersToFollow(User currentUser) {
        Set<User> recommendations = new HashSet<>();
        Set<User> visited = new HashSet<>();
        Queue<User> queue = new LinkedList<>();

        // Start BFS from the current user's friends
        for (User friend : userConnections.getFriends(currentUser)) {
            queue.add(friend);
            visited.add(friend);
        }

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            User user = queue.poll();
            recommendations.add(user);

            // Stop traversing if the recommendations set becomes too large
            if (recommendations.size() >= MAX_RECOMMENDATIONS) {
                break;
            }

            // Add unvisited friends of the current user to the queue
            for (User friend : userConnections.getFriends(user)) {
                if (!visited.contains(friend) && !friend.equals(currentUser)) {
                    queue.add(friend);
                    visited.add(friend);
                }
            }
        }

        // Remove the current user and their direct friends from recommendations
        recommendations.removeAll(userConnections.getFriends(currentUser));

        return recommendations;
    }
}
