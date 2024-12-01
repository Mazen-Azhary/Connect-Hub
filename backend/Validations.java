package backend;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    private final static String emailFormat="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+$";;
    public static boolean validateEmailFormat(String email)
    {
        if(email==null || email.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(emailFormat);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        //if returned false we should use message in the frontend
    }
}
