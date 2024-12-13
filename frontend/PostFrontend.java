package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PostFrontend extends JPanel {

    private int id;
    private String content;
    private String author;
    private String imagePath;
    private boolean isFromGroup;
    private String groupID;
    private static final int PADDING = 10;

    private JLabel contentLabel;
    private JLabel imageLabel;

    public PostFrontend(int id, String content, String author, String imagePath, boolean isFromGroup, String groupID) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.imagePath = imagePath;
        this.isFromGroup = isFromGroup;
        this.groupID = groupID;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400, imagePath != null && !imagePath.isEmpty() ? 200 : 120));
        setBackground(isFromGroup ? new Color(220, 240, 255) : Color.LIGHT_GRAY);

        // Create the content label
        contentLabel = new JLabel("<html>" + content.replace("\n", "<br>") + "</html>");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contentLabel.setForeground(Color.BLACK);
        contentLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

        // Handle the image (if any)
        if (imagePath != null && !imagePath.isEmpty()) {
            ImageIcon postImage = new ImageIcon(imagePath);
            Image scaledImage = postImage.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        }

        // Add content and image to panel
        add(contentLabel);
        if (imageLabel != null) {
            add(imageLabel);
        }
    }
}
