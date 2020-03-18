package com.chongba.schedule.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chongba.schedule.ScheduleApplication;
import com.chongba.schedule.mapper.TaskInfoMapper;
import com.chongba.schedule.pojo.TaskInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/3/18 17:36
 * @Description: 任务信息操作接口测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleApplication.class)
public class TaskInfoMapperTest {
    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Test
    public void demo01() {
        TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .executeTime( new Date() )
                .priority( 1 )
                .taskType( 1001 )
                .parameters( "test".getBytes() ).build();
        //执行插入
        taskInfoMapper.insert( taskInfoEntity );
        Long taskId = taskInfoEntity.getTaskId();
        log.info( "当前操作返回任务id：{}", taskId );

        //执行查询
        TaskInfoEntity selectTaskInfo = taskInfoMapper.selectById( taskId );
        log.info( "当前任务对象信息：{}", selectTaskInfo );

        taskInfoEntity.setTaskType( 1002 );
        //执行更新
        taskInfoMapper.updateById( taskInfoEntity );
        TaskInfoEntity newTaskInfo = taskInfoMapper.selectById( taskId );
        log.info( "修改后任务对象信息：{}", newTaskInfo );
    }

    @Test
    public void demo02() {
        //执行删除
        taskInfoMapper.deleteById( 1240227837846511617L );
    }

    @Test
    public void demo03() {
        //查询全部数据
        taskInfoMapper.selectList( null ).forEach( System.out::println );
    }

    @Test
    public void demo04() {
        //条件查询
        QueryWrapper<TaskInfoEntity> queryWrapper = new QueryWrapper<>();
        //查询表指定列名为1001的数据
        queryWrapper.eq( "task_type", 1002 );
        taskInfoMapper.selectList( queryWrapper ).forEach( System.out::println );
    }

    @Test
    public void demo05() {
        //自定义查询所有方法
        taskInfoMapper.findAll().forEach( System.out::println );
    }
}
