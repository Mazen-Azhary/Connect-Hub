package frontend;

import backend.FriendDatabase;
import backend.ProfileDataBase;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class FriendListPannel extends JPanel {
    FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
    ProfileDataBase profileDatabase = new ProfileDataBase("src/database/Profile.json");
String userID;
    public FriendListPannel(String userID) throws IOException {
        this.userID = userID;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ArrayList<String> friendIDs=friendDatabase.getUser(userID).getProfile().getFriends();

        for (int i = 0; i < friendIDs.size(); i++) {


            FriendFrontend friend = new FriendFrontend(userID,friendIDs.get(i));


            this.add(friend);
            
            this.add(Box.createRigidArea(new Dimension(0, 10))); 
        }
    }
}
