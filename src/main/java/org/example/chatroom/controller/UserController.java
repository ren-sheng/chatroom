package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Result;
import org.example.chatroom.dto.User;
import org.example.chatroom.service.UserService;
import org.example.chatroom.utils.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     * @param id 用户id
     * @param password 用户密码
     * @return 响应数据
     */
    @PostMapping("/login")
    public Result<?> login(Integer id, String password) {
        User u = userService.login(id, password);
        if (u != null) {
            return Result.success(JWTUtil.GenTokens(u));
        } else return Result.error("用户名或密码错误");
    }

    /**
     * 注册
     * @param username 用户名
     * @param password 用户密码
     * @return 响应数据
     */
    @PostMapping("/register")
    public Result<?> register(String username, String password) {
        User u = userService.register(username, password);
        if (u != null) return Result.success(u.getId());
        else return Result.error("注册失败");
    }
}
