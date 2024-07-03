package org.example.chatroom.service;

import org.example.chatroom.dto.User;

import java.util.Set;

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
     * 通过会话id移除用户id和会话id的映射
     * @param sessionId 会话id
     */
    void removeUserSessionBySessionId(String sessionId);


    /**
     * 获取用户id对应的会话id
     *
     * @param  userId 用户id
     * @return 会话id
     */
    String getSessionId(String userId);
    /**
     * 查询在线的用户
     * @return 在线用户id
     */
    User[] getOnlineUsers();


    /**
     * 添加目标id和文件id的映射
     *
     * @param  targetId 目标id
     * @param  fileId 文件id
     */
    void addTargetFile(String targetId, String fileId);

    /**
     * 移除目标id和文件id的映射
     *
     * @param  targetId 目标id
     */
    void removeTargetFile(String targetId);

    /**
     * 获取目标id对应的文件id
     *
     * @param  targetId 目标id
     * @return 文件id
     */
    String getFileId(String targetId);
}
