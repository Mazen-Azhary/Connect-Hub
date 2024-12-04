package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StoryPanel extends JPanel {
    private ArrayList<StoryFrontend> stories = new ArrayList<>();

    public StoryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        for (int i = 0; i < 10; i++) {
            
            ImageIcon storyThumbnail = new ImageIcon(new ImageIcon("src/database/Signup.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            StoryFrontend story = new StoryFrontend(i, "User " + (i + 1), storyThumbnail);
            stories.add(story);
            this.add(story);
            this.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }
}
