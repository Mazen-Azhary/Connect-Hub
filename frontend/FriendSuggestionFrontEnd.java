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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use Y_AXIS for vertical stacking
        setPreferredSize(new Dimension(300, 600)); // Adjusted for multiple suggestions
        displaySuggestions();
    }
    void displaySuggestions() {
        this.removeAll(); // Clear all existing components in the panel

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
                user = profileDataBase.getUser(suggestion);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Create a panel for this suggestion
            JPanel suggestionPanel = new JPanel();
            suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
            suggestionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Profile photo
            JLabel photoLabel;
            String imageIconPath = user.getProfile().getProfilePhoto();
            if (imageIconPath != null) {
                photoLabel = new JLabel(new ImageIcon(imageIconPath));
            } else {
                photoLabel = new JLabel(new ImageIcon("src/database/defaultIcon.png"));
            }
            photoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            suggestionPanel.add(photoLabel);

            // Name label
            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            suggestionPanel.add(nameLabel);

            // Add Friend Button
            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addFriendButton.addActionListener(e -> {
                addFriend(suggestion);
                JOptionPane.showMessageDialog(null, "Add Request successful");
                displaySuggestions(); // Reload suggestions after adding a friend
            });
            suggestionPanel.add(addFriendButton);

            // Block Button
            JButton blockButton = new JButton("Block");
            blockButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            blockButton.addActionListener(e -> {
                blockUser(suggestion);
                JOptionPane.showMessageDialog(null, user.getUsername() + " blocked!");
                displaySuggestions(); // Reload suggestions after blocking
            });
            suggestionPanel.add(blockButton);

            // Add a vertical spacer between suggestions
            suggestionPanel.add(Box.createVerticalStrut(10));

            // Add the suggestionPanel to the main container
            this.add(suggestionPanel);
        }

        // Revalidate and repaint the main panel to reflect changes
        this.revalidate();
        this.repaint();
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
