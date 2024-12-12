package backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupContentManager {
    private static GroupContentManager instance ;
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
        NotificationManager.getInstance().addPost(userID,GroupId,n.getContentId()+"");
    }
    public ArrayList<Content> contents(String userId,String groupId)
    {
        Group group= null;
        try {
            group = groupsDatabase.getGroup(groupId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Content> contents=group.getPosts();
        ArrayList<Content> returned=new ArrayList<>();
        for(Content c:contents)
        {
            if(c.isDeleted())
            {
                continue;
            }
            returned.add(c);
        }
        return returned;
    }
    public void deletePost(String groupId,String contentId) {

        Group group=null;
        try {
            group=groupsDatabase.getGroup(groupId);
            group.deletePost(contentId);
            groupsDatabase.modifyGroupById(group);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editPost(String groupId,Content content,String desc,String imagePath)
    {
        Group group;
        try {
            group=groupsDatabase.getGroup(groupId);
            for(Content c:group.getPosts())
            {
                if(c.getContentId()==content.getContentId())
                {
                    ContentBuilder builder = new ContentBuilder(c);
                    builder.setContent(desc);
                    builder.setImage(imagePath);
                }
            }
            groupsDatabase.modifyGroupById(group);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //function to return posts of a group to user with handling the blocked users
    // delete post
}
