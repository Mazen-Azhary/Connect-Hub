package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Group {
    private String groupId;
    private String name;
    private String description;
    private String photo;
    private Map<String,GroupRole> members;
    private ArrayList<Content>posts;
    private boolean deleted=false;


    public Group(String groupId, String name, String description, String photo, Map<String, GroupRole> members, ArrayList<Content> posts) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.members = members;
        this.posts = posts;
    }
    public Group()
    {
        this.members = new HashMap<>();
        this.posts = new ArrayList<>();

    }
    public Group(String groupId, String name, String description,String primaryAdmin) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.members = new HashMap<>();
        members.put(primaryAdmin,GroupRole.PRIMARYADMIN);
        this.posts = new ArrayList<>();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setMembers(Map<String, GroupRole> members) {
        this.members = members;
    }

    public Map<String, GroupRole> getMembers() {
        return members;
    }

    public ArrayList<Content> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Content> posts) {
        this.posts = posts;
    }
    public void addMember(String memberId)
    {
        members.put(memberId,GroupRole.MEMBER);
    }
    public void deleteMember(String memberId)
    {
        members.remove(memberId);
    }
    public void promoteMember(String memberId)
    {
        members.put(memberId,GroupRole.ADMIN);
    }
    public void demoteMember(String memberId)
    {
        members.put(memberId,GroupRole.MEMBER);
    }
    public void addPost(Content post)
    {
        posts.add(post);
    }
    public void deletePost(String contentId)
    {
        Iterator<Content> iterator=posts.iterator();
        while (iterator.hasNext())
        {
            if(iterator.next().getContentId()==Integer.parseInt(contentId))
            {
                iterator.remove();
            }

        }
    }
    public void deleteGroup()
    {
        deleted=true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
