package frontend;

import backend.GroupManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class PostFrontend extends JPanel {

    private int id;
    private String content;
    private String author;
    private String imagePath;
    private boolean isFromGroup;
    private String groupID;
    private static final int PADDING = 10;

    public PostFrontend(int id, String content, String author, String imagePath, boolean isFromGroup, String groupID) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.imagePath = imagePath;
        this.isFromGroup = isFromGroup;
        this.groupID = groupID;

        setPreferredSize(new Dimension(400, imagePath != null && !imagePath.isEmpty() ? 200 : 120));
        setBackground(isFromGroup ? new Color(220, 240, 255) : Color.LIGHT_GRAY); // Different background for group posts
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString("Author: " + author, 10, 20);
        g.drawString("Content: " + content, 10, 40);
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                imageIcon.paintIcon(this, g, 10, 50);
            } catch (Exception e) {

            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int yPosition = 20; // Initial vertical position for text

        // Display group name if the post is from a group
        if (isFromGroup) {
            System.out.println("haha");
            GroupManager groupManager = GroupManager.getInstance();
            String groupName = groupManager.getGroup(groupID).getName();
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Group: " + groupName, 10, yPosition);
            yPosition += 20; // Move down for next text
        }

        // Display author
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Author: " + author, 10, yPosition);
        yPosition += 20;

        // Display content
        g.drawString("Content: " + content, 10, yPosition);
        yPosition += 20;

        // Display image if available
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Resize image
                g.drawImage(scaledImage, 10, yPosition, null);
            } catch (Exception e) {
                g.setColor(Color.RED);
                g.drawString("Image failed to load.", 10, yPosition);
            }
        }
    }
}
