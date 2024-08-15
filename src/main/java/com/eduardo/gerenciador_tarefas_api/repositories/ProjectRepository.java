package com.eduardo.gerenciador_tarefas_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduardo.gerenciador_tarefas_api.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	public Project findByName(String name);

}
