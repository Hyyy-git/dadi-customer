package com.ccicnet.gd.customer.controller;

import com.ccicnet.gd.customer.task.TaskRunner;
import com.ccicnet.gd.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 批量任务接口
 */
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private TaskRunner taskRunner;

    /**
     * 执行指定的任务
     *
     * @param id    任务的ID，用于消息回传
     * @param name  任务名称
     * @param param 任务参数，JSON 格式
     */
    @PostMapping("/run/{id}/{name}")
    public Response<String> run(@PathVariable("id") Long id, @PathVariable("name") String name, @RequestBody String param) {
        log.info("run id={}, name={}, param={}", id, name, param);
        taskRunner.run(id, name, param);
        log.info("run done");
        return Response.of("任务线程已提交，请查看后台日志或数据库记录，以便跟踪任务状态。");
    }
}
