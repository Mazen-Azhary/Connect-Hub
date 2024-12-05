package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import backend.*;

public class FriendFrontend extends JPanel {

    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    private FriendManager friendManager;

    {
        try {
            friendManager = FriendManager.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel friendPhoto;
    private JLabel friendName;
    private JButton removeButton;
    private JButton blockButton; // New block button
    private JLabel activeStatus;
    private String friendID;
    private String userID;

    public FriendFrontend(String userID, String friendID) {
        setLayout(new BorderLayout());
        this.userID = userID;
        this.friendID = friendID;

        String friendId = this.friendID;
        User friendProfile;
        try {
            friendProfile = profileDataBase.getUser(friendId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load the profile photo
        ImageIcon photo = new ImageIcon(friendProfile.getProfile().getProfilePhoto());

        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50; // Set the desired diameter
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        // Set the circular photo in the JLabel
        friendPhoto = new JLabel(circularPhoto);
        friendPhoto.setPreferredSize(new Dimension(diameter, diameter));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);

        // Active status
        activeStatus = new JLabel();
        activeStatus.setText(friendProfile.getStatus());
        activeStatus.setHorizontalAlignment(JLabel.CENTER);

        // Friend name
        friendName = new JLabel(friendProfile.getUsername());
        friendName.setHorizontalAlignment(JLabel.CENTER);
        friendName.setFont(new Font("Arial", Font.BOLD, 16));
        friendName.setBorder(new EmptyBorder(0, -5, 0, 10)); // Top, Left, Bottom, Right

        // Remove button
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.removeFriend(userID, friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
                revalidate();
            }
        });

        // Block button
        blockButton = new JButton("Block");
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.blockUser(userID, friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
                revalidate();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Adds horizontal spacing
        buttonPanel.add(blockButton);

        // Add components to the panel
        add(friendPhoto, BorderLayout.WEST);
        add(friendName, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(activeStatus, BorderLayout.SOUTH);
    }

    // Helper method to create a circular image
    private static ImageIcon getCircularImageIcon(ImageIcon icon, int diameter) {
        // Create a new buffered image with transparency
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();

        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a circular clipping region
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));

        // Draw the original image scaled to fit the circle
        g2.drawImage(icon.getImage(), 0, 0, diameter, diameter, null);
        g2.dispose();

        return new ImageIcon(circularImage);
    }
}
