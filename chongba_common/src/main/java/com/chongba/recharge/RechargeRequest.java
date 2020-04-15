package com.chongba.recharge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 充值对象
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 15:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RechargeRequest implements Serializable {
    private static final long serialVersionUID = -2926012934118471442L;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 供应商编号:例如jisuapi
     */
    private String supply;

    /**
     * 供应商交易地址
     */
    private String rechargeUrl;

    /**
     * 充值手机号
     */
    private String mobile;

    /**
     * 品类归属品牌编码，例：927
     */
    private String brandId;

    /**
     * 商品类别（1充值，2兑换）
     */
    private Integer categoryId;

    /**
     * 待支付金额，单位：分
     */
    private double pamt;

    /**
     * 面值
     */
    private double factPrice;

    /**
     * 重试发送次数
     */
    private int repeat = 0;

    /**
     * 状态
     */
    private int state = 1;

    /**
     * 业务类型错误码---决定了任务的类型和优先级
     */
    private int errorCode;

    /**
     * 编号
     */
    private String tradeNo;
}