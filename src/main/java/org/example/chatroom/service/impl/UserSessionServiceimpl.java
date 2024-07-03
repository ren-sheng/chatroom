package org.example.chatroom.service.impl;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.User;
import org.example.chatroom.mapper.UserMapper;
import org.example.chatroom.service.UserSessionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserSessionServiceimpl implements UserSessionService {
    @Resource
    UserMapper userMapper;
    /**
     * 用户id和会话id的映射
     */
    private static final ConcurrentMap<String, String> userSessionMap = new ConcurrentHashMap<>();

    /**
     * 目标id和文件id的映射
     */
    private static final ConcurrentMap<String, String> targetFileMap = new ConcurrentHashMap<>();

    @Override
    public void addUserSession(String userId, String sessionId) {
        userSessionMap.put(userId, sessionId);
    }

    @Override
    public User[] getOnlineUsers() {
        Set<String> userIds = userSessionMap.keySet();
        User[] users = new User[userIds.size()];
        int i = 0;
        for (String userId : userIds) {
            // 通过用户id查询用户信息，使用userMapper
            users[i] = userMapper.queryUserByUsername(userId);
            i++;
        }
        return users;
    }

    @Override
    public void removeUserSession(String userId) {
        userSessionMap.remove(userId);
    }

    @Override
    public void removeUserSessionBySessionId(String sessionId) {
        userSessionMap.entrySet().removeIf(entry -> entry.getValue().equals(sessionId));
    }

    @Override
    public String getSessionId(String userId) {
        return userSessionMap.get(userId);
    }

    @Override
    public void addTargetFile(String targetId, String fileId) {
        targetFileMap.put(targetId, fileId);
    }

    @Override
    public void removeTargetFile(String targetId) {
        targetFileMap.remove(targetId);
    }

    @Override
    public String getFileId(String targetId) {
        return targetFileMap.get(targetId);
    }
}
