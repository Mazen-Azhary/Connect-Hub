package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContentDatabase extends Database {
    public ContentDatabase(String filename) {
        super(filename);
    }

    public String readString() {
        try {
            return new String(Files.readAllBytes(new File(fileName).toPath()));
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Map<String, Object>> parseContents(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, ArrayList.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void modifyContentById(Content content) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> contents = parseContents(jsonString);
        if (contents == null) return;

        for (Map<String, Object> contentNode : contents) {
            if ((int) contentNode.get("contentId") == content.getContentId()) {
                contentNode.put("authorId", content.getAuthorId());
                contentNode.put("content", content.getContent());
                contentNode.put("image", content.getImage());
                contentNode.put("timestamp", content.getTimestamp().toString());
                break; // Found the content and modified, no need to continue
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contents);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }
    public int getMax() throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> contents = parseContents(jsonString);
        if (contents == null) return 0;
        int max = 0;
        for (Map<String, Object> contentNode : contents) {
            if ((int) contentNode.get("contentId")>max) {
                max = (int)contentNode.get("contentId");
            }
        }
        return max;
    }

    public void addContent(Content content) throws IOException {
        String jsonString = readString();

        ArrayList<Map<String, Object>> contents = parseContents(jsonString);
        if (contents == null) {
            contents = new ArrayList<>();
        }
        Map<String, Object> newContent = parse(content);
        contents.add(newContent);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contents);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }
    public Content getContentById(int id) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> contents = parseContents(jsonString);
        if (contents == null) return null;

        ObjectMapper objectMapper = new ObjectMapper();
        for (Map<String, Object> contentNode : contents) {
            if ((int) contentNode.get("contentId") == id) {
                String contentJson = objectMapper.writeValueAsString(contentNode);
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(contentJson, Content.class);
            }
        }
        return null;
    }

    private Map<String, Object> parse(Content content) {
        Map<String, Object> map = new HashMap<>();
        map.put("contentId", content.getContentId());
        map.put("authorId", content.getAuthorId());
        map.put("content", content.getContent());
        map.put("image", content.getImage());
        map.put("timestamp", content.getTimestamp().toString());
        return map;
    }

    public void save() {
        // Placeholder for save functionality if needed
    }

    public void writeUpdatedJSONToFile(String filePath, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString); // Write the updated string back to the file
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        ContentDatabase contentDatabase = new ContentDatabase("src/database/Contents.json");
        Content content = ContentFactory.createContent("post",1, LocalDateTime.now());
        ContentBuilder contentBuilder=new ContentBuilder(content);
        contentBuilder.setContent("ana aho");
        contentDatabase.addContent(content);

    }
}