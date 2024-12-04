package backend;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Story extends Content {



    public Story(int contentId, int authorId, String content, String image, LocalDateTime timestamp) {
        super(contentId, authorId, content, image, timestamp);
    }

    public Story(int contentId, int authorId, LocalDateTime timestamp) {
        super(contentId, authorId, timestamp);
    }

    public boolean isExpired() {
        return Duration.between(super.getTimestamp(), LocalDate.now()).toHours() >= 24;
    }
}
