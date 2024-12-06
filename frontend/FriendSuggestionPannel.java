package frontend;

import backend.FriendDatabase;
import backend.ProfileDataBase;
import backend.SuggestionGenerator;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class FriendSuggestionPannel extends JPanel {
    private String userID;
    private  NewsFeed newsFeed;
    public FriendSuggestionPannel(NewsFeed newsFeed,String userID) throws IOException {
        this.userID = userID;
        this.newsFeed = newsFeed;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        SuggestionGenerator generator= SuggestionGenerator.getInstance();
        generator.generateSuggestions(userID);
        HashSet<String> suggestions=generator.shuffleSuggestions(userID);
        for (String suggestion : suggestions) {
            FriendSuggestionFrontEnd friend = new FriendSuggestionFrontEnd(userID,suggestion,this.newsFeed);
            this.add(friend);
            this.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }
}
