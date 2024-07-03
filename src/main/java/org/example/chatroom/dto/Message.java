package org.example.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Integer sender;
    private Integer groupid;
    private Integer receiver;
    private String content;
    private String date;
    private Integer type;//0是普通消息，1是图片，2是文件
    private String headimg;
    private String username;
}
