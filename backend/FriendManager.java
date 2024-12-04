package backend;

import java.io.IOException;
import java.util.ArrayList;

public class FriendManager {
    UserDataBase userDataBase = new UserDataBase("src/database/Users.json");
    private static FriendManager instance;
    private FriendManager() throws IOException {

    }
    public synchronized static FriendManager getInstance() throws IOException {
        if(instance==null)
        {
            instance=new FriendManager();
        }
        return instance;
    }

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
    //singleton design pattern
    public boolean requestFriend(String sender, String receiver) {
        FriendRequest friendRequest=new FriendRequest(sender,receiver,"pending");
        Profile send= userDataBase.search(Integer.parseInt(sender)).getProfile();
        Profile receive= userDataBase.search(Integer.parseInt(receiver)).getProfile();
        send.addFriendRequest(friendRequest);
        receive.addFriendRecievedRequest(friendRequest);
        userDataBase.save();
        return true;
    }
    public boolean respond (String sender, String receiver,boolean accepted) {
        Profile send= userDataBase.search(Integer.parseInt(sender)).getProfile();
        Profile receive= userDataBase.search(Integer.parseInt(receiver)).getProfile();
        ArrayList<FriendRequest> friendRequests=send.getFriendRequests();
        FriendRequest friendRequest = null;
        for(FriendRequest request:friendRequests)
        {
            if(request.getReceiverId().equals(receiver))
            {
                friendRequest=request;
                break;
            }
        }
        if(accepted)
        {
            friendRequest.setStatus("accepted");
            send.addFriend(receiver);
            receive.addFriend(sender);
        }
        else {
            friendRequest.setStatus("rejected");
        }
        userDataBase.save();
        return true;
    }

}
