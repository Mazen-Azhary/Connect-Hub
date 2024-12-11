package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import backend.*;

public class JoinRequestFrontend extends JPanel {

    private ProfileManager profileManager = ProfileManager.getInstance();
    private GroupManager groupManager = GroupManager.getInstance();
    private JLabel userPhoto;
    private JLabel userName;
    private String requesterId;
    private String userID;
    private String groupID;
    private GroupPage groupPage;

    public JoinRequestFrontend(String userID, String groupId, String requesterId, GroupPage groupPage) throws IOException {
        setLayout(new BorderLayout());
        this.userID = userID;
        this.groupID = groupId;
        this.requesterId = requesterId;
        this.groupPage = groupPage;

        User requesterProfile = profileManager.getUser(requesterId);
        ImageIcon photo = new ImageIcon(requesterProfile.getProfile().getProfilePhoto());

        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        userPhoto = new JLabel(circularPhoto);
        userPhoto.setPreferredSize(new Dimension(diameter, diameter));
        userPhoto.setHorizontalAlignment(JLabel.CENTER);
        userName = new JLabel(requesterProfile.getUsername());
        userName.setHorizontalAlignment(JLabel.CENTER);
        userName.setFont(new Font("Arial", Font.BOLD, 16));
        userName.setBorder(new EmptyBorder(0, -5, 0, 10)); // Top, Left, Bottom, Right

        JPanel buttonPanel = new JPanel();

        if (groupManager.getRole(userID, groupId).equals(GroupRole.ADMIN) ||
                groupManager.getRole(userID, groupId).equals(GroupRole.PRIMARYADMIN)) {

            JButton acceptButton = new JButton("Approve");
            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AdminRole adminRole=AdminRole.getInstance();
                    adminRole.respondRequest(requesterId,groupId,true);
                    groupPage.dispose();
                    new GroupPage(userID, groupId);
                }
            });
            buttonPanel.add(acceptButton);

            JButton rejectButton = new JButton("Reject");
            rejectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AdminRole adminRole=AdminRole.getInstance();
                    adminRole.respondRequest(requesterId,groupId,false);
                    groupPage.dispose();
                    new GroupPage(userID, groupId);
                }
            });
            buttonPanel.add(rejectButton);
        }

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Adds horizontal spacing

        add(userPhoto, BorderLayout.WEST);
        add(userName, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    public static ImageIcon getCircularImageIcon(ImageIcon icon, int diameter) {
        // Create a new buffered image with transparency
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();

        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a circular clipping region
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));

        // Draw the original image scaled to fit the circle
        g2.drawImage(icon.getImage(), 0, 0, diameter, diameter, null);
        g2.dispose();

        return new ImageIcon(circularImage);
    }
}
