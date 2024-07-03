package org.example.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Friend_chat_history{
    private Integer sender;
    private Integer receiver;
    private String content;
    private Date date;
    private Integer type;
}
