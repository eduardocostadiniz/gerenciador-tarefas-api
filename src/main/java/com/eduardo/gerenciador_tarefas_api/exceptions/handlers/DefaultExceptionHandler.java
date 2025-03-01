package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

  private static final Logger LOGGER = Logger.getLogger(DefaultExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  private ResponseEntity<RestExceptionResponse> defaultExceptionHandler(Exception exception) {
    LOGGER.error(exception);
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(RuntimeException.class)
  private ResponseEntity<RestExceptionResponse> runtimeExceptionHandler(
      RuntimeException exception) {
    LOGGER.error(exception);
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
