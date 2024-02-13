import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadTask implements Runnable {
    // Declare variables for the URL, progress panel, and atomic booleans for pause and cancel states
    private String url;
    private JPanel progressPanel;
    private AtomicBoolean isPaused = new AtomicBoolean(false);
    private AtomicBoolean isCancelled = new AtomicBoolean(false);
    // Declare Swing components for progress display and control
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton cancelButton;

    // Constructor to initialize the download task with a URL and a panel to display progress
    public DownloadTask(String url, JPanel progressPanel) {
        this.url = url;
        this.progressPanel = progressPanel;
        setupDownloadPanel(); // Setup the download panel with buttons and progress bar
    }

    // Method to setup the download panel with progress bar, status label, and control buttons
    private void setupDownloadPanel() {
        JPanel downloadPanel = new JPanel(new FlowLayout()); // Use FlowLayout for the download panel
        progressBar = new JProgressBar(0, 100); // Initialize the progress bar with min 0 and max 100
        progressBar.setStringPainted(true); // Enable string display on progress bar
        pauseButton = new JButton("Pause"); // Initialize the pause button
        resumeButton = new JButton("Resume"); // Initialize the resume button
        cancelButton = new JButton("Cancel"); // Initialize the cancel button
        statusLabel = new JLabel("Ready to download..."); // Initialize the status label

        // Add components to the download panel
        downloadPanel.add(progressBar);
        downloadPanel.add(pauseButton);
        downloadPanel.add(resumeButton);
        downloadPanel.add(cancelButton);
        downloadPanel.add(statusLabel);

        // Add action listeners to the buttons for their functionality
        pauseButton.addActionListener(e -> pause());
        resumeButton.addActionListener(e -> resume());
        cancelButton.addActionListener(e -> cancel());

        // Initially disable the resume and cancel buttons as no download is active yet
        resumeButton.setEnabled(false);
        cancelButton.setEnabled(false);

        // Add the download panel to the progress panel and update the UI
        SwingUtilities.invokeLater(() -> {
            progressPanel.add(downloadPanel);
            progressPanel.revalidate();
            progressPanel.repaint();
        });
    }

    @Override
    public void run() {
        // Enable the pause and cancel buttons as the download starts
        SwingUtilities.invokeLater(() -> {
            pauseButton.setEnabled(true);
            cancelButton.setEnabled(true);
        });

        try {
            URL downloadUrl = new URL(url); // Create a URL object from the string URL
            HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection(); // Open a connection
            int fileSize = connection.getContentLength(); // Get the file size

            // Extract the file name from the URL and prepare the output file path
            String fileName = new File(downloadUrl.getPath()).getName();
            if (fileName.isEmpty()) {
                fileName = "downloadedFile"; // Use a default file name if none is found
            }
            String outputPath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + fileName;

            // Ensure the output directory exists
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();

            // Start the download process
            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                byte[] dataBuffer = new byte[1024]; // Buffer for reading data
                int bytesRead;
                int totalBytesRead = 0;

                // Read data until the end or cancellation
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1 && !isCancelled.get()) {
                    // Pause the download if paused state is true
                    while (isPaused.get()) {
                        try {
                            synchronized (this) {
                                wait(); // Wait until resume or cancel is triggered
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // Restore the interrupted status
                            SwingUtilities.invokeLater(() -> statusLabel.setText("Download paused..."));
                        }
                    }
                    // Break the loop if cancelled
                    if (isCancelled.get()) {
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText("Download cancelled."); // Update the status label
                            pauseButton.setEnabled(false); // Disable control buttons
                            resumeButton.setEnabled(false);
                            cancelButton.setEnabled(false);
                        });
                        break;
                    }

                    // Write the read data to the file and update the progress bar
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                    int progress = (int) (((double) totalBytesRead / fileSize) * 100);
                    SwingUtilities.invokeLater(() -> progressBar.setValue(progress));

                    Thread.sleep(100); // Artificial delay for testing purposes
                }

                // Update the UI if the download completes successfully
                if (!isCancelled.get()) {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(100); // Set progress bar to complete
                        statusLabel.setText("Download completed. File saved to: " + outputPath); // Update status label
                        pauseButton.setEnabled(false); // Disable control buttons
                        resumeButton.setEnabled(false);
                        cancelButton.setEnabled(false);
                    });
                }
            }
        } catch (Exception e) {
            // Handle exceptions and update the UI accordingly
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Error: " + e.getMessage()); // Show the error message
                pauseButton.setEnabled(false); // Disable control buttons
                resumeButton.setEnabled(false);
                cancelButton.setEnabled(false);
            });
        }
    }

    // Method to pause the download
    public synchronized void pause() {
        isPaused.set(true); // Set the pause state to true
        SwingUtilities.invokeLater(() -> {
            pauseButton.setEnabled(false); // Disable the pause button as it's already paused
            resumeButton.setEnabled(true); // Enable the resume button to allow resuming
            cancelButton.setEnabled(true); // Keep the cancel button enabled
        });
    }

    // Method to resume the download
    public synchronized void resume() {
        isPaused.set(false); // Set the pause state to false
        notifyAll(); // Notify any waiting threads to continue
        SwingUtilities.invokeLater(() -> {
            pauseButton.setEnabled(true); // Enable the pause button again
            resumeButton.setEnabled(false); // Disable the resume button as the download resumes
            cancelButton.setEnabled(true); // Keep the cancel button enabled
        });
    }

    // Method to cancel the download
    public void cancel() {
        isCancelled.set(true); // Set the cancel state to true
        if (isPaused.get()) {
            resume(); // If paused, resume to break out of the wait state before cancelling
        }
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Download canceled."); // Update the status label to indicate cancellation
            pauseButton.setEnabled(false); // Disable all control buttons as the download is cancelled
            resumeButton.setEnabled(false);
            cancelButton.setEnabled(false);
        });
    }
}
