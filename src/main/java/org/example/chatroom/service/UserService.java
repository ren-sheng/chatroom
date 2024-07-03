package org.example.chatroom.service;

import org.example.chatroom.dto.User;

public interface UserService {
    /**
     * 查询用户名
     * @param userId 用户名
     */
    User queryUsername(String userId);

    /**
     * 登录
     *
     * @param id 用户id
     * @param password 用户密码
     * @return 用户信息
     */
    User login(Integer id, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户信息
     */
    User register(String username, String password, String headimg);
}
