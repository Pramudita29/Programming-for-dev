import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PostPanel extends JPanel {
    private Post post;
    private JLabel likesCount;
    private JLabel dislikesCount;
    private JPanel commentsPanel;
    private JTextArea commentTextArea;

    public PostPanel(Post post) {
        this.post = post;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245)); // Light gray background
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        initializeUI();
    }

    private void initializeUI() {
        JLabel authorLabel = new JLabel("Posted by: " + post.getAuthor());
        authorLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Post content with HTML support for better formatting
        JLabel postContent = new JLabel("<html><body style='padding: 5px;'>" + post.getContent() + "</body></html>");
        postContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Interactive Like and Dislike buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton likeButton = new JButton("Like");
        JButton dislikeButton = new JButton("Dislike");
        actionPanel.add(likeButton);
        actionPanel.add(dislikeButton);

        likesCount = new JLabel("Likes: " + post.getLikes());
        dislikesCount = new JLabel("Dislikes: " + post.getDislikes());

        // Comments section
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        commentsScrollPane.setPreferredSize(new Dimension(400, 100));
        commentsScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add comment section
        commentTextArea = new JTextArea(2, 20);
        JButton addCommentButton = new JButton("Add Comment");
        JPanel addCommentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addCommentPanel.add(commentTextArea);
        addCommentPanel.add(addCommentButton);

        // Listeners for buttons
        likeButton.addActionListener(e -> {
            post.likePost();
            updateLikesDislikesDisplay();
        });
        dislikeButton.addActionListener(e -> {
            post.dislikePost();
            updateLikesDislikesDisplay();
        });
        addCommentButton.addActionListener(e -> addComment());

        add(authorLabel);
        add(postContent);
        add(actionPanel);
        add(likesCount);
        add(dislikesCount);
        add(commentsScrollPane);
        add(addCommentPanel);

        updateCommentsDisplay();
    }

    private void addComment() {
        String commentText = commentTextArea.getText().trim();
        if (!commentText.isEmpty()) {
            // Assuming "Current User" is a placeholder. You might want to use the actual user name here.
            post.addComment(new Comment("Current User", commentText));
            commentTextArea.setText(""); // Clear the text area
            updateCommentsDisplay();
        }
    }

    private void updateLikesDislikesDisplay() {
        likesCount.setText("Likes: " + post.getLikes());
        dislikesCount.setText("Dislikes: " + post.getDislikes());
    }

    private void updateCommentsDisplay() {
        commentsPanel.removeAll();
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            JLabel commentLabel = new JLabel(comment.getAuthor() + ": " + comment.getContent());
            commentLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
            commentsPanel.add(commentLabel);
        }
        commentsPanel.revalidate();
        commentsPanel.repaint();
    }
}
