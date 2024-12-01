package backend;

import java.util.ArrayList;

public class ContentCreation {
    private ArrayList<Content> contents = new ArrayList<>();

    public void addContent(Content content) {
        contents.add(content);
    }

    public void deleteContent(Content content) {
        if (contents.contains(content))
            contents.remove(content);
    }
}
