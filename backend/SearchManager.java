package backend;

import java.io.IOException;
import java.util.ArrayList;

public class SearchManager {
    private static SearchManager instance;
    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    private LoginDatabase loginDataBase = new LoginDatabase("src/database/Login.json");

    private SearchManager() {
    }

    public static synchronized SearchManager getInstance() {
        if (instance == null) instance = new SearchManager();
        return instance;
    }

    public ArrayList<User> search(String userID) throws IOException {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> searchedUsers = new ArrayList<>();
        int size = loginDataBase.getMap().size() + 1;
        for (int i = 1; i < size; i++) {
            users.add(profileDataBase.getUser(Integer.toString(i)));
        }
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(userID)) {
                searchedUsers.add(user);
            }
        }
        return searchedUsers;
    }

}