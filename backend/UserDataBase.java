package backend;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

public class UserDataBase {
    private String fileName;
    private ArrayList<User> data;
    public UserDataBase(String fileName) throws IOException {
        this.fileName = fileName;
        data=new ArrayList<>(); //initialize the users
        readFromFile();
    }
    public void readFromFile() throws IOException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException ex) {
            return;
        }
        JSONParser FileToJsonOBJ = new JSONParser();
        JSONArray usersArray;
        try {
            Object obj = FileToJsonOBJ.parse(fileReader);
            usersArray = (JSONArray) obj;
        } catch (ParseException ex) {
            usersArray = new JSONArray();
        }
        finally{
            fileReader.close();
        }
        for (Object us : usersArray) {
            JSONObject userObj = (JSONObject) us;
            String id = (String) userObj.get("id");
            String username = (String) userObj.get("username");
            String password = (String) userObj.get("hashedPassword");
            String salt= (String) userObj.get("salt");
            byte[] s=Base64.getDecoder().decode(salt);
            String email = (String) userObj.get("email");
            LocalDate dob=LocalDate.parse((String)userObj.get("dob"));
            String gender = (String) userObj.get("gender");
            String status= (String) userObj.get("status");
            User user =new User(id,email,username,gender,dob,status);
            user.setHashedPassword(password);
            user.setSalt(s);
            data.add(user);
        }
        int i=0;
        for (Object us : usersArray) {
            JSONObject userObj = (JSONObject) us;
            JSONObject profileObj=(JSONObject) userObj.get("profile");
            Profile profile=parseProfile(profileObj);
            data.get(i).setProfile(profile);
            i++;
        }
    }
    private Profile parseProfile(JSONObject profileObj) {
        String profilePhoto = (String) profileObj.get("profilePhoto");
        String coverPhoto = (String) profileObj.get("coverPhoto");
        String bio = (String) profileObj.get("bio");
        JSONArray friendsArr=(JSONArray) profileObj.get("friends");
        ArrayList<String> friends=new ArrayList<>();
        for(Object obj : friendsArr) {
            JSONObject friendObj = (JSONObject) obj;
            friends.add((String) friendObj.get("id"));
        }
        JSONArray contentsArr=(JSONArray) profileObj.get("contents");
        ArrayList<Content> contents=new ArrayList<>();
        for(Object obj : contentsArr) {
            JSONObject contentObj = (JSONObject) obj;
            Content content =parseContent(contentObj);
            contents.add(content);
        }
        return new Profile(friends,profilePhoto,coverPhoto,bio,contents);

    }
    private Content parseContent(JSONObject contentObj) {
        String content = (String) contentObj.get("content");
        String type = (String) contentObj.get("type");
        int authorId = (Integer) contentObj.get("authorId");
        int contentId=(Integer) contentObj.get("contentId");
        String image=(String) contentObj.get("image");
        LocalDateTime timestamp=LocalDateTime.parse((String)contentObj.get("timestamp"));
        if(type.equals("post"))
        {
            return new Post(contentId,authorId,content,image,timestamp);
        }
        else
        {
            return new Story(contentId,authorId,content,image,timestamp);
        }

    }
    public ArrayList<User> getData() {
        return data;
    }
    public boolean addData(User user) {
        if(contains(user.getEmail()))
        {
            return false;
        }
        data.add(user);
        return true;
    }
    public boolean contains(String Email)
    {
        for(User u:data)
        {
            if (u.getEmail().equals(Email))
                return true;
        }
        return false;
    }
    public void removeData(User user) {
        data.remove(user);
    }
    public User search(String email) {
        for (User u : data) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }
    public User search(int id)
    {
        for (User u : data) {
            if (Integer.parseInt(u.getUserID())==id)
            {
                return u;
            }
        }
        return null;
    }
    public void save() {
        JSONArray jsonArray = new JSONArray();
        for(User u:data)
        {
            JSONObject userObj = new JSONObject();
            userObj.put("id",u.getUserID());
            userObj.put("username",u.getUsername());
            userObj.put("salt", Base64.getEncoder().encodeToString(u.getSalt()));
            userObj.put("hashedPassword",u.getHashedPassword());
            userObj.put("email",u.getEmail());
            userObj.put("gender",u.getGender());
            userObj.put("dob",u.getDateOfBirth().toString());
            userObj.put("status",u.getStatus());
            Profile profile=u.getProfile();
            JSONObject profileObj = new JSONObject();
            profileObj.put("profilePhoto",profile.getProfilePhoto());
            profileObj.put("coverPhoto",profile.getCoverPhoto());
            profileObj.put("bio",profile.getBio());
            JSONArray friendsArr=new JSONArray();
            ArrayList<String> friends=u.getProfile().getFriends();
            for(String friend:friends)
            {
                JSONObject friendObj = new JSONObject();
                friendObj.put("id",friend);
            }
            profileObj.put("friends",friendsArr);
            JSONArray contentsArr=new JSONArray();
            ArrayList<Content> contents=u.getProfile().getContents();
            for(Content content:contents)
            {
                JSONObject contentObj = new JSONObject();
                contentObj.put("content",content.getContent());
                contentObj.put("contentId",content.getContentId());
                contentObj.put("image",content.getImage());
                contentObj.put("timestamp",content.getTimestamp().toString());
                contentObj.put("authorId",content.getAuthorId());
                if(content instanceof Post)
                {
                    contentObj.put("type","post");
                }
                else
                {
                    contentObj.put("type","story");
                }
                contentsArr.add(contentObj);
            }
            profileObj.put("contents",contentsArr);
            userObj.put("profile",profileObj);
            jsonArray.add(userObj);
        }
        try (FileWriter file=new FileWriter(fileName)){
            file.write(jsonArray.toJSONString());
            file.flush();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
