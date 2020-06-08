package com.chongba.schedule.jdk;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * timer 单机延迟任务测试
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/8 17:10
 **/
@Slf4j
public class TimerTaskTest {

    public static void main(String[] args) {
        Timer timer = new Timer();
        //demo01( timer );
        //demo02( timer );
        //demo03( timer );
        //demo04( timer );
        demo05( timer );


    }

    private static void demo01(Timer timer) {
        // 一秒之后 执行任务
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                log.info( "执行任务时间:{}", DateUtil.formatDateTime( new Date() ) );
            }
        }, 1000L );
        log.info( "当前时间:{}", DateUtil.formatDateTime( new Date() ) );
    }

    private static void demo02(Timer timer) {
        //执行时间 <= 当前时间 则任务立马执行
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                log.info( "执行任务时间:{}", DateUtil.formatDateTime( new Date() ) );
            }
        }, new Date( System.currentTimeMillis() - 1000L ) );
        log.info( "当前时间:{}", DateUtil.formatDateTime( new Date() ) );
    }

    private static void demo03(Timer timer) {
        // 延迟1秒之后执行任务，然后每隔2秒执行任务
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                log.info( "执行任务时间:{}", DateUtil.formatDateTime( new Date() ) );
            }
        }, 1000L, 2000L );
        log.info( "当前时间:{}", DateUtil.formatDateTime( new Date() ) );
    }

    private static void demo04(Timer timer) {
        // 执行时间 <= 当前时间 则任务立马执行 然后每隔2秒执行任务
        timer.schedule( new TimerTask() {
            @Override
            public void run() {
                log.info( "执行任务时间:{}", DateUtil.formatDateTime( new Date() ) );
            }
        }, new Date( System.currentTimeMillis() - 1000L ), 2000L );
        log.info( "当前时间:{}", DateUtil.formatDateTime( new Date() ) );
    }

    private static void demo05(Timer timer) {
        //执行时间 <= 当前时间 任务立马执行 并会计算过期该执行的次数并且执行，最后每隔1秒执行一次
        timer.scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                log.info( "执行任务时间:{}", DateUtil.formatDateTime( new Date() ) );
            }
            //时间差3秒，立即执行当前任务，然后补充执行3个任务(4个时间相等任务)，接着一秒执行一次
        }, new Date( System.currentTimeMillis() - 3000L ), 1000L );
        log.info( "当前时间:{}", DateUtil.formatDateTime( new Date() ) );
    }

}