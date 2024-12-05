package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import backend.FriendDatabase;
import backend.FriendManager;
import backend.User;

public class FriendRequestPage extends JFrame {

    private String id;
    private ArrayList<String> friendRequests;
    private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
    private FriendManager friendManager = FriendManager.getInstance();
    private JPanel requestListPanel;
    private JScrollPane scrollPane;

    public FriendRequestPage(String id) throws IOException {
        this.id = id;
        // Set JFrame properties
        setTitle("Friend Requests");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this JFrame on exit
        // Initialize components
        initComponents();
        loadFriendRequests();
        displayFriendRequests();
        setResizable(false);
        // Make the frame visible
        setVisible(true);
    }

    private void loadFriendRequests() {
        try {
            friendRequests = friendDatabase.getUser(id).getProfile().getFriendRecievedRequests();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayFriendRequests() throws IOException {
        requestListPanel.removeAll(); // Clear the panel before re-rendering

        for (String request : friendRequests) {
            // Create a compact panel for each request
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.X_AXIS));
            requestPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
            requestPanel.setPreferredSize(new Dimension(400, 70)); // Adjust width and height

            // Get user details
            User user = friendDatabase.getUser(request);

            // Name label
            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setPreferredSize(new Dimension(100, 30)); // Set a fixed size
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size for compactness
            requestPanel.add(nameLabel);

            // Icon Label with Fixed Size
            JLabel iconLabel;
            String icon = user.getProfile().getProfilePhoto();
            ImageIcon imageIcon;

            if (icon == null) {
                // Use a default icon with fixed size
                imageIcon = new ImageIcon(new ImageIcon("src/database/defaultIcon.jpg")
                        .getImage()
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            } else {
                // Use the user's icon with fixed size
                imageIcon = new ImageIcon(new ImageIcon(icon)
                        .getImage()
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            }

            iconLabel = new JLabel(imageIcon);
            iconLabel.setPreferredSize(new Dimension(50, 50)); // Ensure the JLabel matches the scaled image
            iconLabel.setMaximumSize(new Dimension(50, 50)); // Prevent resizing
            iconLabel.setMinimumSize(new Dimension(50, 50)); // Prevent resizing
            iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT); // Align vertically
            requestPanel.add(Box.createHorizontalStrut(10)); // Add spacing
            requestPanel.add(iconLabel);

            // Accept Button
            JButton acceptButton = new JButton("Accept");
            acceptButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller button font
            acceptButton.setPreferredSize(new Dimension(80, 30)); // Button size
            acceptButton.addActionListener(e -> {
                try {
                    friendManager.respond(id, request, true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                requestListPanel.remove(requestPanel); // Remove the panel upon action
                requestListPanel.revalidate();
                requestListPanel.repaint();
            });
            requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            requestPanel.add(acceptButton);

            // Decline Button
            JButton declineButton = new JButton("Decline");
            declineButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller button font
            declineButton.setPreferredSize(new Dimension(80, 30)); // Button size
            declineButton.addActionListener(e -> {
                try {
                    friendManager.respond(id, request, false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                requestListPanel.remove(requestPanel); // Remove the panel upon action
                requestListPanel.revalidate();
                requestListPanel.repaint();
            });
            requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            requestPanel.add(declineButton);

            // Add the request panel to the list
            requestListPanel.add(requestPanel);
            requestListPanel.add(Box.createVerticalStrut(5)); // Add spacing between rows
        }

        // Refresh the panel to apply changes
        requestListPanel.revalidate();
        requestListPanel.repaint();
    }



    @SuppressWarnings("unchecked")
    private void initComponents() {
        this.setLayout(new BorderLayout());

        requestListPanel = new JPanel();
        requestListPanel.setLayout(new BoxLayout(requestListPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(requestListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);
    }
}