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
            String ImageIcon = user.getProfile().getProfilePhoto();
            if (ImageIcon != null) {
                photoLabel = new JLabel(new ImageIcon(ImageIcon));
            } else {
                photoLabel = new JLabel(new ImageIcon("src/database/defaultIcon.png"));
            }
            photoLabel.setPreferredSize(new Dimension(50, 50));
            photoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            suggestionPanel.add(photoLabel);

            // Name label
            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            suggestionPanel.add(nameLabel);

            // Add Friend Button
            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addFriendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addFriend(suggestion);
                    JOptionPane.showMessageDialog(null, "Add Request successful");
                    new FriendSuggestionFrontEnd(userID);
                }
            });
            suggestionPanel.add(addFriendButton);

            // Block Button
            JButton blockButton = new JButton("Block");
            blockButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            blockButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    blockUser(suggestion);
                    JOptionPane.showMessageDialog(null, user.getUsername() + " blocked!");
                    revalidate();
                    repaint();
                }
            });
            suggestionPanel.add(blockButton);

            // Add a spacer between suggestions
            suggestionPanel.add(Box.createVerticalStrut(10));

            // Add the suggestion panel to the main container
            add(suggestionPanel);

            // Add components to panel with spacing
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Stack buttons vertically
            buttonPanel.add(addFriendButton);
            buttonPanel.add(blockButton);

            // Add components to the main panel
            if(photoLabel!=null){
            add(photoLabel);
            }
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
