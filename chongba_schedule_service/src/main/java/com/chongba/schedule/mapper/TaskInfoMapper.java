package com.chongba.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chongba.schedule.pojo.TaskInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 任务信息通用接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/10 15:51
 **/
public interface TaskInfoMapper extends BaseMapper<TaskInfoEntity> {
    /**
     * 查询所有
     *
     * @return 任务表所有数据
     */
    @Select("select * from taskinfo")
    List<TaskInfoEntity> findAll();

    /**
     * 更据任务信息进行分组数据查询
     *
     * @param taskType 任务类型
     * @param priority 任务优先级
     * @return 当前分组数据集合
     */
    @Select("select * from taskinfo where task_type = #{task_type} and priority = #{priority}")
    List<TaskInfoEntity> queryAllTaskInfoByTaskTypeAndPriority(@Param("task_type") int taskType, @Param("priority") int priority);

    /**
     * 查询符合条件的任务信息
     *
     * @param taskType    任务类型
     * @param priority    任务优先级
     * @param executeTime 任务执行时间
     * @return 当前条件下数据集合
     */
    @Select("select * from taskinfo where task_type = #{taskType} and priority = #{priority} and execute_time <=#{executeTime}")
    List<TaskInfoEntity> queryFutureTaskInfoByTaskTypeAndPriority(@Param("taskType") int taskType, @Param("priority") int priority, @Param("executeTime") Date executeTime);
}