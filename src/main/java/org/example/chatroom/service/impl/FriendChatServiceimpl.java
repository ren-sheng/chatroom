package org.example.chatroom.service.impl;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Friend;
import org.example.chatroom.dto.Friend_chat_history;
import org.example.chatroom.dto.User;
import org.example.chatroom.mapper.FriendChatMapper;
import org.example.chatroom.service.FriendChatService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FriendChatServiceimpl implements FriendChatService {
    @Resource
    FriendChatMapper chatMapper;

    @Override
    public User[] getFriends(Integer id) {
        User user = new User();
        user.setId(id);
        return chatMapper.getFriendList(user);
    }

    @Override
    public Friend_chat_history[] getFriendHistory(Integer id, Integer friendId) {
        Friend friend = new Friend();
        friend.setId(id);
        friend.setFriend(friendId);
        return chatMapper.getFriendHistory(friend);
    }

    @Override
    public boolean addFriend(Integer id, Integer friendId, Date date) {
        Friend friend = new Friend();
        friend.setId(id);
        friend.setFriend(friendId);
        friend.setDate(date);
        try {
            chatMapper.addFriend(friend);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteFriend(Integer id, Integer friendId) {
        Friend friend = new Friend();
        friend.setId(id);
        friend.setFriend(friendId);
        try {
            chatMapper.deleteFriend(friend);
            chatMapper.deleteFriendHistory(friend);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}