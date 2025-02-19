package com.fin.controller;

import com.fin.entity.Task;
import com.fin.entity.Test;
import com.fin.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        log.info("Received request to create task: {}", task);
        Task created = taskService.createTask(task);
        log.info("Task created with id: {}", created.getId());
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        log.info("Received request to get all tasks");
        List<Task> tasks = taskService.getTasks();
        log.info("Returning {} tasks", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.info("Received request to get task with id: {}", id);
        Task task = taskService.getTask(id);
        if (task == null) {
            log.warn("Task with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Returning task: {}", task);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{id}/tests")
    public ResponseEntity<List<Test>> getTestsForTask(@PathVariable Long id) {
        log.info("Received request to get tests for task id: {}", id);
        List<Test> tests = taskService.getTestsForTask(id);
        log.info("Found {} tests for task id: {}", tests.size(), id);
        return ResponseEntity.ok(tests);
    }

    @PostMapping("/{id}/generate-tests")
    public CompletableFuture<ResponseEntity<String>> generateTests(@PathVariable Long id,
                                                                   @RequestParam(defaultValue = "5") int count) {
        log.info("Received request to generate {} tests for task id: {}", count, id);
        return taskService.generateTestsForTask(id, count)
                .thenApply(response -> {
                    log.info("Returning response for test generation: {}", response);
                    return response;
                });
    }

    @GetMapping("/{id}/export-tests")
    public ResponseEntity<ByteArrayResource> exportTests(@PathVariable Long id) {
        log.info("Received request to export tests for task id: {}", id);
        byte[] zipBytes = taskService.exportTestsForTask(id);
        ByteArrayResource resource = new ByteArrayResource(zipBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tests_" + id + ".zip");
        log.info("Exporting tests for task id: {} as ZIP file", id);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
