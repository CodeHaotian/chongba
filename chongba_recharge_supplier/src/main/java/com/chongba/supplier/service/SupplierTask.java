package com.chongba.supplier.service;

import com.chongba.recharge.RechargeRequest;

/**
 * 充值重试
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/18 21:23
 */
public interface SupplierTask {
    /**
     * 添加重试任务
     *
     * @param rechargeRequest 充值信息
     */
    void addRetryTask(RechargeRequest rechargeRequest);

    /**
     * 重试 拉取/消费重试任务
     */
    void retryRecharge();
}