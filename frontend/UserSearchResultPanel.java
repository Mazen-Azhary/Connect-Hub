package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSearchResultPanel extends JPanel {
    private String currentUserID;
    private SearchManager searchManager;

    public UserSearchResultPanel(String currentUserID, List<String> searchResultIDs) throws IOException {
        this.currentUserID = currentUserID;


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding


        for (int i = 0; i < searchResultIDs.size(); i++) {
            String userID = searchResultIDs.get(i);
            boolean isFriend;
            if (FriendManager.getInstance().isFriend(currentUserID, searchResultIDs.get(i))) {
                isFriend = true; // Get the friend status for the current user
            } else {
                isFriend = false;
            }


            // Create the frontend component for each user, passing the friend status
            if (FriendManager.getInstance().isBlocked(currentUserID, searchResultIDs.get(i)) || Objects.equals(userID, currentUserID)) {
                continue;
            }

            UserSearchResultFrontend searchResult = new UserSearchResultFrontend(currentUserID,userID, isFriend);
            add(searchResult);
            add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between components
        }
    }
}
