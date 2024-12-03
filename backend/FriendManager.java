package backend;

public class FriendManager {
    UserDataBase userDataBase = new UserDataBase("src/database/Users.json");

    public boolean removeFriend(String removerID, String removedID) {

        if (userDataBase.search(removerID).getProfile().getFriends().contains(removedID)) {
            userDataBase.search(removerID).getProfile().removeFriend(removedID);
            userDataBase.search(removedID).getProfile().removeFriend(removerID);
            userDataBase.save();
            return true;
        }
        return false;
    }

    public boolean blockUser(String blockerID, String blockedID) {
        userDataBase.search(blockerID).getProfile().addBlockedUser(blockedID);
        userDataBase.search(blockedID).getProfile().addBlockedUser(blockerID);
        return true;
    }

    public boolean unblockUser(String unblockerID, String unblockedID) {
        userDataBase.search(unblockerID).getProfile().removeBlockedUser(unblockedID);
        userDataBase.search(unblockedID).getProfile().removeBlockedUser(unblockerID);
        return true;
    }
}
