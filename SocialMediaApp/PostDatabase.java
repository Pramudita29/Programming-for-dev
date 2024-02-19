import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostDatabase {
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(0, post);
    }

    public boolean removePost(String postId) {
        return posts.removeIf(post -> post.getId().equals(postId));
    }

    public boolean updatePost(String postId, String newContent) {
        Post post = findPostById(postId);
        if (post != null) {
            post.editPost(newContent);
            return true;
        }
        return false;
    }

    public List<Post> getAllPostsDescending() {
        return posts.stream()
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByAuthor(String author) {
        return posts.stream()
                .filter(post -> post.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Post> searchPostsByContent(String keyword) {
        return posts.stream()
                .filter(post -> post.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

     public List<Post> getRecommendedPostsForUser(User user) {
        // Example criteria: User interests, posts by friends, and trending posts
        
        // Fetch user interests and friends' usernames from User object (assuming these methods exist)
        Set<String> userInterests = user.getInterests().keySet(); // Assume this returns a Set<String> of interest tags
        Set<String> friendsUsernames = user.getFriends(); // Assume this returns a Set<String> of friends' usernames

        // Trending Posts: High number of likes/comments recently
        List<Post> trendingPosts = posts.stream()
                .filter(post -> post.getLikes() > 50) // Arbitrary threshold for likes
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // Friends' Posts: Recent posts from friends
        List<Post> friendsPosts = posts.stream()
                .filter(post -> friendsUsernames.contains(post.getAuthor()))
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // Interest-Based Posts: Posts matching user interests
        List<Post> interestBasedPosts = posts.stream()
                .filter(post -> post.getTags().stream().anyMatch(userInterests::contains))
                .sorted(Comparator.comparing(Post::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // Combine and deduplicate recommendations
        return Stream.of(trendingPosts, friendsPosts, interestBasedPosts)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    // Utility method to find a post by ID (if needed)
    private Post findPostById(String postId) {
        return posts.stream().filter(post -> post.getId().equals(postId)).findFirst().orElse(null);
    }

}