package org.example.chatroom.service;

import java.util.Date;

public interface MessageService {
    /**
     * 添加好友聊天记录
     * @param userId 用户id
     * @param friendId 好友id
     * @param content 聊天内容
     * @param date 日期
     */
    void addFriendChatHistory(Integer userId, Integer friendId, String content, Date date, Integer type);

    /**
     * 添加群聊天记录
     * @param userId 用户id
     * @param groupId 群组id
     * @param content 聊天内容
     * @param date 日期
     */
    void addGroupChatHistory(Integer userId, Integer groupId, String content,Date date, Integer type, String username, String headimg);
}
