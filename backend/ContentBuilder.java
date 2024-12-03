package backend;

//builder design pattern for content creation
public class ContentBuilder {
    private Content content;
    public ContentBuilder(Content content) {
        this.content = content;
    }
    public void setContent(String content) {
        this.content.setContent(content);
    }
    public void setImage(String image) {
        this.content.setImage(image);
    }
    public Content build() {
        return content;
    }
}
