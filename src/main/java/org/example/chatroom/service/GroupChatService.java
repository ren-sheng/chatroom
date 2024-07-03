package org.example.chatroom.service;

import org.example.chatroom.dto.Group;
import org.example.chatroom.dto.Group_chat_history;
import org.example.chatroom.dto.User;

import java.util.Date;

public interface GroupChatService {
    /**
     * 群组号查询群组
     * @param id 群组号
     * @return 群组
     */
    Group getGroup(Integer id);

    /**
     * 获取群组列表
     *
     * @param id 用户信息
     * @return 群组列表
     */
    Group[] getGroups(Integer id);

    /**
     * 获取群组成员
     *
     * @param group_id 群组信息
     * @return 群组成员
     */
    User[] getGroupMembers(Integer group_id);

    /**
     * 获取群聊天历史记录
     *
     * @param group_id 群组信息
     * @return 群聊天历史记录
     */
    Group_chat_history[] getGroupHistory(Integer group_id);

    /**
     * 查询是否存在群组关系
     * @param id 用户id
     * @param group_id 群组id
     * @return 是否存在群组关系
     */
    boolean isGroupMember(Integer id, Integer group_id);

    /**
     * 添加群组
     * @param name 群组名称
     * @param owner 群主id
     * @param date 日期
     * @return 是否添加成功
     */
    Group addGroup(String name, Integer owner, Date date, Integer[] members);

    /**
     * 添加群组成员
     * @param group_id 群组id
     * @param user_id 用户id
     * @param date 日期
     * @return 是否添加成功
     */
    boolean addGroupMember(Integer group_id, Integer user_id,Date date);

    /**
     * 删除群组
     * @param group_id 群组id
     * @return 是否删除成功
     */
    boolean deleteGroup(Integer group_id);

    /**
     * 删除群组成员
     * @param group_id 群组id
     * @param user_id 用户id
     * @return 是否删除成功
     */
    boolean deleteGroupMember(Integer group_id, Integer user_id);
}
