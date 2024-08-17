package com.eduardo.gerenciador_tarefas_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.gerenciador_tarefas_api.models.Task;
import com.eduardo.gerenciador_tarefas_api.models.TaskRequestDTO;
import com.eduardo.gerenciador_tarefas_api.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping
	public List<Task> getAll() {
		return taskService.getAll();
	}

	@PostMapping
	public ResponseEntity<Task> create(@RequestBody TaskRequestDTO taskDto) {
		try {
			Task taskCreated = taskService.create(taskDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
