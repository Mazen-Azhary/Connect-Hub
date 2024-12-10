package backend;

import java.io.IOException;

public class MemberRole extends User{
    private static MemberRole instance;
    private GroupContentManager groupContentManager=GroupContentManager.getInstance();
    private GroupManager groupManager=GroupManager.getInstance();
    protected MemberRole()
    {

    }
    public synchronized static MemberRole getInstance() {
        if(instance ==null) return new MemberRole();
        return instance;
    }
    void addPost(String userID,String GroupId,String content,String imagePath)
    {
        try {
            groupContentManager.addPost(userID,GroupId,content,imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void leaveGroup(String groupId,String userId)
    {
        groupManager.leave(groupId,userId);
    }



}
