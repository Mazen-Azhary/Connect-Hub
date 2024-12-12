package backend;

public class PrimaryAdminRole extends AdminRole {
    private static PrimaryAdminRole instance;
    private GroupContentManager groupContentManager=GroupContentManager.getInstance();
    private GroupManager groupManager=GroupManager.getInstance();
    private PrimaryAdminRole()
    {
        super();
    }
    public synchronized static PrimaryAdminRole getInstance() {
        if(instance ==null) return new PrimaryAdminRole();
        return instance;
    }
    public void deleteGroup(String GroupId)
    {
         groupManager.deleteGroup(GroupId);
    }

    public void promote(String groupId,String userId)
    {
         groupManager.promote(groupId,userId);
    }
    public void demote(String groupId,String userId)
    {
         groupManager.demote(groupId,userId);
    }
    //members,admins(remove, promote,demote)+admin Role
}
