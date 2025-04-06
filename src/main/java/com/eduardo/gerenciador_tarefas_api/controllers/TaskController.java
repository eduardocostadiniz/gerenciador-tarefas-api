package com.eduardo.gerenciador_tarefas_api.controllers;

import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.models.TaskRequestDTO;
import com.eduardo.gerenciador_tarefas_api.models.TaskUploadResponseDTO;
import com.eduardo.gerenciador_tarefas_api.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired private TaskService taskService;

  @GetMapping
  public ResponseEntity<List<Task>> getAll(Pageable page) {
    List<Task> tasks = taskService.getAllWithPagination(page).getContent();
    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @GetMapping("/pendings")
  public ResponseEntity<List<Task>> getPendingTasks(@RequestParam String email) {
    return ResponseEntity.ok().body(this.taskService.getPendingTask(email));
  }

  @PostMapping
  public ResponseEntity<Task> create(@RequestBody TaskRequestDTO taskDto) {
    Task taskCreated = taskService.create(taskDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<TaskUploadResponseDTO> upload(
      @RequestParam("arquivo") MultipartFile uploadFile, @RequestParam("nome") String filename) {
    System.out.printf("Processo: %s", filename);
    return ResponseEntity.ok().body(taskService.uploadData(uploadFile));
  }

  @PutMapping("/{taskId}")
  public ResponseEntity<Task> update(
      @PathVariable("taskId") Long taskId, @RequestBody TaskRequestDTO taskDto) {
    taskService.update(taskId, taskDto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Task> delete(@PathVariable("taskId") Long taskId) {
    taskService.delete(taskId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
