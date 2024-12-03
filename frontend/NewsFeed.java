/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import backend.Content;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import backend.*;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author Mazen
 */
public class NewsFeed extends javax.swing.JFrame {

    private ArrayList<Content> contents = new ArrayList<>(); //posts

    /**
     * Creates new form NewsFeed
     */
    public NewsFeed() {
        initComponents();
        setTitle("News Feed");

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                ;

                contents.add(ContentFactory.createContent("post", i, LocalDateTime.MAX));

            } else {
                Content n = ContentFactory.createContent("post", i, LocalDateTime.MAX);
                n.setContent("content" + i);
                n.setAuthorId(i);
                n.setImage("src/database/Signup.png");
                contents.add(n);
            }
        }
        setLocationRelativeTo(null);
        setSize(new Dimension(1000, 1000));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">                          
    private void initComponents() {
        centralPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();
        rightPannel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        createPostButton = new JButton("Create Post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPostActionPerformed(evt);
            }
        });
    
        topPanel.add(createPostButton);
    

        getContentPane().setLayout(new java.awt.BorderLayout());
    
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));  
        centralPanel.setBackground(new java.awt.Color(153, 153, 153));
        centralPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));  
    
    //    centralPanel.add(new JLabel("Loading posts..."));
        
        for (backend.Content content : contents) {
            Post post = new Post(content.getContentId(), content.getContent(), "Author" + content.getContent(),content.getImage());
            centralPanel.add(post);
            centralPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between posts
        }
    
        centralPanel.setPreferredSize(new Dimension(centralPanel.getPreferredSize().width, 1000));
    
        JScrollPane scrollable = new JScrollPane(centralPanel);
        scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
        getContentPane().add(scrollable, java.awt.BorderLayout.CENTER);
    
        topPanel.setBackground(new java.awt.Color(153, 153, 153));
        topPanel.setPreferredSize(new Dimension(300, 100));
        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);
        

        bottomPanel.setBackground(new java.awt.Color(153, 153, 153));
        getContentPane().add(bottomPanel, java.awt.BorderLayout.PAGE_END);
    
        rightPannel.setBackground(new java.awt.Color(153, 153, 153));
        getContentPane().add(rightPannel, java.awt.BorderLayout.LINE_END);
    
        leftPanel.setBackground(new java.awt.Color(153, 153, 153));
        getContentPane().add(leftPanel, java.awt.BorderLayout.LINE_START);
    
        pack(); 
    }
    
    private void createPostActionPerformed(ActionEvent evt){
        /* 
        CreatePostWindow createPostWindow = new CreatePostWindow();
        createPostWindow.setVisible(true);
        */

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewsFeed().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel centralPanel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel rightPannel;
    private javax.swing.JPanel topPanel;
    private JButton createPostButton;
    // End of variables declaration//GEN-END:variables
}
