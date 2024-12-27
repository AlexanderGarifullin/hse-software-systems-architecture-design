package com.cf.cfteam.test.controllers;

import com.cf.cfteam.test.services.TaskService;
import com.cf.cfteam.test.services.interfaces.ITaskService;
import com.cf.cfteam.test.transfer.payloads.TaskPayload;
import com.cf.cfteam.test.transfer.responses.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("task")
public class TaskController {

    private final ITaskService taskService;

    @PostMapping()
    public TaskResponse addTask(TaskPayload taskPayload) {
        return taskService.add(taskPayload);
    }
}
