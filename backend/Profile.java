package backend;
import java.util.ArrayList;
public class Profile {
    private ArrayList<String> friends;
    private String profilePhoto;
    private String coverPhoto;
    private String bio;
    private ArrayList<FriendRequest> friendRequests;
   private ArrayList<Content> contents;
    public Profile()
    {
        this.friends = new ArrayList<>();
        this.contents=new ArrayList<>();
        this.friendRequests=new ArrayList<>();
    }

    public Profile(ArrayList<String> friends, String profilePhoto, String bio, String coverPhoto, ArrayList<Content> contents,ArrayList<FriendRequest> friendRequests) {
        this.friends = friends;
        this.profilePhoto = profilePhoto;
        this.bio = bio;
        this.coverPhoto = coverPhoto;
        this.contents = contents;
        this.friendRequests = friendRequests;
    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
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

    public void addFriend(String friend) {
        if (!friends.contains(friend))
            friends.add(friend);
    }

    public void removeFriend(String friend) {
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
