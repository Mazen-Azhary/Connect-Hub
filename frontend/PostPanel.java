package frontend;

import backend.Content;
import backend.Post;
import backend.ProfileManager;
import backend.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PostPanel extends JPanel {
    private ProfileManager manager = ProfileManager.getInstance();
    public PostPanel(ArrayList<Content> posts) {//backend post item
        for (Content post : posts) {
            // Retrieve user details
            User user = manager.getUser("" + post.getAuthorId());
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
            singlePostPanel.add(headerPanel);
            singlePostPanel.add(contentLabel);
            if (imageLabel != null) {
                singlePostPanel.add(imageLabel);
            }
            singlePostPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts

            // Add the single post to the main postPanel
            this.add(singlePostPanel);
            this.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts in the main panel
        }
    }
}
