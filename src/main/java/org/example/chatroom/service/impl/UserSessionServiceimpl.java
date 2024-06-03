package org.example.chatroom.service.impl;

import org.example.chatroom.service.UserSessionService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserSessionServiceimpl implements UserSessionService {
    /**
     * 用户id和会话id的映射
     */
    private final ConcurrentMap<String, String> userSessionMap = new ConcurrentHashMap<>();

    @Override
    public void addUserSession(String userId, String sessionId) {
        userSessionMap.put(userId, sessionId);
    }

    @Override
    public void removeUserSession(String userId) {
        userSessionMap.remove(userId);
    }

    @Override
    public String getSessionId(String userId) {
        return userSessionMap.get(userId);
    }


}
