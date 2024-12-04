package backend;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Date;
import org.json.simple.parser.ParseException;

public class Signup {
    private static Signup instance;
    private int IDcounter=1; //helps us for the unique id
    private final String filePath = "src/database/Login.json";
    private final String profilePath = "src/database/Profile.json";
    private  LoginDatabase loginDatabase;
    private ProfileDataBase profileDataBase;
    private Signup() {

            loginDatabase=new LoginDatabase(filePath);
            profileDataBase=new ProfileDataBase(profilePath);
            try {
            IDcounter=loginDatabase.getData().size()+1;
            }
            catch (Exception e) {
            }
    }
    public static synchronized Signup getInstance() {
        if (instance == null) {
            instance = new Signup();
        }
        return instance;
    }
    public boolean signup(String userName, String email, String password, String gender, LocalDate dateOFBirth) throws NoSuchAlgorithmException {
        String id=IDcounter+"";
        System.out.println(id);
        User user=new User(id,email,userName,password,gender,dateOFBirth);
        if (loginDatabase.addData(user))
        {
            profileDataBase.addData(user);
            IDcounter++;
            return true;
        }
        else {
            return false;
        }
    }
    public void save()
    {
        loginDatabase.save();
        profileDataBase.save();
    }

}
