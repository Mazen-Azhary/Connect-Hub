package backend;

import java.io.IOException;
import java.time.LocalDateTime;

public class GroupContentManager {
    private static GroupContentManager instance = new GroupContentManager();
    private ContentDatabase contentDatabase = new ContentDatabase("src/database/Contents.json");
    private GroupsDatabase groupsDatabase = new GroupsDatabase("src/database/Groups.json");
    private GroupContentManager()
    {

    }
    public static GroupContentManager  getInstance()
    {
        if (instance==null) instance = new GroupContentManager();
        return instance;
    }
    public void addPost(String userID,String GroupId,String content,String imagePath) throws IOException {
        ContentFactory factory = new ContentFactory();
        Content n = factory.createContent("post",Integer.parseInt(userID), LocalDateTime.now());
        ContentBuilder builder = new ContentBuilder(n);
        builder.setContent(content);
        if(imagePath!=null)
            builder.setImage(imagePath);
        n = builder.build();
        contentDatabase.addContent(n);
        Group group=groupsDatabase.getGroup(GroupId);
        group.addPost(n);
        groupsDatabase.modifyGroupById(group);
    }
}
