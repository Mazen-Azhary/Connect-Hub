package frontend;

import backend.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GroupSearchResultFrontend extends JPanel {

    private JLabel groupPhoto;
    private JLabel groupName;
    private JButton button1;
    private JButton button2;

    public GroupSearchResultFrontend(String currentUserID, String groupID, ArrayList<String> groupIDs, GroupSearchResultPanel groupSearchResultPanel) throws IOException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        GroupManager groupManager = GroupManager.getInstance();
        Group group = groupManager.getGroup(groupID);

        ImageIcon photo = new ImageIcon(group.getPhoto());
        if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
            photo = new ImageIcon("src/database/ACM-LEVEL-UP-COVER.jpg");
        }

        // Resize the image and create a circular version
        int diameter = 50;
        ImageIcon circularPhoto = getCircularImageIcon(photo, diameter);

        groupPhoto = new JLabel(circularPhoto);
        groupPhoto.setPreferredSize(new Dimension(diameter, diameter));
        groupPhoto.setHorizontalAlignment(JLabel.CENTER);

        groupPhoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Placeholder for view group
            }
        });



        groupName = new JLabel(group.getName());
        groupName.setHorizontalAlignment(JLabel.CENTER);
        groupName.setFont(new Font("Arial", Font.BOLD, 16));
        groupName.setBorder(new EmptyBorder(0, 10, 0, 10)); // Add padding

        boolean isMember = groupManager.isMember(currentUserID, groupID);
        button1 = new JButton(isMember ? "Leave" : "Join");
        button1.setBackground(new Color(70, 130, 180)); // Steel blue color
        button1.setForeground(Color.WHITE);
        button1.setPreferredSize(new Dimension(100, 40)); // Button size

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"unimplemented yet");

            }
        });

        button2 = new JButton("View Group");
        button2.setBackground(new Color(34, 139, 34)); // Green color
        button2.setForeground(Color.WHITE);
        button2.setPreferredSize(new Dimension(100, 40));

        button2.addActionListener(new ActionListener() {//view group button
            @Override
            public void actionPerformed(ActionEvent e) {
                    GroupPage groupView = new GroupPage(currentUserID, groupID);
                    groupView.setVisible(true);

            }
        });

        // Layout setup
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(groupPhoto, BorderLayout.NORTH);
        topPanel.add(groupName, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static ImageIcon getCircularImageIcon(ImageIcon icon, int diameter) {
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(icon.getImage(), 0, 0, diameter, diameter, null);
        g2.dispose();

        return new ImageIcon(circularImage);
    }
}