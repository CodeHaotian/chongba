package com.chongba.exception;

/**
 * @Author: Haotian
 * @Date: 2020/3/19 20:53
 * @Description: 系统异常
 **/
public class ScheduleSystemException extends RuntimeException {
    private static final long serialVersionUID = -3138108380887385059L;

    public ScheduleSystemException(final String errorMessage, final Object... args) {
        super( String.format( errorMessage, args ) );
    }

    public ScheduleSystemException(final Throwable cause) {
        super( cause );
    }

}