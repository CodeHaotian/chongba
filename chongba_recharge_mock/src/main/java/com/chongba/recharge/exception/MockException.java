package com.chongba.recharge.exception;

/**
 * 自定义异常
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 17:06
 **/
public class MockException extends Exception {
    private static final long serialVersionUID = 6500948055621728166L;

    public MockException(String message) {
        super( message );
    }
}