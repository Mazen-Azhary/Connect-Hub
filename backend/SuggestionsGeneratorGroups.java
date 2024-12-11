package backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class SuggestionsGeneratorGroups {
    private GroupsDatabase groupsDatabase= new GroupsDatabase("src/database/Groups.json");
    private UserGroupsDatabase userGroupsDatabase= new UserGroupsDatabase("src/database/UserGroups.json");
    private FriendDatabase friendDatabase= new FriendDatabase("src/database/Friends.json");
    private static SuggestionsGeneratorGroups instance;
    private SuggestionsGeneratorGroups()
    {}
    public synchronized static SuggestionsGeneratorGroups getInstance()
    {
        if(instance==null) instance=new SuggestionsGeneratorGroups();
        return instance;
    }
    public ArrayList<Group> generateSuggestions(String id)
    {
        ArrayList<Group> suggestions= new ArrayList<>();
        HashSet<String> sug=new HashSet<>();
        try {
            ArrayList<String> friends = friendDatabase.getUser(id).getProfile().getFriends();
            for(String friend: friends)
            {
                Map<String,GroupRole> friendGroups=userGroupsDatabase.getUser(friend).getProfile().getGroups();
                for(Map.Entry<String,GroupRole> entry: friendGroups.entrySet())
                {
                    Group group=groupsDatabase.getGroup(entry.getKey());
                    if(!group.isDeleted()&&!userGroupsDatabase.getUser(id).getProfile().getGroups().containsKey(entry.getKey()))
                    {
                        sug.add(group.getGroupId());
                    }
                }
            }
            for (String grp:sug)
            {
                suggestions.add(groupsDatabase.getGroup(grp));
            }
            if(suggestions.isEmpty())
            {
                int num= groupsDatabase.getMax();
                for(int i=1;i<=num;i++)
                {
                    Group group=groupsDatabase.getGroup(i+"");
                    if(!group.isDeleted()&&!userGroupsDatabase.getUser(id).getProfile().getGroups().containsKey(i+""))
                    {
                        suggestions.add(group);
                    }
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return suggestions;


    }



}
