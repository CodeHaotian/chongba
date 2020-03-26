package com.chongba.schedule.executor;

import com.chongba.schedule.ScheduleApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Haotian
 * @Date: 2020/3/26 18:55
 * @Description: Spring 线程池测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleApplication.class)
public class ThreadPoolTaskExecutorTest {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void testThreadPoolTaskExecutor() {
        //向线程池中提交100个任务
        for (int i = 0; i < 100; i++) {
            threadPoolTaskExecutor.execute( () -> {
                log.info( "ThreadPoolTaskExecutor test {}", Thread.currentThread().getName() );
            } );
        }
        /*
        默认参数：核心线程数:8
        最大线程数:2147483647
        线程等待超时时间:60
        当前活跃的线程数:8
        线程池内线程的名称前缀:task-
         */
        log.info( "核心线程数:{}", threadPoolTaskExecutor.getCorePoolSize() );
        log.info( "最大线程数:{}", threadPoolTaskExecutor.getMaxPoolSize() );
        log.info( "线程等待超时时间:{}", threadPoolTaskExecutor.getKeepAliveSeconds() );
        log.info( "当前活跃的线程数:{}", threadPoolTaskExecutor.getActiveCount() );
        log.info( "线程池内线程的名称前缀:{}", threadPoolTaskExecutor.getThreadNamePrefix() );
    }
}
