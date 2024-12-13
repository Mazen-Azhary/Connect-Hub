package frontend;

import backend.Content;
import backend.ProfileManager;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PostPanel extends JPanel {

    private ProfileManager manager = ProfileManager.getInstance();

    public PostPanel(ArrayList<Content> posts) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout for posts to be stacked vertically

        for (Content post : posts) {
            // Retrieve user details
            User user = manager.getUser("" + post.getAuthorId());
            String username = user.getUsername();
            String content = post.getContent();
            String imagePath = post.getImage();

            Random random = new Random();
            //for frontend testing
            boolean isFromGroup = random.nextBoolean();
            String groupID = "1";

            PostFrontend postFrontend = new PostFrontend(post.getContentId(), content, username, imagePath, isFromGroup, groupID);

            this.add(postFrontend);
            this.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts
        }
    }
}
