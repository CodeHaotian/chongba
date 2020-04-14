package com.chongba.recharge.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错误信息
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 17:01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 500;
    private Integer code;
    private String message;
    private String url;
    private T data;
}