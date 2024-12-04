package backend;

import java.util.ArrayList;

public class Profile {
    private ArrayList<String> friends;
    private String profilePhoto;
    private String coverPhoto;
    private String bio;
    private ArrayList<FriendRequest> friendRequests;
     private ArrayList<FriendRequest> friendRecievedRequests;
    private ArrayList<String> friendSuggestions;
   private ArrayList<Content> contents;
    private ArrayList<String> blockedUsers;

    public Profile() {
        this.friends = new ArrayList<>();
        this.contents=new ArrayList<>();
        this.friendRequests=new ArrayList<>();
        this.blockedUsers=new ArrayList<>();
        this.friendRecievedRequests=new ArrayList<>();
    }
    public Profile(ArrayList<String> friends, String profilePhoto, String bio, String coverPhoto, ArrayList<Content> contents, ArrayList<FriendRequest> friendRequests, ArrayList<FriendRequest> friendRecievedRequests, ArrayList<String> friendSuggestions, ArrayList<String> blockedUsers) {
        this.friends = friends;
        this.profilePhoto = profilePhoto;
        this.bio = bio;
        this.coverPhoto = coverPhoto;
        this.contents = contents;
        this.friendRequests = friendRequests;
        this.blockedUsers = blockedUsers;
        this.friendRecievedRequests = friendRecievedRequests;
        this.friendSuggestions = friendSuggestions;
    }

    public ArrayList<FriendRequest> getFriendRecievedRequests() {
        return friendRecievedRequests;
    }

    public void setFriendRecievedRequests(ArrayList<FriendRequest> friendRecievedRequests) {
        this.friendRecievedRequests = friendRecievedRequests;
    }

    public ArrayList<String> getFriendSuggestions() {
        return friendSuggestions;
    }

    public void setFriendSuggestions(ArrayList<String> friendSuggestions) {
        this.friendSuggestions = friendSuggestions;
    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }
    public void addFriendRequest(FriendRequest friendRequest) {
        this.friendRequests.add(friendRequest);
    }
    public void removeFriendRequest(FriendRequest friendRequest) {
        this.friendRequests.remove(friendRequest);
    }
    public void addFriendRecievedRequest(FriendRequest friendRequest) {
        this.friendRecievedRequests.add(friendRequest);
    }
    public void removeFriendRecievedRequest(FriendRequest friendRequest) {
        this.friendRecievedRequests.remove(friendRequest);
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
