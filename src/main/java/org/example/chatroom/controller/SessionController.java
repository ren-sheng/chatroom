package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Result;
import org.example.chatroom.service.UserSessionService;
import org.example.chatroom.service.WebSocketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Resource
    private UserSessionService userSessionService;

    /**
     * 获取在线用户
     * @return 在线用户
     */
    @RequestMapping("/getOnlineUsers")
    public Result<?> getOnlineUsers() {
        return Result.success(userSessionService.getOnlineUsers());
    }
}
