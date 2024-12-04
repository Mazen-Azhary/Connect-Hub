package backend;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Story extends Content {

    public Story(int authorId, LocalDate timestamp) {
        super(authorId, timestamp);
    }

    public Story(int contentId, int authorId, String content, String image, LocalDate timestamp) {
        super(contentId, authorId, content, image, timestamp);
    }

    public boolean isExpired() {
        return Duration.between(super.getTimestamp(), LocalDate.now()).toHours() >= 24;
    }
}
