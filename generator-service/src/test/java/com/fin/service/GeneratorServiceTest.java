package com.fin.service;

import com.fin.dto.GenerateTestRequest;
import com.fin.entity.Task;
import com.fin.entity.Test;
import com.fin.repository.TaskRepository;
import com.fin.repository.TestRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeneratorServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private GeneratorService generatorService;

    @org.junit.jupiter.api.Test
    public void generateTests_taskExists_testsGenerated() {
        Long taskId = 1L;
        int count = 3;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        GenerateTestRequest request = new GenerateTestRequest();
        request.setTaskId(taskId);
        request.setCount(count);

        boolean result = generatorService.generateTests(request);

        assertTrue(result, "Expected generateTests to return true when task exists");
        verify(taskRepository).findById(taskId);
        verify(testRepository, times(count)).save(any(Test.class));
    }

    @org.junit.jupiter.api.Test
    public void generateTests_taskNotFound_returnsFalse() {
        Long taskId = 1L;
        int count = 3;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        GenerateTestRequest request = new GenerateTestRequest();
        request.setTaskId(taskId);
        request.setCount(count);

        boolean result = generatorService.generateTests(request);

        assertFalse(result, "Expected generateTests to return false when task not found");
        verify(taskRepository).findById(taskId);
        verifyNoInteractions(testRepository);
    }

    @org.junit.jupiter.api.Test
    public void generateTestsAsync_taskExists_returnsTrue() throws Exception {
        Long taskId = 1L;
        int count = 3;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        GenerateTestRequest request = new GenerateTestRequest();
        request.setTaskId(taskId);
        request.setCount(count);

        CompletableFuture<Boolean> future = generatorService.generateTestsAsync(request);
        Boolean result = future.get();

        assertTrue(result, "Expected generateTestsAsync to return true when task exists");
        verify(taskRepository).findById(taskId);
        verify(testRepository, times(count)).save(any(Test.class));
    }

    @org.junit.jupiter.api.Test
    public void generateTestsAsync_taskNotFound_returnsFalse() throws Exception {
        Long taskId = 1L;
        int count = 3;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
        GenerateTestRequest request = new GenerateTestRequest();
        request.setTaskId(taskId);
        request.setCount(count);

        CompletableFuture<Boolean> future = generatorService.generateTestsAsync(request);
        Boolean result = future.get();

        assertFalse(result, "Expected generateTestsAsync to return false when task not found");
        verify(taskRepository).findById(taskId);
        verifyNoInteractions(testRepository);
    }
}