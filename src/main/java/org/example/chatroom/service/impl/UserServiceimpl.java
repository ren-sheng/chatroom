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
    public User login(Integer id, String password) {
        return userMapper.queryUserByUsernameAndPassword(id, password);
    }

    @Override
    public User register(String username, String password) {
        User u = new User(username,password);
        userMapper.saveUser(u);
        if(u.getId() != null) return u;
        else return null;
    }
}
