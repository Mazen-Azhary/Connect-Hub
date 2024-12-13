package backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    public void request(String userId,String groupId)
    {
        try
        {
            User user=userGroupsDatabase.getUser(userId);
            Group group=groupsDatabase.getGroup(groupId);
            user.getProfile().requestJoin(groupId);
            group.getMembers().put(userId,GroupRole.PENDING);
            userGroupsDatabase.modifyUserGroupsById(user);
            groupsDatabase.modifyGroupById(group);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
              //  System.out.println(entry.getKey());
                Group group=groupsDatabase.getGroup(entry.getKey());
                if(!group.isDeleted())
                {
                groups.add(group);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return groups;
    }

    public boolean isMember(String userId,String groupId){
       if(getRole(userId,groupId)!=null&&getRole(userId,groupId)!=GroupRole.PENDING){
           return true;
       }
       return false;
    }
    public boolean isPending(String userId,String groupId){
        if(getRole(userId,groupId)!=null&&getRole(userId,groupId)==GroupRole.PENDING){
            return true;
        }
        return false;
    }

    public Group getGroup(String groupId) {
        Group group=null;
        try {
            group=groupsDatabase.getGroup(groupId);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return group;
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
    public void createGroup(String userId,String groupName,String description,String imagepath) {
        Group group=null;
        try {
            int groupId =groupsDatabase.getMax()+1;
             group=new Group(groupId+"",groupName,description,imagepath,userId);
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
    public ArrayList<String> getAllMembers(String groupId)
    {
            ArrayList<String> memb=new ArrayList<>();
        try {
            Map <String,GroupRole> members=groupsDatabase.getGroup(groupId).getMembers();
            for(Map.Entry<String,GroupRole> entry:members.entrySet())
            {
                if(!entry.getValue().equals(GroupRole.PENDING))
                {
                    memb.add(entry.getKey());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return memb;
    }
    public ArrayList<String> getRequests(String groupId)
    {
            ArrayList<String> admins=new ArrayList<>();
        try {
          //  System.out.println(groupId);
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
    void removeMember(String userId,String groupId){
        User user=null;
        try {
            user = userGroupsDatabase.getUser(userId);
            Group group=groupsDatabase.getGroup(groupId);
            group.getMembers().remove(userId);
            user.getProfile().getGroups().remove(groupId);
            groupsDatabase.modifyGroupById(group);
            userGroupsDatabase.modifyUserGroupsById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void respondRequest(String userId,String groupId,boolean accept){
        try
        {
            User user=userGroupsDatabase.getUser(userId);
            Group group=groupsDatabase.getGroup(groupId);
            if(accept)
            {
                group.getMembers().put(userId,GroupRole.MEMBER);
                user.getProfile().getGroups().put(groupId,GroupRole.MEMBER);
                NotificationManager.getInstance().additionToGroup(groupId,userId);
            }
            else
            {
                group.getMembers().remove(userId);
                user.getProfile().getGroups().remove(groupId);
            }
            groupsDatabase.modifyGroupById(group);
            userGroupsDatabase.modifyUserGroupsById(user);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void promote(String groupId,String userId)
    {
        try
        {
            User user=userGroupsDatabase.getUser(userId);
            Group group=groupsDatabase.getGroup(groupId);
            group.promoteMember(userId);
            user.getProfile().getGroups().put(groupId,GroupRole.ADMIN);
            System.out.println(user.getProfile().getGroups().get(groupId));
            userGroupsDatabase.modifyUserGroupsById(user);
            groupsDatabase.modifyGroupById(group);
            NotificationManager.getInstance().promotion(groupId,userId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void demote(String groupId,String userId)
    {
        try
        {
            User user=userGroupsDatabase.getUser(userId);
            Group group=groupsDatabase.getGroup(groupId);
            group.demoteMember(userId);
            user.getProfile().getGroups().put(groupId,GroupRole.MEMBER);
            userGroupsDatabase.modifyUserGroupsById(user);
            groupsDatabase.modifyGroupById(group);
            NotificationManager.getInstance().demotion(groupId,userId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void leave (String groupId,String userId)
    {
        try
        {
            User user=userGroupsDatabase.getUser(userId);
            Group group=groupsDatabase.getGroup(groupId);
            if(group.getMembers().get(userId).equals(GroupRole.PRIMARYADMIN))
            {
                //shuffle the admins to get the primary
                ArrayList<String> suggestions = getAdmins(groupId);
                if(suggestions.isEmpty())
                {
                    deleteGroup(groupId);
                }
                else
                {
                    group.getMembers().put(suggestions.get(0),GroupRole.PRIMARYADMIN);
                }
            }
            group.deleteMember(userId);
            user.getProfile().getGroups().remove(groupId);
            userGroupsDatabase.modifyUserGroupsById(user);
            groupsDatabase.modifyGroupById(group);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Group> getAllGroups()
    {
        int num= 0;
        try {
            num = groupsDatabase.getMax();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Group> groups=new ArrayList<>();
        for(int i=1;i<=num;i++)
        {
            Group group= null;
            try {
                group = groupsDatabase.getGroup(i+"");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(!group.isDeleted())
            {
                groups.add(group);
            }
        }
        return groups;
    }


    public ArrayList<String> searchGroupsBySubstringOfTheirNames(String query) throws IOException {
        ArrayList<Group> Groups = getAllGroups();
        ArrayList<String> searchResults = new ArrayList<>();
        for (Group g : Groups) {
            if (g.getName().toLowerCase().contains(query)) {
                searchResults.add(g.getGroupId());
            }
        }
        return searchResults;
    }


}
