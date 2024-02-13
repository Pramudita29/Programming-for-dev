import javax.swing.*;

// DownloadController class to manage the initiation of downloads
public class DownloadController {
    // Reference to the main GUI to allow updates and interactions
    private ImageDownloaderGUI gui;

    // Constructor for DownloadController, accepting a reference to the main GUI window
    public DownloadController(ImageDownloaderGUI gui) {
        this.gui = gui; // Store the GUI reference for later use
    }

    // Method to start a download given a URL and a JPanel for displaying progress
    public void startDownload(String url, JPanel progressPanel) {
        // Create a new DownloadTask instance with the provided URL and progress panel
        DownloadTask downloadTask = new DownloadTask(url, progressPanel);
        // Create a new thread to run the DownloadTask, allowing for non-blocking UI
        Thread downloadThread = new Thread(downloadTask);
        // Start the thread, initiating the download process in the background
        downloadThread.start();
    }
}
