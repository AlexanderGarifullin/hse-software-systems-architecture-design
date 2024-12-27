package com.cf.cfteam.test.services;

import com.cf.cfteam.test.entities.Task;
import com.cf.cfteam.test.repositories.TaskRepository;
import com.cf.cfteam.test.services.interfaces.ITaskService;
import com.cf.cfteam.test.transfer.payloads.TaskPayload;
import com.cf.cfteam.test.transfer.responses.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponse add(TaskPayload taskPayload) {
        Task task = taskRepository.save(convertTaskFromPayload(taskPayload));
        return convertTaskResponseFromTask(task);
    }

    public Task convertTaskFromPayload(TaskPayload taskPayload) {
        return new Task();
    }

    public TaskResponse convertTaskResponseFromTask(Task task) {
        return new TaskResponse();
    }
}
