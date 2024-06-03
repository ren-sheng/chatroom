package org.example.chatroom.service;

import org.example.chatroom.dto.*;

import java.util.Date;

public interface FriendChatService {
    /**
     * 获取好友列表
     *
     * @param id 用户id
     * @return 好友列表
     */
    User[] getFriends(Integer id);



    /**
     * 获取好友聊天历史记录
     *
     * @param id 用户id
     * @param friendId 好友id
     * @return 好友聊天历史记录
     */
    Friend_chat_history[] getFriendHistory(Integer id, Integer friendId);

    /**
     * 添加好友
     * @param id 用户id
     * @param friendId 好友id
     * @param date 日期
     * @return 是否添加成功
     */
    boolean addFriend(Integer id, Integer friendId, Date date);

    /**
     * 删除好友
     * @param id 用户id
     * @param friendId 好友id
     * @return 是否删除成功
     */
    boolean deleteFriend(Integer id, Integer friendId);

}
