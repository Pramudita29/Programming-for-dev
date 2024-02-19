import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ContentRecommendation{
    
    private Graph userConnections; // Your graph data structure for the social network
    private List<Post> allPosts; // List of all posts in the system
    
    public ContentRecommendation(Graph userConnections, List<Post> allPosts) {
        this.userConnections = userConnections;
        this.allPosts = allPosts;
    }

    // Recommend posts for a given user
    public List<Post> recommendPostsForUser(User user, int limit) {
        Set<User> extendedNetwork = getExtendedNetwork(user);
        List<Post> networkPosts = filterPostsByNetwork(extendedNetwork);

        // Incorporate time sensitivity and personalization in ranking
        return rankAndLimitPosts(networkPosts, user, limit);
    }

    private Set<User> getExtendedNetwork(User user) {
        Set<User> directFriends = userConnections.getFriends(user);
        Set<User> extendedNetwork = new HashSet<>(directFriends);
        for (User friend : directFriends) {
            extendedNetwork.addAll(userConnections.getFriends(friend));
        }
        extendedNetwork.remove(user);
        return extendedNetwork;
    }

    private List<Post> filterPostsByNetwork(Set<User> extendedNetwork) {
        return allPosts.stream()
                .filter(post -> extendedNetwork.contains(post.getAuthor()))
                .collect(Collectors.toList());
    }

    private List<Post> rankAndLimitPosts(List<Post> posts, User user, int limit) {
        LocalDateTime now = LocalDateTime.now();
        return posts.stream()
                .sorted((post1, post2) -> {
                    long daysOld1 = ChronoUnit.DAYS.between(post1.getTimestamp(), now);
                    long daysOld2 = ChronoUnit.DAYS.between(post2.getTimestamp(), now);
                    int score1 = post1.getLikes() - (int)daysOld1 + post1.getComments().size();
                    int score2 = post2.getLikes() - (int)daysOld2 + post2.getComments().size();
                    return Integer.compare(score2, score1);
                })
                .limit(limit)
                .collect(Collectors.toList());
    }
}
