package backend;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class NotificationManager {
    private NotificationsDatabase notificationsDatabase = new NotificationsDatabase("src/database/Notifications.json");
    private UserNotificationsDatabase userNotificationsDatabase = new UserNotificationsDatabase("src/database/UserNotifications.json");
    private GroupsDatabase groupsDatabase = new GroupsDatabase("src/database/Groups.json");
    private UserGroupsDatabase userGroupsDatabase = new UserGroupsDatabase("src/database/UserGroups.json");
    private ContentDatabase contentDatabase = new ContentDatabase("src/database/Contents.json");
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
            User user = userNotificationsDatabase.getUser(userId);
            user.getProfile().addNotification(notification);
            userNotificationsDatabase.modifyUserNotificationsById(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequest(String sender, String receiver) throws IOException {
        String message = userGroupsDatabase.getUser(sender).getUsername()+" sent you a friend request";
        createNotification(message,sender,null,NotificationType.REQUEST,receiver);
    }
    public void acceptRequest(String sender, String receiver) throws IOException {
        String message = userGroupsDatabase.getUser(sender).getUsername()+" Accepted your friend request";
        createNotification(message,sender,null,NotificationType.ACCEPTED,receiver);
    }
    public void addPost(String userId,String groupId,String contentId) throws IOException {
        String message=userGroupsDatabase.getUser(userId).getUsername()+" added a post to "+ groupsDatabase.getGroup(groupId).getName();
        ArrayList<String> members=GroupManager.getInstance().getAllMembers(groupId);
        for (String member:members) {
            if(userGroupsDatabase.getUser(member).getUserID().equals(userId)) {
                continue;
            }
            createNotification(message,groupId,userId,NotificationType.POST,member);
        }
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
    public ArrayList<Notification> getNotifications(String userId) throws IOException {
        User user = userNotificationsDatabase.getUser(userId);
        return user.getProfile().getNotifications();
    }
    public void removeNotification(String userId,String recieverId) throws IOException {
        User user = userNotificationsDatabase.getUser(userId);
        ArrayList<Notification> notifications = user.getProfile().getNotifications();
        Notification notification=null;
        for(Notification noti:notifications) {
            if(noti.getRelativeId().equals(recieverId)&&noti.getType().equals(NotificationType.REQUEST)) {
                notification=noti;
                break;
            }
        }
        notifications.remove(notification);
        userNotificationsDatabase.modifyUserNotificationsById(user);
    }
}
