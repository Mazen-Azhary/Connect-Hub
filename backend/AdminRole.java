package backend;

public class AdminRole extends MemberRole{
    private static AdminRole instance;
    private GroupContentManager groupContentManager=GroupContentManager.getInstance();
    private GroupManager groupManager=GroupManager.getInstance();
    protected AdminRole()
    {
        super();

    }
    public synchronized static AdminRole getInstance() {
        if(instance ==null) return new AdminRole();
        return instance;
    }

    public void removeMember(String userId,String groupId){
        groupManager.removeMember(userId,groupId);
    }
    public void editPost(String groupId,Content content,String desc,String imagePath)
    {
        groupContentManager.editPost(groupId,content,desc,imagePath);
    }
    public void deletePost(String groupId,String contentId){
         groupContentManager.deletePost(groupId,contentId);
    }
    public void respondRequest(String userId,String groupId,boolean accept){
         groupManager.respondRequest(userId,groupId,accept);
    }
    //members (delete), pending(approve),posts(edit,delete),add post
}
