package backend;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Login {
    private static Login instance;
    private final String filePath = "src/database/Login.json";
    private final String contentPath="src/database/UserContents.json";
    private LoginDatabase loginDataBase;
    private UserContentDatabase userContentDatabase;
    private Login() throws IOException {
        loginDataBase = new LoginDatabase(filePath);
        userContentDatabase = new UserContentDatabase(contentPath);

    }
    public static synchronized Login getInstance() throws IOException {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }
    public boolean login(String email,String password) throws NoSuchAlgorithmException, IOException {
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
                User use=userContentDatabase.getUser(user.get("userId").toString());
                use.setStatus("online");
                userContentDatabase.modifyUserById(use);
                return true;
            }

        }
        return false;
    }
    public String save()
    {
        loginDataBase.save();
        return userID;
    }
}