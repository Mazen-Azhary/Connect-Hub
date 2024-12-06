/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import backend.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mazen
 */
public class NewsFeed extends javax.swing.JFrame {
    private UserContentDatabase userContentDatabase = new UserContentDatabase("src/database/UserContents.json");
    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
    private ArrayList<Content> contents = new ArrayList<>(); //posts
    private String id;
    /**
     * Creates new form NewsFeed
     */
    public NewsFeed(String id) throws IOException {
        this.id = id;
        initComponents();
        setTitle("NewsFeed");
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(1000, 700);
        setVisible(true);
        FriendListPannel friendPanel = new FriendListPannel(this,null,id);
        friendsScroll.setViewportView(friendPanel);
        FriendSuggestionPannel friendSuggestionPanel = new FriendSuggestionPannel(this,id);
        suggestionsScroll.setViewportView(friendSuggestionPanel);
        ContentViewer contentViewer = ContentViewer.getInstance();
        ArrayList<Content> posts = contentViewer.generatePosts(id);
        ArrayList<Content> stories = contentViewer.generateStories(id);

        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

        for (Content post : posts) {
            // Retrieve user details
            User user = profileDataBase.getUser("" + post.getAuthorId());
            ImageIcon photo = new ImageIcon(user.getProfile().getProfilePhoto());

            // Check if the image is valid
            if (photo.getIconWidth() <= 0 || photo.getIconHeight() <= 0) {
                photo = new ImageIcon("src/database/defaultIcon.jpg");
            }

            // Create circular profile photo
            int diameter = 30;
            ImageIcon circularPhoto = FriendFrontend.getCircularImageIcon(photo, diameter);
            JLabel friendPhoto = new JLabel(circularPhoto);
            friendPhoto.setPreferredSize(new Dimension(diameter, diameter));
            friendPhoto.setHorizontalAlignment(JLabel.CENTER);

            // Username label
            JLabel friendName = new JLabel(user.getUsername());
            friendName.setHorizontalAlignment(JLabel.LEFT);
            friendName.setFont(new Font("Arial", Font.BOLD, 14));
            friendName.setBorder(new EmptyBorder(0, 5, 0, 0)); // Add spacing from the photo

            // Content label
            JLabel contentLabel = new JLabel("<html>" + post.getContent().replace("\n", "<br>") + "</html>");
            contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            contentLabel.setForeground(Color.BLACK);
            contentLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

            // Image (if applicable)
            JLabel imageLabel = null;
            if (post.getImage() != null) {
                ImageIcon postImage = new ImageIcon(post.getImage());
                // Resize the image while maintaining aspect ratio
                int maxWidth = 200; // Desired maximum width
                int maxHeight = 150; // Desired maximum height
                Image scaledImage = postImage.getImage().getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);

                // Wrap the resized image in an ImageIcon
                imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
            }

            // Create a horizontal panel for photo and username
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
            headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            headerPanel.setOpaque(false); // Ensure it blends with background
            headerPanel.add(friendPhoto);
            headerPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between photo and name
            headerPanel.add(friendName);

            // Create a vertical panel for the entire post
            JPanel singlePostPanel = new JPanel();
            singlePostPanel.setLayout(new BoxLayout(singlePostPanel, BoxLayout.Y_AXIS));
            singlePostPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            singlePostPanel.setBackground(Color.WHITE);
            singlePostPanel.add(headerPanel);
            singlePostPanel.add(contentLabel);
            if (imageLabel != null) {
                singlePostPanel.add(imageLabel);
            }
            singlePostPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts

            // Add the single post to the main postPanel
            postPanel.add(singlePostPanel);
            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts in the main panel
        }

        JPanel storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.X_AXIS));
        for (Content story : stories) {
            ImageIcon storyThumbnail = new ImageIcon(story.getImage());
            StoryFrontend storyFrontend = new StoryFrontend(story.getAuthorId(), "User" + story.getAuthorId(), storyThumbnail);
            storiesPanel.add(storyFrontend);
            storiesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        postsScroll.setViewportView(postPanel);
        storiesScrollable.setViewportView(storiesPanel);

        postsScroll.getVerticalScrollBar().setUnitIncrement(20);
        postsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        revalidate();
        repaint();

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        User user= null;
        try {
            user = userContentDatabase.getUser(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OptionsMenu = new javax.swing.JInternalFrame(user.getUsername());
        storiesScrollable = new javax.swing.JScrollPane();
        createPostButton = new javax.swing.JButton();
        friendsScroll = new javax.swing.JScrollPane();
        postsScroll = new javax.swing.JScrollPane();
        suggestionsScroll = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        AddStoryButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 255));

        JMenuBar mb = new JMenuBar();
        JMenu om = new JMenu("Options");
        JMenuItem ref = new JMenuItem("Refresh page");

        ref.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //refreshPage();
            }
        });

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //logout();
            }
        });

        om.add(ref);
        om.add(logoutItem);

        mb.add(om);
        OptionsMenu.setJMenuBar(mb);
        OptionsMenu.setFrameIcon(new ImageIcon("src/database/Signup.png"));
        OptionsMenu.setSize(400, 300);
        OptionsMenu.setVisible(true);

        javax.swing.GroupLayout OptionsMenuLayout = new javax.swing.GroupLayout(OptionsMenu.getContentPane());
        OptionsMenu.getContentPane().setLayout(OptionsMenuLayout);
        OptionsMenuLayout.setHorizontalGroup(
            OptionsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );
        OptionsMenuLayout.setVerticalGroup(
            OptionsMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        createPostButton.setText("Create Post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPostButtonActionPerformed(evt);
            }
        });

        for (backend.Content content : contents) {
            Post post = new Post(content.getContentId(), content.getContent(), "Author" + content.getContent(),content.getImage());
            postsScroll.add(post);
            postsScroll.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
        }

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Stories");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Posts");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Suggestions");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Friends");

        AddStoryButton.setText("Add Story");
        AddStoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStoryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                                .addGap(498, 498, 498))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(324, 324, 324)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(suggestionsScroll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(postsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))
                            .addComponent(friendsScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(storiesScrollable)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(OptionsMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(354, 354, 354)
                .addComponent(createPostButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(AddStoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OptionsMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(storiesScrollable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createPostButton)
                    .addComponent(AddStoryButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(postsScroll)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(friendsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(suggestionsScroll)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPostButtonActionPerformed
          new CreatePostPage(id);
    }//GEN-LAST:event_createPostButtonActionPerformed

    private void AddStoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStoryButtonActionPerformed
        new CreateStoryPage(id);
    }//GEN-LAST:event_AddStoryButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddStoryButton;
    private javax.swing.JInternalFrame OptionsMenu;
    private javax.swing.JButton createPostButton;
    private javax.swing.JScrollPane friendsScroll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane postsScroll;
    private javax.swing.JScrollPane storiesScrollable;
    private javax.swing.JScrollPane suggestionsScroll;
    // End of variables declaration//GEN-END:variables
}
