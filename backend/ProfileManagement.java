package backend;

import java.util.ArrayList;

public class ProfileManagement {
    private ArrayList<User> friends;
    private String profilePhoto;
    private String coverPhoto;
    private String bio;

    public ProfileManagement(String profilePhoto, String coverPhoto, String bio) {
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.bio = bio;
        this.friends = new ArrayList<>();
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
