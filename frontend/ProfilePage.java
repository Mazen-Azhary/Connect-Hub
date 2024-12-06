/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import backend.*;
import backend.Content;
import backend.ContentFactory;
import backend.ProfileDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ProfilePage extends javax.swing.JFrame {
    private ArrayList<Content> contents = new ArrayList<>();
    private ProfileManager profileManager=ProfileManager.getInstance();//posts
    private String userId;
    private JLabel bioLabel = null;

    /**
     * Creates new form profilePage
     */
    public void edit() {
        User user = null;
            user = profileManager.getUser(userId);

        jLabel1.setText(user.getUsername());
        jLabel1.setFont(new Font("Arial", Font.BOLD, 14));

        String icon = user.getProfile().getProfilePhoto();
        String icon2 = user.getProfile().getCoverPhoto();
        ImageIcon imageIcon;
        if (icon == null) {
            // Use a default icon with fixed size
            imageIcon = new ImageIcon(new ImageIcon("src/database/defaultIcon.jpg")
                    .getImage()
                    .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } else {
            // Use the user's icon with fixed size
            imageIcon = new ImageIcon(new ImageIcon(icon)
                    .getImage()
                    .getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        }
        ProfilePictureLabel.setIcon(imageIcon);
        ImageIcon imageIcon2;
        if (icon2 == null) {
            // Use a default icon with fixed size
            imageIcon2 = new ImageIcon(new ImageIcon("src/database/defaultIcon.jpg")
                    .getImage()
                    .getScaledInstance(630, 150, Image.SCALE_SMOOTH));
        } else {
            // Use the user's icon with fixed size
            imageIcon2 = new ImageIcon(new ImageIcon(icon2)
                    .getImage()
                    .getScaledInstance(630, 150, Image.SCALE_SMOOTH));
        }
        CoverLabel.setIcon(imageIcon2);
        JLabel bioLabel = new JLabel();

        bioLabel.setText(user.getProfile().getBio()); // Assuming `getBio()` returns the user's bio
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bioLabel.setForeground(Color.GRAY); // Optional: Set a different color
        bioLabel.setHorizontalAlignment(SwingConstants.CENTER); // Optional: Center-align the text
        bioLabel.setBounds(jLabel1.getX(), jLabel1.getY() + 30, jLabel1.getWidth(), 20); // Position below the username
        if(this.bioLabel!=null)
        BioPanel.remove(this.bioLabel);
        BioPanel.add(bioLabel);
        this.bioLabel=bioLabel;
        BioPanel.repaint();
        FriendListPannel friendsPanel;
        try {
             friendsPanel = new FriendListPannel(null,this,userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jScrollPane2.setViewportView(friendsPanel);
    }

    public ProfilePage(String UserId) throws IOException {
        initComponents();
        setVisible(true);
        setResizable(false);
        setTitle("ProfilePage");
        this.userId = UserId;
        edit();
        JButton button = new JButton("Back to News Feed");
        button.setBounds(100, 70, 100, 30);

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new NewsFeed(userId);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button.setVisible(true);
        this.add(button);
         ContentViewer contentViewer=ContentViewer.getInstance();
        ArrayList<Content> posts=contentViewer.generateProfilePosts(userId);

        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

        // Add posts to jPanel1
        for (Content post : posts) {
            // Retrieve user details
            User user = profileManager.getUser("" + post.getAuthorId());
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime localDateTime=post.getTimestamp();
            String formattedDate = localDateTime.format(formatter);
            JLabel friendName = new JLabel(user.getUsername()+"        "+formattedDate);
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

        postScrollPane.getVerticalScrollBar().setUnitIncrement(20); // 20 pixels per scroll step
        postScrollPane.getHorizontalScrollBar().setUnitIncrement(20); // (Optional) for horizontal scrolling
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setLocationRelativeTo(null);
        //setSize(new Dimension(1000, 1000));
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

        HeaderPanel = new javax.swing.JPanel();
        CoverPhotoPanl = new javax.swing.JPanel();
        CoverLabel = new javax.swing.JLabel();
        ProfilePicturePanel = new javax.swing.JPanel();
        ProfilePictureLabel = new javax.swing.JLabel();
        BioPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        EditProfileButton = new javax.swing.JButton();
        AddStoryButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        changeProfilePictureButton = new javax.swing.JButton();
        changeCoverPictureButton = new javax.swing.JButton();
        CreatePostButton = new javax.swing.JButton();
        EditBioButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        postScrollPane = new javax.swing.JScrollPane();
        postPanel = new javax.swing.JPanel();
        ContentLabel1 = new javax.swing.JLabel();
        AuthorUsername1 = new javax.swing.JLabel();
        ImageLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout CoverPhotoPanlLayout = new javax.swing.GroupLayout(CoverPhotoPanl);
        CoverPhotoPanl.setLayout(CoverPhotoPanlLayout);
        CoverPhotoPanlLayout.setHorizontalGroup(
                CoverPhotoPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(CoverPhotoPanlLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(CoverLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        CoverPhotoPanlLayout.setVerticalGroup(
                CoverPhotoPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CoverPhotoPanlLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(CoverLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout ProfilePicturePanelLayout = new javax.swing.GroupLayout(ProfilePicturePanel);
        ProfilePicturePanel.setLayout(ProfilePicturePanelLayout);
        ProfilePicturePanelLayout.setHorizontalGroup(
                ProfilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ProfilePictureLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        ProfilePicturePanelLayout.setVerticalGroup(
                ProfilePicturePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ProfilePicturePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ProfilePictureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Username");

//        jLabel2.setText("Number of friends");

        EditProfileButton.setText("Change Password");
        EditProfileButton.setFocusable(false);
        EditProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditProfileButtonActionPerformed(evt);
            }
        });

        AddStoryButton.setText("Add Story");
        AddStoryButton.setFocusable(false);
        AddStoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStoryButtonActionPerformed(evt);
            }
        });

        changeProfilePictureButton.setText("Change Profile Picture");
        changeProfilePictureButton.setFocusable(false);
        changeProfilePictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeProfilePictureButtonActionPerformed(evt);
            }
        });

        changeCoverPictureButton.setText("Change Cover Picture");
        changeCoverPictureButton.setFocusable(false);
        changeCoverPictureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeCoverPictureButtonActionPerformed(evt);
            }
        });

        CreatePostButton.setText("Create Post");
        CreatePostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreatePostButtonActionPerformed(evt);
            }
        });

        EditBioButton.setText("Edit Bio");
        EditBioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBioButtonActionPerformed(evt);
            }
        });
        backButton.setText("Back to Newsfeed");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
                try {
                    new NewsFeed(userId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout BioPanelLayout = new javax.swing.GroupLayout(BioPanel);
        BioPanel.setLayout(BioPanelLayout);
        BioPanelLayout.setHorizontalGroup(
                BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(BioPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(BioPanelLayout.createSequentialGroup()
                                                .addGroup(BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(AddStoryButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CreatePostButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(EditBioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(BioPanelLayout.createSequentialGroup()
                                                .addComponent(changeProfilePictureButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(changeCoverPictureButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(EditProfileButton)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        BioPanelLayout.setVerticalGroup(
                BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(BioPanelLayout.createSequentialGroup()
                                .addGroup(BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(BioPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2))
                                        .addGroup(BioPanelLayout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addGroup(BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(AddStoryButton)
                                                        .addComponent(CreatePostButton)
                                                        .addComponent(EditBioButton)
                                                        .addComponent(backButton))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(BioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(changeProfilePictureButton)
                                        .addComponent(changeCoverPictureButton)
                                        .addComponent(EditProfileButton)
                                        .addComponent(backButton))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
                HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(CoverPhotoPanl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(HeaderPanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(ProfilePicturePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        HeaderPanelLayout.setVerticalGroup(
                HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(HeaderPanelLayout.createSequentialGroup()
                                .addComponent(CoverPhotoPanl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ProfilePicturePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 153, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 318, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout postPanelLayout = new javax.swing.GroupLayout(postPanel);
        postPanel.setLayout(postPanelLayout);
        postPanelLayout.setHorizontalGroup(
                postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ContentLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AuthorUsername1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ImageLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        );
        postPanelLayout.setVerticalGroup(
                postPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(postPanelLayout.createSequentialGroup()
                                .addComponent(AuthorUsername1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ContentLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ImageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(49, Short.MAX_VALUE))
        );

        postScrollPane.setViewportView(postPanel);

        for (backend.Content content : contents) {
            Post post = new Post(content.getContentId(), content.getContent(), "Author" + content.getContent(), content.getImage());
            postScrollPane.add(post);
            postScrollPane.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
        }

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Posts");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Friends");


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(HeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(postScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(postScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{jScrollPane2, postScrollPane});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changeProfilePictureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeProfilePictureButtonActionPerformed
        try {
            PictureChange profilePictureChange = new PictureChange(userId, this, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_changeProfilePictureButtonActionPerformed

    private void AddStoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStoryButtonActionPerformed
        new CreateStoryPage(userId);
    }//GEN-LAST:event_AddStoryButtonActionPerformed

    private void CreatePostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreatePostButtonActionPerformed
        new CreatePostPage(userId);
    }//GEN-LAST:event_CreatePostButtonActionPerformed

    private void EditProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditProfileButtonActionPerformed
        //we will handle the password changer here
        PasswordChangeDialog passwordChangeDialog=new PasswordChangeDialog();
        try {
            passwordChangeDialog.showPasswordChangeDialog(userId);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }//GEN-LAST:event_EditProfileButtonActionPerformed

    private void changeCoverPictureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeCoverPictureButtonActionPerformed
        try {
            PictureChange profilePictureChange = new PictureChange(userId, this, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_changeCoverPictureButtonActionPerformed

    private void EditBioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBioButtonActionPerformed

        String newBio = JOptionPane.showInputDialog(this, "Enter your new bio:", "Edit Bio", JOptionPane.PLAIN_MESSAGE);


        if (newBio != null ) {
            if(newBio.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bio cannot be empty. Please enter a valid bio.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String userBio = newBio.trim();
            User user;

                user=profileManager.getUser(userId);
                profileManager.editBio(userId,userBio);
            edit();

        } else {

        }
    }//GEN-LAST:event_EditBioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frontend.ProfilePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frontend.ProfilePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frontend.ProfilePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frontend.ProfilePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frontend.ProfilePage("2").setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddStoryButton;
    private javax.swing.JLabel AuthorUsername1;
    private javax.swing.JPanel BioPanel;
    private javax.swing.JLabel ContentLabel1;
    private javax.swing.JLabel CoverLabel;
    private javax.swing.JPanel CoverPhotoPanl;
    private javax.swing.JButton CreatePostButton;
    private javax.swing.JButton EditBioButton;
    private javax.swing.JButton EditProfileButton;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel ImageLabel1;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel ProfilePictureLabel;
    private javax.swing.JPanel ProfilePicturePanel;
    private javax.swing.JButton changeCoverPictureButton;
    private javax.swing.JButton changeProfilePictureButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel postPanel;
    private javax.swing.JScrollPane postScrollPane;
    // End of variables declaration//GEN-END:variables
}