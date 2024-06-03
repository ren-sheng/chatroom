package org.example.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Friend {
    private Integer id;
    private Integer friend;
    private Date date;
}
