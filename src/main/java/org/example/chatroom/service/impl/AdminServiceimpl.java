package org.example.chatroom.service.impl;

import jakarta.annotation.Resource;
import org.example.chatroom.mapper.AdminMapper;
import org.example.chatroom.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceimpl implements AdminService {
    @Resource
    AdminMapper adminMapper;

    @Override
    public boolean adminLogin(String username, String password) {
        return adminMapper.queryAdmin(username, password) != null;
    }
}
