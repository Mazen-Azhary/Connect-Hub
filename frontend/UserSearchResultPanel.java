package frontend;

import backend.ProfileManager;
import backend.SearchManager;
import backend.User;
import backend.UserContentDatabase;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserSearchResultPanel extends JPanel {
    private String currentUserID;
    private SearchManager searchManager;

    public UserSearchResultPanel(String currentUserID, List<String> searchResultIDs) throws IOException {
        this.currentUserID = currentUserID;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding


        for (String userID : searchResultIDs) {


            // Create the frontend component for each user
            UserSearchResultFrontend searchResult = new UserSearchResultFrontend(userID);
            add(searchResult);
            add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between components
        }
    }
}
