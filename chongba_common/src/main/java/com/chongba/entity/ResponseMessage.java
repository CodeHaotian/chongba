package com.chongba.entity;

import com.chongba.constant.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一请求返回封装类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/11 16:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage<T> {
    private boolean flag;
    private Integer code;
    private String message;
    private T data;
    private String url;

    public static ResponseMessage ok(Object data) {
        return ResponseMessage.builder().flag( true ).code( StatusCode.OK ).message( "success" ).data( data ).build();
    }

    public static ResponseMessage error(Object data) {
        return ResponseMessage.builder().flag( false ).code( StatusCode.ERROR ).message( "fail" ).data( data ).build();
    }
}