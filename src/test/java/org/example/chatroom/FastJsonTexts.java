package org.example.chatroom;

import org.example.chatroom.dto.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.example.chatroom.utils.FastJsonUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class FastJsonTexts {
    @Test
    void toJSON() throws ParseException {
        //创建一个Message对象
        Message message = new Message();
        message.setSender(1);
        message.setReceiver(2);
        message.setContent("Hello");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        message.setDate(formatter.format(new Date()));
        //将JSON字符串转换为Message对象
        String jsonString = FastJsonUtil.toJSONString(message);
        System.out.println(jsonString);
    }
    //获取JSON中的某个字段(先转化为对应Message对象)
    @Test
    void getField() {
        String jsonString = "{\"sender\":1,\"receiver\":2,\"content\":\"Hello\",\"date\":\"01-01-2021 00:00:00\",\"type\":0}";
        Message message = FastJsonUtil.parseObject(jsonString, Message.class);
        System.out.println(message.getContent());
    }


    @Test
    void Stringtext(){
        String a="\n";
        System.out.println(a);
    }
}
