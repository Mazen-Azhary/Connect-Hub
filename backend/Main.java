package backend;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        String fileName = "database/contentDatabase.json";
        ContentDataBase contentDB = null;

        try {
            contentDB = new ContentDataBase(fileName);

        } catch (IOException e) {
            System.out.println("Error reading content database: " + e.getMessage());
        }


        if (contentDB != null) {

            Content post = ContentFactory.createContent("post", 101, LocalDateTime.now());
            post.setContent("This is a post about Java programming!");
            post.setImage("image_data_here");


            Content story = ContentFactory.createContent("story", 102, LocalDateTime.now());
            story.setContent("This is a story about an amazing adventure!");
            story.setImage("image_data_here");


            contentDB.addData(post);
            contentDB.addData(story);


            contentDB.save();
            System.out.println("Content saved successfully!");


            try {

                ContentDataBase contentDBRead = new ContentDataBase(fileName);


                for (Content content : contentDBRead.getData()) {
                    System.out.println("Content ID: " + content.getContentId());
                    System.out.println("Author ID: " + content.getAuthorId());
                    System.out.println("Content: " + content.getContent());
                    System.out.println("Image: " + content.getImage());
                    System.out.println("Timestamp: " + content.getTimestamp());
                    System.out.println("-----");
                }
            } catch (IOException e) {
                System.out.println("Error reading content database: " + e.getMessage());
            }
        }
    }
}
