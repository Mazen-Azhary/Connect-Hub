package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import backend.FriendDatabase;

public class FriendFrontend extends JPanel {
FriendDatabase database = new FriendDatabase("src/database/Friends.json");



    private JLabel friendPhoto;
    private JLabel friendName;
    private JButton removeButton;
    private JButton blockButton; // New block button
    private JLabel activeStatus;

    public FriendFrontend(String name, ImageIcon photo) {
        setLayout(new BorderLayout());
        database.getUser();
        //database.getUser();

        friendPhoto = new JLabel(photo);
        friendPhoto.setPreferredSize(new Dimension(50, 50));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);

        activeStatus = new JLabel();
        activeStatus.setText("test status");
        activeStatus.setHorizontalAlignment(JLabel.CENTER);

        friendName = new JLabel(name);
        friendName.setHorizontalAlignment(JLabel.CENTER);
        
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle remove action here
                System.out.println("Friend removed: " + name);
            }
        });
        
        blockButton = new JButton("Block");
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                System.out.println("Friend blocked: " + name);
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
    }
}
