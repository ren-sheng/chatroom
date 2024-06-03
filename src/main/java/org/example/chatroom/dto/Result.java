package org.example.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private boolean code; //结果码
    private String message;
    private T data; //返回的数据

    public static <E> Result<E> success(E data) {
        return new Result<>(true, "操作成功", data);
    }

    public static Result<?> success(){
        return new Result<>(true,"操作成功",null);
    }

    public static Result<?> error(String message){
        return new Result<>(false,message,null);
    }
}
