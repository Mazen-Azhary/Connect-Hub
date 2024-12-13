package backend;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Content {
    private int contentId;
    private int authorId;
    private String content;
    private String image;
    private LocalDateTime timestamp=LocalDateTime.now();
    private String groupId;
    private boolean deleted=false;
    public Content()
    {

    }

    public Content(int contentId, int authorId, LocalDateTime timestamp) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.timestamp = timestamp;
    }

    public Content(int contentId, int authorId, String content, String image, LocalDateTime timestamp, String groupId, boolean deleted) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.content = content;
        this.image = image;
        this.timestamp = timestamp;
        this.groupId = groupId;
        this.deleted = deleted;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentId() {
        return contentId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public boolean isExpired()
    {
        return Duration.between(getTimestamp(), LocalDateTime.now()).toHours() >= 24;
    }
    public void deleteContent()
    {
        deleted=true;
    }

    public boolean isDeleted() {
        return deleted;
    }


}
