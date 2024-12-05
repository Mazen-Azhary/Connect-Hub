package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FriendListPannel extends JPanel {
    private ArrayList<FriendFrontend> friends = new ArrayList<>();
String userID;
    public FriendListPannel(String userID) {
        this.userID = userID;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (int i = 0; i < 10; i++) {
            ImageIcon userPhoto = new ImageIcon(new ImageIcon("src/database/Signup.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

            FriendFrontend friend = new FriendFrontend("Friend " + (i + 1), userPhoto,userID);

            friends.add(friend);
            this.add(friend);
            
            this.add(Box.createRigidArea(new Dimension(0, 10))); 
        }
    }
}
