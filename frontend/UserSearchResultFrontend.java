package frontend;

import backend.ProfileManager;
import backend.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UserSearchResultFrontend extends JPanel {

    private JLabel userPhoto;
    private JLabel userName;
    private JButton viewProfileButton;

    public UserSearchResultFrontend(String userID) throws IOException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        User user = ProfileManager.getInstance().getUser(userID);

        ImageIcon photo = new ImageIcon(user.getProfile().getProfilePhoto());
        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        userPhoto = new JLabel(circularPhoto);
        userPhoto.setPreferredSize(new Dimension(diameter, diameter));
        userPhoto.setHorizontalAlignment(JLabel.CENTER);

        userName = new JLabel(user.getUsername());
        userName.setHorizontalAlignment(JLabel.CENTER);
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setBorder(new EmptyBorder(0, 10, 0, 10)); // Add some padding around the name

        viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBackground(new Color(70, 130, 180)); // Steel blue color
        viewProfileButton.setForeground(Color.WHITE);
        viewProfileButton.setFocusPainted(false);

        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to view profile
                System.out.println("View Profile clicked for user: " + user.getUsername());
                // Logic to navigate to the user's profile page
                // e.g., new ProfilePage(userID).setVisible(true);
            }
        });

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(userPhoto, BorderLayout.NORTH);
        centerPanel.add(userName, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(viewProfileButton, BorderLayout.SOUTH);
    }

    public static ImageIcon getCircularImageIcon(ImageIcon icon, int diameter) {
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
