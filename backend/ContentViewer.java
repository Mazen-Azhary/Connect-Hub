package backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ContentViewer {
    private UserContentDatabase userContentDatabase = new UserContentDatabase("src/database/UserContents.json");
    private FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");
    private static ContentViewer instance;
    private ContentViewer() {
    }
    public synchronized static ContentViewer getInstance() {
        if(instance ==null) return new ContentViewer();
        return instance;
    }
    public ArrayList<Content> generatePosts(String id) throws IOException {
        ArrayList<Content> posts = new ArrayList<>();
        ArrayList<String> friends=friendDatabase.getUser(id).getProfile().getFriends();
        for (String friend : friends) {
            User f = userContentDatabase.getUser(friend);
            for (Content content : f.getProfile().getContents()) {
                if (content.getContentId() > 0) {
                    posts.add(content);
                }
            }
        }
        User user=userContentDatabase.getUser(id);
        for(Content content:user.getProfile().getContents()) {
            if(content.getContentId()>0) {
                posts.add(content);
            }
        }
            Collections.shuffle(posts);
        return posts;
    }
    public ArrayList<Content> generateStories(String id) throws IOException {
        ArrayList<Content> stories = new ArrayList<>();
        ArrayList<String> friends = friendDatabase.getUser(id).getProfile().getFriends();
        for (String friend : friends) {
            User f = userContentDatabase.getUser(friend);
            for (Content content : f.getProfile().getContents()) {
                if (content.getContentId() < 0) {
                    if (!content.isExpired()) {
                        stories.add(content);
                    }
                }
            }
        }
        User user = userContentDatabase.getUser(id);
        for (Content content : user.getProfile().getContents()) {
            if (content.getContentId() < 0) {
                if (!content.isExpired()) {
                    stories.add(content);
                }
            }
        }
        Collections.shuffle(stories);
        return stories;
    }
    public ArrayList<Content> generateProfilePosts(String id) throws IOException {
        ArrayList<Content> posts = new ArrayList<>();
        User user=userContentDatabase.getUser(id);
        for(Content content:user.getProfile().getContents()) {
            if(content.getContentId()>0) {
                posts.add(content);
            }
        }
            Collections.shuffle(posts);
        return posts;
    }

}
