package com.chongba.recharge;

import com.chongba.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 充值返回实体
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 15:41
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RechargeResponse implements Serializable {
    private static final long serialVersionUID = 5524019846483682009L;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 编号
     */
    private String tradeNo;

    /**
     * 付款金额
     */
    private double pamt;

    /**
     * 订单默认状态
     */
    private int status = OrderStatusEnum.WARTING.getCode();
}