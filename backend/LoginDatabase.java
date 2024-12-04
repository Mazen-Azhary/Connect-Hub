package backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginDatabase extends Database {
    public LoginDatabase(String filename) {
        super(filename);
        readFromFile();
    }
    public void save() {
        ArrayList<Map<String,Object>> users = new ArrayList<>();
        for (User u : data) {
            Map<String,Object> map = new HashMap<>();
            map.put("email", u.getEmail());
            map.put("userID", u.getUserID());
            map.put("salt", u.getSalt());
            map.put("hashedPassword", u.getHashedPassword());
            users.add(map);
        }
        DatabaseWrite databaseWrite=DatabaseWrite.getInstance();
        databaseWrite.write(fileName, users);
    }
}
