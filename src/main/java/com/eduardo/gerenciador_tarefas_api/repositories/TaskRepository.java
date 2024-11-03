package com.eduardo.gerenciador_tarefas_api.repositories;

import com.eduardo.gerenciador_tarefas_api.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(String id);
}
