package com.chongba.schedule.jdk;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Haotian
 * @Date: 2020/3/17 19:42
 * @Description: 延迟队列
 */
@Slf4j
public class DelayedTask implements Delayed {
    /**
     * 任务执行时间
     */
    private int executeTime = 0;

    public DelayedTask(int delay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.SECOND, delay );
        this.executeTime = (int) (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 元素在队列中的剩余时间
     *
     * @return 剩余时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        Calendar calendar = Calendar.getInstance();
        return executeTime - (calendar.getTimeInMillis() / 1000);
    }

    /**
     * 用于不同任务对象之间的排序
     */
    @Override
    public int compareTo(Delayed o) {
        long l = this.getDelay( TimeUnit.SECONDS ) - o.getDelay( TimeUnit.SECONDS );
        //当前对象等于比较对象返回0，小于返回-1，大于返回1
        return l == 0 ? 0 : (l < 0 ? -1 : 1);
    }

    public static void main(String[] args) {
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        //创建任务
        queue.add( new DelayedTask( 5 ) );
        queue.add( new DelayedTask( 10 ) );
        queue.add( new DelayedTask( 15 ) );
        log.info( "开始执行延迟任务:{}", DateUtil.formatDateTime( new Date() ) );
        while (queue.size() != 0) {
            DelayedTask delayedTask = queue.poll();
            //取到了任务
            if (delayedTask != null) {
                log.info( "开始消费任务，当前时间:{}", DateUtil.formatDateTime( new Date() ) );
            }
            try {
                //每次间隔一秒
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
