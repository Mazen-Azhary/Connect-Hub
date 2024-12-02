package backend;
import java.util.ArrayList;
public class Profile {
    private ArrayList<User> friends;
    private String profilePhoto;
    private String coverPhoto;
    private String bio;
    ArrayList<Content> contents;
    public Profile()
    {
        this.friends = new ArrayList<>();
        this.contents=new ArrayList<>();
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

    public ArrayList<Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Content> contents) {
        this.contents = contents;
    }
    public void addContent(Content content) {
        contents.add(content);
    }
    public void removeContent(Content content) {
        contents.remove(content);
    }
}
