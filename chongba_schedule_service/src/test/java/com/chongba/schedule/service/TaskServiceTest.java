package com.chongba.schedule.service;

import com.chongba.entity.Task;
import com.chongba.schedule.ScheduleApplication;
import com.chongba.schedule.inf.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 任务服务测试
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/8 17:11
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleApplication.class)
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @Test
    public void testAddTask() {
        Task task = Task.builder()
                .taskType( 119 )
                .executeTime( System.currentTimeMillis() )
                .priority( 1001 )
                .parameters( "test".getBytes() ).build();
        taskService.addTask( task );
        log.info( "当前任务id={}", task.getTaskId() );
    }

    @Test
    public void testCancelTask() {
        taskService.cancelTask( 1241302579621965825L );
    }

    @Test
    public void testPoolTask() {
        // 添加任务数据
        Date now = new Date();
        for (int i = 0; i < 3; i++) {
            Task task = Task.builder()
                    .taskType( 250 )
                    .executeTime( now.getTime() + 50000 * i )
                    .priority( 250 )
                    .parameters( "testPoolTask".getBytes() ).build();
            taskService.addTask( task );
        }
        // 消费拉取任务
        while (true) {
            Task task = taskService.poll( 250, 250 );
            if (task != null) {
                System.out.println( "成功消费了任务:" + task.getTaskId() );
            }
            //每隔一秒消费一次
            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}