package backend;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Login {
    private static Login instance;
    private final String filePath = "src/database/Users.json";
    private  UserDataBase userDataBase;
    private Login() throws IOException {
        userDataBase=new UserDataBase(filePath);
    }
    public static synchronized Login getInstance() throws IOException {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }
    public boolean login(String email,String password) throws NoSuchAlgorithmException {
        //checking from the data base
        //hashing the password
        if(userDataBase.contains(email))
        {
            User user=userDataBase.search(email);
            byte[] salt = user.getSalt();
            if(PasswordHasher.hashedPassword(password,salt).equals(user.getHashedPassword()))
            {
                user.setStatus("online");
                return true;
            }

        }
        return false;
    }
    public void save()
    {
        userDataBase.save();
    }
}
