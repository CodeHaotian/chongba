package com.chongba.recharge.service;

import com.chongba.entity.Result;
import com.chongba.recharge.RechargeRequest;
import com.chongba.recharge.RechargeResponse;
import com.chongba.recharge.entity.OrderTrade;

import java.util.List;

/**
 * 订单系统集成
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 16:18
 **/
public interface OrderProcessService {
    /**
     * 充值对接订单系统
     *
     * @param request 充值信息
     * @return 充值返回实体
     */
    Result<RechargeResponse> recharge(RechargeRequest request);

    /**
     * 查询订单信息
     *
     * @param orderNo 订单编号
     * @return 订单信息
     * @throws Exception
     */
    OrderTrade queryOrderByNo(String orderNo) throws Exception;

    /**
     * 查询所有订单
     *
     * @return 订单信息集合
     * @throws Exception
     */
    List<OrderTrade> queryAllOrder() throws Exception;

    /**
     * 根据订单编号删除订单
     *
     * @param orderNo 订单编号
     */
    void removeOrderTrade(String orderNo);
}