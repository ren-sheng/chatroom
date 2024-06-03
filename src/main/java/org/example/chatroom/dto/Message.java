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
}
