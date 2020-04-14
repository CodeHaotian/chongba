package com.chongba.enums;

import com.chongba.constant.StatusCode;
import lombok.Getter;

/**
 * 状态码 类型及业务对应关系
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 15:31
 **/
@Getter
public enum TaskTypeEnum {

    ORDER_REQ_FAILED( StatusCode.ORDER_REQ_FAILED, 1001, 1, "话费充值失败，重试" ),
    BALANCE_NOT_ENOUGH( StatusCode.BALANCE_NOT_ENOUGH, 1001, 2, "供应商余额不足，轮转其他供应商" ),
    REMOTE_ERROR( StatusCode.REMOTE_ERROR, 1001, 3, "远程调用失败，重试" ),
    STATE_CHECK( StatusCode.STATE_CHECK, 1001, 4, "检查订单状态" );

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 对应具体业务
     */
    private int taskType;

    /**
     * 业务不同级别
     */
    private int priority;

    /**
     * 描述信息
     */
    private String desc;

    TaskTypeEnum(int errorCode, int taskType, int priority, String desc) {
        this.errorCode = errorCode;
        this.taskType = taskType;
        this.priority = priority;
        this.desc = desc;
    }

    /**
     * 获取当前错误任务类型
     *
     * @param errorCode 错误码
     * @return 错误信息
     */
    public static TaskTypeEnum getTaskType(int errorCode) {
        for (TaskTypeEnum c : TaskTypeEnum.values()) {
            if (c.getErrorCode() == errorCode) {
                return c;
            }
        }
        return null;
    }
}