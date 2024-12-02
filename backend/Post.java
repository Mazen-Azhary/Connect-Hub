package backend;

import java.time.LocalDateTime;

public class Post extends Content{
    public Post(int authorId, LocalDateTime timestamp) {
        super(authorId, timestamp);
    }
}
