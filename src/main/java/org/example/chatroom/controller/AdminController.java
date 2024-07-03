package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.chatroom.dto.Result;
import org.example.chatroom.dto.User;
import org.example.chatroom.service.AdminService;
import org.example.chatroom.service.UserSessionService;
import org.example.chatroom.service.WebSocketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    //指定注入WebSocketService
    @Resource(name = "webSocket")
    private WebSocketService webSocket;
    @Resource
    private UserSessionService userSessionService;

    //管理员登录
    @PostMapping("/login")
    public Result<?> adminLogin(String username, String password) {
        log.info("管理员登录：" + username + " " + password);
        if(adminService.adminLogin(username, password)) {
            log.info("登录成功");
            return Result.success(true);
        } else {
            return Result.error("登录失败");
        }
    }

    //中断指定连接
    @PostMapping("/disconnect")
    public Result<?> disconnect(String userId) {
        try {
            String sessionId = userSessionService.getSessionId(userId);
            webSocket.closeSession(sessionId);
        } catch (Exception e) {
            return Result.error("中断失败");
        }
        //中断成功则移除用户的session映射
        userSessionService.removeUserSession(userId);
        return Result.success("中断成功");
    }

    //广播消息
    @PostMapping("/broadcast")
    public Result<?> broadcast(String message) {
        try {
            //处理广播消息，封装为json格式，包括一个warning字段
            message = "{\"warning\":\"系统消息\",\"content\":\"" + message + "\"}";
            User[] users = userSessionService.getOnlineUsers();
            for (User user : users) {
                //向每个用户发送消息
                log.info("广播消息：" + user.getId() + " " + message);
                webSocket.sendMessage(userSessionService.getSessionId(String.valueOf(user.getId())), message);
            }
        } catch (Exception e) {
            return Result.error("广播失败");
        }
        return Result.success("广播成功");
    }
}
