package backend;

import java.io.IOException;
import java.util.ArrayList;

public class FriendsViewer {
    private FriendDatabase friendDatabase=new FriendDatabase("src/database/Friends.json");
    private UserContentDatabase userDatabase=new UserContentDatabase("src/database/UserContents.json");
    private FriendsViewer()
    {
    }
    private static FriendsViewer instance;
    public static FriendsViewer getInstance()
    {
        if (instance==null)
            instance=new FriendsViewer();
        return instance;
    }
    public User getUser(String id) throws IOException {
        return friendDatabase.getUser(id);
    }
    public ArrayList<String> getFriends(String id)
    {
        ArrayList<String> friends;
        try {
             friends=getUser(id).getProfile().getFriends();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return friends;
    }
    public ArrayList <String> getBlocked(String id)
    {
        ArrayList <String> blocked;
        try {
        blocked=getUser(id).getProfile().getBlockedUsers();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
        return blocked;

    }
    public ArrayList <String> getRequests(String id)
    {
        ArrayList <String> requests;
        try {
        requests=getUser(id).getProfile().getFriendRequests();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
        return requests;

    }public ArrayList <String> getReceived(String id)
    {
        ArrayList <String> received;
        try {
        received=getUser(id).getProfile().getFriendReceivedRequests();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
        return received;

    }
    public String getStatus(String id)
    {
        try {
            return userDatabase.getUser(id).getStatus();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setStatus(String id, String status)
    {
        User user = null;
        try {
            user = userDatabase.getUser(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setStatus(status);
        try {
            userDatabase.modifyUserById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
