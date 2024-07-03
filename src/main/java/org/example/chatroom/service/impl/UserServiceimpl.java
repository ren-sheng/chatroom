package org.example.chatroom.service.impl;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.User;
import org.example.chatroom.mapper.UserMapper;
import org.example.chatroom.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Override
    public User queryUsername(String userId) {
        return userMapper.queryUserByUsername(userId);
    }

    @Override
    public User login(Integer id, String password) {
        return userMapper.queryUserByUsernameAndPassword(id, password);
    }

    @Override
    public User register(String username, String password,String headimg) {
        User u = new User(username,password);
        userMapper.saveUser(u);
        userMapper.setUserHeadimg(u.getId(),headimg);
        if(u.getId() != null) return u;
        else return null;
    }
}
