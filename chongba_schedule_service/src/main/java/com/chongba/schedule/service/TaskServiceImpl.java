package com.chongba.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.chongba.cache.CacheService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private CacheService cacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addTask(Task task) throws ScheduleSystemException {
        //添加任务到数据库
        boolean success = addTaskToDb( task );
        if (success) {
            //添加任务到缓存
            addTaskToCache( task );
        }
        return task.getTaskId();
    }

    /**
     * 添加任务到数据库
     *
     * @param task 任务数据
     * @return 是否添加成功
     */
    private boolean addTaskToDb(Task task) {
        boolean success = false;
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
            success = true;
        } catch (Exception e) {
            log.error( "add task exception taskId={}", task.getTaskId() );
            throw new ScheduleSystemException( e.getMessage() );
        }
        return success;
    }

    /**
     * 添加任务到缓存
     *
     * @param task 任务数据
     */
    private void addTaskToCache(Task task) {
        cacheService.zAdd( Constants.DB_CACHE, JSON.toJSONString( task ), task.getExecuteTime() );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelTask(long taskId) throws TaskNotExistException {
        boolean flag = false;
        //更新数据库
        Task task = updateDb( taskId, Constants.CANCELLED );
        if (task != null) {
            //更新缓存
            removeTaskFromCache( task );
            flag = true;
        }
        return flag;
    }

    /**
     * 更新任务数据库
     *
     * @param taskId 任务id
     * @param status 任务状态
     * @return 任务实体
     */
    private Task updateDb(long taskId, int status) {
        Task task = null;
        try {
            //删除任务
            taskInfoMapper.deleteById( taskId );
            //更新任务状态
            TaskInfoLogsEntity taskInfoLogsEntity = taskInfoLogsMapper.selectById( taskId );
            taskInfoLogsEntity.setStatus( status );
            taskInfoLogsMapper.updateById( taskInfoLogsEntity );
            // 构造返回的任务对象
            task = new Task();
            //拷贝数据
            BeanUtils.copyProperties( taskInfoLogsEntity, task );
            task.setExecuteTime( taskInfoLogsEntity.getExecuteTime().getTime() );
        } catch (Exception e) {
            log.error( "task cancel exception taskId={}", taskId );
            throw new TaskNotExistException( e.getMessage() );
        }
        return task;
    }

    /**
     * 删除缓存任务数据
     *
     * @param task 任务数据
     */
    private void removeTaskFromCache(Task task) {
        cacheService.zRemove( Constants.DB_CACHE, JSON.toJSONString( task ) );
    }

    @Override
    public long size() {
        Set<String> rangeAll = cacheService.zRangeAll( Constants.DB_CACHE );
        return rangeAll.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task poll() throws TaskNotExistException {
        Task task = null;
        try {
            //获取当前时间任务
            Set<String> byScore = cacheService.zRangeByScore( Constants.DB_CACHE, 0, System.currentTimeMillis() );
            if (CollUtil.isNotEmpty( byScore )) {
                //获取第一个可执行任务
                String taskJson = byScore.iterator().next();
                if (StrUtil.isNotEmpty( taskJson )) {
                    //还原对象
                    task = JSON.parseObject( taskJson, Task.class );
                    //从缓存中移除当前任务
                    cacheService.zRemove( Constants.DB_CACHE, taskJson );
                    //更新数据库
                    updateDb( task.getTaskId(), Constants.EXECUTED );
                }
            }
        } catch (Exception e) {
            log.error( "poll task exception" );
            throw new TaskNotExistException( e );
        }
        return task;
    }

    /**
     * 缓存恢复
     */
    @PostConstruct
    private void syncData() {
        log.info( "******init******" );
        // 清除缓存中原有的数据
        clearCache();
        //从数据库查询所有任务数据
        List<TaskInfoEntity> taskInfos = taskInfoMapper.findAll();
        //将任务数据存入缓存
        taskInfos.forEach( t -> {
            Task task = new Task();
            //属性拷贝
            BeanUtils.copyProperties( t, task );
            task.setExecuteTime( t.getExecuteTime().getTime() );
            //加入缓存
            addTaskToCache( task );
        } );

    }

    /**
     * 移除缓存中的数据
     */
    private void clearCache() {
        cacheService.delete( Constants.DB_CACHE );
    }
}