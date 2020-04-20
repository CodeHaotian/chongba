package com.chongba.schedule.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 任务信息日志实体类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/20 23:47
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false) //不继承equals和hashcode方法
@TableName("taskinfo_logs")
public class TaskInfoLogsEntity extends TaskInfoEntity {
    /**
     * version` INT(11) NOT NULL COMMENT '版本号,用乐观锁'
     */
    @Version
    private Integer version;

    /**
     * 状态 0=初始化状态 1=执行 2=取消
     */
    @TableField
    private Integer status;
}