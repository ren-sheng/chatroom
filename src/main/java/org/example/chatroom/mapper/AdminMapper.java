package org.example.chatroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.chatroom.dto.Admin;

@Mapper
public interface AdminMapper {
    //查询管理员
    @Select("select * from admin where id=#{id} and password=#{password}")
    Admin queryAdmin(String id, String password);
}
