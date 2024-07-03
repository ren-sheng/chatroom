package org.example.chatroom.mapper;

import org.apache.ibatis.annotations.*;
import org.example.chatroom.dto.Group;
import org.example.chatroom.dto.Group_chat_history;
import org.example.chatroom.dto.Groupmember;
import org.example.chatroom.dto.User;

@Mapper
public interface GroupChatMapper {
    //查询群组
    @Select("""
            select *
            from `group`
            where id = #{id}
            """)
    Group getGroup(Group group);

    //查询是否存在群组关系
    @Select("""
            select * from groupmember
            where id = #{id} and groupid = #{groupid}
            """)
    Groupmember isGroupMember(Groupmember groupmember);


    //查询群组列表
    @Select("""
            select *
            from `group`
            where id in (select groupid from Groupmember where id = #{id})
            """)
    Group[] getGroupList(User user);

    //查询群组成员
    @Select("""
            select *
            from user
            where id in (select id from groupmember where groupid = #{id})
            """)
    User[] getGroupMembers(Group group);

    //查询群聊天历史记录
    @Select("""
            select * from group_chat_history
            where groupid = #{id}
            order by date
            """)
    Group_chat_history[] getGroupHistory(Group group);

    //添加群组
    @Insert("""
            insert into `group`(name, owner, number, date)
            values(#{name}, #{owner}, #{number}, #{date})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addGroup(Group group);

    //添加群组成员
    @Insert("""
            insert into groupmember(groupid, id, date)
            values(#{groupid}, #{id}, #{date})
            """)
    void addGroupMember(Groupmember groupmember);

    //群组人数加一
    @Update("""
            update `group`
            set number = number + 1
            where id = #{groupid}
            """)
    void addGroupNumber(Integer groupid);

    //群组人数减一
    @Update("""
            update `group`
            set number = number - 1
            where id = #{groupid}
            """)
    void reduceGroupNumber(Integer groupid);

    //删除群组
    @Delete("""
            delete from `group`
            where id = #{id}
            """)
    void deleteGroup(Group group);

    //删除群组成员
    @Delete("""
            delete from groupmember
            where groupid = #{groupid} and id = #{id}
            """)
    void deleteGroupMember(Groupmember groupmember);

    //删除所有群组成员
    @Delete("""
            delete from groupmember
            where groupid = #{id}
            """)
    void deleteAllGroupMember(Group group);

    //删除所有群聊天记录
    @Delete("""
            delete from group_chat_history
            where groupid = #{id}
            """)
    void deleteGroupHistory(Group group);
}
