package com.chongba.job.service;

import com.chongba.entity.Constants;
import com.chongba.entity.ResponseMessage;
import com.chongba.feign.TaskServiceClient;
import com.chongba.job.config.SystemParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author: Haotian
 * @Date: 2020/4/10 17:07
 * @Description: 任务调度服务
 */
@Service
@Slf4j
public class JobService {
    private final SelectMaster selectMaster;
    private final TaskServiceClient taskServiceClient;

    public JobService(TaskServiceClient taskServiceClient, SelectMaster selectMaster, SystemParams systemParams) {
        this.taskServiceClient = taskServiceClient;
        this.selectMaster = selectMaster;
    }

    @PostConstruct
    public void init() {
        //启动进行主节点争抢注册
        selectMaster.selectMaster( Constants.JOB_LEADER_PATH );
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void refresh() {
        ResponseMessage<String> message = null;
        if (selectMaster.checkMaster( Constants.JOB_LEADER_PATH )) {
            // 只有主节点才去统一调度刷新
            log.info( "job主节点开始进行刷新任务调度" );
            try {
                message = taskServiceClient.refresh();
                log.info( "refresh,{}", message );
            } catch (Exception e) {
                log.error( "refresh exception {}", e.getMessage() );
            }
        } else {
            //未注册到目录为从节点用做备份
            log.info( "job从节点备用" );
        }
    }
}