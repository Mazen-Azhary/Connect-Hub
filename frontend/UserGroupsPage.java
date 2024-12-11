package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import backend.*;

public class UserGroupsPage extends JFrame {

    private String id;
    private JPanel groupsPannel;
    private JScrollPane scrollPane;
    private ArrayList<Group> groups;
    private boolean sug;

    public UserGroupsPage(String id,boolean sug) throws IOException {
        this.id = id;
        this.sug = sug;
        // Set JFrame properties
        setTitle("Groups");
        setSize(500, 600);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this JFrame on exit
        // Initialize components
        initComponents();
        if(sug)
        {
            this.groups=new ArrayList<>(SuggestionsGeneratorGroups.getInstance().generateSuggestions(id));
        }
        else this.groups=GroupManager.getInstance().getGroups(id);
        displayFriendRequests();
        setResizable(false);
        // Make the frame visible
        setVisible(true);
    }


    private void displayFriendRequests() throws IOException {
        groupsPannel.removeAll(); // Clear the panel before re-rendering

        for (Group group : groups) {
            // Create a compact panel for each request
            JPanel requestPanel = new JPanel();
            requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.X_AXIS));
            requestPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
            requestPanel.setPreferredSize(new Dimension(400, 70)); // Adjust width and height

            // Get user details

            // Name label
            JLabel nameLabel = new JLabel(group.getName());
            nameLabel.setPreferredSize(new Dimension(100, 30)); // Set a fixed size
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font size for compactness
            requestPanel.add(nameLabel);

            // Icon Label with Fixed Size
            JLabel iconLabel;
            String icon = group.getPhoto();
            ImageIcon imageIcon;

            if (icon == null) {
                // Use a default icon with fixed size
                //me7tageen ne3adelha
                imageIcon = new ImageIcon(new ImageIcon("src/database/defaultIcon.jpg")
                        .getImage()
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            } else {
                // Use the user's icon with fixed size
                imageIcon = new ImageIcon(new ImageIcon(icon)
                        .getImage()
                        .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            }

            iconLabel = new JLabel(imageIcon);
            iconLabel.setPreferredSize(new Dimension(50, 50)); // Ensure the JLabel matches the scaled image
            iconLabel.setMaximumSize(new Dimension(50, 50)); // Prevent resizing
            iconLabel.setMinimumSize(new Dimension(50, 50)); // Prevent resizing
            iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT); // Align vertically
            requestPanel.add(Box.createHorizontalStrut(10)); // Add spacing
            requestPanel.add(iconLabel);

            if (sug)
            {
                JButton viewButton = new JButton("Ask to join");
                viewButton.setBackground(Color.GREEN);
                viewButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller button font
                viewButton.setPreferredSize(new Dimension(80, 30)); // Button size
                viewButton.addActionListener(e -> {
                    GroupManager.getInstance().request(id,group.getGroupId());
                    groupsPannel.revalidate();
                    groupsPannel.repaint();
                });
                requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
                requestPanel.add(viewButton);
                requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            }
            else
            {

            JButton viewButton = new JButton("View Group");
            viewButton.setBackground(Color.GREEN);
            viewButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller button font
            viewButton.setPreferredSize(new Dimension(80, 30)); // Button size
            viewButton.addActionListener(e -> {
                new GroupPage(id,group.getGroupId());
                dispose();
                groupsPannel.revalidate();
                groupsPannel.repaint();
            });
            requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            requestPanel.add(viewButton);
            requestPanel.add(Box.createHorizontalStrut(10)); // Add space between components
            }

            // Add the request panel to the list
            groupsPannel.add(requestPanel);
            groupsPannel.add(Box.createVerticalStrut(5)); // Add spacing between rows
        }

        // Refresh the panel to apply changes
        groupsPannel.revalidate();
        groupsPannel.repaint();
    }



    @SuppressWarnings("unchecked")
    private void initComponents() {
        this.setLayout(new BorderLayout());

        groupsPannel = new JPanel();
        groupsPannel.setLayout(new BoxLayout(groupsPannel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(groupsPannel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            new UserGroupsPage("1",true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}