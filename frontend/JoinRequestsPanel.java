package frontend;

import backend.GroupManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class JoinRequestsPanel extends JPanel {
    private GroupManager groupManager = GroupManager.getInstance();
    private String userID;
    private String groupId;
    private GroupPage groupPage;

    public JoinRequestsPanel(GroupPage groupPage, String userID, String groupId) throws IOException {
        this.userID = userID;
        this.groupPage = groupPage;
        this.groupId = groupId;

        // Set layout to display requests vertically
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Get join requests from the group manager
        ArrayList<String> joinRequests = groupManager.getRequests(groupId);

        // Iterate through the join requests and add JoinRequestFrontend components
        for (String requesterId : joinRequests) {
            JoinRequestFrontend request = new JoinRequestFrontend(userID, groupId, requesterId, groupPage);
            this.add(request);
            this.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between requests
        }
    }
}
