package com.chongba.schedule.inf;

import com.chongba.entity.Task;
import com.chongba.exception.ScheduleSystemException;
import com.chongba.exception.TaskNotExistException;

/**
 * @Author: Haotian
 * @Date: 2020/3/19 20:57
 * @Description: 任务服务
 */
public interface TaskService {
    /**
     * 添加任务
     *
     * @param task 任务对象
     * @return 任务id
     * @throws ScheduleSystemException 系统异常
     */
    long addTask(Task task) throws ScheduleSystemException;

    /**
     * 取消任务
     *
     * @param taskId 任务id
     * @return 取消结果
     * @throws TaskNotExistException 任务异常
     */
    boolean cancelTask(long taskId) throws TaskNotExistException;

    /**
     * 获取任务数量
     *
     * @return 任务数量
     */
    long size();

    /**
     * 拉取任务
     *
     * @return 任务实体
     * @throws TaskNotExistException 任务异常
     */
    Task poll() throws TaskNotExistException;
}