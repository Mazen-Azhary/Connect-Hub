package backend;

import java.util.ArrayList;

public class Profile {
    private ArrayList<String> friends;
    private String profilePhoto;
    private String coverPhoto;
    private String bio;
    private ArrayList<String> friendRequests;
     private ArrayList<String> friendReceivedRequests;
    private ArrayList<String> friendSuggestions;
   private ArrayList<Content> contents;
    private ArrayList<String> blockedUsers;

    public Profile() {
        this.friends = new ArrayList<>();
        this.contents=new ArrayList<>();
        this.friendRequests=new ArrayList<>();
        this.blockedUsers=new ArrayList<>();
        this.friendReceivedRequests=new ArrayList<>();
        this.friendSuggestions=new ArrayList<>();
    }

    public ArrayList<String> getFriendReceivedRequests() {
        return friendReceivedRequests;
    }

    public void setFriendReceivedRequests(ArrayList<String> friendReceivedRequests) {
        this.friendReceivedRequests = friendReceivedRequests;
    }

    public Profile(ArrayList<String> friends, String profilePhoto, String bio, String coverPhoto, ArrayList<Content> contents, ArrayList<String> friendRequests, ArrayList<String> friendReceivedRequests, ArrayList<String> friendSuggestions, ArrayList<String> blockedUsers) {
        this.friends = friends;
        this.profilePhoto = profilePhoto;
        this.bio = bio;
        this.coverPhoto = coverPhoto;
        this.contents = contents;
        this.friendRequests = friendRequests;
        this.blockedUsers = blockedUsers;
        this.friendReceivedRequests = friendReceivedRequests;
        this.friendSuggestions = friendSuggestions;
    }

    public ArrayList<String> getFriendRecievedRequests() {
        return friendReceivedRequests;
    }

    public void setFriendRecievedRequests(ArrayList<String> friendRecievedRequests) {
        this.friendReceivedRequests = friendRecievedRequests;
    }

    public ArrayList<String> getFriendSuggestions() {
        return friendSuggestions;
    }

    public void setFriendSuggestions(ArrayList<String> friendSuggestions) {
        this.friendSuggestions = friendSuggestions;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
    public void addFriendRequest(String id) {
        this.friendRequests.add(id);
    }
    public void removeFriendRequest(String id) {
        this.friendRequests.remove(id);
    }
    public void addFriendRecievedRequest(String id) {
        this.friendReceivedRequests.add(id);
    }
    public void removeFriendRecievedRequest(String id) {
        this.friendReceivedRequests.remove(id);
    }
    public void addFriendSuggestion(String suggestion) {
        this.friendSuggestions.add(suggestion);
    }
    public void removeFriendSuggestion(String suggestion) {
        this.friendSuggestions.remove(suggestion);
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
            friends.remove(friend);
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

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void addBlockedUser(String blockedUser) {
        if (!blockedUsers.contains(blockedUser))
            blockedUsers.add(blockedUser);
    }

    public void removeBlockedUser(String blockedUser) {
        if (blockedUsers.contains(blockedUser))
            blockedUsers.remove(blockedUser);
    }
}
