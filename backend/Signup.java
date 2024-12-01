package backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Signup {
    
    
    
    public static void main(String[] args) throws IOException{
    
       if(Signup("user", "email", "", "male", new Date())){
       
           System.out.println("succesful");
       }
    
    
    }

    private static int IDcounter = 1; //helps us for the unique id
    private static final String filePath = "src/database/Users.json";

    public static boolean Signup(String userName, String email, String password, String gender, Date dateOFBirth) throws IOException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException ex) {
            System.out.println("file doesn't exist or wrong path");
            return false;
        }
        JSONParser FileToJsonOBJ = new JSONParser();
        
            JSONArray usersArray;
        try {
            
            Object obj = FileToJsonOBJ.parse(fileReader);
            usersArray = (JSONArray) obj;
        } catch (ParseException ex) {
            
            System.out.println("error in parsing file to json obj");
            return false;
        }
        finally{
        fileReader.close();
        }
        
        //add validations here when implemented 
        
        JSONObject newUser = new JSONObject();
        newUser.put("id",IDcounter);
        newUser.put("username", userName);
        newUser.put("email", email);
        //hash password then uncomment this line
        //newUser.put("password", password);
        newUser.put("gender", gender);
        newUser.put("DOB", dateOFBirth);
        JSONArray friends = new JSONArray();//empty friend list
        newUser.put("friends", friends);
        usersArray.add(newUser);
        
         try (FileWriter file = new FileWriter(filePath)) {
                file.write(usersArray.toJSONString());
                file.flush();//forces data from buffer to file 
            }
         
        IDcounter++;//auto increment
        return true;
    }

}
