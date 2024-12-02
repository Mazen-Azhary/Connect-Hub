package backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class ContentDataBase {
    private String fileName;
    private ArrayList<Content> data;

    public ContentDataBase(String fileName) throws IOException {
        this.fileName = fileName;
        data = new ArrayList<>();
        readFromFile();
    }

    @SuppressWarnings("unchecked")
    public void readFromFile() throws IOException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println("File doesn't exist or wrong path");
            return;
        }
        JSONParser fileToJsonOBJ = new JSONParser();
        JSONArray contentArray;
        try {
            Object obj = fileToJsonOBJ.parse(fileReader);
            contentArray = (JSONArray) obj;
        } catch (ParseException ex) {
            contentArray = new JSONArray();
            System.out.println("Exception occurred");
        } finally {
            fileReader.close();
        }


        for (Object cont : contentArray) {
            JSONObject contentObj = (JSONObject) cont;
            int contentId = ((Long) contentObj.get("contentId")).intValue();
            int authorId = ((Long) contentObj.get("authorId")).intValue();
            String contentText = (String) contentObj.get("content");
            String image = (String) contentObj.get("image");
            LocalDateTime timestamp = LocalDateTime.parse((String) contentObj.get("timestamp"));
            String type = (String) contentObj.get("type");


            Content content = ContentFactory.createContent(type, authorId, timestamp);
            if (content != null) {
                content.setContent(contentText);
                content.setImage(image);
                data.add(content);
            }
        }
    }

    public ArrayList<Content> getData() {
        return data;
    }

    public boolean addData(Content content) {
        if (contains(content.getContentId())) {
            return false;
        }
        data.add(content);
        return true;
    }

    public boolean contains(int contentId) {
        for (Content c : data) {
            if (c.getContentId() == contentId) {
                return true;
            }
        }
        return false;
    }

    public void removeData(Content content) {
        data.remove(content);
    }

    public Content search(int contentId) {
        for (Content c : data) {
            if (c.getContentId() == contentId) {
                return c;
            }
        }
        return null;
    }

    public void save() {
        JSONArray jsonArray = new JSONArray();
        for (Content c : data) {
            JSONObject contentObj = new JSONObject();
            contentObj.put("contentId", c.getContentId());
            contentObj.put("authorId", c.getAuthorId());
            contentObj.put("content", c.getContent());
            contentObj.put("image", c.getImage());
            contentObj.put("timestamp", c.getTimestamp().toString());


            if (c instanceof Post) {
                contentObj.put("type", "post");
            } else if (c instanceof Story) {
                contentObj.put("type", "story");
            }

            jsonArray.add(contentObj);
        }
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
