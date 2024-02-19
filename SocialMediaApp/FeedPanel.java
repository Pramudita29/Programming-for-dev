import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FeedPanel extends JPanel {
    private PostDatabase postDatabase;
    private User currentUser;
    private JPanel postsContainer;
    private JTextField postField; // Moved to a class member to clear it after posting

    public FeedPanel(PostDatabase postDatabase, User currentUser) {
        this.postDatabase = postDatabase;
        this.currentUser = currentUser;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        postsContainer = new JPanel();
        postsContainer.setLayout(new BoxLayout(postsContainer, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(postsContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        postField = new JTextField(); // Initialize postField
        JButton postButton = new JButton("Post");
        postButton.addActionListener(this::postContent);
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.add(postField, BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);

        add(postPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        refreshFeed();
    }

    private void postContent(ActionEvent e) {
        String content = postField.getText();
        if (!content.isEmpty()) {
            Post post = new Post(content, currentUser.getUsername(), List.of()); // Assuming Post constructor includes tags, pass empty list if not using tags
            postDatabase.addPost(post);
            postField.setText(""); // Clear the post field
            postsContainer.add(createPostPanel(post), 0); // Add new post panel at the top
            postsContainer.revalidate();
            postsContainer.repaint();
        }
    }

    private void refreshFeed() {
        List<Post> posts = postDatabase.getAllPostsDescending(); // Assumes posts are fetched in descending order of their creation or relevance
        postsContainer.removeAll();
        posts.forEach(post -> postsContainer.add(createPostPanel(post)));
        postsContainer.revalidate();
        postsContainer.repaint();
    }

    private JPanel createPostPanel(Post post) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel authorLabel = new JLabel("Posted by " + post.getAuthor());
        JLabel contentLabel = new JLabel("<html><p style='width:200px'>" + post.getContent() + "</p></html>");
        JLabel timestampLabel = new JLabel(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(post.getTimestamp()));
        JLabel likeLabel = new JLabel("Likes: " + post.getLikes()); // Display like count

        JButton likeButton = new JButton("Like");
        likeButton.addActionListener(e -> {
            post.likePost(); // This method should ideally notify the PostDatabase or similar to update the post's like count persistently
            likeLabel.setText("Likes: " + post.getLikes()); // Update like count display
        });

        panel.add(authorLabel);
        panel.add(contentLabel);
        panel.add(timestampLabel);
        panel.add(likeLabel); // Add the like count label
        panel.add(likeButton);

        return panel;
    }
}
