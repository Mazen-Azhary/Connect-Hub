package backend;

public class NotificationManager {
    NotificationsDatabase notificationsDatabase=new NotificationsDatabase("src/database/Notifications.json");
    UserNotificationsDatabase userNotificationsDatabase=new UserNotificationsDatabase("src/database/UserNotifications.json");
    public static NotificationManager instance;
    private NotificationManager() {

    }
    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }
    public void addRequest(String sender,String receiver)
    {
    }
}
