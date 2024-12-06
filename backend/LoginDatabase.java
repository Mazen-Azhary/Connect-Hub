package backend;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginDatabase extends Database {
    ArrayList<Map> data=new ArrayList<>();
    public LoginDatabase(String filename) {
        super(filename);
        readFromFile();
    }

    @Override
    public void readFromFile() {
        DatabaseRead databaseRead=DatabaseRead.getInstance();
        data=databaseRead.read(fileName,Map.class);
    }

    public void save() {
        ArrayList<Map<String,Object>> users = new ArrayList<>();
        for (Map<String,Object> u : data) {
            Map<String,Object> map = new HashMap<>();
            map.put("email", u.get("email"));
            map.put("userId", u.get("userId"));
            map.put("salt", u.get("salt"));
            map.put("hashedPassword", u.get("hashedPassword"));
            users.add(map);
        }
        DatabaseWrite databaseWrite=DatabaseWrite.getInstance();
        databaseWrite.write(fileName, users);
    }
    public boolean addData(Map<String,Object> user)
    {
        if(contains(user))
        {
            return false;
        }
        data.add(user);
        return true;
    }
    public boolean contains(Map<String,Object> user)
    {
        for (Map<String,Object> u : data) {
        if(u.get("email").equals(user.get("email")))
            return true;
        }
        return false;
    }
    public Map<String,Object> search(Map<String,Object> user)
    {
        for (Map<String,Object> u : data) {
            if(u.get("email").equals(user.get("email")))
            {
                return u;
            }
            if(u.get("userId").equals(user.get("userId")))
            {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Map> getMap() {
        return data;
    }
}
