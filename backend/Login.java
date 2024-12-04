package backend;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private static Login instance;
    private final String filePath = "src/database/Login.json";
    private LoginDatabase loginDataBase;
    private Login() throws IOException {
        loginDataBase = new LoginDatabase(filePath);

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
        Map<String,Object> loginData = new HashMap<>();
        loginData.put("email",email);
        if(loginDataBase.contains(loginData))
        {
            Map<String,Object> user=loginDataBase.search(loginData);
            byte[] salt = Base64.getDecoder().decode(user.get("salt").toString());
            if(PasswordHasher.hashedPassword(password,salt).equals(user.get("hashedPassword").toString()))
            {
                //don't forget the status
                return true;
            }

        }
        return false;
    }
    public void save()
    {
        loginDataBase.save();
    }
}
