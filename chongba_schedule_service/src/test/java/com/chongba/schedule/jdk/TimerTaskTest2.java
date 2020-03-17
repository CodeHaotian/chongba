package com.chongba.schedule.jdk;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Haotian
 * @Date: 2020/3/17 17:56
 * @Description: Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用线程池进行优化
 */
@Slf4j
public class TimerTaskTest2 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool( 5 );
        for (int i = 0; i < 100; i++) {
            int temp = i;
            scheduledExecutorService.schedule( new TimerTask() {
                @Override
                public void run() {
                    log.info( "" + temp );
                    if (temp == 20) {
                        throw new RuntimeException();
                    }
                }
            }, 1, TimeUnit.SECONDS );
        }
    }
}