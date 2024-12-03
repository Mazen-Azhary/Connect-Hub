package backend;

import java.io.IOException;
import java.util.ArrayList;

public class FriendManager {
    //singleton design pattern
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
