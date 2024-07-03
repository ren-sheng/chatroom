package org.example.chatroom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Group {
    private Integer id;
    private String name;
    private Integer owner;
    private Integer number;
    private Date date;
    private String headimg;
}
