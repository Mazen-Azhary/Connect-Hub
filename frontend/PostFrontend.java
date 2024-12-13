package frontend;

import backend.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class PostFrontend extends JPanel {
    private ProfileManager profileManager = ProfileManager.getInstance();
    private static final int PADDING = 10;

    public PostFrontend(Content post, boolean isFromGroup, String groupID) {
        User user = profileManager.getUser(Integer.toString(post.getAuthorId()));
        ImageIcon photo = new ImageIcon(user.getProfile().getProfilePhoto());

        // Check if the image is valid
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Create circular profile photo
        int diameter = 30;
        ImageIcon circularPhoto = FriendFrontend.getCircularImageIcon(photo, diameter);
        JLabel friendPhoto = new JLabel(circularPhoto);
        friendPhoto.setPreferredSize(new Dimension(diameter, diameter));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDateTime = post.getTimestamp();
        String formattedDate = localDateTime.format(formatter);
        JLabel friendName = new JLabel(user.getUsername() + "        " + formattedDate);
        friendName.setHorizontalAlignment(JLabel.LEFT);
        friendName.setFont(new Font("Arial", Font.BOLD, 14));
        friendName.setBorder(new EmptyBorder(0, 5, 0, 0)); // Add spacing from the photo

        // Content label
        JLabel contentLabel = new JLabel("<html>" + post.getContent().replace("\n", "<br>") + "</html>");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contentLabel.setForeground(Color.BLACK);
        contentLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

        // Image (if applicable)
        JLabel imageLabel = null;
        if (post.getImage() != null) {
            ImageIcon postImage = new ImageIcon(post.getImage());
            // Resize the image while maintaining aspect ratio
            int maxWidth = 200; // Desired maximum width
            int maxHeight = 150; // Desired maximum height
            Image scaledImage = postImage.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);

            // Wrap the resized image in an ImageIcon
            imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        }

        // Create a horizontal panel for photo and username
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.setOpaque(false); // Ensure it blends with background
        headerPanel.add(friendPhoto);
        headerPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between photo and name
        headerPanel.add(friendName);

        // Create a vertical panel for the entire post
        JPanel singlePostPanel = new JPanel();
        singlePostPanel.setLayout(new BoxLayout(singlePostPanel, BoxLayout.Y_AXIS));
        singlePostPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        singlePostPanel.setBackground(Color.WHITE);

        // Check if it's from a group
        if (isFromGroup) {
            // Add a group name label
            JLabel groupNameLabel = new JLabel("Group: " + GroupManager.getInstance().getGroup(groupID).getName()); // Display the group name
            groupNameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            groupNameLabel.setForeground(new Color(50, 100, 200)); // Blue color for group name
            groupNameLabel.setBorder(new EmptyBorder(0, 0, 5, 0)); // Add spacing

            // Set background color for group posts
            singlePostPanel.setBackground(new Color(240, 240, 255)); // Light blue background
            groupNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align group name left
            singlePostPanel.add(groupNameLabel);
        }

        // Add header panel, content, and image to the post panel
        singlePostPanel.add(headerPanel);
        singlePostPanel.add(contentLabel);
        if (imageLabel != null) {
            singlePostPanel.add(imageLabel);
        }
        singlePostPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts

        // Align everything to the left
        singlePostPanel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Ensure entire panel aligns left
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(singlePostPanel);
    }
}
