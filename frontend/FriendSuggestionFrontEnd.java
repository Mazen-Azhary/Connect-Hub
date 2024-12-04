package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendSuggestionFrontEnd extends JPanel {
    private int id;
    private String name;
    private ImageIcon photo;
    private JButton addFriendButton;
    private JButton blockButton;

    public FriendSuggestionFrontEnd(int id, String name, ImageIcon photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Use X_AXIS to align components horizontally
        setPreferredSize(new Dimension(300, 60));

        // Photo
        JLabel photoLabel = new JLabel(photo);

        // Name label
        JLabel nameLabel = new JLabel(name);

        // Add Friend Button
        addFriendButton = new JButton("Add Friend");
        addFriendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFriend();
            }
        });

        // Block Button
        blockButton = new JButton("Block");
        blockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                blockUser();
            }
        });

        // Add components to panel with spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Stack buttons vertically
        buttonPanel.add(addFriendButton);
        buttonPanel.add(blockButton);

        // Add components to the main panel
        add(photoLabel);
        add(nameLabel);
        add(Box.createHorizontalStrut(10)); // Add space between the name and buttons
        add(buttonPanel);
    }

    // Method to handle adding a friend
    private void addFriend() {
        System.out.println("Friend " + name + " added!");
        // Implement logic to add this friend
    }

    // Method to handle blocking a user
    private void blockUser() {
        System.out.println("User " + name + " blocked!");
        // Implement logic to block this user
    }
}
