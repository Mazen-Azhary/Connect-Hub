package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import backend.*;

public class CancelRequest extends JFrame {

    private String id;
    private ArrayList<String> request;
    private FriendsViewer friendsViewer=FriendsViewer.getInstance();
    private FriendManager friendManager = FriendManager.getInstance();
    private ProfileManager profileManager = ProfileManager.getInstance();
    private JPanel requestListPanel;
    private JScrollPane scrollPane;

    public CancelRequest(String id) throws IOException {
        this.id = id;
        // Set JFrame properties
        setTitle("sent requests");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this JFrame on exit
        // Initialize components
        initComponents();
        loadrequest();
        displayrequest();
        setResizable(false);
        // Make the frame visible
        setVisible(true);
    }


    private void loadrequest() {
        request=friendsViewer.getRequests(id);
    }

    private void displayrequest() throws IOException {
        requestListPanel.removeAll(); // Clear the panel before re-rendering

        for (String r : request) {
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.X_AXIS));
            requestPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
            requestPanel.setPreferredSize(new Dimension(400, 70)); // Adjust width and height
            User user =profileManager.getUser(r);
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
            JButton acceptButton = new JButton("Remove Request");
            acceptButton.setBackground(Color.BLUE);
            acceptButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller button font
            acceptButton.setPreferredSize(new Dimension(80, 30)); // Button size
            acceptButton.addActionListener(e -> {
                try {
                    friendManager.removeRequest(id,r);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                requestListPanel.remove(requestPanel); // Remove the panel upon action
                requestListPanel.revalidate();
                requestListPanel.repaint();
            });
            requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            requestPanel.add(acceptButton);
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