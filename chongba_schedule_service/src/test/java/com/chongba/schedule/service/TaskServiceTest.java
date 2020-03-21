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

/**
 * @Author: Haotian
 * @Date: 2020/3/20 17:11
 * @Description: 任务服务测试
 */
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
}