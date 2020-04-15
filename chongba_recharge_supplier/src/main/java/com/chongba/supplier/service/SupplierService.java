package com.chongba.supplier.service;

import com.chongba.recharge.RechargeRequest;

/**
 * 对接供应商接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/15 16:21
 */
public interface SupplierService {
    /**
     * 对接供应商下单
     *
     * @param rechargeRequest 充值信息
     */
    void recharge(RechargeRequest rechargeRequest);
}