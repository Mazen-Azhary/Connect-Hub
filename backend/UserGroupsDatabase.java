package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserGroupsDatabase extends Database {

    public UserGroupsDatabase(String filename) {
        super(filename);
    }

    public String readString() {
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
        } catch (Exception e) {
            return null;
        }
    }

    public void modifyUserGroupsById(User user) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        if (users == null) return;

        for (Map<String, Object> userNode : users) {
            if (userNode.get("userID").equals(user.getUserID())) {
                userNode.put("username", user.getUsername());
                Object profileObj = userNode.get("profile");
                Map<String, Object> profile = (Map<String, Object>) profileObj;
                profile.put("profilePhoto", user.getProfile().getProfilePhoto());
                profile.put("groups", user.getProfile().getGroups());
                userNode.put("profile", profile);
                break; // Found the user and modified, no need to continue
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }
    public void save()
    {

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
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }

    public User getUser(String id) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        if (users == null) return null;

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

    private Map<String, Object> parse(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("userID", user.getUserID());
        Profile profileData = user.getProfile();
        Map<String, Object> profile = new HashMap<>();
        profile.put("groups", profileData.getGroups());
        profile.put("profilePhoto",profileData.getProfilePhoto());
        map.put("profile", profile);

        return map;
    }

    public void writeUpdatedJSONToFile(String filePath, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString); // Write the updated string back to the file
        writer.close();
    }
}
