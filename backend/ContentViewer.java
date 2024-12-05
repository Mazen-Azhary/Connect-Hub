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
            User f=userContentDatabase.getUser(friend);
            for(Content content:f.getProfile().getContents()) {
                if(content instanceof Post) {
                    posts.add((Post)content);
                }
            }
        }
            Collections.shuffle(posts);
        return posts;

    }
    public ArrayList<Content> generateStories(String id) throws IOException {
        ArrayList<Content> stories = new ArrayList<>();
        ArrayList<String> friends=friendDatabase.getUser(id).getProfile().getFriends();
        for (String friend : friends) {
            User f=userContentDatabase.getUser(friend);
            for(Content content:f.getProfile().getContents()) {
                if(content instanceof Story story) {
                    if(!story.isExpired()) {
                        stories.add(story);
                    }
                }
            }
        }
        Collections.shuffle(stories);
        return stories;
    }
}
