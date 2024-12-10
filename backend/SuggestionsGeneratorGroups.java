package backend;

import java.util.HashSet;

public class SuggestionsGeneratorGroups {
    private GroupsDatabase groupsDatabase= new GroupsDatabase("src/database/Groups.json");
    private UserGroupsDatabase userGroupsDatabase= new UserGroupsDatabase("src/database/UserGroups.json");
    private static SuggestionsGeneratorGroups instance;
    private SuggestionsGeneratorGroups()
    {}
    public synchronized static SuggestionsGeneratorGroups getInstance()
    {
        if(instance==null) instance=new SuggestionsGeneratorGroups();
        return instance;
    }
//    public HashSet<Group> generateSuggestions(String id)
//    {
//        HashSet<Group> suggestions= new HashSet<>();
//
//
//    }



}
