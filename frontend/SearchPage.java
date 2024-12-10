package frontend;

import backend.SearchManager;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class SearchPage extends javax.swing.JFrame {
    static String id;
    private String searchPeopleSubstring = ""; // Current search term for people
    private ArrayList<String> oldExecutedQueryToAvoidResearchingForSamePeopleQuery = new ArrayList<>();

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

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchPeopleButton = new javax.swing.JButton();
        searchGroupsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });

        jButton1.setText("Clear");
        jButton1.addActionListener(evt -> clearSearchResults());

        searchPeopleButton.setText("Search People");
        searchPeopleButton.addActionListener(evt -> {
            try {
                searchPeopleButtonActionPerformed(evt);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(166, 166, 166)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(searchPeopleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(searchGroupsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(166, 166, 166))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(searchPeopleButton)
                                        .addComponent(searchGroupsButton))
                                .addGap(4, 4, 4)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {
        String input = searchBar.getText().trim();
        if (!input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter to confirm search!", "Hint", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearSearchResults() {
        if (jScrollPane1.getViewport().getView() != null) {
            jScrollPane1.setViewportView(null);
            searchBar.setText("");
        } else {
        }
    }

    private void searchPeopleButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        String query = searchBar.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term", "Empty Search", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (searchPeopleSubstring.equalsIgnoreCase(query)) {

            return;
        }

        searchPeopleSubstring = query;

        ArrayList<String> userIds = SearchManager.getInstance().searchUserQuery(id, searchPeopleSubstring);
        if (userIds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found for the search query.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            oldExecutedQueryToAvoidResearchingForSamePeopleQuery = userIds;
            UserSearchResultPanel userSearchResultPanel = new UserSearchResultPanel(id, userIds);
            jScrollPane1.setViewportView(userSearchResultPanel);
        }
    }

    private void searchGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new SearchPage(id).setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchGroupsButton;
    private javax.swing.JButton searchPeopleButton;
}
