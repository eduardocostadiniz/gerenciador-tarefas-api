package com.eduardo.gerenciador_tarefas_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduardo.gerenciador_tarefas_api.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
