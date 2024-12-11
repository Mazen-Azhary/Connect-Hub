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

public class MembersFrontend extends JPanel {

    private ProfileManager profileManager=ProfileManager.getInstance();
    private GroupManager groupManager=GroupManager.getInstance();
    private JLabel friendPhoto;
    private JLabel friendName;
    private String memberId;
    private String userID;
    private String groupID;
    private GroupPage groupPage;
    public MembersFrontend(String userID, String groupId,String memberId,GroupPage groupPage,boolean admin) throws IOException {
        setLayout(new BorderLayout());
        this.userID = userID;
        this.groupID=groupId;
        this.memberId=memberId;
        this.groupPage=groupPage;
        User friendProfile;
        friendProfile = profileManager.getUser(memberId);
        ImageIcon photo = new ImageIcon(friendProfile.getProfile().getProfilePhoto());

        // Check if the image is valid (non-zero width/height)
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/defaultIcon.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        friendPhoto = new JLabel(circularPhoto);
        friendPhoto.setPreferredSize(new Dimension(diameter, diameter));
        friendPhoto.setHorizontalAlignment(JLabel.CENTER);
        friendName = new JLabel(friendProfile.getUsername());
        friendName.setHorizontalAlignment(JLabel.CENTER);
        friendName.setFont(new Font("Arial", Font.BOLD, 16));
        friendName.setBorder(new EmptyBorder(0, -5, 0, 10)); // Top, Left, Bottom, Right
        JPanel buttonPanel = new JPanel();
        if(groupManager.getRole(userID,groupId).equals(GroupRole.PRIMARYADMIN))
        {
            PrimaryAdminRole primaryAdminRole=PrimaryAdminRole.getInstance();
            if(admin)
            {
                JButton demoteButton=new JButton("Demote");
                demoteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        primaryAdminRole.demote(memberId,groupId);
                        groupPage.dispose();
                        new GroupPage(userID,groupId);
                    }
                });
                buttonPanel.add(demoteButton);
                JButton removeButton=new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        primaryAdminRole.removeMember(memberId,groupId);
                        groupPage.dispose();
                        new GroupPage(userID,groupId);
                    }
                });
                buttonPanel.add(removeButton);
            }
            else {
                JButton promoteButton=new JButton("Promote");
                promoteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        primaryAdminRole.promote(memberId,groupId);
                        groupPage.dispose();
                        new GroupPage(userID,groupId);
                    }
                });
                buttonPanel.add(promoteButton);
                JButton removeButton=new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        primaryAdminRole.removeMember(memberId,groupId);
                        groupPage.dispose();
                        new GroupPage(userID,groupId);
                    }
                });
                buttonPanel.add(removeButton);
            }
        }
        else if(groupManager.getRole(userID,groupId).equals(GroupRole.ADMIN))
        {
            AdminRole adminRole=AdminRole.getInstance();
            if(!admin)
            {
                JButton removeButton=new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        adminRole.removeMember(memberId,groupId);
                        groupPage.dispose();
                        new GroupPage(userID,groupId);
                    }
                });
                buttonPanel.add(removeButton);
            }
        }
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Adds horizontal spacing
        add(friendPhoto, BorderLayout.WEST);
        add(friendName, BorderLayout.CENTER);
        if(groupManager.getRole(userID,groupId).equals(GroupRole.MEMBER))
            return;
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