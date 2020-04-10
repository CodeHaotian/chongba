package com.chongba.job.service;

import com.chongba.entity.ResponseMessage;
import com.chongba.feign.TaskServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author: Haotian
 * @Date: 2020/4/10 17:07
 * @Description: 任务调度服务
 */
@Service
@Slf4j
public class JobService {
    private final TaskServiceClient taskServiceClient;

    public JobService(TaskServiceClient taskServiceClient) {
        this.taskServiceClient = taskServiceClient;
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void refresh() {
        ResponseMessage<String> message = null;
        try {
            message = taskServiceClient.refresh();
            log.info( "refresh,{}", message );
        } catch (Exception e) {
            log.error( "refresh exception {}", e.getMessage() );
        }
    }
}