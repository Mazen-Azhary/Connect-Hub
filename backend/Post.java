package backend;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post extends Content{
    public Post(int contentId, int authorId, String content, String image, LocalDate timestamp) {
        super(contentId, authorId, content, image, timestamp);
    }

    public Post(int authorId, LocalDate timestamp) {
        super(authorId, timestamp);
    }
}
