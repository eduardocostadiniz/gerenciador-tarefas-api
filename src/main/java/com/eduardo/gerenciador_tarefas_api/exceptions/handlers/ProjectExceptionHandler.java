package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eduardo.gerenciador_tarefas_api.exceptions.ProjectAlreadyExistsException;
import com.eduardo.gerenciador_tarefas_api.exceptions.ProjectNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;

@Slf4j
@ControllerAdvice
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProjectAlreadyExistsException.class)
  private ResponseEntity<RestExceptionResponse> projectAlreadyExistsHandler(
      ProjectAlreadyExistsException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.CONFLICT, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(ProjectNotFoundException.class)
  private ResponseEntity<RestExceptionResponse> projectNotFoundHandler(
      ProjectNotFoundException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
