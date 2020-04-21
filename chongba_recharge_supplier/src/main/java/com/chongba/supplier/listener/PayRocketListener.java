package com.chongba.supplier.listener;

import com.chongba.recharge.RechargeRequest;
import com.chongba.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/15 15:24
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "pay", consumerGroup = "order-paid-consumer")
public class PayRocketListener implements RocketMQListener<RechargeRequest> {
    private final SupplierService supplierService;

    public PayRocketListener(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Override
    public void onMessage(RechargeRequest rechargeRequest) {
        log.info( "payRocketListener监听到了支付成功消息,{}", rechargeRequest );
        supplierService.recharge( rechargeRequest );
    }
}