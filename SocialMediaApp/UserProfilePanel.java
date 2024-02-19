import javax.swing.*;
import java.awt.*;

public class UserProfilePanel extends JPanel {
    private User currentUser;
    private UserDatabase userDatabase; // Assuming this is needed for updating user info
    private Runnable onLogout; // A callback to execute when the user logs out

    private JLabel usernameLabel;
    private JTextField emailField;
    private JTextArea bioTextArea;
    private JButton saveButton;
    private JButton logoutButton; // Logout button

    public UserProfilePanel(User currentUser, UserDatabase userDatabase, Runnable onLogout) {
        this.currentUser = currentUser;
        this.userDatabase = userDatabase;
        this.onLogout = onLogout; // Store the logout callback
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // User information display panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        usernameLabel = new JLabel("Username: " + currentUser.getUsername());
        emailField = new JTextField(currentUser.getEmail());

        infoPanel.add(usernameLabel);
        infoPanel.add(new JLabel("Email:")); // Label for the email field
        infoPanel.add(emailField);

        // Bio section
        bioTextArea = new JTextArea(5, 20);
        bioTextArea.setText(currentUser.getBio());
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        JScrollPane bioScrollPane = new JScrollPane(bioTextArea);

        // Save button for applying changes
        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveProfileChanges());

        // Logout button for logging out
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(logoutButton);

        // Adding components to the panel
        add(infoPanel, BorderLayout.NORTH);
        add(bioScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveProfileChanges() {
        String newEmail = emailField.getText().trim();
        String newBio = bioTextArea.getText().trim();

        // Validate the new email and bio before updating
        if (!validateEmail(newEmail)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the user's email and bio in the current session and database
        currentUser.setEmail(newEmail);
        currentUser.setBio(newBio);
        boolean updateResult = userDatabase.updateUser(currentUser);

        if (updateResult) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update the profile.", "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateEmail(String email) {
        // Simple email validation logic; consider using a more robust validation method
        return email.contains("@");
    }

    private void performLogout() {
        // Perform any necessary cleanup before logging out
        // For example, clearing session data, resetting the UI, etc.

        // Trigger the logout action, typically by showing the login screen
        if (onLogout != null) {
            onLogout.run();
        }
    }
}
