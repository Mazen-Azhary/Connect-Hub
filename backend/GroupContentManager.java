package backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupContentManager {
    private static GroupContentManager instance ;
    private ContentDatabase contentDatabase = new ContentDatabase("src/database/Contents.json");
    private GroupsDatabase groupsDatabase = new GroupsDatabase("src/database/Groups.json");
    private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
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
    public ArrayList<Content> contents(String userId,String groupId)
    {
        Group group= null;
        try {
            group = groupsDatabase.getGroup(groupId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Content> contents=group.getPosts();
        //checking the blocked users
        User user=null;
        try {
            user=friendDatabase.getUser(userId);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//        for(Content c:contents)
//        {
//            if(user.getProfile().getBlockedUsers().contains(c.getAuthorId()+"")||c.isDeleted())
//            {
//                contents.remove(c);
//            }
//        }
        return contents;
    }
    public void deletePost(String groupId,Content content) {
        content.deleteContent();
        Group group=null;
        try {
            group=groupsDatabase.getGroup(groupId);
        groupsDatabase.modifyGroupById(group);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editPost(String groupId,Content content,String desc,String imagePath)
    {
        ContentBuilder builder = new ContentBuilder(content);
        builder.setContent(desc);
        builder.setImage(imagePath);
        Group group;
        try {
            group=groupsDatabase.getGroup(groupId);
            groupsDatabase.modifyGroupById(group);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //function to return posts of a group to user with handling the blocked users
    // delete post
}
