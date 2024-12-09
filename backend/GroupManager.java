package backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupManager {
    private GroupsDatabase groupsDatabase=new GroupsDatabase("src/database/Groups.json");
    private UserGroupsDatabase userGroupsDatabase=new UserGroupsDatabase("src/database/UserGroups.json");
    private static GroupManager instance;
    private GroupManager() {

    }
    public static GroupManager getInstance() {
        if (instance == null) {
            instance = new GroupManager();
        }
        return instance;
    }
    public Map<String,GroupRole> getGroups(String userId) {
        User user=null;
        try {
             user=userGroupsDatabase.getUser(userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user.getProfile().getGroups();
    }
    public void createGroup(String userId,String groupName,String description) {
        Group group=null;
        try {
            int groupId =groupsDatabase.getMax()+1;
             group=new Group(groupId+"",groupName,description,userId);
             groupsDatabase.addGroup(group);
             User user=userGroupsDatabase.getUser(userId);
             user.getProfile().addGroup(groupId+"",GroupRole.PRIMARYADMIN);
             userGroupsDatabase.modifyUserGroupsById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getAdmins(String groupId)
    {

    }


}
