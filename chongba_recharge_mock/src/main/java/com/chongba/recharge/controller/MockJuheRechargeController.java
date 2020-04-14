package com.chongba.recharge.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chongba.constant.StatusCode;
import com.chongba.entity.Result;
import com.chongba.enums.OrderStatusEnum;
import com.chongba.recharge.RechargeRequest;
import com.chongba.recharge.RechargeResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聚合充值mock
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 17:15
 **/
@RestController
@RequestMapping("/juheapi")
public class MockJuheRechargeController extends BaseController {

    @RequestMapping("/recharge")
    public Result<RechargeResponse> add(@RequestBody RechargeRequest request) {
        RechargeResponse response = RechargeResponse.builder().mobile( request.getMobile() )
                .orderNo( request.getOrderNo() )
                .tradeNo( IdWorker.get32UUID() )
                .pamt( request.getPamt() ).build();
        return Result.<RechargeResponse>builder().code( StatusCode.BALANCE_NOT_ENOUGH )
                .msg( "余额不足" )
                .data( response ).build();
    }

    @RequestMapping("/orderState")
    public Result<RechargeResponse> orderState(String outorderNo, String tradeNo) {
        Result<RechargeResponse> result = new Result<>();
        RechargeResponse response = new RechargeResponse();
        response.setStatus( OrderStatusEnum.FAIL.getCode() );
        response.setOrderNo( outorderNo );
        result.setCode( StatusCode.ERROR );
        result.setMsg( "充值失败" );
        return result;
    }
}