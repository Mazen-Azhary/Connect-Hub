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

    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    private FriendManager friendManager;
    private UserContentDatabase userContentDatabase = new UserContentDatabase("src/database/UserContents.json");

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
    private String friendID;
    private String userID;
    private NewsFeed newsFeed;

    public FriendSuggestionFrontEnd(String userID, String friendID, NewsFeed newsFeed) throws IOException {
        setLayout(new BorderLayout());
        this.userID = userID;
        this.friendID = friendID;
        this.newsFeed = newsFeed;

        User friendProfile;
        try {
            friendProfile = profileDataBase.getUser(friendID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageIcon photo = new ImageIcon(friendProfile.getProfile().getProfilePhoto());

        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        // Photo and username panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding

        friendPhoto = new JLabel(circularPhoto);
        friendPhoto.setPreferredSize(new Dimension(diameter, diameter));

        friendName = new JLabel(friendProfile.getUsername());
        friendName.setFont(new Font("Arial", Font.BOLD, 16));
        friendName.setBorder(new EmptyBorder(0, 10, 0, 0)); // Padding to the right of the photo

        topPanel.add(friendPhoto);
        topPanel.add(friendName);

        // Buttons stacked vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(5, 10, 10, 10));

        addButton = new JButton("Add Request");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.requestFriend(userID, friendID);
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

        blockButton = new JButton("Block");
        blockButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.blockUser(userID, friendID);
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

        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 0))); // Vertical spacing
        buttonPanel.add(blockButton);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static ImageIcon getCircularImageIcon(ImageIcon icon, int diameter) {
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(icon.getImage(), 0, 0, diameter, diameter, null);
        g2.dispose();
        return new ImageIcon(circularImage);
    }
}
