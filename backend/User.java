package backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class User {
    private String userID;
    private String email;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String status;
    private String hashedPassword;
    private byte[] salt;
    @JsonIgnore
    private String gender;
    @JsonIgnore
    private LocalDate dateOfBirth=LocalDate.now();
    @JsonIgnore
    private Profile profile;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public User()
    {

    }

    public User(String userID, String email, String username, String password, String gender, LocalDate dateOfBirth) throws NoSuchAlgorithmException {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.salt=PasswordHasher.salt();
        this.hashedPassword=PasswordHasher.hashedPassword(password,salt);
        this.dateOfBirth = dateOfBirth;
        this.gender=gender;
        ProfileBuilder profileBuilder=new ProfileBuilder();
        this.profile=profileBuilder.build();
        this.status="offline";
    }

    public User(String userID, String email, String username, String status, String hashedPassword, byte[] salt, String gender, LocalDate dateOfBirth, Profile profile) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.status = status;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.profile = profile;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
