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

public class FriendDatabase extends Database {
    public FriendDatabase(String filename) {
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

    public void modifyUserById(User user) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        if (users == null) return;

        for (Map<String, Object> userNode : users) {
            if (userNode.get("userID").equals(user.getUserID())) {
                userNode.put("username", user.getUsername());
                Object profileObj = userNode.get("profile");
                Map<String, Object> profile = (Map<String, Object>) profileObj;

                // Update the profile with friend-related attributes
                profile.put("friendRequests", user.getProfile().getFriendRequests());
                profile.put("friendReceivedRequests", user.getProfile().getFriendRecievedRequests());
                profile.put("friendSuggestions", user.getProfile().getFriendSuggestions());
                profile.put("friends", user.getProfile().getFriends());
                profile.put("blockedUsers", user.getProfile().getBlockedUsers());

                userNode.put("profile", profile);
                break; // Found and modified the user, no need to continue
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
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

        // Parse the profile
        Map<String, Object> profile = new HashMap<>();
        profile.put("friendRequests", user.getProfile().getFriendRequests());
        profile.put("friendReceivedRequests", user.getProfile().getFriendRecievedRequests());
        profile.put("friendSuggestions", user.getProfile().getFriendSuggestions());
        profile.put("friends", user.getProfile().getFriends());
        profile.put("blockedUsers", user.getProfile().getBlockedUsers());

        map.put("profile", profile);
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
        FriendDatabase friendDatabase = new FriendDatabase("src/database/Friends.json");

        // Example usage
        User user = new User();
        user.setUsername("admin");
        user.setStatus("active");
        user.setUserID("1");

        Profile profile = new Profile();
        profile.setFriendRequests(new ArrayList<>());
        profile.setFriendRecievedRequests(new ArrayList<>());
        profile.setFriendSuggestions(new ArrayList<>());
        profile.setFriends(new ArrayList<>());
        profile.setBlockedUsers(new ArrayList<>());

        profile.getFriendRequests().add("2");
        profile.getFriends().add("3");

        user.setProfile(profile);
        friendDatabase.addUser(user);

        User retrievedUser = friendDatabase.getUser("1");
        retrievedUser.getProfile().getBlockedUsers().add("4");
        friendDatabase.modifyUserById(retrievedUser);
    }
}