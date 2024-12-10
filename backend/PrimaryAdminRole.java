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
    void deleteGroup(String GroupId)
    {
         groupManager.deleteGroup(GroupId);
    }

    void promote(String UserId,String GroupId)
    {
         groupManager.promote(UserId,GroupId);
    }
    void demote(String UserId,String GroupId)
    {
         groupManager.demote(UserId,GroupId);
    }
    //members,admins(remove, promote,demote)+admin Role
}
