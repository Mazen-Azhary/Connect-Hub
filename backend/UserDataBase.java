package backend;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UserDataBase {
    private String fileName;
    private ArrayList<User> data;
    public UserDataBase(String fileName) throws IOException {
        this.fileName = fileName;
        data=new ArrayList<>(); //initialize the users
        readFromFile();
    }
    public void readFromFile()  {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try
        {
            data = mapper.readValue(new File(fileName),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        File file = new File(fileName);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
