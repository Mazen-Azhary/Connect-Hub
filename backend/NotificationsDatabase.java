package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsDatabase extends Database {

    public NotificationsDatabase(String filename) {
        super(filename);
    }

    public String readString() {
        try {
            return new String(Files.readAllBytes(new File(fileName).toPath()));
        } catch (IOException e) {
            return null;
        }
    }
    public void save()
    {

    }

    public ArrayList<Map<String, Object>> parseNotifications(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, ArrayList.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void modifyNotificationById(Notification notification) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> notifications = parseNotifications(jsonString);
        if (notifications == null) return;

        for (Map<String, Object> notificationNode : notifications) {
            if (notificationNode.get("notificationId").equals(notification.getNotificationId())) {
                notificationNode.put("message", notification.getMessage());
                notificationNode.put("notificationId", notification.getNotificationId());
                notificationNode.put("time", notification.getTime());
                notificationNode.put("relativeId", notification.getRelativeId());
                notificationNode.put("authorId", notification.getAuthorId());
                notificationNode.put("type", notification.getType().name());
                break;
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(notifications);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }

    public void addNotification(Notification notification) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> notifications = parseNotifications(jsonString);
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        Map<String, Object> newNotification = parse(notification);
        notifications.add(newNotification);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(notifications);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }

    public Notification getNotification(String id) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> notifications = parseNotifications(jsonString);
        if (notifications == null) return null;

        ObjectMapper objectMapper = new ObjectMapper();
        for (Map<String, Object> notificationNode : notifications) {
            if (notificationNode.get("notificationId").equals(id)) {
                String notificationJson = objectMapper.writeValueAsString(notificationNode);
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(notificationJson, Notification.class);
            }
        }
        return null;
    }

    private Map<String, Object> parse(Notification notification) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", notification.getMessage());
        map.put("notificationId", notification.getNotificationId());
        map.put("time", notification.getTime());
        map.put("relativeId", notification.getRelativeId());
        map.put("authorId", notification.getAuthorId());
        map.put("type", notification.getType().name());
        return map;
    }

    public void writeUpdatedJSONToFile(String filePath, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString);
        writer.close();
    }

    public int getMax() throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> notifications = parseNotifications(jsonString);
        if (notifications == null) return 0;
        int max = 0;
        for (Map<String, Object> notificationNode : notifications) {
            if (Integer.parseInt((String) notificationNode.get("notificationId")) > max) {
                max = Integer.parseInt((String) notificationNode.get("notificationId"));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        NotificationsDatabase notificationsDatabase = new NotificationsDatabase("src/database/Notifications.json");

        Notification notification = new Notification();
        notification.setNotificationId("1");
        notification.setMessage("This is a test notification");
        notification.setRelativeId("1234");
        notification.setAuthorId("5678");
        notification.setType(NotificationType.POST);
        notification.setTime(LocalDateTime.now());

        try {
            notificationsDatabase.addNotification(notification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
