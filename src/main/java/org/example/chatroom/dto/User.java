package org.example.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Date date;
    private String headimg;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
