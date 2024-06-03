package org.example.chatroom.service;

public interface UserSessionService {
    /**
     * 添加用户id和会话id的映射
     *
     * @param  userId 用户id
     * @param  sessionId 会话id
     */
    void addUserSession(String userId, String sessionId);

    /**
     * 移除用户id和会话id的映射
     *
     * @param  userId 用户id
     */
    void removeUserSession(String userId);

    /**
     * 获取用户id对应的会话id
     *
     * @param  userId 用户id
     * @return 会话id
     */
    String getSessionId(String userId);


}
