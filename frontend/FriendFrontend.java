package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import backend.*;

public class FriendFrontend extends JPanel {
private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
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
    private String id;

    public FriendFrontend(String name, ImageIcon photo,String id) {
        setLayout(new BorderLayout());
       User user;
       this.id = id;
        try {
            user = friendDatabase.getUser(this.id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //database.getUser();
    for(int i=0;i<user.getProfile().getFriends().size();i++){


        String friendId = user.getProfile().getFriends().get(i);
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
        activeStatus.setText(user.getStatus());
        activeStatus.setHorizontalAlignment(JLabel.CENTER);

        friendName = new JLabel(user.getUsername());
        friendName.setHorizontalAlignment(JLabel.CENTER);
        
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.removeFriend(user.getUserID(), friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        
        blockButton = new JButton("Block");
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendManager.blockUser(user.getUserID(), friendId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

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
}
