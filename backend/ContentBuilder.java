package backend;

import java.time.LocalDateTime;
//builder design pattern for content creation
public class ContentBuilder {
    private Content content;
    public ContentBuilder(Content content) {
        this.content = content;
    }
    public Content getContent() {
        return content;
    }
    public ContentBuilder setContent(String content) {
        this.content.setContent(content);
        return this;
    }

    public ContentBuilder setImage(String image) {
        this.content.setImage(image);
        return this;
    }
    public Content build() {
        return content;
    }
}
