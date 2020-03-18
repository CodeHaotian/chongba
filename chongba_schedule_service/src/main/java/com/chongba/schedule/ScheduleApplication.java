package com.chongba.schedule;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Haotian
 * @Date: 2020/3/17 16:25
 * @Description: 启动类
 */
@SpringBootApplication
@MapperScan("com.chongba.schedule.mapper")
public class ScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run( ScheduleApplication.class, args );
    }

    /**
     * mp 乐观锁支持
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}