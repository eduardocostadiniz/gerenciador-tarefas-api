package com.eduardo.gerenciador_tarefas_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.gerenciador_tarefas_api.exceptions.ProjectAlreadyExistsException;
import com.eduardo.gerenciador_tarefas_api.exceptions.ProjectNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.Project;
import com.eduardo.gerenciador_tarefas_api.models.ProjectRequestDTO;
import com.eduardo.gerenciador_tarefas_api.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public List<Project> getAll() {
		return projectRepository.findAll();
	}

	public Project create(ProjectRequestDTO projectDto) {
		Project project = projectRepository.findByName(projectDto.name());

		if (project != null) {
			throw new ProjectAlreadyExistsException();
		}

		return projectRepository.save(new Project(projectDto));
	}

	public void update(Long projectId, ProjectRequestDTO projectDto) {

		Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException());

		project.setName(projectDto.name());
		project.setDescription(projectDto.description());

		projectRepository.save(project);

	}

	public void delete(Long projectId) {

		Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException());

		// TODO: Adicionar uma validação se existe tarefa associada antes de excluir

		projectRepository.delete(project);

	}

}
