package com.chongba.recharge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chongba.entity.Result;
import com.chongba.enums.OrderStatusEnum;
import com.chongba.recharge.RechargeRequest;
import com.chongba.recharge.RechargeResponse;
import com.chongba.recharge.entity.OrderTrade;
import com.chongba.recharge.mapper.OrderTradeMapper;
import com.chongba.recharge.service.OrderProcessService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 对接订单系统
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 16:22
 **/
@Service
public class OrderProcessServiceImpl implements OrderProcessService {

    protected final OrderTradeMapper orderTradeMapper;

    public OrderProcessServiceImpl(OrderTradeMapper orderTradeMapper) {
        this.orderTradeMapper = orderTradeMapper;
    }

    @Override
    public Result<RechargeResponse> recharge(RechargeRequest request) {
        //去调用订单微服务
        //订单trade
        OrderTrade orderTrade = createTrade( request );

        RechargeResponse rechargeResponse = RechargeResponse.builder().mobile( request.getMobile() )
                .orderNo( orderTrade.getOrderNo() )
                .pamt( request.getPamt() ).build();
        return Result.<RechargeResponse>builder().data( rechargeResponse ).build();
    }

    @Override
    public OrderTrade queryOrderByNo(String orderNo) throws Exception {
        QueryWrapper<OrderTrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "order_no", orderNo );
        return orderTradeMapper.selectOne( queryWrapper );
    }

    @Override
    public List<OrderTrade> queryAllOrder() throws Exception {
        QueryWrapper<OrderTrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc( "order_time" );
        return orderTradeMapper.selectList( queryWrapper );
    }

    @Override
    public void removeOrderTrade(String orderNo) {
        QueryWrapper<OrderTrade> wrapper = new QueryWrapper<>();
        wrapper.eq( "order_no", orderNo );
        orderTradeMapper.delete( wrapper );
    }

    /**
     * 模拟订单系统 ，放到trade
     *
     * @param request 充值信息
     * @return 订单信息
     */
    private OrderTrade createTrade(RechargeRequest request) {

        String orderId = IdWorker.get32UUID();
        OrderTrade orderTrade = OrderTrade.builder().orderNo( orderId )
                .orderStatus( OrderStatusEnum.WARTING.getCode() )
                .facePrice( request.getFactPrice() )
                .mobile( request.getMobile() )
                .salesPrice( request.getPamt() )
                .orderTime( new Date() )
                .brandId( request.getBrandId() )
                .categoryId( request.getCategoryId() ).build();
        orderTradeMapper.insert( orderTrade );
        return orderTrade;
    }
}