package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import backend.*;

public class BlockedUsers extends JFrame {
    private FriendsViewer friendsViewer=FriendsViewer.getInstance();
    private ProfileManager profileManager=ProfileManager.getInstance();
    private String id;
    private ArrayList<String> blocked;
    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    private FriendManager friendManager = FriendManager.getInstance();
    private JPanel blockedListPanel;
    private JScrollPane scrollPane;

    public BlockedUsers(String id) throws IOException {
        this.id = id;
        // Set JFrame properties
        setTitle("Block list");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this JFrame on exit
        // Initialize components
        initComponents();
        loadblocked();
        displayblocked();
        setResizable(false);
        // Make the frame visible
        setVisible(true);
    }

    private void loadblocked() {
            blocked = friendsViewer.getBlocked(id);
    }

    private void displayblocked() throws IOException {
        blockedListPanel.removeAll(); // Clear the panel before re-rendering

        for (String block : blocked) {
            if(block.startsWith("-")) continue;
            JPanel blockedPanel = new JPanel();
            blockedPanel.setLayout(new BoxLayout(blockedPanel, BoxLayout.X_AXIS));
            blockedPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
            blockedPanel.setPreferredSize(new Dimension(400, 70)); // Adjust width and height
            User user =profileManager.getUser(block) ;
            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setPreferredSize(new Dimension(100, 30)); // Set a fixed size
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size for compactness
            blockedPanel.add(nameLabel);

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
            blockedPanel.add(Box.createHorizontalStrut(10)); // Add spacing
            blockedPanel.add(iconLabel);

            // Accept Button
            JButton acceptButton = new JButton("Unblock");
            acceptButton.setBackground(Color.WHITE);
            acceptButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller button font
            acceptButton.setPreferredSize(new Dimension(80, 30)); // Button size
            acceptButton.addActionListener(e -> {
                try {
                    friendManager.unblockUser(id,block);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                blockedListPanel.remove(blockedPanel); // Remove the panel upon action
                blockedListPanel.revalidate();
                blockedListPanel.repaint();
            });
            blockedPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            blockedPanel.add(acceptButton);
            // Add the blocked panel to the list
            blockedListPanel.add(blockedPanel);
            blockedListPanel.add(Box.createVerticalStrut(5)); // Add spacing between rows
        }

        // Refresh the panel to apply changes
        blockedListPanel.revalidate();
        blockedListPanel.repaint();
    }



    @SuppressWarnings("unchecked")
    private void initComponents() {
        this.setLayout(new BorderLayout());

        blockedListPanel = new JPanel();
        blockedListPanel.setLayout(new BoxLayout(blockedListPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(blockedListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);
    }
}