package frontend;

import backend.FriendManager;
import backend.ProfileDataBase;
import backend.SuggestionGenerator;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class FriendSuggestionFrontEnd extends JPanel {
    SuggestionGenerator suggestionGenerator;
    ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    FriendManager friendMan;
    {
        try {
            friendMan = FriendManager.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            suggestionGenerator = SuggestionGenerator.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String userID;
    private ImageIcon photo;
    private JButton addFriendButton;
    private JButton blockButton;

    public FriendSuggestionFrontEnd(String userID) {
        this.userID = userID;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Use X_AXIS to align components horizontally
        setPreferredSize(new Dimension(300, 60));


        try {
            suggestionGenerator.generateSuggestions(userID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> suggestions;
        try {
            suggestions = suggestionGenerator.shuffleSuggestions(userID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (String suggestion : suggestions) {
                User user;
            try {
                        user= profileDataBase.getUser(suggestion);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JLabel photoLabel = new JLabel(new ImageIcon(user.getProfile().getProfilePhoto()));
            photoLabel.setPreferredSize(new Dimension(50, 50));
            photoLabel.setHorizontalAlignment(JLabel.CENTER);

            // Name label
            JLabel nameLabel = new JLabel(user.getUsername());

            // Add Friend Button
            addFriendButton = new JButton("Add Friend");
            addFriendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addFriend(suggestion);
                }
            });

            // Block Button
            blockButton = new JButton("Block");
            blockButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    blockUser(suggestion);
                    JOptionPane.showMessageDialog(null, suggestion+"blocked!");
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
    }
    private void addFriend(String r) {

        try {
            friendMan.requestFriend(userID,r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Method to handle blocking a user
    private void blockUser(String r) {

        try {
            friendMan.blockUser(userID,r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
