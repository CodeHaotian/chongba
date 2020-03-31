package com.chongba.schedule.service;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;

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
    @Qualifier("visiableThreadPool")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private CacheService cacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addTask(Task task) throws ScheduleSystemException {
        Future<Long> future = threadPoolTaskExecutor.submit( new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                //添加任务到数据库
                boolean success = addTaskToDb( task );
                if (success) {
                    //添加任务到缓存
                    addTaskToCache( task );
                }
                return task.getTaskId();
            }
        } );
        long taskId = -1;
        try {
            //获取结果
            taskId = future.get( 5, TimeUnit.SECONDS );
        } catch (Exception e) {
            log.info( "add task exception" );
            throw new ScheduleSystemException( e );
        }
        return taskId;
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
        // 使用任务类型和优先级作为key
        String key = task.getTaskType() + "_" + task.getPriority();
        // 判断任务应该放入消费者队列还是未来数据集合
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lLeftPush( Constants.TOPIC + key, JSON.toJSONString( task ) );
        } else {
            cacheService.zAdd( Constants.FUTURE, JSON.toJSONString( task ), task.getExecuteTime() );
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelTask(long taskId) throws TaskNotExistException {
        boolean flag = false;
        //更新数据库
        Task task = updateDb( taskId, Constants.CANCELLED );
        if (Objects.nonNull( task )) {
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
        String key = task.getTaskType() + "_" + task.getPriority();
        //判断是从消费者队列移除还是从未来数据集合中移除
        if (task.getExecuteTime() <= System.currentTimeMillis()) {
            cacheService.lRemove( Constants.TOPIC + key, 0, JSON.toJSONString( task ) );
        } else {
            cacheService.zRemove( Constants.FUTURE + key, JSON.toJSONString( task ) );
        }
    }

    @Override
    public long size() {
        Set<String> rangeAll = cacheService.zRangeAll( Constants.DB_CACHE );
        return rangeAll.size();
    }

    @Override
    public long size(int type, int priority) {
        // 任务数量=未来数据集合中的任务数量+消费者队列中的数量
        String key = type + "_" + priority;
        Set<String> zRangeAll = cacheService.zRangeAll( Constants.FUTURE + key );
        Long len = cacheService.lLen( Constants.TOPIC + key );
        return zRangeAll.size() + len;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task poll(int type, int priority) throws TaskNotExistException {
        Future<Task> future = threadPoolTaskExecutor.submit( new Callable<Task>() {
            @Override
            public Task call() throws Exception {
                Task task = null;
                String key = type + "_" + priority;
                //获取第一个可执行任务
                String taskJson = cacheService.lRightPop( Constants.TOPIC + key );
                if (StrUtil.isNotEmpty( taskJson )) {
                    //还原对象
                    task = JSON.parseObject( taskJson, Task.class );
                    //更新数据库
                    updateDb( task.getTaskId(), Constants.EXECUTED );
                }
                return task;
            }
        } );
        //获取线程返回结果
        Task task = null;
        try {
            task = future.get( 5, TimeUnit.SECONDS );
        } catch (Exception e) {
            log.error( "poll task exception,type={},priority={}", type, priority );
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
        //移除未来数据集合
        Set<String> futureKeys = cacheService.scan( Constants.FUTURE + "*" );
        cacheService.delete( futureKeys );
        //移除消费者队列
        Set<String> topicKeys = cacheService.scan( Constants.TOPIC + "*" );
        cacheService.delete( topicKeys );
    }

    /**
     * 每秒刷新待消费任务到消费对列
     */
    @Scheduled(cron = "*/1 * * * * ?")
    public void refresh() {
        //从未来数据集合中获取所有的key
        Set<String> futureKeys = cacheService.scan( Constants.FUTURE + "*" );
        for (String futureKey : futureKeys) {
            //转换key future_001_001 ->  topic_001_001
            String topicKey = Constants.TOPIC + futureKey.split( Constants.FUTURE )[1];
            //获取当前key的任务数据集合
            Set<String> values = cacheService.zRangeByScore( futureKey, 0, System.currentTimeMillis() );
            if (!values.isEmpty()) {
                cacheService.refreshWithPipeline( futureKey, topicKey, values );
                log.info( "成功的将{}定时刷新到{}", futureKey, topicKey );
            }
        }
    }
}