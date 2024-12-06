package backend;

import java.util.ArrayList;

public abstract class Database {
    protected final String fileName;
    protected ArrayList<User> data;
    public Database(String fileName) {
        this.fileName = fileName;
        data = new ArrayList<>();
    }

    public void readFromFile()  {
        DatabaseRead databaseRead=DatabaseRead.getInstance();
        data=databaseRead.read(fileName,User.class);
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
    public abstract void save();

}
