package com.fin.service;

import com.fin.entity.Task;
import com.fin.repository.TaskRepository;
import com.fin.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TestRepository testRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void createTask_validTask_taskCreated() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);
        verify(taskRepository).save(task);
        assertEquals(task, result);
    }

    @Test
    public void getTask_existingTask_returnsTask() {
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTask(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void getTask_nonExistingTask_returnsNull() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Task result = taskService.getTask(1L);
        assertNull(result);
    }

    @Test
    public void getTestsForTask_validTaskId_returnsTestsList() {
        com.fin.entity.Test t1 = new com.fin.entity.Test();
        t1.setId(1);
        com.fin.entity.Test t2 = new com.fin.entity.Test();
        t2.setId(2);
        List<com.fin.entity.Test> tests = Arrays.asList(t1, t2);
        when(testRepository.findByTaskId(1L)).thenReturn(tests);

        List<com.fin.entity.Test> result = taskService.getTestsForTask(1L);
        verify(testRepository).findByTaskId(1L);
        assertEquals(2, result.size());
    }

    @Test
    public void getTasks_returnsAllTasks() {
        Task t1 = new Task();
        t1.setId(1L);
        Task t2 = new Task();
        t2.setId(2L);
        List<Task> tasks = Arrays.asList(t1, t2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getTasks();
        verify(taskRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void generateTestsForTask_successfulResponse_returnsAcceptedResponse() throws Exception {
        Long taskId = 1L;
        int count = 5;

        ResponseEntity<String> restResponse = new ResponseEntity<>("OK", HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(restResponse);

        CompletableFuture<ResponseEntity<String>> future = taskService.generateTestsForTask(taskId, count);
        ResponseEntity<String> response = future.get();

        verify(restTemplate).postForEntity(contains("/generate"), any(), eq(String.class));
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Generation request accepted", response.getBody());
    }

    @Test
    public void generateTestsForTask_failedResponse_returnsErrorResponse() throws Exception {
        Long taskId = 1L;
        int count = 5;

        ResponseEntity<String> restResponse = new ResponseEntity<>("BAD", HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class))).thenReturn(restResponse);

        CompletableFuture<ResponseEntity<String>> future = taskService.generateTestsForTask(taskId, count);
        ResponseEntity<String> response = future.get();

        verify(restTemplate).postForEntity(contains("/generate"), any(), eq(String.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Generation request failed", response.getBody());
    }

    @Test
    public void exportTestsForTask_successfulResponse_returnsZipBytes() {
        Long taskId = 1L;
        byte[] expectedBytes = "dummyZip".getBytes();
        ResponseEntity<byte[]> restResponse = new ResponseEntity<>(expectedBytes, HttpStatus.OK);
        when(restTemplate.getForEntity(contains("/export?taskId=" + taskId), eq(byte[].class))).thenReturn(restResponse);

        byte[] result = taskService.exportTestsForTask(taskId);
        verify(restTemplate).getForEntity(contains("/export?taskId=" + taskId), eq(byte[].class));
        assertArrayEquals(expectedBytes, result);
    }
}