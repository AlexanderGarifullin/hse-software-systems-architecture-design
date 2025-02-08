package hse_paps_code.paps.services;

import hse_paps_code.paps.entities.Task;
import hse_paps_code.paps.mappers.payloads.TaskMapper;
import hse_paps_code.paps.repositories.TaskRepository;
import hse_paps_code.paps.repositories.UserRepository;
import hse_paps_code.paps.transfers.TaskPayload;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskMapper taskMapper;

    public List<Task> getTasksByUser(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return taskRepository.findByUserId(userId);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public Task createTask(Long userId, TaskPayload payload) {
        Task task = taskMapper.payloadToEntity(payload);

        task.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, TaskPayload updatedTask) {
        Task task = getTaskById(taskId);
        task.setName(updatedTask.name());
        task.setStatement(updatedTask.statement());
        task.setTimeLimit(updatedTask.timeLimit());
        task.setMemoryLimit(updatedTask.memoryLimit());
        task.setSolve(updatedTask.solve());
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        Task task = getTaskById(taskId);
        taskRepository.delete(task);
    }
}
