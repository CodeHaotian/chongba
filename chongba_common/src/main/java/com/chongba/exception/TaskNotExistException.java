package com.chongba.exception;

/**
 * @Author: Haotian
 * @Date: 2020/3/19 20:52
 * @Description: 任务异常
 **/
public class TaskNotExistException extends RuntimeException {
    private static final long serialVersionUID = 4571317516880121730L;

    public TaskNotExistException(final String errorMessage, final Object... args) {
        super( String.format( errorMessage, args ) );
    }

    public TaskNotExistException(final Throwable cause) {
        super( cause );
    }

}