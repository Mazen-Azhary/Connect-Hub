package frontend;

import backend.Content;
import backend.ContentViewer;
import backend.ProfileManager;
import backend.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserProfileFrame extends JFrame {

    private final String userId;

    public UserProfileFrame(String userId) throws IOException {
        this.userId = userId;

        // Frame properties
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen

        // Add a custom window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // Close the window
            }
        });

        // Fetch user data
        ProfileManager profileManager = ProfileManager.getInstance();
        User user = profileManager.getUser(userId);

        // Main layout
        setLayout(new BorderLayout());

        // Resize images
        ImageIcon coverPhotoIcon = resizeImage(user.getProfile().getCoverPhoto(), 800, 200);
        ImageIcon profilePhotoIcon = resizeImage(user.getProfile().getProfilePhoto(), 100, 100);

        // Cover photo
        JLabel coverPhoto = new JLabel(coverPhotoIcon);
        coverPhoto.setHorizontalAlignment(JLabel.CENTER);

        // Profile and username section
        JPanel profileSection = new JPanel();
        profileSection.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel profilePhoto = new JLabel(profilePhotoIcon);
        JLabel usernameLabel = new JLabel(user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));

        profileSection.add(profilePhoto);
        profileSection.add(usernameLabel);

        // Posts section
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));

        ContentViewer contentViewer = ContentViewer.getInstance();
        ArrayList<Content> posts = contentViewer.generateProfilePosts(userId);

        for (Content post : posts) {
            // Format post
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
            postPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            postPanel.setBackground(Color.WHITE);

            // Add post content
            JLabel contentLabel = new JLabel("<html>" + post.getContent().replace("\n", "<br>") + "</html>");
            contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            // Add timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            JLabel timestampLabel = new JLabel(post.getTimestamp().format(formatter));
            timestampLabel.setFont(new Font("Arial", Font.ITALIC, 10));
            timestampLabel.setForeground(Color.GRAY);

            postPanel.add(contentLabel);
            postPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            postPanel.add(timestampLabel);
            postsPanel.add(postPanel);
            postsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts
        }

        JScrollPane postsScrollPane = new JScrollPane(postsPanel);
        postsScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Layout
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BorderLayout());
        mainContent.add(profileSection, BorderLayout.NORTH);
        mainContent.add(postsScrollPane, BorderLayout.CENTER);

        add(coverPhoto, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
