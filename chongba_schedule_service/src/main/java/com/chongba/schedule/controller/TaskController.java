package com.chongba.schedule.controller;

import com.chongba.entity.ResponseMessage;
import com.chongba.entity.Task;
import com.chongba.schedule.inf.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: Haotian
 * @Date: 2020/4/7 10:49
 * @Description: 任务请求接口
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * 添加任务
     *
     * @param task 任务信息
     * @return 请求回显信息
     */
    @PostMapping("/push")
    public ResponseMessage<Object> pushTask(@RequestBody @Valid Task task) {
        log.info( "add task:{}", task );
        long taskId = taskService.addTask( task );
        return ResponseMessage.ok( taskId );
    }

    /**
     * 消费任务
     *
     * @param taskType 任务类型
     * @param priority 任务优先级
     * @return 请求回显信息
     */
    @GetMapping("/poll/{taskType}/{priority}")
    public ResponseMessage<Task> pollTask(@Valid @PathVariable("taskType") Integer taskType, @Valid @PathVariable("priority") Integer priority) {
        log.info( "poll taskType:{},priority:{}", taskType, priority );
        Task task = taskService.poll( taskType, priority );
        return ResponseMessage.ok( task );
    }

    /**
     * 取消任务
     *
     * @param taskId 任务id
     * @return 请求回显信息
     */
    @PostMapping("/cancel")
    public ResponseMessage<Object> cancelTask(@RequestParam("taskId") @NotNull(message = "任务id不能为空") Long taskId) {
        log.info( "cancel taskId:{}", taskId );
        boolean isSuccess = taskService.cancelTask( taskId );
        return ResponseMessage.ok( isSuccess );
    }
}