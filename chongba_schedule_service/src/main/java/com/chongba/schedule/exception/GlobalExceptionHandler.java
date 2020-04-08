package com.chongba.schedule.exception;

import com.chongba.entity.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @Author: Haotian
 * @Date: 2020/4/8 16:46
 * @Description: 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseMessage<Object> error(Exception e) {
        log.error( e.getMessage());
        if (e instanceof MethodArgumentNotValidException) {
            // 对方法上@RequestBody的Bean参数校验的处理
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            return ResponseMessage.error( methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage() );
        }
        if (e instanceof ConstraintViolationException) {
            // 方法上单个普通类型（如：String、Long等）参数校验异常（校验注解直接写在参数前面的方式）
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            return ResponseMessage.error( constraintViolationException.getConstraintViolations().iterator().next().getMessage() );
        }
        return ResponseMessage.error( "系统异常" );
    }
}