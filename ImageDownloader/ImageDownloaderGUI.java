import javax.swing.*;
import java.awt.*;

public class ImageDownloaderGUI extends JFrame {
    private DownloadController downloadController; // Controller to handle download logic

    public ImageDownloaderGUI() {
        downloadController = new DownloadController(this); // Initialize the download controller
        setTitle("Advanced Image Downloader"); // Set window title
        setSize(600, 400); // Set window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set default close operation
        initComponents(); // Initialize components
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(); // Create a panel for inputs and buttons
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Set layout for the top panel

        JTextField urlField1 = new JTextField(); // Create the first URL input field
        JTextField urlField2 = new JTextField(); // Create the second URL input field
        JButton downloadButton = new JButton("Download"); // Create the download button

        topPanel.add(urlField1); // Add the first URL input field to the top panel
        topPanel.add(urlField2); // Add the second URL input field to the top panel
        topPanel.add(downloadButton); // Add the download button to the top panel

        JPanel progressPanel = new JPanel(); // Create a panel to display download progress
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS)); // Set layout for the progress panel

        // Set action listener for the download button
        downloadButton.addActionListener(e -> {
            String url1 = urlField1.getText(); // Get text from the first URL input field
            String url2 = urlField2.getText(); // Get text from the second URL input field
            if (!url1.isEmpty()) { // Check if the first URL input field is not empty
                downloadController.startDownload(url1, progressPanel); // Start download for the first URL
            }
            if (!url2.isEmpty()) { // Check if the second URL input field is not empty
                downloadController.startDownload(url2, progressPanel); // Start download for the second URL
            }
        });

        getContentPane().add(new JScrollPane(topPanel), BorderLayout.NORTH); // Add the top panel with a scroll pane to the frame
        getContentPane().add(new JScrollPane(progressPanel), BorderLayout.CENTER); // Add the progress panel with a scroll pane to the frame
    }

    public void addDownloadPanel(JPanel downloadPanel) {
        getContentPane().add(downloadPanel); // Add a download panel to the frame
        revalidate(); // Revalidate the layout
        repaint(); // Repaint the frame
    }
}
