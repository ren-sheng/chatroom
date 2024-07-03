package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Result;
import org.example.chatroom.dto.User;
import org.example.chatroom.service.GroupChatService;
import org.example.chatroom.service.UserService;
import org.example.chatroom.utils.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private GroupChatService groupChatService;

    /**
     * 查询用户名
     *
     * @param userId 用户Id
     */
    @PostMapping("/queryUsername")
    public Result<?> queryUsername(String userId) {
        log.info("查询用户名：" + userId);
        User u = userService.queryUsername(userId);
        if (u != null) return Result.success(u);
        else return Result.error("用户不存在");
    }

    /**
     * 登录
     *
     * @param userID   用户id
     * @param password 用户密码
     * @return 响应数据
     */
    @PostMapping("/login")
    public Result<?> login(String userID, String password) {
        log.info("用户登录：" + userID);
        Integer id = Integer.parseInt(userID);
        User u = userService.login(id, password);
        if (u != null) {
            return Result.success(JWTUtil.GenTokens(u));
        } else return Result.error("用户名或密码错误");
    }

    /**
     * 注册id
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 响应数据
     */
    @PostMapping("/register")
    public Result<?> register(String username, String password, String headimg) {
        log.info("用户注册：" + username);
        User u = userService.register(username, password, headimg);
        if (u != null) {
            //自动加入默认群聊,获取日期
            groupChatService.addGroupMember(1, u.getId(), new java.sql.Date(System.currentTimeMillis()));
            return Result.success(u.getId());
        } else return Result.error("注册失败");
    }
}
