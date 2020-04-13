package com.chongba.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 分布式任务调度中心启动类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/13 21:38
 **/
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = {"com.chongba.feign"})
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run( JobApplication.class );
    }
}