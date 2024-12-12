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
    private List<String> searchResultIDs; // Store the search result IDs

    public UserSearchResultPanel(String currentUserID, List<String> searchResultIDs) throws IOException {
        this.currentUserID = currentUserID;
        this.searchResultIDs = new ArrayList<>(searchResultIDs); // Copy the list to allow modification
        initializeUI();
    }

    private void initializeUI() throws IOException {
        removeAll(); // Clear the panel's content
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        for (String userID : searchResultIDs) {
            int state ;
            if(FriendManager.getInstance().isFriend(currentUserID, userID))
            {
                state = 1;
            }
            else if(FriendManager.getInstance().isReceived(currentUserID, userID))
            {
                state = 2;
            }
            else if (FriendManager.getInstance().isRequested(currentUserID, userID))
            {
                state = 3;
            }
            else {
                state = 0;
            }

            // Skip blocked users or the current user
            if (FriendManager.getInstance().isBlocked(currentUserID, userID) || Objects.equals(userID, currentUserID)) {
                continue;
            }

            // Create the frontend component for each user
            UserSearchResultFrontend searchResult = new UserSearchResultFrontend(currentUserID, userID, state, (ArrayList<String>) searchResultIDs, this);
            add(searchResult);
            add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between components
        }

        revalidate(); // Refresh the layout
        repaint(); // Redraw the panel
    }

    public void reloadSearchResults(List<String> updatedSearchResultIDs) throws IOException {
        this.searchResultIDs = new ArrayList<>(updatedSearchResultIDs); // Update the search result IDs
        initializeUI(); // Reinitialize the UI with updated data
    }
}
