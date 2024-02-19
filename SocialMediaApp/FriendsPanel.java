import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class FriendsPanel extends JPanel {
    private UserDatabase userDatabase;
    private User currentUser;
    private JList<String> friendsList;
    private DefaultListModel<String> friendsListModel; // Use DefaultListModel for dynamic updates
    private JTextField searchField;
    private JButton addButton, removeButton; // Added remove button

    public FriendsPanel(UserDatabase userDatabase, User currentUser) {
        this.userDatabase = userDatabase;
        this.currentUser = currentUser;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        friendsListModel = new DefaultListModel<>();
        currentUser.getFriends().forEach(friendsListModel::addElement); // Populate list model
        friendsList = new JList<>(friendsListModel);
        JScrollPane scrollPane = new JScrollPane(friendsList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        addButton = new JButton("Add Friend");
        removeButton = new JButton("Remove Friend"); // Button to remove a friend
        
        actionPanel.add(searchField);
        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        add(actionPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addFriend());
        removeButton.addActionListener(e -> removeFriend()); // Add listener to remove friend
    }

    private void addFriend() {
        String username = searchField.getText().trim();
        userDatabase.getUserByUsername(username).ifPresentOrElse(user -> {
            if (!currentUser.isFriend(username)) {
                currentUser.addFriend(username);
                refreshFriendsList();
                JOptionPane.showMessageDialog(this, "Friend added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Already friends.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }, () -> JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE));
    }

    private void removeFriend() {
        String username = friendsList.getSelectedValue(); // Get selected friend from list
        if (username != null && currentUser.isFriend(username)) {
            currentUser.removeFriend(username); // Assuming removeFriend method exists
            refreshFriendsList();
            JOptionPane.showMessageDialog(this, "Friend removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refreshFriendsList() {
        friendsListModel.clear(); // Clear and repopulate the list model for dynamic updates
        currentUser.getFriends().forEach(friendsListModel::addElement);
    }
}
