package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import com.eduardo.gerenciador_tarefas_api.exceptions.UserAlreadyExistsException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  private ResponseEntity<RestExceptionResponse> userAlreadyExistsHandler(
      UserAlreadyExistsException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.CONFLICT, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @ExceptionHandler(UserNotFoundException.class)
  private ResponseEntity<RestExceptionResponse> userNotFoundHandler(
      UserNotFoundException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
