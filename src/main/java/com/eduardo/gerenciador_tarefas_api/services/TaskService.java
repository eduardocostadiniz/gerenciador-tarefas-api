package com.eduardo.gerenciador_tarefas_api.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eduardo.gerenciador_tarefas_api.exceptions.ProjectNotFoundException;
import com.eduardo.gerenciador_tarefas_api.exceptions.TaskNotFoundException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.Project;
import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.models.TaskRequestDTO;
import com.eduardo.gerenciador_tarefas_api.models.TaskUploadRequestDTO;
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

	public Page<Task> getAll(Pageable page) {
		return taskRepository.findAll(page);
	}

	public Task create(TaskRequestDTO taskDto) {
		Task task = new Task(taskDto);
		User user = userRepository.findById(taskDto.ownerId()).orElseThrow(() -> new UserNotFoundException());
		Project project = projectRepository.findById(taskDto.projectId())
				.orElseThrow(() -> new ProjectNotFoundException());
		LocalDateTime now = LocalDateTime.now();

		task.setUser(user);
		task.setProject(project);
		task.setCreatedAt(now);
		task.setUpdatedAt(now);

		return taskRepository.save(task);
	}

	public void update(Long taskId, TaskRequestDTO taskDto) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException());

		if (taskDto.ownerId() == null) {
			task.setUser(null);
		} else {
			User changedUser = userRepository.findById(taskDto.ownerId())
					.orElseThrow(() -> new UserNotFoundException());
			task.setUser(changedUser);
		}

		if (taskDto.projectId() != null && taskDto.projectId() != task.getProject().getId()) {
			Project changedProject = projectRepository.findById(taskDto.projectId())
					.orElseThrow(() -> new ProjectNotFoundException());
			task.setProject(changedProject);
		}

		task.setName(taskDto.name());
		task.setDescription(taskDto.description());
		task.setStatus(taskDto.status());
		task.setUpdatedAt(LocalDateTime.now());

		taskRepository.save(task);
	}

	public void uploadData(MultipartFile uploadFile) {
		try {
			InputStream inputStream = uploadFile.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader buffer = new BufferedReader(reader);
			List<TaskUploadRequestDTO> data = buffer.lines().skip(1).map(line -> this.parseLine(line)).toList();

			for (TaskUploadRequestDTO t : data) {
				System.out.println(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TaskUploadRequestDTO parseLine(String line) {
		// projectName, userEmail, taskName, taskDescription, taskStatus
		String[] parsed = line.split(";");
		TaskUploadRequestDTO dto = new TaskUploadRequestDTO(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4]);
		return dto;
	}

	public void delete(Long taskId) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException());

		taskRepository.delete(task);
	}

}
