package com.cf.cfteam.test.repositories;

import com.cf.cfteam.test.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
