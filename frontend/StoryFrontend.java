package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoryFrontend extends JPanel {
    private int id;
    private String userName;
    private ImageIcon storyThumbnail;
    private JButton viewStoryButton;

    public StoryFrontend(int id, String userName, ImageIcon storyThumbnail) {
        this.id = id;
        this.userName = userName;
        this.storyThumbnail = storyThumbnail;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); 
        setPreferredSize(new Dimension(200, 150)); 

        JLabel thumbnailLabel = new JLabel(storyThumbnail);
        thumbnailLabel.setPreferredSize(new Dimension(100, 100)); 

        JLabel nameLabel = new JLabel(userName);

        viewStoryButton = new JButton("View Story");
        viewStoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewStory();
            }
        });

        add(thumbnailLabel);
        add(nameLabel);
        add(viewStoryButton);
    }

    // Method to handle viewing the story (could be a placeholder or show a detailed view)
    private void viewStory() {
        System.out.println("Viewing story of " + userName);
    }
}
