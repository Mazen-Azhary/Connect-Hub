package frontend;

import backend.FriendManager;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SearchPage extends JFrame {
    private String id;
    private JPanel usersPanel;
    private FriendManager friendManager = FriendManager.getInstance();

    public SearchPage(ArrayList<User> users, String id) throws IOException {
        this.id = id;
        setTitle("Search Users");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a scroll pane for the user list
        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS)); // Vertical layout

        // Populate the panel with user details
        for (User user : users) {
            usersPanel.add(createUserPanel(user));
        }

        JScrollPane scrollPane = new JScrollPane(usersPanel);
        add(scrollPane);
    }

    private JPanel createUserPanel(User user) {
        // Panel to display each user with image, username and action buttons
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout());

        // Load user picture (you can replace this with an ImageIcon or a label)
        ImageIcon userImage = new ImageIcon(user.getProfile().getProfilePhoto());
        JLabel imageLabel = new JLabel(userImage);

        // Display username
        JLabel usernameLabel = new JLabel(user.getUsername());

        // Create action buttons
        JButton addFriendButton = new JButton("Add Friend");
        JButton removeFriendButton = new JButton("Remove Friend");
        JButton blockUserButton = new JButton("Block User");
        JButton viewProfileButton = new JButton("View Profile");

        // Set actions for buttons
        addFriendButton.addActionListener(e -> sendFriendRequest(user));
        removeFriendButton.addActionListener(e -> removeFriend(user));
        blockUserButton.addActionListener(e -> blockUser(user));
        viewProfileButton.addActionListener(e -> viewUserProfile(user));

        // Add components to user panel
        userPanel.add(imageLabel);
        userPanel.add(usernameLabel);
        userPanel.add(addFriendButton);
        userPanel.add(removeFriendButton);
        userPanel.add(blockUserButton);
        userPanel.add(viewProfileButton);

        return userPanel;
    }

    private void sendFriendRequest(User user) {

        try {
            friendManager.requestFriend(id, user.getUserID());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(this, "Friend request sent to " + user.getUsername());
    }

    private void removeFriend(User user) {
        try {
            friendManager.removeFriend(id, user.getUserID());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(this, "Removed " + user.getUsername() + " from friends.");
    }

    private void blockUser(User user) {
        try {
            friendManager.blockUser(id, user.getUserID());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(this, user.getUsername() + " has been blocked.");
    }

    private void viewUserProfile(User user) {
        //

    }


}