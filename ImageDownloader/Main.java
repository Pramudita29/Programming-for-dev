// Main class to launch the application
public class Main {
    // The main method, the entry point of the application
    public static void main(String[] args) {
        // Ensures that the GUI is created and updated on the Event Dispatch Thread (EDT)
        // This is a best practice for Swing applications to avoid potential threading issues
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Creates an instance of ImageDownloaderGUI, the main window of the application
            ImageDownloaderGUI gui = new ImageDownloaderGUI();
            // Makes the GUI window visible to the user
            gui.setVisible(true);
        });
    }
}
