package com.fin.controller;

import com.fin.entity.Task;
import com.fin.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("dss")
            .withUsername("postgres")
            .withPassword("123");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @MockBean
    private RestTemplate restTemplateForServices;

    @Test
    @Order(1)
    public void createTask_validTask_taskCreated() {
        Task task = new Task();
        task.setName("Integration Test Task");
        task.setTimeLimit(500);
        task.setMemoryLimit(128);

        ResponseEntity<Task> response = restTemplate.postForEntity("/tasks", task, Task.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Task createdTask = response.getBody();
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isNotNull();
    }

    @Test
    @Order(2)
    public void generateTestsForTask_successfulResponse_returnsAccepted() throws Exception {
        Task task = new Task();
        task.setName("Generation Test Task");
        task.setTimeLimit(500);
        task.setMemoryLimit(128);
        Task createdTask = restTemplate.postForEntity("/tasks", task, Task.class).getBody();
        assertThat(createdTask).isNotNull();

        ResponseEntity<String> mockGeneratorResponse = new ResponseEntity<>("OK", HttpStatus.OK);
        when(restTemplateForServices.postForEntity(any(String.class), any(), any(Class.class)))
                .thenReturn(mockGeneratorResponse);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/tasks/" + createdTask.getId() + "/generate-tests?count=5",
                null,
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).contains("Generation request accepted");
    }

    @Test
    @Order(3)
    public void exportTestsForTask_successfulResponse_returnsZipFile() {
        Task task = new Task();
        task.setName("Export Test Task");
        task.setTimeLimit(500);
        task.setMemoryLimit(128);
        Task createdTask = restTemplate.postForEntity("/tasks", task, Task.class).getBody();
        assertThat(createdTask).isNotNull();


        byte[] dummyZip = "dummyZipContent".getBytes();
        ResponseEntity<byte[]> mockExportResponse = new ResponseEntity<>(dummyZip, HttpStatus.OK);

        when(restTemplateForServices.getForEntity(any(String.class), any(Class.class)))
                .thenReturn(mockExportResponse);

        ResponseEntity<byte[]> exportResponse = restTemplate.getForEntity(
                "/tasks/" + createdTask.getId() + "/export-tests",
                byte[].class);
        assertThat(exportResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        byte[] responseBody = exportResponse.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody).isEqualTo(dummyZip);
    }
}