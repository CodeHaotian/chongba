package com.chongba.supplier.listener;

import com.chongba.recharge.RechargeRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/15 15:24
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "pay", consumerGroup = "order-paid-consumer")
public class PayRocketListener implements RocketMQListener<RechargeRequest> {
    @Override
    public void onMessage(RechargeRequest rechargeRequest) {
        log.info( "payRocketListener监听到了支付成功消息,{}", rechargeRequest );
    }
}