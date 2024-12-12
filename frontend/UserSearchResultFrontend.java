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
import java.util.ArrayList;


public class UserSearchResultFrontend extends JPanel {

    private JLabel userPhoto;
    private JLabel userName;
    private JButton viewProfileButton;
    private JButton actionButton;
    private JButton blockButton;
    private FriendManager friendManager = FriendManager.getInstance();
    private static int state;


    public UserSearchResultFrontend(String id, String userID, int state, ArrayList<String> ids, UserSearchResultPanel userSearchResultPanel) throws IOException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        UserSearchResultFrontend.state = state;
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

        // Make the photo clickable and print "Hello World"
        userPhoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
                try {
                    UserProfileFrame userProfileFrame = new UserProfileFrame(userID);
                    userProfileFrame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace(); // Handle the exception properly
                }
            }
        });

        // Action button to add/remove friend
        actionButton = new JButton();
        switch (state)
        {
            case 0:{
                actionButton.setText("Add");
                break;
            }
            case 1:{
                actionButton.setText("Remove");
                break;
            }
            case 2:{
                actionButton.setText("Accept Request");
                break;
            }
            case 3:
            {
                actionButton.setText("Cancel Request");
                break;
            }

        }
        actionButton.setBackground(new Color(34, 139, 34)); // Green color for add/remove friend
        actionButton.setForeground(Color.WHITE);
        actionButton.setPreferredSize(new Dimension(100, 40)); // Make the button smaller

        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int st=UserSearchResultFrontend.state;
                if (st==1) {
                    // Remove friend logic
                    try {
                        friendManager.removeFriend(id, userID);
                    } catch (IOException ex) {
                        // Handle exception
                    }
                    actionButton.setText("Add Friend");
                    UserSearchResultFrontend.state=0;
                } else if(st==0) {
                    // Add friend logic
                    try {
                        friendManager.requestFriend(id, userID);
                    } catch (IOException ex) {
                        // Handle exception
                    }
                    actionButton.setText("Cancel Request");
                    UserSearchResultFrontend.state=3;
                }
                else if (st==2) {
                    try {
                        friendManager.respond(id, userID,true);
                    } catch (IOException ex) {
                        // Handle exception
                    }
                    actionButton.setText("Remove");
                    UserSearchResultFrontend.state=1;
                }
                else {
                    try {
                        friendManager.removeRequest(id, userID);
                    } catch (IOException ex) {
                        // Handle exception
                    }
                    actionButton.setText("Add Friend");
                    UserSearchResultFrontend.state=0;
                }
            }
        });

        // Block button (same for both friend and non-friend)
        blockButton = new JButton("Block");
        blockButton.setBackground(new Color(255, 69, 0)); // Red color for block
        blockButton.setForeground(Color.WHITE);
        blockButton.setPreferredSize(new Dimension(100, 40)); // Make the button smaller
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.blockUser(id, userID);
                    ArrayList<String> updatedSearchResultIDs = new ArrayList<>(ids);
                    updatedSearchResultIDs.remove(userID); // Remove the blocked user
                    userSearchResultPanel.reloadSearchResults(updatedSearchResultIDs); // Refresh the panel
                } catch (IOException ex) {
                    ex.printStackTrace(); // Handle the exception properly
                }
            }
        });
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
