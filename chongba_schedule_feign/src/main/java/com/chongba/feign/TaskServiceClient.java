package com.chongba.feign;

import com.chongba.entity.ResponseMessage;
import com.chongba.entity.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: Haotian
 * @Date: 2020/4/10 16:03
 * @Description: 服务调用接口
 */
@FeignClient("schedule-service")
public interface TaskServiceClient {
    /**
     * 添加任务
     *
     * @param task 任务信息
     * @return 请求回显信息
     */
    @PostMapping("/task/push")
    ResponseMessage<Object> pushTask(@RequestBody @Valid Task task);

    /**
     * 消费任务
     *
     * @param taskType 任务类型
     * @param priority 任务优先级
     * @return 请求回显信息
     */
    @GetMapping("/task/poll/{taskType}/{priority}")
    ResponseMessage<Task> pollTask(@Valid @PathVariable("taskType") Integer taskType,
                                   @Valid @PathVariable("priority") Integer priority);

    /**
     * 取消任务
     *
     * @param taskId 任务id
     * @return 请求回显信息
     */
    @PostMapping("/task/cancel")
    ResponseMessage<Object> cancelTask(@RequestParam("taskId") @NotNull(message = "任务id不能为空") Long taskId);

    /**
     * 每秒刷新待消费任务到消费对列
     *
     * @return 请求回显信息
     */
    @GetMapping("/task/refresh")
    ResponseMessage<String> refresh();
}