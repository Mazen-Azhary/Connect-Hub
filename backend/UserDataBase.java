package backend;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
            System.out.println("file doesn't exist or wrong path");
            return;
        }
        JSONParser FileToJsonOBJ = new JSONParser();
        JSONArray usersArray;
        try {
            Object obj = FileToJsonOBJ.parse(fileReader);
            usersArray = (JSONArray) obj;
        } catch (ParseException ex) {
            usersArray = new JSONArray();
            System.out.println("exception occured");
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
            User user =new User(id,email,username,gender,dob);
            user.setHashedPassword(password);
            user.setSalt(s);
            data.add(user);
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
