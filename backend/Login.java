package backend;

import java.security.NoSuchAlgorithmException;

public class Login {
    private static Login instance;
    private final String filePath = "src/database/Users.json";
    private  UserDataBase userDataBase;
    private Login(){

    }
    public static synchronized Login getInstance() {
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
                return true;
            }

        }
        return false;
    }
}
