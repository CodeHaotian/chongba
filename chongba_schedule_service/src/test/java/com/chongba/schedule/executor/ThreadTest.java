package com.chongba.schedule.executor;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Haotian
 * @Date: 2020/3/24 12:43
 * @Description: 线程测试类
 */
@Slf4j
public class ThreadTest {
    public static void main(String[] args) {
        //继承Thread 实例化一个线程类能创建一个线程,要创建多个线程就需要实例化多个线程类
        myThread myThread1 = new myThread();
        myThread1.setName( "thread01" );
        myThread1.start();
        myThread myThread2 = new myThread();
        myThread2.setName( "thread02" );
        myThread2.start();
        //实现runnable接口 创建一个线程类即可,多个线程之间可以共享
        myRunnable myRunnable = new myRunnable();
        Thread runnableThread1 = new Thread( myRunnable );
        runnableThread1.setName( "runnable01" );
        runnableThread1.start();
        Thread runnableThread2 = new Thread( myRunnable );
        runnableThread2.setName( "runnable02" );
        runnableThread2.start();
        log.info( Thread.currentThread().getName() + System.currentTimeMillis() );
    }
}

@Slf4j
class myThread extends Thread {
    @Override
    public void run() {
        log.info( Thread.currentThread().getName() + System.currentTimeMillis() );
    }
}

@Slf4j
class myRunnable implements Runnable {
    @Override
    public void run() {
        log.info( Thread.currentThread().getName() + System.currentTimeMillis() );
    }
}