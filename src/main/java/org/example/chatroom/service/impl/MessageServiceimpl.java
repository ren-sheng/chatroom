package org.example.chatroom.service.impl;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Friend_chat_history;
import org.example.chatroom.dto.Group_chat_history;
import org.example.chatroom.mapper.MessageMapper;
import org.example.chatroom.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceimpl implements MessageService {
    @Resource
    MessageMapper messageMapper;

    @Override
    public void addFriendChatHistory(Integer userId, Integer friendId, String content, Date date, Integer type) {
        Friend_chat_history friend_chat_history = new Friend_chat_history();
        friend_chat_history.setSender(userId);
        friend_chat_history.setReceiver(friendId);
        friend_chat_history.setContent(content);
        friend_chat_history.setDate(date);
        friend_chat_history.setType(type);
        messageMapper.insertHistory(friend_chat_history);
    }

    @Override
    public void addGroupChatHistory(Integer userId, Integer groupId, String content, Date date, Integer type, String username, String headimg) {
        Group_chat_history group_chat_history = new Group_chat_history();
        group_chat_history.setSender(userId);
        group_chat_history.setGroupid(groupId);
        group_chat_history.setContent(content);
        group_chat_history.setDate(date);
        group_chat_history.setType(type);
        group_chat_history.setUsername(username);
        group_chat_history.setHeadimg(headimg);
        messageMapper.insertGroupHistory(group_chat_history);
    }
}
