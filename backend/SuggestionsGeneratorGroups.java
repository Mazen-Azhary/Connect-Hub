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
    public HashSet<Group> generateSuggestions(String id)
    {
        HashSet<Group> suggestions= new HashSet<>();
        try {
            ArrayList<String> friends = friendDatabase.getUser(id).getProfile().getFriends();
            for(String friend: friends)
            {
                Map<String,GroupRole> friendGroups=userGroupsDatabase.getUser(friend).getProfile().getGroups();
                for(Map.Entry<String,GroupRole> entry: friendGroups.entrySet())
                {
                    Group group=groupsDatabase.getGroup(entry.getKey());
                    if(!group.isDeleted())
                    {
                        suggestions.add(group);
                    }
                }
            }
            if(suggestions.isEmpty())
            {
                int num= groupsDatabase.getMax();
                for(int i=0;i<num;i++)
                {
                    Group group=groupsDatabase.getGroup(i+"");
                    if(!group.isDeleted())
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
