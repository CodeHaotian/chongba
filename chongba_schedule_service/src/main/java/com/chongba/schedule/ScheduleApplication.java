package com.chongba.schedule;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.chongba.schedule.service.VisiableThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: Haotian
 * @Date: 2020/3/17 16:25
 * @Description: 启动类
 */
@SpringBootApplication
@MapperScan("com.chongba.schedule.mapper")
@ComponentScan({"com.chongba.cache", "com.chongba.schedule"})
@EnableScheduling
@EnableAsync
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

    @Bean
    public ThreadPoolTaskExecutor myThreadpool() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize( 5 );
        //设置最大线程数
        threadPool.setMaxPoolSize( 100 );
        //设置线程超时等待时间
        threadPool.setKeepAliveSeconds( 60 );
        //设置任务等待队列的大小
        threadPool.setQueueCapacity( 100 );
        //设置线程池内线程的名称前缀---阿里编码规约推荐----出错了方便调试
        threadPool.setThreadNamePrefix( "myThreadPool-" );
        //设置任务拒绝策略
        threadPool.setRejectedExecutionHandler( new ThreadPoolExecutor.AbortPolicy() );
        //直接初始化
        threadPool.initialize();
        return threadPool;
    }

    @Bean("visiableThreadPool")
    public ThreadPoolTaskExecutor visiableThreadPool(){
        ThreadPoolTaskExecutor visiableThreadPool = new VisiableThreadPool();
        visiableThreadPool.setCorePoolSize(10);
        visiableThreadPool.setMaxPoolSize(1000);
        visiableThreadPool.setKeepAliveSeconds(60);
        visiableThreadPool.setQueueCapacity(1000);
        visiableThreadPool.setThreadNamePrefix("visiableThreadPool-");
        visiableThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        visiableThreadPool.initialize();
        return visiableThreadPool;
    }
}