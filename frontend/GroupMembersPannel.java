package frontend;

import backend.GroupManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GroupMembersPannel extends JPanel{
    private GroupManager groupManager=GroupManager.getInstance();
    private String userID;
    private String groupId;
    private GroupPage groupPage;
    public GroupMembersPannel(GroupPage groupPage,String userID,String groupId,boolean admin) throws IOException {
        this.userID = userID;
        this.groupPage=groupPage;
        this.groupId=groupId;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if(admin)
        {
            ArrayList<String> admins=groupManager.getAdmins(groupId);
            for (int i = 0; i <admins.size(); i++) {
                MembersFrontend member= new MembersFrontend(userID,groupId,admins.get(i),this.groupPage,admin);
                this.add(member);
                this.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            return;
        }
        ArrayList<String> members=groupManager.getMembers(groupId);
        for (int i = 0; i <members.size(); i++) {
            MembersFrontend member= new MembersFrontend(userID,groupId,members.get(i),this.groupPage,admin);
            this.add(member);
            this.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

}
