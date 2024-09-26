package com.eduardo.gerenciador_tarefas_api.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eduardo.gerenciador_tarefas_api.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eduardo.gerenciador_tarefas_api.exceptions.ProjectNotFoundException;
import com.eduardo.gerenciador_tarefas_api.exceptions.TaskNotFoundException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
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
        User user = userRepository.findById(taskDto.ownerId()).orElseThrow(UserNotFoundException::new);
        Project project = projectRepository.findById(taskDto.projectId())
                .orElseThrow(ProjectNotFoundException::new);
        LocalDateTime now = LocalDateTime.now();

        task.setUser(user);
        task.setProject(project);
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        return taskRepository.save(task);
    }

    public void update(Long taskId, TaskRequestDTO taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        if (taskDto.ownerId() == null) {
            task.setUser(null);
        } else {
            User changedUser = userRepository.findById(taskDto.ownerId())
                    .orElseThrow(UserNotFoundException::new);
            task.setUser(changedUser);
        }

        if (taskDto.projectId() != null && taskDto.projectId().equals(task.getProject().getId())) {
            Project changedProject = projectRepository.findById(taskDto.projectId())
                    .orElseThrow(ProjectNotFoundException::new);
            task.setProject(changedProject);
        }

        task.setName(taskDto.name());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);
    }

    private List<TaskUploadRequestDTO> getData(MultipartFile uploadFile) throws IOException {
        InputStream inputStream = uploadFile.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader buffer = new BufferedReader(reader);
        // Adicionar linha no DTO para tratativa de erros
        return buffer.lines().skip(1).map(this::parseLine).toList();
    }

    private void saveData(TaskUploadRequestDTO taskDTO, User user, Project project) {
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task();
        task.setName(taskDTO.taskName());
        task.setDescription(taskDTO.taskDescription());
        task.setStatus(taskDTO.taskStatus());
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        task.setUser(user);
        task.setProject(project);
        this.taskRepository.save(task);
    }

    public TaskUploadResponseDTO uploadData(MultipartFile uploadFile) {
        try {
            List<TaskUploadRequestDTO> data = this.getData(uploadFile);
            List<String> errors = new ArrayList<>();
            List<String> warnings = new ArrayList<>();
            int totalSuccess = 0;

            // TODO: Receber o ID da tarefa para permitir atualização

            for (TaskUploadRequestDTO taskDTO : data) {
                try {
                    User user = this.userRepository.findByEmail(taskDTO.userEmail());
                    Project project = this.projectRepository.findByName(taskDTO.projectName());

                    if (user == null) {
                        warnings.add(String.format("[%s] - Usuário não encontrado", taskDTO.userEmail()));
                    }

                    if (project == null) {
                        errors.add(String.format("[%s] - Projeto não encontrado", taskDTO.projectName()));
                        continue;
                    }

                    this.saveData(taskDTO, user, project);
                    totalSuccess++;

                } catch (RuntimeException ex) {
                    System.out.println(ex.getMessage());
                    errors.add(String.format("%s -> %s", taskDTO.toString(), ex.getMessage()));
                }
            }
            return new TaskUploadResponseDTO(data.size(), totalSuccess, warnings.size(), errors.size(), warnings, errors);

        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private TaskUploadRequestDTO parseLine(String line) {
        String[] parsed = line.split(";");
        return new TaskUploadRequestDTO(parsed[0], parsed[1], parsed[2], parsed[3], parsed[4]);
    }

    public void delete(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(task);
    }

}
