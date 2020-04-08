package com.chongba.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "任务类型不能为空")
    private Integer taskType;

    /**
     * 优先级
     */
    @NotNull(message = "任务优先级不能为空")
    private Integer priority;

    /**
     * 执行时间
     */
    @NotNull(message = "任务执行时间不能为空")
    @Future(message = "任务执行时间必须大于当前时间")
    private long executeTime;

    /**
     * task参数
     */
    private byte[] parameters;
}