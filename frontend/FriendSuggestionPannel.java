package frontend;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FriendSuggestionPannel extends JPanel {
    private ArrayList<FriendSuggestionFrontEnd> friendSuggestions = new ArrayList<>();
    String userID;
    public FriendSuggestionPannel(String userID) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.userID = userID;
        for (int i = 0; i < 10; i++) {
            ImageIcon userPhoto = new ImageIcon(new ImageIcon("src/database/Signup.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

            FriendSuggestionFrontEnd suggestion = new FriendSuggestionFrontEnd(userID);

            friendSuggestions.add(suggestion);
            this.add(suggestion);

            // Add space between each suggestion component
            this.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }
}
