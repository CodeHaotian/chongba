package com.chongba.schedule.service;

import com.chongba.entity.Constants;
import com.chongba.entity.Task;
import com.chongba.exception.ScheduleSystemException;
import com.chongba.exception.TaskNotExistException;
import com.chongba.schedule.inf.TaskService;
import com.chongba.schedule.mapper.TaskInfoLogsMapper;
import com.chongba.schedule.mapper.TaskInfoMapper;
import com.chongba.schedule.pojo.TaskInfoEntity;
import com.chongba.schedule.pojo.TaskInfoLogsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: Haotian
 * @Date: 2020/3/20 16:41
 * @Description: 任务接口实现
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskInfoMapper taskInfoMapper;
    @Autowired
    private TaskInfoLogsMapper taskInfoLogsMapper;

    @Override
    public long addTask(Task task) throws ScheduleSystemException {
        try {
            TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                    .taskType( task.getTaskType() )
                    .executeTime( new Date( task.getExecuteTime() ) )
                    .priority( task.getPriority() )
                    .parameters( task.getParameters() ).build();
            taskInfoMapper.insert( taskInfoEntity );
            //插入返回任务主键id
            task.setTaskId( taskInfoEntity.getTaskId() );
            //记录任务日志
            TaskInfoLogsEntity taskInfoLogsEntity = TaskInfoLogsEntity.builder()
                    .taskId( taskInfoEntity.getTaskId() )
                    .taskType( taskInfoEntity.getTaskType() )
                    .executeTime( taskInfoEntity.getExecuteTime() )
                    .priority( taskInfoEntity.getPriority() )
                    .parameters( taskInfoEntity.getParameters() )
                    .status( Constants.SCHEDULED ).build();
            taskInfoLogsMapper.insert( taskInfoLogsEntity );
        } catch (Exception e) {
            log.error( "add task exception taskId={}", task.getTaskId() );
            throw new ScheduleSystemException( e.getMessage() );
        }
        return task.getTaskId();
    }

    @Override
    public boolean cancelTask(long taskId) throws TaskNotExistException {
        boolean flag = false;
        try {
            //删除任务
            taskInfoMapper.deleteById( taskId );
            //更新任务状态
            TaskInfoLogsEntity taskInfoLogsEntity = taskInfoLogsMapper.selectById( taskId );
            taskInfoLogsEntity.setStatus( Constants.CANCELLED );
            taskInfoLogsMapper.updateById( taskInfoLogsEntity );
            flag = true;
        } catch (Exception e) {
            log.error( "task cancel exception taskId={}", taskId );
            throw new TaskNotExistException( e.getMessage() );
        }
        return flag;
    }
}