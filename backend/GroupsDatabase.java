package backend;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupsDatabase extends Database {

    public GroupsDatabase(String filename) {
        super(filename);
    }

    public String readString() {
        try {
            return new String(Files.readAllBytes(new File(fileName).toPath()));
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<Map<String, Object>> parseGroups(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, ArrayList.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void modifyGroupById(Group group) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> groups = parseGroups(jsonString);
        if (groups == null) return;

        for (Map<String, Object> groupNode : groups) {
            if (groupNode.get("groupId").equals(group.getGroupId())) {
                groupNode.put("name", group.getName());
                groupNode.put("description", group.getDescription());
                groupNode.put("photo", group.getPhoto());

                Map<String, Object> membersMap = new HashMap<>();
                for (Map.Entry<String, GroupRole> entry : group.getMembers().entrySet()) {
                    membersMap.put(entry.getKey(), entry.getValue().name());
                }
                groupNode.put("members", membersMap);

                ArrayList<Map<String, Object>> postsList = new ArrayList<>();
                for (Content post : group.getPosts()) {
                    postsList.add(parseContent(post));
                }
                groupNode.put("posts", postsList);
                break;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groups);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }

    private Map<String, Object> parseContent(Content content) {
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("contentId", content.getContentId());
        contentMap.put("authorId", content.getAuthorId());
        contentMap.put("timestamp", content.getTimestamp());
        contentMap.put("image", content.getImage());
        contentMap.put("content", content.getContent());
        return contentMap;
    }

    public void addGroup(Group group) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> groups = parseGroups(jsonString);
        if (groups == null) {
            groups = new ArrayList<>();
        }
        Map<String, Object> newGroup = parse(group);
        groups.add(newGroup);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(groups);
        writeUpdatedJSONToFile(fileName, updatedJsonString);
    }

    public Group getGroup(String id) throws IOException {
        String jsonString = readString();
        ArrayList<Map<String, Object>> groups = parseGroups(jsonString);
        if (groups == null) return null;
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map<String, Object> groupNode : groups) {
            if (groupNode.get("groupId").equals(id)) {
                String groupJson = objectMapper.writeValueAsString(groupNode);
                objectMapper.registerModule(new JavaTimeModule());
                return objectMapper.readValue(groupJson, Group.class);
            }
        }
        return null;
    }

    private Map<String, Object> parse(Group group) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", group.getGroupId());
        map.put("name", group.getName());
        map.put("description", group.getDescription());
        map.put("photo", group.getPhoto());

        Map<String, Object> membersMap = new HashMap<>();
        for (Map.Entry<String, GroupRole> entry : group.getMembers().entrySet()) {
            membersMap.put(entry.getKey(), entry.getValue().name());
        }
        map.put("members", membersMap);

        ArrayList<Map<String, Object>> postsList = new ArrayList<>();
        for (Content post : group.getPosts()) {
            postsList.add(parseContent(post));
        }
        map.put("posts", postsList);

        return map;
    }

    public void writeUpdatedJSONToFile(String filePath, String jsonString) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(jsonString);
        writer.close();
    }
    public void save()
    {

    }

    public static void main(String[] args) throws IOException {
        GroupsDatabase groupsDatabase = new GroupsDatabase("src/database/Groups.json");
//        Group group = new Group();
//        group.setGroupId("group1");
//        group.setName("Tech Enthusiasts");
//        group.setDescription("A group for tech lovers");
//        group.setPhoto("group1.jpg");
//        group.setMembers(new HashMap<>());
//        group.getMembers().put("1", GroupRole.ADMIN);
//        group.getMembers().put("2", GroupRole.MEMBER);
//        group.setPosts(new ArrayList<>());
//
//        Content content = new Content();
//        content.setContentId(1);
//        content.setAuthorId(123);
//        content.setContent("Welcome to the group!");
//        content.setImage("welcome.jpg");
//        content.setTimestamp(LocalDateTime.now());
//        group.getPosts().add(content);
//        groupsDatabase.addGroup(group);
        Group grp =groupsDatabase.getGroup("group1");
        Map<String,GroupRole> map=grp.getMembers();
        System.out.println(map.get("1").name());

    }
}