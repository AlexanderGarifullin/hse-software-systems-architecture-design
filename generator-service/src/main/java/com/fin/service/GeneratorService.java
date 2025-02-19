package com.fin.service;

import com.fin.dto.GenerateTestRequest;
import com.fin.entity.Task;
import com.fin.entity.Test;
import com.fin.repository.TaskRepository;
import com.fin.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorService {

    private final TaskRepository taskRepository;
    private final TestRepository testRepository;

    @Async
    public CompletableFuture<Boolean> generateTestsAsync(GenerateTestRequest request) {
        log.info("Starting asynchronous test generation for taskId: {} with count: {}", request.getTaskId(), request.getCount());
        boolean result = generateTests(request);
        log.info("Asynchronous test generation completed for taskId: {} with result: {}", request.getTaskId(), result);
        return CompletableFuture.completedFuture(result);
    }

    public boolean generateTests(GenerateTestRequest request) {
        log.debug("Looking for task with id: {}", request.getTaskId());
        Optional<Task> taskOpt = taskRepository.findById(request.getTaskId());
        if (taskOpt.isEmpty()) {
            log.error("Task with id {} not found", request.getTaskId());
            return false;
        }
        Task task = taskOpt.get();
        log.info("Found task: {}. Generating {} tests.", task.getId(), request.getCount());
        for (int i = 0; i < request.getCount(); i++) {
            Test test = new Test();
            test.setTask(task);
            String generatedInput = "RandomInput_" + UUID.randomUUID().toString();
            test.setInput(generatedInput);
            testRepository.save(test);
            log.debug("Generated test {} for task {}: {}", i + 1, task.getId(), generatedInput);
        }
        log.info("Successfully generated {} tests for task id: {}", request.getCount(), task.getId());
        return true;
    }
}
