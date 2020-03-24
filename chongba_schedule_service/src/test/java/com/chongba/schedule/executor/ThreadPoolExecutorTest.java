package com.chongba.schedule.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: Haotian
 * @Date: 2020/3/24 15:16
 * @Description: 线程池测试
 */
@Slf4j
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws Exception {
        //创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor
                (
                        5,
                        100,
                        5, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>() );

        //向线程池提交100个Runnable任务
       /* for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute( new myRunnableTask( i ) );
        }*/

        //执行任务并且获取返回值
       /* Future<String> future = threadPoolExecutor.submit( new myCallableTask( "世界" ) );
        log.info( future.get( 5, TimeUnit.SECONDS ) );*/

        //批量执行任务并返回任务的结果集合
        List<myCallableTask> callableTaskList = new ArrayList<>( 100 );
        for (int i = 0; i < 100; i++) {
            callableTaskList.add( new myCallableTask( "艾莉丝" + i ) );
        }
        List<Future<String>> futureList = threadPoolExecutor.invokeAll( callableTaskList );
        futureList.forEach( f -> {
            try {
                log.info( f.get( 5, TimeUnit.SECONDS ) );
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        } );
    }
}

@Slf4j
class myRunnableTask implements Runnable {
    private int taskNumber = 0;

    public myRunnableTask(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void run() {
        log.info( "execute myRunnableTask" + taskNumber + "-" + Thread.currentThread().getName() );
    }
}

@Slf4j
class myCallableTask implements Callable<String> {
    private String taskName = "";

    public myCallableTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String call() throws Exception {
        log.info( "execute myCallableTask" + taskName + "-" + Thread.currentThread().getName() );
        return "Hello" + taskName;
    }
}