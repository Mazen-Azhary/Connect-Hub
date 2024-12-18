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
    private NewsFeed newsFeed;
    public GroupSearchResultFrontend(String currentUserID, String groupID, ArrayList<String> groupIDs, GroupSearchResultPanel groupSearchResultPanel,NewsFeed newsFeed) throws IOException {
        this.newsFeed = newsFeed;
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
                if(groupManager.getRole(currentUserID,groupID)!=null) {
                    GroupPage groupView = new GroupPage(currentUserID, groupID);
                    groupView.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"Please request joining group first and wait for acceptance");
                }
            }
        });



        groupName = new JLabel(group.getName());
        groupName.setHorizontalAlignment(JLabel.CENTER);
        groupName.setFont(new Font("Arial", Font.BOLD, 16));
        groupName.setBorder(new EmptyBorder(0, 10, 0, 10)); // Add padding

        boolean isMember = groupManager.isMember(currentUserID, groupID);
        boolean isPending = groupManager.isPending(currentUserID, groupID);
        button1 = new JButton();
        System.out.println(isMember);
        System.out.println(isPending);
        if(isMember){
            button1.setText("Leave");
        }
        else if (isPending){
            button1.setVisible(false);
        }
        else {
            button1.setText("Request");
        }
        button1.setBackground(new Color(70, 130, 180)); // Steel blue color
        button1.setForeground(Color.WHITE);
        button1.setPreferredSize(new Dimension(150, 40)); // Button size

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupManager.isPending(currentUserID,groupID)){//request already sent to acoid duplicate reuqest
                    JOptionPane.showMessageDialog(null,"Request already sent");
                }else if(groupManager.isMember(currentUserID,groupID)){//leave group if member
                        groupManager.leave(groupID, currentUserID);
                        button1.setText("Request");
                        JOptionPane.showMessageDialog(null,"left group");
                }
                else{//send request
                    JOptionPane.showMessageDialog(null,"Sent request for group");
                    groupManager.request(currentUserID, groupID);
                    button1.setVisible(false);
                }

                revalidate();
                repaint();
            }
        });

        button2 = new JButton("View Group");
        button2.setBackground(new Color(34, 139, 34)); // Green color
        button2.setForeground(Color.WHITE);
        button2.setPreferredSize(new Dimension(150, 40));

        button2.addActionListener(new ActionListener() {//view group button
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupManager.isMember(currentUserID,groupID)){
                    new GroupPage(currentUserID, groupID);
                    newsFeed.dispose();
                }else if (groupManager.isPending(currentUserID,groupID))
                {
                    JOptionPane.showMessageDialog(null,"You can join when some one approve you");
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Please request joining group first and wait for acceptance");
                }
                revalidate();
                repaint();
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