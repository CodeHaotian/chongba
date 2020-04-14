package com.chongba.recharge.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用异常处理
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 17:02
 **/
@RestControllerAdvice(basePackages = "com.recharge.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ErrorInfo<String> errorResult(HttpServletRequest req) {
        return ErrorInfo.<String>builder().code( ErrorInfo.ERROR )
                .message( "参数异常" )
                .url( req.getRequestURL().toString() ).build();
    }

    @ExceptionHandler(value = MockException.class)
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, MockException e) throws Exception {
        return ErrorInfo.<String>builder().code( ErrorInfo.ERROR )
                .message( e.getMessage() )
                .data( "Some Data" )
                .url( req.getRequestURL().toString() ).build();
    }
}