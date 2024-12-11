package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupSearchResultPanel extends JPanel {
    private String currentUserID;
    private List<String> groupIDs;

    public GroupSearchResultPanel(String currentUserID, List<String> groupIDs) throws IOException {
        this.currentUserID = currentUserID;
        this.groupIDs = new ArrayList<>(groupIDs);
        initializeUI();
    }

    private void initializeUI() throws IOException {
        removeAll();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String groupID : groupIDs) {
            GroupSearchResultFrontend groupResult = new GroupSearchResultFrontend(currentUserID, groupID, (ArrayList<String>) groupIDs, this);
            add(groupResult);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        revalidate();
        repaint();
    }

    public void reloadGroupResults(List<String> updatedGroupIDs) throws IOException {
        this.groupIDs = new ArrayList<>(updatedGroupIDs);
        initializeUI();
    }
}
