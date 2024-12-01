package backend;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {
    private final static String emailFormat="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+$";
    private final static String nameFormat = "^[A-Za-zÀ-ÿ][A-Za-zÀ-ÿ'\\- ]{1,48}[A-Za-zÀ-ÿ]$"; //allow only alphabets, spaces, hyphens, and apostrophes
    private final static String passwordFormat =  "^(?=.*[A-Za-z])(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
    //8 characters at least, one alphabet , one symbol
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
    public static boolean validateName(String name)
    {
        if(name==null || name.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(nameFormat);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean validatePassword(String password)
    {
        if(password==null || password.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(passwordFormat);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
