package frontend;

import backend.Content;
import backend.ProfileManager;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PostPanel extends JPanel {
    public PostPanel(ArrayList<Content> posts) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout for posts to be stacked vertically

        for (Content post : posts) {
            String groupID =post.getGroupId();
            boolean isFromGroup;
            if(groupID==null)
            {
                isFromGroup=false;
            }
            else
            {
                isFromGroup=true;
            }
            PostFrontend postFrontend = new PostFrontend(post, isFromGroup, groupID);

            this.add(postFrontend);
            this.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts
        }
    }
}
