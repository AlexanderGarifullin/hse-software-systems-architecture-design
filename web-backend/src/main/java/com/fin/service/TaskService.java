package com.fin.service;

import com.fin.dto.GenerateTestRequest;
import com.fin.entity.Task;
import com.fin.entity.Test;
import com.fin.repository.TaskRepository;
import com.fin.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TestRepository testRepository;
    private final RestTemplate restTemplate;

    @Value("${generator.service.url:http://localhost:8082}")
    private String generatorServiceUrl;

    @Value("${export.service.url:http://localhost:8083}")
    private String exportServiceUrl;

    public Task createTask(Task task) {
        log.debug("Saving task: {}", task);
        Task savedTask = taskRepository.save(task);
        log.info("Task saved with id: {}", savedTask.getId());
        return savedTask;
    }

    public Task getTask(Long id) {
        log.debug("Retrieving task with id: {}", id);
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            log.warn("Task with id {} not found", id);
        }
        return task;
    }

    public List<Test> getTestsForTask(Long taskId) {
        log.debug("Retrieving tests for task id: {}", taskId);
        List<Test> tests = testRepository.findByTaskId(taskId);
        log.info("Found {} tests for task id: {}", tests.size(), taskId);
        return tests;
    }

    public List<Task> getTasks() {
        log.debug("Retrieving all tasks");
        List<Task> tasks = taskRepository.findAll();
        log.info("Found {} tasks", tasks.size());
        return tasks;
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> generateTestsForTask(Long taskId, int count) {
        log.info("Starting test generation for task id: {} with count: {}", taskId, count);
        GenerateTestRequest request = new GenerateTestRequest();
        request.setTaskId(taskId);
        request.setCount(count);

        log.debug("Sending test generation request to generator-service at {} with payload: {}", generatorServiceUrl, request);
        ResponseEntity<String> response = restTemplate.postForEntity(generatorServiceUrl + "/generate", request, String.class);
        log.info("Received response from generator-service: {}", response);

        if(response.getStatusCode().is2xxSuccessful()){
            log.info("Test generation succeeded for task id: {}", taskId);
            return CompletableFuture.completedFuture(
                    ResponseEntity.accepted().body("Generation request accepted")
            );
        } else {
            log.error("Test generation failed for task id: {} with status: {}", taskId, response.getStatusCode());
            return CompletableFuture.completedFuture(
                    ResponseEntity.status(response.getStatusCode()).body("Generation request failed")
            );
        }
    }

    public byte[] exportTestsForTask(Long taskId) {
        log.info("Exporting tests for task id: {}", taskId);
        ResponseEntity<byte[]> response = restTemplate.getForEntity(exportServiceUrl + "/export?taskId=" + taskId, byte[].class);
        log.info("Received export response with status: {}", response.getStatusCode());
        return response.getBody();
    }
}
