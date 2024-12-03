package backend;
import java.time.LocalDateTime;

public class ContentFactory {
    //factory design pattern
    public static Content createContent(String type, int id, LocalDateTime timestamp) {
        if (type.equalsIgnoreCase("post")) {
            return new Post(id,timestamp);
        }
        if (type.equalsIgnoreCase("story")) {
            return new Story(id,timestamp);
        }
        return null;
    }
}
