package backend;

import java.time.LocalDateTime;

public class Notification {
    private String message;
    private String notificationId;
    private LocalDateTime time=LocalDateTime.now();
    private String relativeId;
    private String authorId;
    private NotificationType type;



    public Notification() {
    }


    public Notification(String message, String notificationId, LocalDateTime time, String relativeId, String authorId, NotificationType type) {
        this.message = message;
        this.notificationId = notificationId;
        this.time = time;
        this.relativeId = relativeId;
        this.authorId = authorId;
        this.type = type;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(String relativeId) {
        this.relativeId = relativeId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
