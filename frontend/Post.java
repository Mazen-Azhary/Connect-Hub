package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Post extends JPanel {
    private int id;
    private String content;
    private String author;
    private String imagePath;

    public Post(int id, String content, String author, String imagePath) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.imagePath = imagePath;

        setPreferredSize(new Dimension(300, 100));
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
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
}