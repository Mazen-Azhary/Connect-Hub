package backend;

import java.io.IOException;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContentFactory {
    //factory design pattern
    public static Content createContent(String type, int id, LocalDateTime timestamp) throws IOException {
        ContentDatabase contentDatabase=new ContentDatabase("src/database/Contents.json");
        int contentId=contentDatabase.getMax()+1;
        if (type.equalsIgnoreCase("post")) {
            return new Post(contentId,id,timestamp);
        }
        if (type.equalsIgnoreCase("story")) {
            return new Story(contentId,id,timestamp);
        }
        return null;
    }
}
