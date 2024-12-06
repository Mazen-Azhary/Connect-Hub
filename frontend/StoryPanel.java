package frontend;

import backend.Content;
import backend.ContentViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import backend.*;
public class StoryPanel extends JPanel {
    private ArrayList<StoryFrontend> stories_ = new ArrayList<>();
    private ContentViewer contentViewer;
    private ProfileManager profileManager=ProfileManager.getInstance();
    private ArrayList<Content> stories;
    public StoryPanel(String UserId) {
        contentViewer=ContentViewer.getInstance();
        try {
            stories=contentViewer.generateStories(UserId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Horizontal arrangement
        setPreferredSize(new Dimension(700, 50)); // Fixed height for the panel
        for(Content story : stories) {
            ImageIcon profilePhoto = null;
            String img;

                img=profileManager.getUser(story.getAuthorId()+"").getProfile().getProfilePhoto();

            if (img != null) {
                profilePhoto = new ImageIcon(img);
            }
            else
            {
                profilePhoto = new ImageIcon("src/database/defaultIcon.jpg");
            }
            StoryFrontend pack = null;

                pack = new StoryFrontend(UserId, profileManager.getUser(story.getAuthorId()+"").getUsername(), profilePhoto,story.getImage(),story.getContent());

            //stories_.add(pack);
            this.add(pack);
            this.add(Box.createHorizontalStrut(5)); // Space between stories_
        }
//        for (int i = 0; i < 200; i++) {
//            // Load and resize the thumbnail image
//            ImageIcon profilePhoto = new ImageIcon(new ImageIcon("src/database/Signup.png")
//                    .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
//            StoryFrontend story = new StoryFrontend(i, "User " + (i + 1), profilePhoto);
//            stories_.add(story);
//            this.add(story);
//            this.add(Box.createHorizontalStrut(5)); // Space between stories_
//        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Stories");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 150);
//
//        // Create the StoryPanel and wrap it in a JScrollPane
//        StoryPanel storyPanel = new StoryPanel();
//        JScrollPane scrollPane = new JScrollPane(storyPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
//                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); // Smooth scrolling
//
//        frame.add(scrollPane);
//        frame.setVisible(true);
//}
}
