package backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post extends Content{
    public Post(int contentId, int authorId, String content, String image, LocalDateTime timestamp) {
        super(contentId, authorId, content, image, timestamp);
    }

    public Post(int contentId, int authorId, LocalDateTime timestamp) {
        super(contentId, authorId, timestamp);
    }
}
