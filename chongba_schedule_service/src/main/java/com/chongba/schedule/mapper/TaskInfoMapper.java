package com.chongba.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chongba.schedule.pojo.TaskInfoEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2020/3/18 17:32
 * @Description: 任务信息通用接口
 */
public interface TaskInfoMapper extends BaseMapper<TaskInfoEntity> {
    /**
     * 查询所有
     *
     * @return 任务表所有数据
     */
    @Select("select * from taskinfo")
    List<TaskInfoEntity> findAll();
}