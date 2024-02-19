import javax.swing.*;

public class MainApp {
    private static JFrame frame;
    private static UserDatabase userDatabase;
    private static PostDatabase postDatabase; // Assuming you're managing posts as well

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Social Network App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400); // Adjust the size as needed
            userDatabase = new UserDatabase(); // Ensure you have an appropriate constructor
            postDatabase = new PostDatabase(); // Initialize your post database here

            showSignInPanel(); // Start with the sign-in panel

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void showSignInPanel() {
        SignInPanel signInPanel = new SignInPanel(userDatabase, MainApp::showMainContentPanel, MainApp::showSignUpPanel);
        frame.setContentPane(signInPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showSignUpPanel() {
        SignUpPanel signUpPanel = new SignUpPanel(userDatabase, MainApp::showSignInPanel);
        frame.setContentPane(signUpPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showMainContentPanel(User user) {
        // Pass a Runnable logout action that switches back to the sign-in panel
        Runnable logoutAction = () -> {
            showSignInPanel(); // Show the sign-in panel upon logout
            frame.setTitle("Social Network App"); // Reset the frame title or any other necessary UI adjustments
        };

        // Now also pass the logout action to the MainContentPanel
        MainContentPanel mainContentPanel = new MainContentPanel(user, postDatabase, userDatabase, logoutAction);
        frame.setContentPane(mainContentPanel);
        frame.setTitle("Welcome, " + user.getUsername()); // Optionally set the frame title to the user's name or username
        frame.revalidate();
        frame.repaint();
    }
}
