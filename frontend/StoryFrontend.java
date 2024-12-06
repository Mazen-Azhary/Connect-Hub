package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class StoryFrontend extends JPanel {
    private String UserId;
    private String userName;
    private ImageIcon profilePhoto;
    private JButton viewStoryButton;
    private String thumbnailPhoto;
    private String Content;

    public StoryFrontend(String UserId, String Username, ImageIcon profilePhoto, String thumbnailPhoto, String Content) {
        this.UserId = UserId;
        this.userName = Username;
        this.profilePhoto = profilePhoto;
        this.thumbnailPhoto = thumbnailPhoto;
        this.Content = Content;

        // Use BoxLayout for a vertical alignment with reduced spacing
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(60, 70)); // Compact overall size
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // Minimal padding

        // Rounded Profile Picture
        int diameter = 40;
        ImageIcon circularPhoto = getCircularImageIcon(profilePhoto, diameter);
        JLabel thumbnailLabel = new JLabel(circularPhoto);
        thumbnailLabel.setAlignmentX(CENTER_ALIGNMENT);
        JLabel nameLabel = new JLabel(userName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 10)); // Smaller font
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Compact "View Story" button
        viewStoryButton = new JButton("View Story");
        viewStoryButton.setFont(new Font("Arial", Font.PLAIN, 8)); // Smaller font
        viewStoryButton.setMargin(new Insets(0, 2, 0, 2)); // Minimal button padding
        viewStoryButton.setFocusable(false);
        viewStoryButton.setAlignmentX(CENTER_ALIGNMENT);

        // Add ActionListener to the button
        viewStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStory();
            }
        });

        // Add components with tight spacing
        add(thumbnailLabel);
        add(Box.createVerticalStrut(2)); // Minimal vertical space between components
        add(nameLabel);
        add(Box.createVerticalStrut(2));
        add(viewStoryButton);
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

    // Method to display the story
    private void viewStory() {
        // Create and display the StoryDisplayer frame
        SwingUtilities.invokeLater(() -> {
            StoryDisplayer storyDisplayer = new StoryDisplayer(thumbnailPhoto, Content);
            storyDisplayer.setVisible(true); // Make the frame visible
      });
 }
}
