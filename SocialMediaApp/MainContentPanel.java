import javax.swing.*;
import java.awt.*;

public class MainContentPanel extends JPanel {
    private User currentUser;
    private PostDatabase postDatabase;
    private UserDatabase userDatabase;
    private Runnable onLogout;

    public MainContentPanel(User currentUser, PostDatabase postDatabase, UserDatabase userDatabase, Runnable onLogout) {
        this.currentUser = currentUser;
        this.postDatabase = postDatabase;
        this.userDatabase = userDatabase;
        this.onLogout = onLogout;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Feed Panel
        FeedPanel feedPanel = new FeedPanel(postDatabase, currentUser);
        tabbedPane.addTab("Feed", null, feedPanel, "See latest posts");

        // Friends Panel
        FriendsPanel friendsPanel = new FriendsPanel(userDatabase, currentUser);
        tabbedPane.addTab("Friends", null, friendsPanel, "Manage your friends");

        // Messaging Panel
        MessagingPanel messagingPanel = new MessagingPanel(currentUser, userDatabase);
        tabbedPane.addTab("Messages", null, messagingPanel, "Chat with friends");

        // User Profile Panel
        UserProfilePanel userProfilePanel = new UserProfilePanel(currentUser, userDatabase, onLogout);
        tabbedPane.addTab("Profile", null, userProfilePanel, "View and edit your profile");

        add(tabbedPane, BorderLayout.CENTER);
    }
}
