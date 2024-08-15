package com.eduardo.gerenciador_tarefas_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.gerenciador_tarefas_api.models.Project;
import com.eduardo.gerenciador_tarefas_api.models.ProjectRequestDTO;
import com.eduardo.gerenciador_tarefas_api.services.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping
	public List<Project> getAll() {
		return projectService.getAll();
	}

	@PostMapping
	public ResponseEntity<Project> create(@RequestBody ProjectRequestDTO projectDto) {
		try {
			Project project = projectService.create(projectDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(project);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{projectId}")
	public ResponseEntity<Project> update(@PathVariable("projectId") Long projectId,
			@RequestBody ProjectRequestDTO projectDto) {
		try {
			projectService.update(projectId, projectDto);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<Project> delete(@PathVariable("projectId") Long projectId) {
		try {
			projectService.delete(projectId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
