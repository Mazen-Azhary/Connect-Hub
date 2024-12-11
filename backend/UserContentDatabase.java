package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserContentDatabase extends Database {
    public UserContentDatabase(String filename) {
        super(filename);
    }
    public String readString()  {
        try {

            return new String(Files.readAllBytes(new File(fileName).toPath()));
        } catch (IOException e) {
            return null;
        }
    }
    public ArrayList<Map<String, Object>> parseUsers(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            return objectMapper.readValue(jsonString, ArrayList.class);
        }
        catch (Exception e) {
            return null;
        }
    }
    public void modifyUserById(User user) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        if(users == null) return;
        for (Map<String, Object> userNode : users) {
            if (userNode.get("userID").equals(user.getUserID())){
                userNode.put("username", user.getUsername());
                userNode.put("status", user.getStatus());
                Object profileObj = userNode.get("profile");
                Map<String, Object> profile = (Map<String, Object>) profileObj;
                ArrayList<Map<String, Object>> newContents = new ArrayList<>();
                for (Content content : user.getProfile().getContents()) {
                    newContents.add(parseContent(content));
                }
                profile.put("contents", newContents);
                userNode.put("profile", profile);
                break; // Found the user and modified, no need to continue
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }
    private Map<String, Object> parseContent(Content content) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("authorId", content.getAuthorId());
        contentMap.put("contentId", content.getContentId());
        contentMap.put("timestamp", content.getTimestamp());
        contentMap.put("image", content.getImage());
        contentMap.put("content", content.getContent());
        return contentMap;
    }
    public void addUser(User user) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        if (users == null) {
            users = new ArrayList<>();
        }
        Map<String, Object> newUser = parse(user);
        users.add(newUser);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(users);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }
    public User getUser(String id) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        if(users == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map<String, Object> userNode : users) {
            if (userNode.get("userID").equals(id)) {
                String userJson = objectMapper.writeValueAsString(userNode);
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(userJson, User.class);
            }
        }
        return null;
    }
    private Map<String,Object> parse(User u)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("username", u.getUsername());
        map.put("userID", u.getUserID());
        map.put("status", u.getStatus());
        Profile p = u.getProfile();
        Map<String, Object> profile = new HashMap<>();
        ArrayList<Map<String, Object>> parsedContents = new ArrayList<>();
        for (Content content : p.getContents()) {
            parsedContents.add(parseContent(content)); // Convert each Content to a Map
        }
        profile.put("contents", parsedContents);
        map.put("profile", profile);

        return map;
    }
    public void save()
    {

    }
    public void writeUpdatedJSONToFile(String filePath, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString);  // Write the updated string back to the file
        writer.close();
    }
}
