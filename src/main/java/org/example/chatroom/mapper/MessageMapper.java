package org.example.chatroom.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.chatroom.dto.Friend_chat_history;
import org.example.chatroom.dto.Group_chat_history;

@Mapper
public interface MessageMapper {
    //插入好友聊天历史记录
    @Insert("""
            insert into friend_chat_history(sender, receiver, content, date)
            values(#{sender}, #{receiver}, #{content}, #{date})
            """)
    void insertHistory(Friend_chat_history friend_chat_history);

    //插入群聊天历史记录
    @Insert("""
            insert into group_chat_history(sender, groupid, content, date)
            values(#{sender}, #{groupid}, #{content}, #{date})
            """)
    void insertGroupHistory(Group_chat_history group_chat_history);
}
