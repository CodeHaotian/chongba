package com.chongba.schedule.executor;

import com.chongba.schedule.ScheduleApplication;
import com.chongba.schedule.async.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring 线程池测试
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/5 16:50
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleApplication.class)
public class ThreadPoolTaskExecutorTest {
    @Autowired
    @Qualifier("visiableThreadPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private AsyncTask asyncTask;

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

    @Test
    public void testAsyncTask() {
        for (int i = 0; i < 100; i++) {
            //测试异步调用
            asyncTask.myAsync();
        }
    }

    @Test
    public void testVisable() {
        for (int i = 0; i < 1000; i++) {
            threadPoolTaskExecutor.execute( new Runnable() {
                @Override
                public void run() {
                    System.out.println( "visiableThreadPool test-" + Thread.currentThread().getName() );
                }
            } );
        }
    }
}