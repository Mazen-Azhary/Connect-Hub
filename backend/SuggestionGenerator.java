package backend;

import java.io.IOException;
import java.util.*;

public class SuggestionGenerator {
    private FriendDatabase friendsDataBase = new FriendDatabase("src/database/Friends.json");
    private static SuggestionGenerator instance;
    private SuggestionGenerator() throws IOException {

    }
    public synchronized static SuggestionGenerator getInstance() throws IOException {
        if(instance==null)
        {
            instance=new SuggestionGenerator();
        }
        return instance;
    }
    public void generateSuggestions(String id) throws IOException {
        Set<String> exclusions = new HashSet<String>();
        ArrayList<String> suggestions =new ArrayList<>();
        exclusions.add(id);
        User user=friendsDataBase.getUser(id);
        exclusions.addAll(user.getProfile().getFriends());
        exclusions.addAll(user.getProfile().getBlockedUsers());
        exclusions.addAll(user.getProfile().getFriendReceivedRequests());
        exclusions.addAll(user.getProfile().getFriendRequests());
        for(String friend:user.getProfile().getFriends())
        {
            for(String mutual : friendsDataBase.getUser(friend).getProfile().getFriends())
            {
                if(!(exclusions.contains(mutual)||exclusions.contains("-"+mutual)))
                {
                    suggestions.add(mutual);
                }
            }
        }
        user.getProfile().setFriendSuggestions(suggestions);
        friendsDataBase.modifyUserById(user);
    }
    public ArrayList<String> shuffleSuggestions(String id) throws IOException {
        ArrayList<String> suggestions = friendsDataBase.getUser(id).getProfile().getFriendSuggestions();
        Collections.shuffle(suggestions);
        if(suggestions.size()==0)
        {
            Set<String> exclusions = new HashSet<String>();
            exclusions.add(id);
            User user=friendsDataBase.getUser(id);
            exclusions.addAll(user.getProfile().getFriends());
            exclusions.addAll(user.getProfile().getBlockedUsers());
            exclusions.addAll(user.getProfile().getFriendReceivedRequests());
            exclusions.addAll(user.getProfile().getFriendRequests());
            LoginDatabase loginDatabase = new LoginDatabase("src/database/Login.json");
            ArrayList<Map> users=loginDatabase.getMap();
            for(Map<String,Object> map:users)
            {
                String mutual=(String) map.get("userId");
                System.out.println(mutual);
                if(!(exclusions.contains(mutual)||exclusions.contains("-"+mutual)))
                {
                    suggestions.add(mutual);
                    System.out.println(mutual);
                }
            }

        }
        return suggestions;
    }
}
