package com.chongba.schedule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @Author: Haotian
 * @Date: 2020/3/29 23:41
 * @Description: 线程池监控扩展
 */
@Slf4j
public class VisiableThreadPool extends ThreadPoolTaskExecutor {

    /**
     * 收集线程池运行状况数据信息
     */
    private void logs(String info) {
        //线程名称前缀
        String threadNamePrefix = this.getThreadNamePrefix();
        //任务总数
        long taskCount = this.getThreadPoolExecutor().getTaskCount();
        //当前正在执行任务的线程数
        int activeCount = this.getThreadPoolExecutor().getActiveCount();
        //已完成的任务数
        long completedTaskCount = this.getThreadPoolExecutor().getCompletedTaskCount();
        //任务等待队列中任务数
        int queueSize = this.getThreadPoolExecutor().getQueue().size();
        log.info( "{},{},taskCount={},activeCount={},completedTaskCount={},queueSize={}",
                threadNamePrefix, info, taskCount, activeCount, completedTaskCount, queueSize );
    }

    @Override
    public void execute(Runnable task) {
        super.execute( task );
        logs( "do execute " );
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        logs( "do submit" );
        return super.submit( task );
    }
}
