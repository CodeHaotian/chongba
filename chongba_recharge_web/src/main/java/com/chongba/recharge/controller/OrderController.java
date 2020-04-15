package com.chongba.recharge.controller;

import com.chongba.entity.Result;
import com.chongba.recharge.RechargeRequest;
import com.chongba.recharge.RechargeResponse;
import com.chongba.recharge.entity.OrderTrade;
import com.chongba.recharge.service.OrderProcessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 订单控制器
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 16:11
 **/
@Controller
@Slf4j
public class OrderController {
    @Autowired
    private OrderProcessService orderProcessService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 跳转首页
     *
     * @return 首页路径
     */
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView( "index" );
    }

    /**
     * 充值操作（充值订单）
     *
     * @param rechargeRequest 订单请求信息
     * @return 充值成功或者失败界面
     */
    @RequestMapping("/crtorder")
    public ModelAndView createRechargeOrder(RechargeRequest rechargeRequest) {
        Result<RechargeResponse> result = null;
        ModelAndView view = null;
        try {
            //对接订单系统
            result = orderProcessService.recharge( rechargeRequest );
        } catch (Exception e) {
            e.printStackTrace();
            view = new ModelAndView( "recharge" );
        }
        if (result.getCode() == 200) {
            //成功
            view = new ModelAndView( "pay" );
            view.addObject( "result", result );
        } else {
            //失败
            view = new ModelAndView( "recharge" );
        }
        return view;
    }

    /**
     * 选择订单支付方式
     *
     * @return 选择支付页面
     */
    @RequestMapping("/payorder")
    public ModelAndView payOrder(String orderNo) {
        OrderTrade orderTrade = null;
        try {
            //根据订单号查询待支付订单
            orderTrade = orderProcessService.queryOrderByNo( orderNo );
            // 调用支付服务完成支付,接收支付结果

            //支付后通知供应商对接模块----异步通知
            RechargeRequest request = RechargeRequest.builder().orderNo( orderNo )
                    .mobile( orderTrade.getMobile() )
                    .pamt( orderTrade.getSalesPrice() ).build();
            rocketMQTemplate.convertAndSend( "pay", request );
        } catch (Exception e) {
            return new ModelAndView( "payfail" );
        }
        ModelAndView view = new ModelAndView( "paysuccess" );
        view.addObject( "orderTrade", orderTrade );
        return view;
    }

    /**
     * 查询所有订单
     *
     * @param userId 用户id
     * @return 用户订单界面
     */
    @RequestMapping("/orderList")
    public ModelAndView orderList(String userId) {
        List<OrderTrade> orderList = null;
        try {
            orderList = orderProcessService.queryAllOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView( "myOrder" );
        view.addObject( "orderList", orderList );
        return view;
    }

    /**
     * 删除订单
     *
     * @param orderNo 订单编号
     * @return 订单界面
     */
    @RequestMapping("/remove")
    public String remove(String orderNo) {
        orderProcessService.removeOrderTrade( orderNo );
        return "redirect:orderList";
    }
}