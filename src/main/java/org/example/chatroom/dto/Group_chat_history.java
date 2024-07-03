package org.example.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Group_chat_history {
    private Integer groupid;
    private Integer sender;
    private String content;
    private Date date;
    private Integer type;
    private String headimg;
    private String username;
}
