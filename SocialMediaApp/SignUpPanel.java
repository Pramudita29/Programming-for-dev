import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SignUpPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextArea bioField;
    private JButton signUpButton;
    private JLabel signInLabel; // Label for navigating back to sign-in
    private UserDatabase userDatabase;
    private Runnable signUpSuccessCallback; // Callback to execute on successful sign-up

    public SignUpPanel(UserDatabase userDatabase, Runnable onSignUpSuccess) {
        this.userDatabase = userDatabase;
        this.signUpSuccessCallback = onSignUpSuccess; // Correctly assign the callback
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        bioField = new JTextArea(3, 20);
        bioField.setLineWrap(true);
        bioField.setWrapStyleWord(true);
        signUpButton = new JButton("Sign Up");
        signInLabel = new JLabel("Already have an account? Sign in here.");

        addComponents();
        signUpButton.addActionListener(this::signUp);

        // Styling the sign-in navigation label
        signInLabel.setForeground(Color.BLUE.darker());
        signInLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signInLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Implement navigation to sign-in panel
                if (signUpSuccessCallback != null) {
                    signUpSuccessCallback.run(); // Execute the callback to transition back to the sign-in panel
                }
            }
        });
    }

    private void addComponents() {
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Bio (optional):"));
        add(new JScrollPane(bioField));
        add(signUpButton);
        // Adding space between the sign-up button and sign-in navigation label
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(signInLabel);
    }

    private void signUp(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim(); // Hashing should be considered
        String email = emailField.getText().trim();
        String bio = bioField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields except bio, which is optional.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean added = userDatabase.addUser(new User(username, password, email, bio));
        if (added) {
            JOptionPane.showMessageDialog(this, "Sign up successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (signUpSuccessCallback != null) {
                signUpSuccessCallback.run(); // Execute the callback to transition back to the sign-in panel
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
