package org.example.chatroom.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.chatroom.dto.*;

@Mapper
public interface FriendChatMapper {
    //查询好友列表
    @Select("""
            select id, username, date
            from user
            where id in (select friend from friend where id = #{id})
            """)
    User[] getFriendList(User user);

    //查询好友聊天历史记录
    @Select("""
            select * from friend_chat_history
            where (sender = #{id} and receiver = #{friend}) or (sender = #{friend} and receiver = #{id})
            order by date
            """)
    Friend_chat_history[] getFriendHistory(Friend friend);

    //添加好友
    @Insert("""
            insert into friend(id, friend, date)
            values
            (#{id}, #{friend}, #{date}),
            (#{friend}, #{id}, #{date})
            """)
    void addFriend(Friend friend);

    //删除好友
    @Delete("""
            delete from friend
            where
            (id = #{id} and friend = #{friend}) or (id = #{friend} and friend = #{id})
            """)
    void deleteFriend(Friend friend);

    //删除所有聊天记录
    @Delete("""
            delete from friend_chat_history
            where (sender = #{id} and receiver = #{friend}) or (sender = #{friend} and receiver = #{id})
            """)
    void deleteFriendHistory(Friend friend);
}
