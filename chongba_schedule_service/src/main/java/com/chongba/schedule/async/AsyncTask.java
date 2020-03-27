package com.chongba.schedule.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/3/27 21:52
 * @Description: 异步调用
 */
@Component
@Slf4j
public class AsyncTask {
    @Async("myThreadpool") //默认采用SimpleAsyncTaskExecutor线程池，可配置为自定义的线程池bean名
    public void myAsync() {
        log.info( "spring boot async task test:{}", Thread.currentThread().getName() );
    }
}