package com.chongba.recharge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 页面渲染启动类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 16:09
 **/
@SpringBootApplication
@MapperScan("com.chongba.recharge.mapper")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run( WebApplication.class, args );
    }
}