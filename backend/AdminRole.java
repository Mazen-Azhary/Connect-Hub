package backend;

public class AdminRole extends MemberRole{
    private static AdminRole instance;
    private GroupContentManager groupContentManager=GroupContentManager.getInstance();
    //private GroupManager groupManager=GroupManager.getInstance();
    protected AdminRole()
    {
        super();

    }
    public synchronized static AdminRole getInstance() {
        if(instance ==null) return new AdminRole();
        return instance;
    }
    void promote(String UserId,String GroupId)
    {
        // groupManager.promote(UserId,GroupId);
    }
    void demote(String UserId,String GroupId)
    {
        // groupManager.demote(UserId,GroupId);
    }
    void removeMember(String UserId,String GroupId){
        // groupManager.removeMember(UserId,GroupId);
    }
    void editPost(String UserId,String GroupId,String ContentId)
    {
        // groupManager.editPost(UserId,GroupId,contentId);
    }
    void deletePost(String UserId,String GroupId,String ContentId){
        // groupManager.deletePost(UserId,GroupId,ContentId);
    }
    void respondRequest(String UserId,String GroupId,boolean accept){
        // groupManager.approveRequest(UserId,GroupId,accept);
    }
}
