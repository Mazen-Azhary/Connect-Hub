package backend;

import java.time.Duration;
import java.time.LocalDateTime;

public class Story extends Content {

    public Story(int authorId, String content, String image) {
        super(authorId, content, image);
    }

    public boolean isExpired() {
        return Duration.between(super.getTimestamp(), LocalDateTime.now()).toHours() >= 24;
    }
}
