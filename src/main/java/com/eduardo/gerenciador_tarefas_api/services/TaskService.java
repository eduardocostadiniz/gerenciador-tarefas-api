package com.eduardo.gerenciador_tarefas_api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.gerenciador_tarefas_api.models.Project;
import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.models.TaskRequestDTO;
import com.eduardo.gerenciador_tarefas_api.models.User;
import com.eduardo.gerenciador_tarefas_api.repositories.ProjectRepository;
import com.eduardo.gerenciador_tarefas_api.repositories.TaskRepository;
import com.eduardo.gerenciador_tarefas_api.repositories.UserRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public List<Task> getAll() {
		return taskRepository.findAll();
	}

	public Task create(TaskRequestDTO taskDto) throws Exception {
		Task task = new Task(taskDto);
		User user = userRepository.findById(taskDto.ownerId()).orElseThrow(() -> new Exception("Usuário inexistente"));
		Project project = projectRepository.findById(taskDto.projectId())
				.orElseThrow(() -> new Exception("Projeto inexistente"));
		LocalDateTime now = LocalDateTime.now();

		task.setUser(user);
		task.setProject(project);
		task.setCreatedAt(now);
		task.setUpdatedAt(now);

		return taskRepository.save(task);
	}

	public void update(Long taskId, TaskRequestDTO taskDto) throws Exception {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new Exception("Tarefa inexistente"));

		if (taskDto.ownerId() == null) {
			task.setUser(null);
		} else {
			User changedUser = userRepository.findById(taskDto.ownerId())
					.orElseThrow(() -> new Exception("Usuário alterado inexistente"));
			task.setUser(changedUser);
		}

		if (taskDto.projectId() != null && taskDto.projectId() != task.getProject().getId()) {
			Project changedProject = projectRepository.findById(taskDto.projectId())
					.orElseThrow(() -> new Exception("Projeto alterado inexistente"));
			task.setProject(changedProject);
		}

		task.setName(taskDto.name());
		task.setDescription(taskDto.description());
		task.setStatus(taskDto.status());
		task.setUpdatedAt(LocalDateTime.now());

		taskRepository.save(task);
	}

	public void delete(Long taskId) throws Exception {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new Exception("Tarefa inexistente"));

		taskRepository.delete(task);
	}

}
