package backend;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class User {
    private String userID;
    private String email;
    private String username;
    private String hashedPassword;
    private byte[] salt;
    private String gender;
    private LocalDate dateOfBirth;
    private String status;
    private ArrayList<User> friends;
    private String profilePhoto;
    private String coverPhoto;
    private String bio;
    public User(String userID, String email, String username, String password,String gender, LocalDate dateOfBirth) throws NoSuchAlgorithmException {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.salt=PasswordHasher.salt();
        this.hashedPassword=PasswordHasher.hashedPassword(password,salt);
        this.dateOfBirth = dateOfBirth;
        this.gender=gender;
        this.friends = new ArrayList<>();
    }
    public User(String userID, String email, String username,String gender, LocalDate dateOfBirth) {
        this.userID = userID;
        this.email = email;
        this.username = username;
        this.salt=PasswordHasher.salt();
        this.dateOfBirth = dateOfBirth;
        this.gender=gender;
        this.friends = new ArrayList<>();
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



    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void addFriend(User friend) {
        if (!friends.contains(friend))
            friends.add(friend);
    }

    public void removeFriend(User friend) {
        if (friends.contains(friend))
            friends.add(friend);
    }
}
