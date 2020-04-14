package com.chongba.constant;

/**
 * @Author: Haotian
 * @Date: 2020/4/7 10:41
 * @Description: 请求状态码
 **/
public class StatusCode {
    /**
     * 成功
     */
    public static final int OK = 200;
    /**
     * 失败
     */
    public static final int ERROR = 500;
    /**
     * 用户名或密码错误
     */
    public static final int LOGIN_ERROR = 20002;
    /**
     * 权限不足
     */
    public static final int ACCESS_ERROR = 20003;
    /**
     * 远程调用失败
     */
    public static final int REMOTE_ERROR = 20004;
    /**
     * 重复操作
     */
    public static final int REP_ERROR = 20005;
    /**
     * 余额不足
     */
    public static final int BALANCE_NOT_ENOUGH = 20006;
    /**
     * 状态检查
     */
    public static final int STATE_CHECK = 20008;
    /**
     * 订单请求失败，重试
     */
    public static final int ORDER_REQ_FAILED = 208508;
}