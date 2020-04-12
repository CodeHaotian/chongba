package com.chongba.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Haotian
 * @Date: 2020/4/10 17:03
 * @Description: 分布式任务调度中心启动类
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = {"com.chongba.feign"})
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run( JobApplication.class );
    }
}