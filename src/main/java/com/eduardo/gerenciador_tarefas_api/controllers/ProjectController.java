package com.eduardo.gerenciador_tarefas_api.controllers;

import com.eduardo.gerenciador_tarefas_api.models.Project;
import com.eduardo.gerenciador_tarefas_api.models.ProjectRequestDTO;
import com.eduardo.gerenciador_tarefas_api.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

@RestController
@RequestMapping("/projects")
public class ProjectController {

  @Autowired private ProjectService projectService;

  @Operation(
      summary = "Listar todos os projetos",
      description = "Retorna uma lista paginada de projetos.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
      })
  @GetMapping
  public ResponseEntity<List<Project>> getAll(@PageableDefault Pageable page) {
    List<Project> projects = projectService.getAll(page).getContent();
    return ResponseEntity.status(HttpStatus.OK).body(projects);
  }

  @Operation(
      summary = "Criar um novo projeto",
      description = "Cria um novo projeto com os dados fornecidos.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(schema = @Schema(implementation = ProjectRequestDTO.class)))
      })
  @PostMapping
  public ResponseEntity<Project> create(@RequestBody ProjectRequestDTO projectDto) {
    Project project = projectService.create(projectDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(project);
  }

  @Operation(
      summary = "Atualizar um projeto",
      description = "Atualiza os dados de um projeto existente.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Projeto atualizado com sucesso"),
        @ApiResponse(
            responseCode = "404",
            description = "Projeto não encontrado",
            content = @Content(schema = @Schema(implementation = ProjectRequestDTO.class)))
      })
  @PutMapping("/{projectId}")
  public ResponseEntity<Project> update(
      @PathVariable("projectId") Long projectId, @RequestBody ProjectRequestDTO projectDto) {
    projectService.update(projectId, projectDto);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Deletar um projeto", description = "Remove um projeto existente pelo ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Projeto removido com sucesso"),
        @ApiResponse(
            responseCode = "404",
            description = "Projeto não encontrado",
            content = @Content(schema = @Schema(description = "ID do projeto a ser removido")))
      })
  @DeleteMapping("/{projectId}")
  public ResponseEntity<Project> delete(@PathVariable("projectId") Long projectId) {
    projectService.delete(projectId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
