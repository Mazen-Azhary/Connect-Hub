package backend;

import java.io.IOException;
import java.time.LocalDateTime;

public class ContentManager {
    private static ContentManager instance = new ContentManager();
    private ContentDatabase contentDatabase = new ContentDatabase("src/database/Contents.json");
    private UserContentDatabase userContentDatabase = new UserContentDatabase("src/database/UserContents.json");
    private ContentManager()
    {

    }
    public static ContentManager  getInstance()
    {
        if (instance==null) instance = new ContentManager();
        return instance;
    }
    public void addPost(String userID,String content,String imagePath) throws IOException {
        ContentFactory factory = new ContentFactory();
            Content n = factory.createContent("post",Integer.parseInt(userID), LocalDateTime.now());
            ContentBuilder builder = new ContentBuilder(n);
            builder.setContent(content);
            if(imagePath!=null)
                builder.setImage(imagePath);
            n = builder.build();
            contentDatabase.addContent(n);
            User u=userContentDatabase.getUser(userID);
            u.getProfile().addContent(n);
            userContentDatabase.modifyUserById(u);

        }
        public void addStory(String userID,String content,String imagePath) throws IOException {
        ContentFactory factory = new ContentFactory();
            Content n = factory.createContent("story",Integer.parseInt(userID), LocalDateTime.now());
            ContentBuilder builder = new ContentBuilder(n);
            builder.setContent(content);
            if(imagePath!=null)
                builder.setImage(imagePath);
            n = builder.build();
            contentDatabase.addContent(n);
            User u=userContentDatabase.getUser(userID);
            u.getProfile().addContent(n);
            userContentDatabase.modifyUserById(u);
        }


    }

