package frontend;

import backend.FriendManager;
import backend.ProfileManager;
import backend.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UserSearchResultFrontend extends JPanel {

    private JLabel userPhoto;
    private JLabel userName;
    private JButton viewProfileButton;
    private JButton actionButton;
    private JButton blockButton;

    public UserSearchResultFrontend(String userID, boolean isFriend) throws IOException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        User user = ProfileManager.getInstance().getUser(userID);

        ImageIcon photo = new ImageIcon(user.getProfile().getProfilePhoto());
        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }
        final boolean[] solution = {isFriend};
        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        userPhoto = new JLabel(circularPhoto);
        userPhoto.setPreferredSize(new Dimension(diameter, diameter));
        userPhoto.setHorizontalAlignment(JLabel.CENTER);

        // Make the photo clickable and print "Hello World"
        userPhoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchResultProfile();
            }
        });

        userName = new JLabel(user.getUsername());
        userName.setHorizontalAlignment(JLabel.CENTER);
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setBorder(new EmptyBorder(0, 10, 0, 10)); // Add some padding around the name

        viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBackground(new Color(70, 130, 180)); // Steel blue color
        viewProfileButton.setForeground(Color.WHITE);
        viewProfileButton.setPreferredSize(new Dimension(100, 40)); // Make the button smaller

        viewProfileButton.setFocusPainted(false);
        // Action for the view profile button
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProfile();
            }
        });

        // Action button to add/remove friend
        actionButton = new JButton(isFriend ? "Remove" : "Add");
        actionButton.setBackground(new Color(34, 139, 34)); // Green color for add/remove friend
        actionButton.setForeground(Color.WHITE);
        actionButton.setPreferredSize(new Dimension(100, 40)); // Make the button smaller

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solution[0]) {
                    // Remove friend logic
                    try {
                        throw new IOException("implement this part");
                    } catch (IOException ex) {
                        // Handle exception
                    }
                    actionButton.setText("Add Friend");
                    solution[0] = false;
                } else {
                    // Add friend logic
                    try {
                        throw new IOException("implement this part");
                    } catch (IOException ex) {
                        // Handle exception
                    }
                    actionButton.setText("Remove Friend");
                    solution[0] = true;
                }
            }
        });

        // Block button (same for both friend and non-friend)
        blockButton = new JButton("Block");
        blockButton.setBackground(new Color(255, 69, 0)); // Red color for block
        blockButton.setForeground(Color.WHITE);
        blockButton.setPreferredSize(new Dimension(100, 40)); // Make the button smaller

        // Layout setup
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(userPhoto, BorderLayout.NORTH);
        topPanel.add(userName, BorderLayout.CENTER);

        // Panel to hold the buttons horizontally
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the buttons
        buttonPanel.add(viewProfileButton);
        buttonPanel.add(actionButton);
        buttonPanel.add(blockButton);

        add(topPanel, BorderLayout.NORTH); // Top section with photo and name
        add(buttonPanel, BorderLayout.CENTER); // Center section with buttons
    }

    private void searchResultProfile() {
        System.out.println("Hello World");
    }

    // This method is called when the "View Profile" button is clicked
    private void viewProfile() {
        System.out.println("Viewing Profile...");
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
