package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import com.eduardo.gerenciador_tarefas_api.exceptions.FeatureFlagNotEnabledException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class FeatureFlagExceptionHandler {

  @ExceptionHandler(FeatureFlagNotEnabledException.class)
  private ResponseEntity<RestExceptionResponse> featureFlagNotEnabledException(
      UserNotFoundException exception) {
    log.error(exception.getMessage());
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
