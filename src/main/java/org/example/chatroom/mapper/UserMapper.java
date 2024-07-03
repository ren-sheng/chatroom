package org.example.chatroom.mapper;

import org.apache.ibatis.annotations.*;
import org.example.chatroom.dto.User;

@Mapper
public interface UserMapper {
    //根据用户Id查询用户
    @Select("select * from user where id=#{userId}")
    User queryUserByUsername(String userId);

    //根据id和密码查询用户
    @Select("select * from user where id=#{id} and password=#{password}")
    User queryUserByUsernameAndPassword(Integer id, String password);

    //添加用户
    @Insert("insert into user(username, password) values(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveUser(User user);

    //设置用户头像
    @Update("update user set headimg=#{headimg} where id=#{id}")
    void setUserHeadimg(Integer id, String headimg);

    //根据用户名修改密码
    @Update("update user set password=#{password} where id=#{id}")
    void updatePasswordByUsername(Integer id, String password);

    //根据用户名删除用户(仅限管理员使用)
    @Delete("delete from user where id=#{id}")
    void deleteUserByUsername(Integer id);
}
