package backend;

import java.io.IOException;
import java.util.ArrayList;

public class FriendManager {
    private FriendDatabase friendsDataBase = new FriendDatabase("src/database/Friends.json");
    private static FriendManager instance;
    SuggestionGenerator suggestionGenerator = SuggestionGenerator.getInstance();

    private FriendManager() throws IOException {

    }

    public synchronized static FriendManager getInstance() throws IOException {
        if (instance == null) {
            instance = new FriendManager();
        }
        return instance;
    }

    public boolean removeFriend(String removerID, String removedID) throws IOException {
        User user1 = friendsDataBase.getUser(removerID);
        User user2 = friendsDataBase.getUser(removedID);
        if (!user1.getProfile().getFriends().contains(removedID)) {
            return false;
        }
        user1.getProfile().removeFriend(removedID);
        user2.getProfile().removeFriend(removerID);
        friendsDataBase.modifyUserById(user1);
        friendsDataBase.modifyUserById(user2);
        return true;
    }

    public boolean isFriend(String userID, String friendID) throws IOException {
        User user1 = friendsDataBase.getUser(userID);
        if (user1.getProfile().getFriends().contains(friendID)) {
            return true;
        }
        return false;
    }

    public boolean isBlocked(String userID, String friendID) throws IOException {
        User user1 = friendsDataBase.getUser(userID);
        if (user1.getProfile().getBlockedUsers().contains(friendID)) {
            return true;
        }
        return false;
    }

    public boolean blockUser(String blockerID, String blockedID) throws IOException {
        //if the id positive then you are the blocker, if negative you are the blocked
        User user1 = friendsDataBase.getUser(blockerID);
        User user2 = friendsDataBase.getUser(blockedID);
        if (user1.getProfile().getFriends().contains(blockedID)) {
            user1.getProfile().removeFriend(blockedID);
            user2.getProfile().removeFriend(blockerID);
        }
        user1.getProfile().getBlockedUsers().add(user2.getUserID());
        user2.getProfile().getBlockedUsers().add("-" + user1.getUserID());
        friendsDataBase.modifyUserById(user1);
        friendsDataBase.modifyUserById(user2);
        return true;
    }

    public boolean unblockUser(String unblockerID, String unblockedID) throws IOException {
        User user1 = friendsDataBase.getUser(unblockerID);
        User user2 = friendsDataBase.getUser(unblockedID);
        user1.getProfile().getBlockedUsers().remove(user2.getUserID());
        user2.getProfile().getBlockedUsers().remove("-" + user1.getUserID());
        friendsDataBase.modifyUserById(user1);
        friendsDataBase.modifyUserById(user2);
        return true;
    }

    public boolean requestFriend(String sender, String receiver) throws IOException {
        User user1 = friendsDataBase.getUser(sender);
        User user2 = friendsDataBase.getUser(receiver);
        if (user1.getProfile().getFriends().contains(receiver) || user1.getProfile().getFriendReceivedRequests().contains(receiver)) {
            return false;
        }
        user1.getProfile().getFriendRequests().add(user2.getUserID());
        user2.getProfile().getFriendReceivedRequests().add(user1.getUserID());
        friendsDataBase.modifyUserById(user1);
        friendsDataBase.modifyUserById(user2);
        return true;
    }

    public boolean respond(String sender, String receiver, boolean accepted) throws IOException {
        User user1 = friendsDataBase.getUser(sender);
        User user2 = friendsDataBase.getUser(receiver);
        if (!user1.getProfile().getFriendReceivedRequests().contains(receiver)) {
            return false;
        }
        user1.getProfile().getFriendReceivedRequests().remove(receiver);
        user2.getProfile().getFriendRequests().remove(sender);
        if (accepted) {
            user1.getProfile().addFriend(receiver);
            user2.getProfile().addFriend(sender);
        }
        friendsDataBase.modifyUserById(user1);
        friendsDataBase.modifyUserById(user2);
        return true;
    }

    public boolean removeRequest(String sender, String receiver) throws IOException {
        User user1 = friendsDataBase.getUser(sender);
        User user2 = friendsDataBase.getUser(receiver);
        if (!user1.getProfile().getFriendRequests().contains(receiver)) {
            return false;
        }
        user1.getProfile().getFriendRequests().remove(receiver);
        user2.getProfile().getFriendReceivedRequests().remove(sender);
        friendsDataBase.modifyUserById(user1);
        friendsDataBase.modifyUserById(user2);
        return true;
    }
}
