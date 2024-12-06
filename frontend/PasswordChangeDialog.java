package frontend;
import backend.LoginDatabase;
import backend.PasswordHasher;
import backend.User;
import backend.Validations;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PasswordChangeDialog {
    private LoginDatabase loginDatabase=new LoginDatabase("src/database/Login.json");
    public void showPasswordChangeDialog(String id) throws NoSuchAlgorithmException {
        // Create a panel to hold the fields
        JPanel panel = new JPanel();

        // Create the labels for the fields
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        JLabel repeatPasswordLabel = new JLabel("Repeat New Password:");


        // Create the password fields
        JPasswordField oldPasswordField = new JPasswordField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField repeatPasswordField = new JPasswordField(20);

        // Add the labels and password fields to the panel
        panel.add(oldPasswordLabel);
        panel.add(oldPasswordField);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(repeatPasswordLabel);
        panel.add(repeatPasswordField);

        // Show the dialog with the panel
        int option = JOptionPane.showConfirmDialog(null, panel,
                "Password Change", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // Get the passwords from the fields
            char[] oldPassword = oldPasswordField.getPassword();
            char[] newPassword = newPasswordField.getPassword();
            char[] repeatPassword = repeatPasswordField.getPassword();
            String oldPasswordString = new String(oldPassword);
            String newPasswordString = new String(newPassword);

            if (oldPassword.length == 0 || newPassword.length == 0 || repeatPassword.length == 0) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (!new String(newPassword).equals(new String(repeatPassword))) {
                JOptionPane.showMessageDialog(null, "The new passwords do not match!", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Map<String,Object> u=new HashMap<>();
                u.put("userId",id);
                Map<String,Object> user=loginDatabase.search(u);
                byte[] salt = Base64.getDecoder().decode(user.get("salt").toString());

                if(PasswordHasher.hashedPassword(oldPasswordString,salt).equals(user.get("hashedPassword").toString()))
                {
                    if(!Validations.validatePassword(newPasswordString))
                    {
                        JOptionPane.showMessageDialog(null,"the password should contain 8 characters at least, one alphabet , one symbol" ,"password error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    byte[] salt2=PasswordHasher.salt();
                    String hashedPassword=PasswordHasher.hashedPassword(newPasswordString,salt2);
                    user.put("hashedPassword",hashedPassword);
                    user.put("salt",salt2);
                    loginDatabase.save();
                JOptionPane.showMessageDialog(null, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Old Password does not match!", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
