import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecommendationPanel extends JPanel {
    private User currentUser;
    private PostDatabase postDatabase;

    public RecommendationPanel(User currentUser, PostDatabase postDatabase) {
        this.currentUser = currentUser;
        this.postDatabase = postDatabase;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240)); // Set a light background color
        refreshRecommendations(); // Initialize UI with recommendations
    }

    // Method to refresh the list of recommendations
    private void refreshRecommendations() {
        removeAll(); // Clear existing content
        List<Post> recommendedPosts = postDatabase.getRecommendedPostsForUser(currentUser);

        if (recommendedPosts.isEmpty()) {
            add(new JLabel("No recommendations available."));
        } else {
            for (Post post : recommendedPosts) {
                add(createPostPanel(post));
            }
        }
        revalidate(); // Revalidate and repaint to reflect changes
        repaint();
    }

    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        postPanel.setBackground(Color.WHITE); // Set a white background for each post panel

        JLabel authorLabel = new JLabel("Posted by: " + post.getAuthor());
        authorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel contentLabel = new JLabel("<html><p style='padding: 5px;'>" + post.getContent() + "</p></html>");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton likeButton = new JButton("Like");
        JButton commentButton = new JButton("Comment"); // Additional button for comments
        
        likeButton.addActionListener(e -> {
            post.likePost();
            refreshRecommendations(); // Refresh to reflect the like count change
        });

        commentButton.addActionListener(e -> {
            // Open a dialog or another panel for commenting
        });

        buttonsPanel.add(likeButton);
        buttonsPanel.add(commentButton);

        postPanel.add(authorLabel);
        postPanel.add(contentLabel);
        postPanel.add(buttonsPanel);

        return postPanel;
    }
}
