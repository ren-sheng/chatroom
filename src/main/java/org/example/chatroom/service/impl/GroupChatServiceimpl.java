package org.example.chatroom.service.impl;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Group;
import org.example.chatroom.dto.Group_chat_history;
import org.example.chatroom.dto.Groupmember;
import org.example.chatroom.dto.User;
import org.example.chatroom.mapper.GroupChatMapper;
import org.example.chatroom.service.GroupChatService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GroupChatServiceimpl implements GroupChatService {
    @Resource
    GroupChatMapper chatMapper;

    @Override
    public Group[] getGroups(Integer id) {
        User user = new User();
        user.setId(id);
        return chatMapper.getGroupList(user);
    }

    @Override
    public User[] getGroupMembers(Integer group_id) {
        Group group = new Group();
        group.setId(group_id);
        return chatMapper.getGroupMembers(group);
    }

    @Override
    public Group_chat_history[] getGroupHistory(Integer group_id) {
        Group group = new Group();
        group.setId(group_id);
        return chatMapper.getGroupHistory(group);
    }

    @Override
    public Group addGroup(String name, Integer owner, Date date, Integer[] members) {
        Group group = new Group();
        group.setName(name);
        group.setOwner(owner);
        group.setNumber(0);
        group.setDate(date);
        try {
            chatMapper.addGroup(group);
            for (Integer member : members) {
                addGroupMember(group.getId(), member, date);
            }
            group.setNumber(members.length);
            return group;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean addGroupMember(Integer group_id, Integer user_id, Date date) {
        Groupmember groupmember = new Groupmember();
        groupmember.setGroupid(group_id);
        groupmember.setId(user_id);
        groupmember.setDate(date);
        try {
            chatMapper.addGroupNumber(group_id);
            chatMapper.addGroupMember(groupmember);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteGroup(Integer group_id) {
        Group group = new Group();
        group.setId(group_id);
        try {
            chatMapper.deleteAllGroupMember(group);
            chatMapper.deleteGroupHistory(group);
            chatMapper.deleteGroup(group);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteGroupMember(Integer group_id, Integer user_id) {
        Groupmember groupmember = new Groupmember();
        groupmember.setGroupid(group_id);
        groupmember.setId(user_id);
        try {
            chatMapper.reduceGroupNumber(group_id);
            chatMapper.deleteGroupMember(groupmember);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
