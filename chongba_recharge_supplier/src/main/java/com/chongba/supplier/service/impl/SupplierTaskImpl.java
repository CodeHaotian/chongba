package com.chongba.supplier.service.impl;

import com.chongba.entity.Task;
import com.chongba.enums.TaskTypeEnum;
import com.chongba.feign.TaskServiceClient;
import com.chongba.recharge.RechargeRequest;
import com.chongba.supplier.service.SupplierTask;
import com.chongba.utils.ProtostuffUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Objects;

/**
 * 任务重试实现
 *
 * @author Haotian
 * @date 2020/4/18 21:29
 */
public class SupplierTaskImpl implements SupplierTask {
    @Autowired
    private TaskServiceClient taskServiceClient;

    @Override
    public void addRetryTask(RechargeRequest rechargeRequest) {
        // 根据错误码获取错误信息
        TaskTypeEnum taskTypeEnum = TaskTypeEnum.getTaskType( rechargeRequest.getErrorCode() );
        // 设定重试任务时间
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.MINUTE, 1 );
        // 创建任务
        Task task = Task.builder()
                .taskType( Objects.requireNonNull( taskTypeEnum ).getTaskType() )
                .priority( taskTypeEnum.getPriority() )
                .parameters( ProtostuffUtil.serialize( rechargeRequest ) )
                .executeTime( calendar.getTimeInMillis() ).build();
        taskServiceClient.pushTask( task );
    }

    @Override
    public void retryRecharge() {

    }
}