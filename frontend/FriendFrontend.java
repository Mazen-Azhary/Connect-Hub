package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import backend.*;

public class FriendFrontend extends JPanel {
    private UserDataBase userDataBase;
    {
        try {
            userDataBase = new UserDataBase("src/database/UserContents.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
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
    private JButton removeButton;
    private JButton blockButton; // New block button
    private JLabel activeStatus;
    private String friendID;
    private String userID;

    public FriendFrontend(String userID,String friendID) {
        setLayout(new BorderLayout());
        this.userID = userID;
       this.friendID = friendID;

        String friendId = this.friendID;
            User friendProfile;
        try {
                   friendProfile = profileDataBase.getUser(friendId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        friendPhoto = new JLabel(new ImageIcon(friendProfile.getProfile().getProfilePhoto()));
        friendPhoto.setPreferredSize(new Dimension(50, 50));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);

        activeStatus = new JLabel();
        activeStatus.setText(friendProfile.getStatus());
        activeStatus.setHorizontalAlignment(JLabel.CENTER);

        friendName = new JLabel(friendProfile.getUsername());
        friendName.setHorizontalAlignment(JLabel.CENTER);

        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.removeFriend(userID, friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                repaint();
                revalidate();

            }

        });

        blockButton = new JButton("Block");
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.blockUser(userID, friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                repaint();
                revalidate();

            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(removeButton);
        buttonPanel.add(blockButton);

        add(friendPhoto, BorderLayout.WEST);
        add(friendName, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(activeStatus, BorderLayout.SOUTH);
    }}

