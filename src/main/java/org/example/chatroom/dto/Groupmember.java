package org.example.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Groupmember {
    private Integer id;
    private Integer groupid;
    private Date date;
}
