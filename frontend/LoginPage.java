package frontend;

import backend.Login;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginPage extends javax.swing.JFrame {
    private boolean PassWordHidden = true;

    WelcomePage welcomePage;

    public LoginPage(WelcomePage welcomePage) {
        initComponents();
        setTitle("Login");
        this.welcomePage = welcomePage;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        showPasswordButton = new javax.swing.JButton();
        loginButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Email");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Password");

        showPasswordButton.setText("Show Password");
        showPasswordButton.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        showPasswordButton.setBackground(new java.awt.Color(85, 139, 47)); // Green button
        showPasswordButton.setForeground(java.awt.Color.WHITE);
        showPasswordButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(85, 139, 47), 2));
        showPasswordButton.setFocusPainted(false);
      //  showPasswordButton.setBorderRadius(20);
        showPasswordButton.addActionListener(evt -> showPasswordButtonActionPerformed(evt));

        loginButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        loginButton.setText("Login");
        loginButton.setBackground(new java.awt.Color(85, 139, 47)); // Green button
        loginButton.setForeground(java.awt.Color.WHITE);
        loginButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(85, 139, 47), 2));
        loginButton.setFocusPainted(false);
      //  loginButton.setBorderRadius(20);
        loginButton.addActionListener(evt -> {
            try {
                loginButtonActionPerformed(evt);
            } catch (NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                        .addComponent(emailTxt))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showPasswordButton)
                                .addGap(8, 8, 8))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(191, 191, 191)
                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(showPasswordButton))
                                .addGap(74, 74, 74)
                                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(143, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2});

        pack();
    }

    private void showPasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (!(PassWordHidden = !PassWordHidden)) {
            passwordField.setEchoChar('\0');
        } else {
            passwordField.setEchoChar('*');
        }
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) throws NoSuchAlgorithmException, IOException {
        String email = emailTxt.getText().trim();
        String password = new String(passwordField.getPassword());
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill email and password", "Insufficient fields", JOptionPane.WARNING_MESSAGE);
            return;
        }
        backend.Login login = Login.getInstance();
        if (login.login(email, password)) {
            String id = login.save();
            NewsFeed newsFeed = new NewsFeed(id);
            welcomePage.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect email or password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new LoginPage(new WelcomePage()).setVisible(true));
    }

    private javax.swing.JTextField emailTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton showPasswordButton;
}
