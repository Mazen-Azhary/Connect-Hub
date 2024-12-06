package backend;

import java.io.IOException;

public class ProfileManager {
    private static ProfileManager instance;
    private ProfileDataBase profileDataBase = new ProfileDataBase("src/database/Profile.json");
    private ProfileManager()
    {
    }
    public static synchronized ProfileManager getInstance()
    {
        if (instance == null) instance = new ProfileManager();
        return instance;
    }
    public User getUser(String id)
    {
        try {
            return profileDataBase.getUser(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editPhoto(String id,String imagePath)
    {
        User user = getUser(id);
        ProfileBuilder builder = new ProfileBuilder(user.getProfile());
        builder.setPhoto(imagePath);
        try {
            profileDataBase.modifyUserById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editCover(String id,String imagePath)
    {
        User user = getUser(id);
        ProfileBuilder builder = new ProfileBuilder(user.getProfile());
        builder.setCover(imagePath);
        try {
            profileDataBase.modifyUserById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void editBio(String id,String bio)
    {
        User user = getUser(id);
        ProfileBuilder builder = new ProfileBuilder(user.getProfile());
        builder.setBio(bio);
        try {
            profileDataBase.modifyUserById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
