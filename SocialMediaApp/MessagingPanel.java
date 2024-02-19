import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class MessagingPanel extends JPanel {
    private JComboBox<String> friendSelector;
    private JTextPane chatArea; // Use JTextPane for richer text formatting
    private JTextField messageInputField;
    private JButton sendButton;
    private User currentUser;
    private UserDatabase userDatabase; // Assuming this has methods to manage messages

    public MessagingPanel(User currentUser, UserDatabase userDatabase) {
        this.currentUser = currentUser;
        this.userDatabase = userDatabase;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Friend selector
        friendSelector = new JComboBox<>(currentUser.getFriends().toArray(new String[0]));
        add(friendSelector, BorderLayout.NORTH);

        // Chat area
        chatArea = new JTextPane();
        chatArea.setEditable(false);
        chatArea.setContentType("text/html"); // Enable HTML content
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(chatScrollPane, BorderLayout.CENTER);

        // Message input and send button
        JPanel messagePanel = new JPanel(new BorderLayout());
        messageInputField = new JTextField();
        sendButton = new JButton("Send");
        messagePanel.add(messageInputField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);
        add(messagePanel, BorderLayout.SOUTH);

        // Load initial chat history
        loadChatHistory((String) friendSelector.getSelectedItem());

        // Listeners
        friendSelector.addActionListener(this::onFriendSelectionChanged);
        sendButton.addActionListener(this::onSendMessage);
    }

    private void onFriendSelectionChanged(ActionEvent e) {
        String selectedFriend = (String) friendSelector.getSelectedItem();
        loadChatHistory(selectedFriend);
    }

    private void onSendMessage(ActionEvent e) {
        String messageText = messageInputField.getText().trim();
        if (!messageText.isEmpty() && friendSelector.getSelectedItem() != null) {
            String recipient = (String) friendSelector.getSelectedItem();
            Message message = new Message(currentUser.getUsername(), recipient, messageText);
            userDatabase.saveMessage(message);
            messageInputField.setText("");
            loadChatHistory(recipient);
        }
    }

    private void loadChatHistory(String friendUsername) {
        StringBuilder chatContent = new StringBuilder("<html><body>");
        List<Message> messages = userDatabase.getMessagesBetweenUsers(currentUser.getUsername(), friendUsername);
        messages.sort(Comparator.comparing(Message::getTimestamp));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Message message : messages) {
            String timestamp = message.getTimestamp().format(formatter);
            chatContent.append("<p><b>").append(message.getSender()).append(":</b> ")
                       .append(message.getContent()).append(" <i>(").append(timestamp).append(")</i></p>");
        }
        chatContent.append("</body></html>");
        chatArea.setText(chatContent.toString());

        // Auto-scroll to the latest message
        SwingUtilities.invokeLater(() -> chatArea.setCaretPosition(chatArea.getDocument().getLength()));
    }
}
