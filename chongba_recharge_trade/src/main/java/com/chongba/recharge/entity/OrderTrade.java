package com.chongba.recharge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 追踪订单实体
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 15:53
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("order_trade")
public class OrderTrade implements Serializable {
    private static final long serialVersionUID = -473304268672660327L;

    /**
     * 流水号
     */
    @TableId
    private Long id;

    /**
     * 商品编号
     */
    private String brandId;

    /**
     * 商品类别（1充值，2兑换）
     */
    private Integer categoryId;

    /**
     * 订单状态： 0.创建 1.处理中 2 成功 3 失败 9.未确认
     */
    private Integer orderStatus;

    /**
     * 销售价格
     */
    private double salesPrice;

    /**
     * 面值
     */
    private double facePrice;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 交易订单号
     */
    private Long tradeNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单交易时间
     */
    private Date orderTime;
}