package com.chongba.supplier.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.chongba.entity.Constants;
import com.chongba.recharge.RechargeRequest;
import com.chongba.supplier.config.SupplierConfig;
import com.chongba.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 对接供应商接口逻辑
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/15 16:23
 */
@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {
    private final RestTemplate restTemplate;
    private final SupplierConfig supplierConfig;

    public SupplierServiceImpl(RestTemplate restTemplate, SupplierConfig supplierConfig) {
        this.restTemplate = restTemplate;
        this.supplierConfig = supplierConfig;
    }

    @Override
    public void recharge(RechargeRequest rechargeRequest) {
        doDispatchSupplier( rechargeRequest );
    }

    /**
     * 供应商分发
     *
     * @param rechargeRequest 充值信息
     */
    private void doDispatchSupplier(RechargeRequest rechargeRequest) {
        if (StrUtil.isEmpty( rechargeRequest.getSupply() )) {
            rechargeRequest.setSupply( Constants.juheapi );
        }
        // 获取供应商的调用地址
        String url = supplierConfig.getApis().get( rechargeRequest.getSupply() );
        rechargeRequest.setRechargeUrl( url );
        //根据需要对接的供应商的编号确定不同的对接方式---不同的api需要传递的参数类型和参数名称等各不相同
        if (Constants.juheapi.equals( rechargeRequest.getRechargeUrl() )) {
            // 对接聚合
            doPostJuhe( rechargeRequest );
        } else if (Constants.jisuapi.equals( rechargeRequest.getRechargeUrl() )) {
            // 对接极速
            doPostJisu( rechargeRequest );
        }
    }

    /**
     * 聚合平台对接
     *
     * @param rechargeRequest 充值信息
     */
    private void doPostJuhe(RechargeRequest rechargeRequest) {
        // 聚合要求传递的是json格式的数据
        // 创建并设置请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType( MediaType.APPLICATION_JSON );
        // 创建请求实体
        HttpEntity<String> httpEntity = new HttpEntity<>( JSON.toJSONString( rechargeRequest ), httpHeaders );
        // 发送请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity( rechargeRequest.getRechargeUrl(), httpEntity, String.class );
        // 获取结果
        String entityBody = responseEntity.getBody();
        log.info( "聚合平台充值回显{}", entityBody );
    }

    /**
     * 极速平台对接
     *
     * @param rechargeRequest 充值信息
     */
    private void doPostJisu(RechargeRequest rechargeRequest) {
    }
}
