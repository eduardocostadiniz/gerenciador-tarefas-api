package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(Exception.class)
  private ResponseEntity<RestExceptionResponse> defaultExceptionHandler(Exception exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(RuntimeException.class)
  private ResponseEntity<RestExceptionResponse> runtimeExceptionHandler(
      RuntimeException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
