/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;
import frontend.*;
import backend.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Etijah
 */
public class GroupPage extends javax.swing.JFrame {
    private String userId;
    private String groupId;

    /**
     * Creates new form GroupPage
     */
    private void loadPosts() {
        // Create a container panel to hold the posts
        ArrayList<Content> posts= null;
            posts = GroupContentManager.getInstance().contents(userId,groupId);
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        for (int i=posts.size()-1; i>=0; i--) {
            Content post=posts.get(i);
            // Retrieve user details
            User user = ProfileManager.getInstance().getUser("" + post.getAuthorId());
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
            if(GroupManager.getInstance().getRole(userId,groupId).equals(GroupRole.PRIMARYADMIN))
            {
                PrimaryAdminRole primaryAdminRole=PrimaryAdminRole.getInstance();
                {
                    JButton editButton = new JButton("Edit");
                    editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new EditPostPage(user.getUserID(),post);
                        }
                    });
                    headerPanel.add(editButton);
                    JButton deleteButton = new JButton("Delete");
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            primaryAdminRole.deletePost(groupId,post);
                        }
                    });
                    headerPanel.add(deleteButton);
                }
            }
            headerPanel.add(friendPhoto);
            headerPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between photo and name
            headerPanel.add(friendName);

            // Create a vertical panel for the entire post
            // Create a vertical panel for the entire post
            JPanel singlePostPanel = new JPanel();
            singlePostPanel.setLayout(new BorderLayout()); // Use BorderLayout for better control
            singlePostPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            singlePostPanel.setBackground(Color.WHITE);

// Create a panel for post content
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(Color.WHITE);
            contentPanel.add(headerPanel);
            contentPanel.add(contentLabel);
            if (imageLabel != null) {
                contentPanel.add(imageLabel);
            }
            singlePostPanel.add(contentPanel, BorderLayout.CENTER); // Add content to the center

// Check user role and add buttons if applicable
            if (GroupManager.getInstance().getRole(userId, groupId).equals(GroupRole.PRIMARYADMIN) ||
                    GroupManager.getInstance().getRole(userId, groupId).equals(GroupRole.ADMIN)) {

                // Create a panel for the buttons
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Stack buttons vertically
                buttonPanel.setBackground(Color.WHITE);

                // Edit button
                JButton editButton = new JButton("Edit Post");
                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new CreatePostGroup(userId, groupId, false, post);
                    }
                });
                buttonPanel.add(editButton);

                // Add spacing between buttons
                buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                // Delete button
                JButton deleteButton = new JButton("Delete Post");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GroupContentManager.getInstance().deletePost(groupId, post.getContentId()+"");
                    }
                });
                buttonPanel.add(deleteButton);

                // Add button panel to the right of the post
                singlePostPanel.add(buttonPanel, BorderLayout.EAST);
            }

// Add space between posts
            singlePostPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);

// Add the single post to the main postPanel
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
            postPanel.add(singlePostPanel);
            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts in the main panelreateRigidArea(new Dimension(0, 10))); // Space between posts in the main panel
        }
        PostScrollPane.setViewportView(postPanel);
        PostScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        PostScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        revalidate();
        repaint();

        // Add the post panel to the container
    }
    private GroupContentManager groupContentManager=GroupContentManager.getInstance();
    public GroupPage(String userId,String groupId) {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent default behavior
        setVisible(true);
        this.userId = userId;
        this.groupId=groupId;
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("GroupPage");
        loadPosts();
        GroupMembersPannel groupMembersPannel=null;
        try {
            groupMembersPannel=new GroupMembersPannel(this,userId,groupId,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MembersScrollPane1.setViewportView(groupMembersPannel);
        try {
            groupMembersPannel=new GroupMembersPannel(this,userId,groupId,true);
            MembersScrollPane.setViewportView(groupMembersPannel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        BackButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        descLabel=new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        MembersScrollPane = new javax.swing.JScrollPane();
        PostScrollPane = new javax.swing.JScrollPane();
        jLabel5 = new javax.swing.JLabel();
        MembersScrollPane1 = new javax.swing.JScrollPane();
        leaveButton = new javax.swing.JButton();
        createPostButton= new javax.swing.JButton();
        requestsButton=new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
                try {
                    new NewsFeed(userId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ImageIcon imageIcon;
        if(GroupManager.getInstance().getGroup(groupId).getPhoto()==null)
        {
            imageIcon=new ImageIcon("src/database/CoverDefault.jpg");
        }
        else imageIcon= new ImageIcon(GroupManager.getInstance().getGroup(groupId).getPhoto());

        jLabel1.setIcon(imageIcon); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Group group=GroupManager.getInstance().getGroup(groupId);
        jLabel2.setText(group.getName());
        descLabel.setFont(new java.awt.Font("Segoe UI", 2, 20));
        descLabel.setText(group.getDescription());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Posts");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Members");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Admins");
        leaveButton.setText("Leave Group");
        leaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(GroupManager.getInstance().getRole(userId,groupId).equals(GroupRole.PRIMARYADMIN))
                {
                    PrimaryAdminRole.getInstance().leaveGroup(groupId,userId);
                }
                else {
                    MemberRole.getInstance().leaveGroup(groupId,userId);
                }
                dispose();
                try {
                    new NewsFeed(userId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        createPostButton.setText("Add post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new CreatePostGroup(userId,groupId,true,null);
            }
        });
        requestsButton.setText("View Requests");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //handle the request code here
            }
        });
        if(GroupManager.getInstance().getRole(userId,groupId).equals(GroupRole.ADMIN)||GroupManager.getInstance().getRole(userId,groupId).equals(GroupRole.PRIMARYADMIN))
        {

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.add(requestsButton);
            add(buttonPanel, BorderLayout.PAGE_START);
            revalidate();
            repaint();
        }


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(descLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(BackButton))
                                                .addContainerGap(316, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(PostScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(MembersScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(createPostButton)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(MembersScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(50, 50, 50)
                                                        .addComponent(leaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(46, 46, 46))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup((layout.createSequentialGroup()
                                .addGap(16, 30, 35)
                                .addComponent(BackButton)
                                .addGap(18, 18, 18)
                                .addComponent(leaveButton))
                        .addGap(18, 18, 18)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(createPostButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(descLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(PostScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(48, 48, 48))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(83, 83, 83)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(MembersScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addComponent(MembersScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap())))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(355, 355, 355))
        );

        pack();
    }

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
            java.util.logging.Logger.getLogger(GroupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GroupPage("3","2").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton BackButton;
    private javax.swing.JScrollPane MembersScrollPane;
    private javax.swing.JScrollPane MembersScrollPane1;
    private javax.swing.JScrollPane PostScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel descLabel;
    private javax.swing.JButton leaveButton;
    private javax.swing.JButton createPostButton;
    private javax.swing.JButton requestsButton;

    // End of variables declaration
}