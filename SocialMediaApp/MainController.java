// import javax.swing.JFrame;

// public class MainController {
//     private JFrame frame;
//     private UserDatabase userDatabase;

//     public MainController(JFrame frame, UserDatabase userDatabase) {
//         this.frame = frame;
//         this.userDatabase = userDatabase;
//     }

//     public void init() {
//         showSignInPanel();
//     }

//     private void showSignInPanel() {
//         SignInPanel signInPanel = new SignInPanel(userDatabase, this::showMainContentPanel);
//         frame.setContentPane(signInPanel);
//         frame.pack(); // Adjusts the frame size to fit the content
//     }

//     private void showSignUpPanel() {
//         SignUpPanel signUpPanel = new SignUpPanel(userDatabase, this::showSignInPanel);
//         frame.setContentPane(signUpPanel);
//         frame.pack();
//     }

//     // Example method signature, adjust according to your User class
//     private void showMainContentPanel(User user) {
//         MainContentPanel mainContentPanel = new MainContentPanel(user);
//         frame.setContentPane(mainContentPanel);
//         frame.pack();
//     }

//     // Method to switch between sign-in and sign-up panels
//     public void switchToSignUp() {
//         showSignUpPanel();
//     }

//     public void switchToSignIn() {
//         showSignInPanel();
//     }
// }
