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
    public ArrayList<Group> getGroups(String userId) {
        User user=null;
        try {
             user=userGroupsDatabase.getUser(userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String,GroupRole> grps=user.getProfile().getGroups();
        ArrayList<Group> groups=new ArrayList<>();
        for(Map.Entry<String,GroupRole> entry:grps.entrySet()){
            if(entry.getValue()==GroupRole.PENDING){
                continue;
            }
            try {
                groups.add(groupsDatabase.getGroup(entry.getKey()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return groups;
    }
    public GroupRole getRole(String userId, String groupId)
    {
        Group group= null;
        try {
            group = groupsDatabase.getGroup(groupId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return group.getMembers().get(userId);
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
    public void deleteGroup(String groupName) {
        Group group=null;
        try {
            group=groupsDatabase.getGroup(groupName);
            group.deleteGroup();
            groupsDatabase.modifyGroupById(group);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getAdmins(String groupId)
    {
            ArrayList<String> admins=new ArrayList<>();
        try {
            Map <String,GroupRole> members=groupsDatabase.getGroup(groupId).getMembers();
            for(Map.Entry<String,GroupRole> entry:members.entrySet())
            {
                if(entry.getValue().equals(GroupRole.ADMIN))
                {
                    admins.add(entry.getKey());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }
    public ArrayList<String> getMembers(String groupId)
    {
            ArrayList<String> admins=new ArrayList<>();
        try {
            Map <String,GroupRole> members=groupsDatabase.getGroup(groupId).getMembers();
            for(Map.Entry<String,GroupRole> entry:members.entrySet())
            {
                if(entry.getValue().equals(GroupRole.MEMBER))
                {
                    admins.add(entry.getKey());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }
    public ArrayList<String> getRequests(String groupId)
    {
            ArrayList<String> admins=new ArrayList<>();
        try {
            Map <String,GroupRole> members=groupsDatabase.getGroup(groupId).getMembers();
            for(Map.Entry<String,GroupRole> entry:members.entrySet())
            {
                if(entry.getValue().equals(GroupRole.PENDING))
                {
                    admins.add(entry.getKey());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }
}
