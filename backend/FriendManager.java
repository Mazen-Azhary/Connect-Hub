package backend;

public class FriendManager {
    UserDataBase userDataBase = new UserDataBase("src/database/Users.json");

    public boolean removeFriend(String removerID, String removedID) {

        if (userDataBase.search(Integer.parseInt(removerID)).getProfile().getFriends().contains(removedID)) {
            userDataBase.search(Integer.parseInt(removerID)).getProfile().removeFriend(removedID);
            userDataBase.search(Integer.parseInt(removedID)).getProfile().removeFriend(removerID);
            userDataBase.save();
            return true;
        }
        return false;
    }

    public boolean blockUser(String blockerID, String blockedID) {
        userDataBase.search(Integer.parseInt(blockerID)).getProfile().addBlockedUser(blockedID);
        userDataBase.search(Integer.parseInt(blockedID)).getProfile().addBlockedUser(blockerID);
        userDataBase.save();
        return true;
    }

    public boolean unblockUser(String unblockerID, String unblockedID) {
        userDataBase.search(Integer.parseInt(unblockerID)).getProfile().removeBlockedUser(unblockedID);
        userDataBase.search(Integer.parseInt(unblockedID)).getProfile().removeBlockedUser(unblockerID);
        userDataBase.save();
        return true;
    }
}
