package com.eduardo.gerenciador_tarefas_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Project create(ProjectRequestDTO projectDto) throws Exception {
		Project project = projectRepository.findByName(projectDto.name());

		if (project != null) {
			throw new Exception("Já existe um projeto com o mesmo nome");
		}

		return projectRepository.save(new Project(projectDto));
	}

	public void update(Long projectId, ProjectRequestDTO projectDto) throws Exception {

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new Exception("Projeto Inexistente!"));

		project.setName(projectDto.name());
		project.setDescription(projectDto.description());

		projectRepository.save(project);

	}

	public void delete(Long projectId) throws Exception {

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new Exception("Projeto Inexistente!"));

		// TODO: Adicionar uma validação se existe tarefa associada antes de excluir

		projectRepository.delete(project);

	}

}
