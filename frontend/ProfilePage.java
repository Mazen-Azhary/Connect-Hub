/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import backend.Content;
import backend.ContentFactory;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Etijah
 */
public class ProfilePage extends JFrame {
    private ArrayList<Content> contents = new ArrayList<>(); //posts
    
    /**
     * Creates new form profilePage
     */
    public ProfilePage() throws IOException {
        initComponents();
        setVisible(true);
        setResizable(false);
        
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                Content n = ContentFactory.createContent("post", i, LocalDateTime.MAX);
                n.setContent("Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,\n" +
"molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum\n" +
"numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium\n" +
"optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis\n" +
"obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam\n" +
"nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,\n" +
"tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit,\n" +
"quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos \n" +
"sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam\n" +
"recusandae alias error harum maxime adipisci amet laborum. Perspiciatis \n" +
"minima nesciunt dolorem! Officiis iure rerum voluptates a cumque velit \n" +
"quibusdam sed amet tempora. Sit laborum ab, eius fugit doloribus tenetur \n" +
"fugiat, temporibus enim commodi iusto libero magni deleniti quod quam \n" +
"consequuntur! Commodi minima excepturi repudiandae velit hic maxime\n" +
"doloremque. Quaerat provident commodi consectetur veniam similique ad \n" +
"earum omnis ipsum saepe, voluptas, hic voluptates pariatur est explicabo \n" +
"fugiat, dolorum eligendi quam cupiditate excepturi mollitia maiores labore \n" +
"suscipit quas? Nulla, placeat. Voluptatem quaerat non architecto ab laudantium\n" +
"modi minima sunt esse temporibus sint culpa, recusandae aliquam numquam \n" +
"totam ratione voluptas quod exercitationem fuga. Possimus quis earum veniam \n" +
"quasi aliquam eligendi, placeat qui corporis!" + i);
                n.setAuthorId(i);
                contents.add(n);
            } else {
                Content n = ContentFactory.createContent("post", i, LocalDateTime.MAX);
                n.setContent("Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,\n" +
"molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum\n" +
"numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium\n" +
"optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis\n" +
"obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam\n" +
"nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,\n" +
"tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit,\n" +
"quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias eos \n" +
"sapiente officiis modi at sunt excepturi expedita sint? Sed quibusdam\n" +
"recusandae alias error harum maxime adipisci amet laborum. Perspiciatis \n" +
"minima nesciunt dolorem! Officiis iure rerum voluptates a cumque velit \n" +
"quibusdam sed amet tempora. Sit laborum ab, eius fugit doloribus tenetur \n" +
"fugiat, temporibus enim commodi iusto libero magni deleniti quod quam \n" +
"consequuntur! Commodi minima excepturi repudiandae velit hic maxime\n" +
"doloremque. Quaerat provident commodi consectetur veniam similique ad \n" +
"earum omnis ipsum saepe, voluptas, hic voluptates pariatur est explicabo \n" +
"fugiat, dolorum eligendi quam cupiditate excepturi mollitia maiores labore \n" +
"suscipit quas? Nulla, placeat. Voluptatem quaerat non architecto ab laudantium\n" +
"modi minima sunt esse temporibus sint culpa, recusandae aliquam numquam \n" +
"totam ratione voluptas quod exercitationem fuga. Possimus quis earum veniam \n" +
"quasi aliquam eligendi, placeat qui corporis!" + i);
                n.setAuthorId(i);
                n.setImage("src/database/Signup.png");      
                contents.add(n);
            }
        }

        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

        // Add posts to postPanel
        for (Content post : contents) {
            // Create components for each post
            JLabel authorLabel = new JLabel("Omar Adel Abdallah");
            authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            authorLabel.setFont(new Font("Arial", Font.BOLD, 12));
            JTextArea contentLabel = new JTextArea(post.getContent());
            contentLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            contentLabel.setWrapStyleWord(true);
            contentLabel.setLineWrap(true);
            contentLabel.setEditable(false);
            contentLabel.setOpaque(false);
            JLabel imageLabel = null;
            if(post.getImage()!=null&&!post.getImage().isEmpty())
            {
                imageLabel = new JLabel(new ImageIcon(post.getImage()));
            }
            postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
            postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add labels to the post panel
            postPanel.add(authorLabel, BorderLayout.NORTH);
            postPanel.add(contentLabel, BorderLayout.CENTER);
            if(imageLabel!=null)
                postPanel.add(imageLabel, BorderLayout.SOUTH);

        }

        postScrollPane.getVerticalScrollBar().setUnitIncrement(20); // 20 pixels per scroll step
        postScrollPane.getHorizontalScrollBar().setUnitIncrement(20); // (Optional) for horizontal scrolling
        postScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        postScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        setLocationRelativeTo(null);
        //setSize(new Dimension(1000, 1000));
        revalidate();
        repaint();
        jLabel1.setText("Omar");
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regلهفenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HeaderPanel = new JPanel();
        CoverPhotoPanl = new JPanel();
        CoverPictureLabel = new JLabel();
        ProfilePicturePanel = new JPanel();
        ProfilePictureLabel = new JLabel();
        BioPanel = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        EditProfileButton = new JButton();
        AddStoryButton = new JButton();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jScrollPane2 = new JScrollPane();
        jPanel2 = new JPanel();
        postScrollPane = new JScrollPane();
        postPanel = new JPanel();
        ContentLabel1 = new JLabel();
        AuthorUsername1 = new JLabel();
        ImageLabel1 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout CoverPhotoPanlLayout = new GroupLayout(CoverPhotoPanl);
        CoverPhotoPanl.setLayout(CoverPhotoPanlLayout);
        CoverPhotoPanlLayout.setHorizontalGroup(
            CoverPhotoPanlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(CoverPictureLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CoverPhotoPanlLayout.setVerticalGroup(
            CoverPhotoPanlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(CoverPhotoPanlLayout.createSequentialGroup()
                .addComponent(CoverPictureLabel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        GroupLayout ProfilePicturePanelLayout = new GroupLayout(ProfilePicturePanel);
        ProfilePicturePanel.setLayout(ProfilePicturePanelLayout);
        ProfilePicturePanelLayout.setHorizontalGroup(
            ProfilePicturePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(ProfilePictureLabel, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        ProfilePicturePanelLayout.setVerticalGroup(
            ProfilePicturePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(ProfilePictureLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setText("Username");

        jLabel2.setText("Number of friends");

        EditProfileButton.setText("Edit Profile");
        EditProfileButton.setFocusable(false);

        AddStoryButton.setText("Add Story");
        AddStoryButton.setFocusable(false);

        jButton1.setText("Change Profile Picture");
        jButton1.setFocusable(false);

        jButton2.setText("Change Cover Picture");
        jButton2.setFocusable(false);

        GroupLayout BioPanelLayout = new GroupLayout(BioPanel);
        BioPanel.setLayout(BioPanelLayout);
        BioPanelLayout.setHorizontalGroup(
            BioPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BioPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BioPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(BioPanelLayout.createSequentialGroup()
                        .addGroup(BioPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(AddStoryButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EditProfileButton))
                    .addGroup(BioPanelLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BioPanelLayout.setVerticalGroup(
            BioPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(BioPanelLayout.createSequentialGroup()
                .addGroup(BioPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(BioPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(BioPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(BioPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(AddStoryButton)
                            .addComponent(EditProfileButton))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BioPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        GroupLayout HeaderPanelLayout = new GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(CoverPhotoPanl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ProfilePicturePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BioPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addComponent(CoverPhotoPanl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HeaderPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(ProfilePicturePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BioPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel2);

        GroupLayout postPanelLayout = new GroupLayout(postPanel);
        postPanel.setLayout(postPanelLayout);
        postPanelLayout.setHorizontalGroup(
            postPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(ContentLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(AuthorUsername1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ImageLabel1, GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );
        postPanelLayout.setVerticalGroup(
            postPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(postPanelLayout.createSequentialGroup()
                .addComponent(AuthorUsername1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContentLabel1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ImageLabel1, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        postScrollPane.setViewportView(postPanel);

        for (Content content : contents) {
            Post post = new Post(content.getContentId(), content.getContent(), "Author" + content.getContent(),content.getImage());
            postScrollPane.add(post);
            postScrollPane.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
        }

        jLabel3.setFont(new Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("Posts");

        jLabel4.setFont(new Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("Friends");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(HeaderPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(postScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
                    .addComponent(postScrollPane, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {jScrollPane2, postScrollPane});

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProfilePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ProfilePage().setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton AddStoryButton;
    private JLabel AuthorUsername1;
    private JPanel BioPanel;
    private JLabel ContentLabel1;
    private JPanel CoverPhotoPanl;
    private JLabel CoverPictureLabel;
    private JButton EditProfileButton;
    private JPanel HeaderPanel;
    private JLabel ImageLabel1;
    private JLabel ProfilePictureLabel;
    private JPanel ProfilePicturePanel;
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel2;
    private JScrollPane jScrollPane2;
    private JPanel postPanel;
    private JScrollPane postScrollPane;
    // End of variables declaration//GEN-END:variables
}
