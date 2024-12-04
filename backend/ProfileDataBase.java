package backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileDataBase extends Database {
    public ProfileDataBase(String filename) {
        super(filename);
    }
    public String readString()  {
        try {

        return new String(Files.readAllBytes(new File(fileName).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Map<String, Object>> parseUsers(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, ArrayList.class);
    }
    public void modifyUserById(User user) throws IOException {
        String jsonString = readString();
        System.out.println(jsonString);
        ArrayList<Map<String, Object>> users = parseUsers(jsonString);
        for (Map<String, Object> userNode : users) {
            if (userNode.get("userID").equals(user.getUserID())){
                System.out.println(user.getDateOfBirth());
                userNode.put("gender", user.getGender());
                Object profileObj = userNode.get("profile");
                System.out.println(profileObj.toString());
                Map<String, Object> profile = (Map<String, Object>) profileObj;
                profile.put("profilePhoto", user.getProfile().getProfilePhoto());
                profile.put("coverPhoto", user.getProfile().getCoverPhoto());
                profile.put("bio", user.getProfile().getBio());
                userNode.put("profile", profile);
            break; // Found the user and modified, no need to continue
        }
        }
        String updatedJsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(users);
            writeUpdatedJSONToFile(fileName, updatedJsonString);
    }
    private Map<String,Object> parse(User u)
    {
        Map<String,Object> map = new HashMap<>();
        map.put("userID", u.getUserID());
        map.put("gender", u.getGender());
        map.put("dateOfBirth", u.getDateOfBirth());
        Profile p = u.getProfile();
        Map<String,Object> profile = new HashMap<>();
        profile.put("profilePhoto", p.getProfilePhoto());
        profile.put("coverPhoto", p.getCoverPhoto());
        profile.put("bio", p.getBio());
        map.put("profile", profile);
        return map;
    }
    public void save()
    {
        ArrayList<Map<String,Object>> users = new ArrayList<>();
        for (User u : data) {
           Map<String,Object> map = parse(u);
            users.add(map);
        }
        DatabaseWrite databaseWrite=DatabaseWrite.getInstance();
        databaseWrite.write(fileName, users);
    }
    public void writeUpdatedJSONToFile(String filePath, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString);  // Write the updated string back to the file
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
        User user=new User();
        user.setUserID("1");
        user.setGender("female");
        user.setProfile(new Profile());
        user.getProfile().setBio("leo");
        profileDataBase.modifyUserById(user);
    }
}
