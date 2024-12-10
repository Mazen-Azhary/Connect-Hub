package frontend;

import backend.FriendManager;
import backend.SearchManager;
import backend.User;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchPage extends javax.swing.JFrame {
    static String id;
    private SearchManager searchManager = SearchManager.getInstance();
    private ArrayList<User> searchedUsers;
    private HashMap<String, ArrayList<String>> peopleSearchResults = new HashMap<>();
    private HashMap<String, ArrayList<String>> groupSearchResults = new HashMap<>();

    /**
     * Creates new form SearchPage
     */
    public SearchPage(String id) {
        this.id = id;
        initComponents();
        setTitle("Search Page");
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchPeopleButton = new javax.swing.JButton();
        searchGroupsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchBar.addActionListener(evt -> searchBarActionPerformed(evt));

        jButton1.setText("Clear");

        searchPeopleButton.setText("Search People");
        searchPeopleButton.addActionListener(evt -> {
            try {
                searchPeopleButtonActionPerformed(evt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        searchGroupsButton.setText("Search Groups");
        searchGroupsButton.addActionListener(evt -> searchGroupsButtonActionPerformed(evt));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(161, 161, 161)
                                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton1))
                                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(166, 166, 166)
                                .addComponent(searchPeopleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(128, 128, 128)
                                .addComponent(searchGroupsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(232, 232, 232))
                        .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchPeopleButton)
                                        .addComponent(searchGroupsButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))
        );

        pack();
    }

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {
        // Placeholder for additional actions if needed when searchBar is activated
    }

    private void searchPeopleButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {

        String query = searchBar.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term in search bar", "Empty Search", JOptionPane.ERROR_MESSAGE);
            return;
        }

        searchedUsers = searchManager.search(query);

        if (searchedUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<String> searchedUsersID = new ArrayList<>();
        for (User user : searchedUsers) {
            searchedUsersID.add(user.getUserID());

        }

        UserSearchResultPanel userSearchResultPanel = new UserSearchResultPanel(id, searchedUsersID);
        updateScrollPane(userSearchResultPanel);

    }

    private void searchGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String query = searchBar.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term", "Empty Search", JOptionPane.ERROR_MESSAGE);
            return;
        }

        /*
        if (groupSearchResults.containsKey(query)) {
            ArrayList<String> groupIds = groupSearchResults.get(query);
            GroupSearchResultPanel groupSearchResultPanel = new GroupSearchResultPanel(id, groupIds);
            updateScrollPane(groupSearchResultPanel);
            return;
        }

        ArrayList<String> groupIds = SearchManager.getInstance().searchGroupQuery(id, query);
        groupSearchResults.put(query, groupIds);

        GroupSearchResultPanel groupSearchResultPanel = new GroupSearchResultPanel(id, groupIds);
        updateScrollPane(groupSearchResultPanel);
        */
    }

    private void updateScrollPane(JPanel panel) {
        jScrollPane1.setViewportView(panel);
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SearchPage(id).setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchGroupsButton;
    private javax.swing.JButton searchPeopleButton;
    // End of variables declaration
}