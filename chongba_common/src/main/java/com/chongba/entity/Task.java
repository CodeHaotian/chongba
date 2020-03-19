package com.chongba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Haotian
 * @Date: 2020/3/19 20:47
 * @Description: 任务实体类
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable {
    private static final long serialVersionUID = -852887735827147097L;
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 类型
     */
    private Integer taskType;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 执行id
     */
    private long executeTime;

    /**
     * task参数
     */
    private byte[] parameters;
}