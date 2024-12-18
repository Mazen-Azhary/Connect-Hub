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

public class FriendSuggestionFrontEnd extends JPanel {

    ProfileManager profileManager=ProfileManager.getInstance();
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
    private JButton addButton;
    private JButton blockButton; // New block button
    private JLabel activeStatus;
    private String friendID;
    private String userID;
    private NewsFeed newsFeed;
    public FriendSuggestionFrontEnd(String userID, String friendID,NewsFeed newsFeed) throws IOException {
        setLayout(new BorderLayout());
        this.userID = userID;
        this.friendID = friendID;
        this.newsFeed=newsFeed;
        String friendId = this.friendID;
        User friendProfile;
            friendProfile = profileManager.getUser(friendId);


        ImageIcon photo = new ImageIcon(friendProfile.getProfile().getProfilePhoto());

        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        friendPhoto = new JLabel(circularPhoto);
        friendPhoto.setPreferredSize(new Dimension(diameter, diameter));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);



        friendName = new JLabel(friendProfile.getUsername());
        friendName.setHorizontalAlignment(JLabel.CENTER);
        friendName.setFont(new Font("Arial", Font.BOLD, 16));
        friendName.setBorder(new EmptyBorder(0, -5, 0, 10)); // Top, Left, Bottom, Right
        addButton = new JButton("Add Request");
        addButton.setBackground(Color.GREEN);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.requestFriend(userID,friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                newsFeed.dispose();
                try {
                    new NewsFeed(userID);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Block button
        blockButton = new JButton("Block");
        blockButton.setBackground(Color.RED);
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.blockUser(userID, friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                newsFeed.dispose();
                try {
                    new NewsFeed(userID);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Adds horizontal spacing
        buttonPanel.add(blockButton);

        add(friendPhoto, BorderLayout.WEST);
        add(friendName, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
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