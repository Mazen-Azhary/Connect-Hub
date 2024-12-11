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

    public ArrayList<String> searchUserQuery(String searcherID,String query)  {
        ArrayList<String> searchedUsers = new ArrayList<>();

        for(int i=0;i<profileDataBase.getData().size();i++){
            System.out.println("index:"+i);
            if(profileDataBase.getData().get(i).getUserID().equalsIgnoreCase(searcherID)){
                //of if user is blocked my searcher , do it ya regala
                continue;

            }
            if(query.equalsIgnoreCase(profileDataBase.getData().get(i).getUsername())){
                searchedUsers.add(profileDataBase.getData().get(i).getUserID());
            }

        }


        return searchedUsers;
    }


}