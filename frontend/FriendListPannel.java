package frontend;

import backend.FriendDatabase;
import backend.ProfileDataBase;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class FriendListPannel extends JPanel {
    private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
    private ProfileDataBase profileDatabase = new ProfileDataBase("src/database/Profile.json");
private String userID;
private  NewsFeed newsFeed;
    public FriendListPannel(NewsFeed newsFeed,String userID) throws IOException {
        this.userID = userID;
        this.newsFeed = newsFeed;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ArrayList<String> friendIDs=friendDatabase.getUser(userID).getProfile().getFriends();

        for (int i = 0; i < friendIDs.size(); i++) {


            FriendFrontend friend = new FriendFrontend(userID,friendIDs.get(i),this.newsFeed);


            this.add(friend);
            
            this.add(Box.createRigidArea(new Dimension(0, 10))); 
        }
    }
}
