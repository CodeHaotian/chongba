package com.chongba.supplier;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 供应商对接启动类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/15 15:20
 */
@SpringBootApplication
@MapperScan("com.chongba.recharge.mapper") //扫描操作订单的mapper
@EnableFeignClients(basePackages = {"com.chongba.feign"}) // 扫描feign接口所在的包
@ComponentScan({"com.chongba.cache", "com.chongba.supplier"})
public class SupplierApplication {
    public static void main(String[] args) {
        SpringApplication.run( SupplierApplication.class, args );
    }
}