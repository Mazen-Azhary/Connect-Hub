package backend;

public class ProfileBuilder {
    private Profile profile;
    public ProfileBuilder() {
        profile = new Profile();
    }
    public ProfileBuilder(Profile profile) {
        this.profile = profile;
    }
    public void setPhoto(String profilePhoto) {
        profile.setProfilePhoto(profilePhoto);
    }
    public void setCover(String coverPhoto) {
        profile.setCoverPhoto(coverPhoto);
    }
    public void setBio(String bio) {
        profile.setBio(bio);
    }
    public Profile build() {
        return profile;
    }

}
