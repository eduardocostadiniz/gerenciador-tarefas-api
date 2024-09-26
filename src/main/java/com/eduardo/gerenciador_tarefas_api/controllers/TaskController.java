package com.eduardo.gerenciador_tarefas_api.controllers;

import java.util.List;

import com.eduardo.gerenciador_tarefas_api.models.TaskUploadResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.models.TaskRequestDTO;
import com.eduardo.gerenciador_tarefas_api.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAll(Pageable page) {
        List<Task> tasks = taskService.getAll(page).getContent();
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody TaskRequestDTO taskDto) {
        Task taskCreated = taskService.create(taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TaskUploadResponseDTO> upload(@RequestParam("arquivo") MultipartFile uploadFile,
                                                        @RequestParam("nome") String filename) {
        System.out.printf("Processo: %s", filename);
        return ResponseEntity.ok().body(taskService.uploadData(uploadFile));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> update(@PathVariable("taskId") Long taskId, @RequestBody TaskRequestDTO taskDto) {
        taskService.update(taskId, taskDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Task> delete(@PathVariable("taskId") Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
