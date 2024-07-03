package org.example.chatroom.controller;

import jakarta.annotation.Resource;
import org.example.chatroom.service.GroupChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.chatroom.dto.Result;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/groupchat")
public class GroupChatController {
    @Resource
    private GroupChatService groupChatService;

    /**
     * 获取用户的群聊
     *
     * @param userId 用户id
     * @return 响应数据
     */
    @PostMapping("/getGroups")
    public Result<?> getGroups(String userId) {
        Integer id = Integer.parseInt(userId);
        return Result.success(groupChatService.getGroups(id));
    }

    /**
     * 获取群聊成员
     *
     * @param group_Id 群聊id
     * @return 响应数据
     */
    @PostMapping("/getGroupMembers")
    public Result<?> getGroupMembers(String group_Id) {
        Integer group_id = Integer.parseInt(group_Id);
        return Result.success(groupChatService.getGroupMembers(group_id));
    }

    /**
     * 获取群聊历史记录
     *
     * @param group_Id 群聊id
     * @return 响应数据
     */
    @PostMapping("/getGroupHistory")
    public Result<?> getGroupHistory(String group_Id) {
        Integer group_id = Integer.parseInt(group_Id);
        return Result.success(groupChatService.getGroupHistory(group_id));
    }

    /**
     * 添加群聊
     *
     * @param name    群聊名
     * @param owner   群主id
     * @param date    创建时间
     * @param members 成员id
     * @return 响应数据
     */
    @PostMapping("/addGroup")
    public Result<?> addGroup(String name, Integer owner, Date date, Integer[] members) {
        return Result.success(groupChatService.addGroup(name, owner, date, members));
    }

    /**
     * 添加群聊成员
     *
     * @param group_id 群聊id
     * @param user_id  用户id
     * @param date     加入时间
     * @return 响应数据
     */
    @PostMapping("/addGroupMember")
    public Result<?> addGroupMember(String group_id, String user_id, String date) {
        Integer group_Id = Integer.parseInt(group_id);
        //检查是否有该群聊
        if (groupChatService.getGroup(group_Id) == null) return Result.error("群聊不存在");
        //检查是否已经是群聊成员
        if (groupChatService.isGroupMember(group_Id, Integer.parseInt(user_id))) return Result.error("已经是群聊成员");
        Integer user_Id = Integer.parseInt(user_id);
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = date.replace("/", "-");
            date1 = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(groupChatService.addGroupMember(group_Id, user_Id, date1));
    }

    /**
     * 删除群聊
     *
     * @param group_id 群聊id
     * @return 响应数据
     */
    @PostMapping("/deleteGroup")
    public Result<?> deleteGroup(Integer group_id) {
        if (groupChatService.deleteGroup(group_id)) return Result.success();
        else return Result.error("删除失败");
    }

    /**
     * 删除群聊成员
     *
     * @param group_id 群聊id
     * @param user_id  用户id
     * @return 响应数据
     */
    @PostMapping("/deleteGroupMember")
    public Result<?> deleteGroupMember(Integer group_id, Integer user_id) {
        return Result.success(groupChatService.deleteGroupMember(group_id, user_id));
    }
}
