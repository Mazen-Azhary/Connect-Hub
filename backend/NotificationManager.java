package backend;

import java.io.IOException;
import java.time.LocalDateTime;

public class NotificationManager {
    private NotificationsDatabase notificationsDatabase = new NotificationsDatabase("src/database/Notifications.json");
    private UserNotificationsDatabase userNotificationsDatabase = new UserNotificationsDatabase("src/database/UserNotifications.json");
    private GroupsDatabase groupsDatabase = new GroupsDatabase("src/database/Groups.json");
    private UserGroupsDatabase userGroupsDatabase = new UserGroupsDatabase("src/database/UserGroups.json");
    public static NotificationManager instance;

    private NotificationManager() {

    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }


    public void createNotification(String message, String relativeId, String authorId, NotificationType type, String userId) {
        Notification notification = null;
        try {
            int notificationId = notificationsDatabase.getMax() + 1;
            notification = new Notification(message, Integer.toString(notificationId), LocalDateTime.now(), relativeId, authorId, type);
            notificationsDatabase.addNotification(notification);
            User user = userGroupsDatabase.getUser(userId);
            user.getProfile().addNotification(notification);
            userNotificationsDatabase.modifyUserNotificationsById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequest(String sender, String receiver) {
    }

    public void promotion(String groupId, String userId) throws IOException {
        String message = groupsDatabase.getGroup(groupId).getName() + " group: You have been promoted to " + groupsDatabase.getGroup(groupId).getMembers().get(userId);
        createNotification(message, groupId, null, NotificationType.PROMOTION, userId);
    }

    public void demotion(String groupId, String userId) throws IOException {
        String message = groupsDatabase.getGroup(groupId).getName() + " group: You have been demoted to " + groupsDatabase.getGroup(groupId).getMembers().get(userId);
        createNotification(message, groupId, null, NotificationType.DEMOTION, userId);
    }

    public void additionToGroup(String groupId, String userId) throws IOException {
        String message = groupsDatabase.getGroup(groupId).getName() + " group: You have been added to the group";
        createNotification(message, groupId, null, NotificationType.ADDING, userId);
    }
}
