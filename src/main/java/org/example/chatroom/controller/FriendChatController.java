package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Result;
import org.example.chatroom.service.FriendChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/friendchat")
public class FriendChatController {
    @Resource
    private FriendChatService chatService;

    /**
     * 获取用户的好友
     * @param id 用户id
     * @return 响应数据
     */
    @PostMapping("/getFriends")
    public Result<?> getFriends(Integer id) {
        return Result.success(chatService.getFriends(id));
    }

    /**
     * 获取好友历史记录
     * @param id 用户id
     * @param friendId 好友id
     * @return 响应数据
     */
    @PostMapping("/getFriendHistory")
    public Result<?> getFriendHistory(Integer id, Integer friendId) {
        return Result.success(chatService.getFriendHistory(id, friendId));
    }

    /**
     * 添加好友
     * @param id 用户id
     * @param friendId 好友id
     * @param date 添加时间
     * @return 响应数据
     */
    @PostMapping("/addFriend")
    public Result<?> addFriend(Integer id, Integer friendId, Date date) {
        return Result.success(chatService.addFriend(id, friendId, date));
    }

    /**
     * 删除好友
     * @param id 用户id
     * @param friendId 好友id
     * @return 响应数据
     */
    @PostMapping("/deleteFriend")
    public Result<?> deleteFriend(Integer id, Integer friendId) {
        return Result.success(chatService.deleteFriend(id, friendId));
    }
}
