package com.chongba.schedule.dao;

import com.chongba.schedule.ScheduleApplication;
import com.chongba.schedule.mapper.TaskInfoLogsMapper;
import com.chongba.schedule.pojo.TaskInfoLogsEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/3/18 19:14
 * @Description: 任务信息操作日志接口测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleApplication.class)
public class TaskInfoLogsMapperTest {
    @Autowired
    private TaskInfoLogsMapper taskInfoLogsMapper;

    @Test
    public void demo01() {
        TaskInfoLogsEntity taskInfoLogsEntity = TaskInfoLogsEntity.builder()
                .taskType( 1001 )
                .priority( 3 )
                .executeTime( new Date() )
                .status( 0 )
                .parameters( "logs".getBytes() ).build();
        //执行插入
        taskInfoLogsMapper.insert( taskInfoLogsEntity );

        //执行查询
        taskInfoLogsEntity = taskInfoLogsMapper.selectById( taskInfoLogsEntity.getTaskId() );
        log.info( "最初插入返回数据：{}", taskInfoLogsEntity );

        taskInfoLogsEntity.setPriority( 4 );
        taskInfoLogsMapper.updateById( taskInfoLogsEntity );
        taskInfoLogsEntity = taskInfoLogsMapper.selectById( taskInfoLogsEntity.getTaskId() );
        log.info( "第一次修改后返回数据：{}", taskInfoLogsEntity );

        taskInfoLogsEntity.setPriority( 5 );
        taskInfoLogsMapper.updateById( taskInfoLogsEntity );
        taskInfoLogsEntity = taskInfoLogsMapper.selectById( taskInfoLogsEntity.getTaskId() );
        //修改两次version=3
        log.info( "第二次修改后返回数据：{}", taskInfoLogsEntity );
    }
}