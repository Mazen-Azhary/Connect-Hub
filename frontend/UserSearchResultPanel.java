package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserSearchResultPanel extends JPanel {
    private String currentUserID;
    private SearchManager searchManager;

    public UserSearchResultPanel(String currentUserID, List<String> searchResultIDs) throws IOException {
        this.currentUserID = currentUserID;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Testing frontend
        searchResultIDs = new ArrayList<>();
        searchResultIDs.add("1");
        searchResultIDs.add("2");
        searchResultIDs.add("3");
        searchResultIDs.add("5");
     for (int i = 0; i < searchResultIDs.size(); i++) {
            String userID = searchResultIDs.get(i);
         boolean isFriend;
            if(FriendManager.getInstance().isFriend(userID,searchResultIDs.get(i))) {
                isFriend = true; // Get the friend status for the current user
            }else{
                isFriend = false;
            }
            System.out.println("haha");
            // Create the frontend component for each user, passing the friend status
         if(i==3){
             isFriend=true;
         }
            UserSearchResultFrontend searchResult = new UserSearchResultFrontend(userID, isFriend);
            add(searchResult);
            add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between components
        }
    }
}
