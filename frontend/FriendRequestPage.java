package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import backend.FriendDatabase;
import backend.FriendManager;

public class FriendRequestPage extends JFrame {

    private String id;
    private ArrayList<String> friendRequests;
    private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
    private FriendManager friendManager = FriendManager.getInstance();
    private JPanel requestListPanel;
    private JScrollPane scrollPane;

    public FriendRequestPage(String id) throws IOException {
        this.id = id;

        // Set JFrame properties
        setTitle("Friend Requests");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this JFrame on exit

        // Initialize components
        initComponents();
        loadFriendRequests();
        displayFriendRequests();
        setResizable(false);
        // Make the frame visible
        setVisible(true);
    }

    private void loadFriendRequests() {
        try {
            friendRequests = friendDatabase.getUser(id).getProfile().getFriendRecievedRequests();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayFriendRequests() throws IOException {
        requestListPanel.removeAll();
        for (String request : friendRequests) {
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            requestPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel nameLabel = new JLabel(friendDatabase.getUser(request).getUsername());
            nameLabel.setPreferredSize(new Dimension(150, 30));
            requestPanel.add(nameLabel);

            JButton acceptButton = new JButton("Accept");
            acceptButton.addActionListener(e -> {
                try {
                    friendManager.respond(id, request, true);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                requestListPanel.remove(requestPanel);
                requestListPanel.revalidate();
                requestListPanel.repaint();
            });
            requestPanel.add(acceptButton);

            JButton declineButton = new JButton("Decline");
            declineButton.addActionListener(e -> {
                try {
                    friendManager.respond(id, request, false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                requestListPanel.remove(requestPanel);
                requestListPanel.revalidate();
                requestListPanel.repaint();
            });
            requestPanel.add(declineButton);

            requestListPanel.add(requestPanel);
        }

        requestListPanel.revalidate();
        requestListPanel.repaint();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        this.setLayout(new BorderLayout());

        requestListPanel = new JPanel();
        requestListPanel.setLayout(new BoxLayout(requestListPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(requestListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);
    }
}