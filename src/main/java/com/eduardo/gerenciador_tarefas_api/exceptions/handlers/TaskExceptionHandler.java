package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import com.eduardo.gerenciador_tarefas_api.exceptions.TaskNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(TaskNotFoundException.class)
  private ResponseEntity<RestExceptionResponse> taskNotFoundHandler(
      TaskNotFoundException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
