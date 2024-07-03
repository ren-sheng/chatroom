package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import org.example.chatroom.dto.Result;
import org.example.chatroom.service.FriendChatService;
import org.example.chatroom.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/friendchat")
public class FriendChatController {
    @Resource
    private FriendChatService chatService;
    @Resource
    private UserService userService;

    /**
     * 获取用户的好友
     * @param userId 用户id
     * @return 响应数据
     */
    @PostMapping("/getFriends")
    public Result<?> getFriends(String userId) {
        Integer id = Integer.parseInt(userId);
        return Result.success(chatService.getFriends(id));
    }

    /**
     * 获取好友聊天历史记录
     * @param userId 用户id
     * @param friendId 好友id
     * @return 响应数据
     */
    @PostMapping("/getFriendHistory")
    public Result<?> getFriendHistory(String userId, String friendId) {
        Integer id = Integer.parseInt(userId);
        Integer friendid = Integer.parseInt(friendId);
        return Result.success(chatService.getFriendHistory(id, friendid));
    }

    /**
     * 添加好友
     * @param userId 用户id
     * @param friendId 好友id
     * @param date 添加时间
     * @return 响应数据
     */
    @PostMapping("/addFriend")
    public Result<?> addFriend(String userId, String friendId, String date) {
        Integer id = Integer.parseInt(userId);
        //检查好友是否存在
        if(userService.queryUsername(friendId) == null) {
            return Result.error("好友不存在");
        }
        //检查是否已经是好友
        if(chatService.isFriend(id, Integer.parseInt(friendId))) {
            return Result.error("已经是好友");
        }
        Integer friendid = Integer.parseInt(friendId);
        Date date1 = new Date();
        //字符串转日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //接收到的时间格式"2024/7/2 14:37:16",转为"2024-07-02 14:37:16"
            date = date.replace("/", "-");
            date1 = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(chatService.addFriend(id, friendid, date1));
    }

    /**
     * 删除好友
     * @param userId 用户id
     * @param friendId 好友id
     * @return 响应数据
     */
    @PostMapping("/deleteFriend")
    public Result<?> deleteFriend(String userId, String friendId) {
        Integer id = Integer.parseInt(userId);
        Integer friendid = Integer.parseInt(friendId);
        return Result.success(chatService.deleteFriend(id, friendid));
    }
}
