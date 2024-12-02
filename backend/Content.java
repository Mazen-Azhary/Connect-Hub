package backend;

import java.time.LocalDateTime;

public abstract class Content {
    private int contentId;
    private int authorId;
    private String content;
    private String image;
    private LocalDateTime timestamp;
    private static int postCount = 0;

    public Content(int authorId, LocalDateTime timestamp) {
        this.authorId = authorId;
        this.timestamp = timestamp;
        this.contentId = ++postCount;
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

    public static int getPostCount() {
        return postCount;
    }


}
