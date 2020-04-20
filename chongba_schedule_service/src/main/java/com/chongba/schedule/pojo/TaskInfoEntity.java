package com.chongba.schedule.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务信息实体类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/20 23:47
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@TableName("taskinfo")
public class TaskInfoEntity implements Serializable {
    private static final long serialVersionUID = -3218564191925663119L;
    /**
     * 任务id
     */
    @TableId(type = IdType.ID_WORKER)
    private Long taskId;

    /**
     * 执行时间
     */
    @TableField
    private Date executeTime;

    /**
     * 优先级
     */
    @TableField
    private Integer priority;

    /**
     * 任务类型
     */
    @TableField
    private Integer taskType;

    /**
     * 参数
     */
    @TableField
    private byte[] parameters;
}