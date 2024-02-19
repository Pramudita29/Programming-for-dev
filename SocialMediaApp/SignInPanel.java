import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SignInPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton signUpButton; // Changed from JLabel to JButton for better UX
    private UserDatabase userDatabase;
    private Consumer<User> onSignInSuccess;
    private Runnable onSignUpRequested;

    public SignInPanel(UserDatabase userDatabase, Consumer<User> onSignInSuccess, Runnable onSignUpRequested) {
        this.userDatabase = userDatabase;
        this.onSignInSuccess = onSignInSuccess;
        this.onSignUpRequested = onSignUpRequested;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        signInButton = new JButton("Sign In");
        signUpButton = new JButton("Don't have an account? Sign up here.");
        signUpButton.setBorderPainted(false);
        signUpButton.setOpaque(false);
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(signInButton);
        add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        add(signUpButton);

        signInButton.addActionListener(e -> signIn());
        passwordField.addActionListener(e -> signIn()); // Enter key to submit

        signUpButton.addActionListener(e -> onSignUpRequested.run());
    }

    private void signIn() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userDatabase.authenticateUser(username, password).ifPresentOrElse(user -> {
            JOptionPane.showMessageDialog(this, "Sign in successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            onSignInSuccess.accept(user);
        }, () -> {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
}
