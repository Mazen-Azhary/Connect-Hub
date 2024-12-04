package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendFrontend extends JPanel {
    private JLabel friendPhoto;
    private JLabel friendName;
    private JButton removeButton;
    private JButton blockButton; // New block button

    public FriendFrontend(String name, ImageIcon photo) {
        setLayout(new BorderLayout());
        
        friendPhoto = new JLabel(photo);
        friendPhoto.setPreferredSize(new Dimension(50, 50));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);
        
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
    }
}
