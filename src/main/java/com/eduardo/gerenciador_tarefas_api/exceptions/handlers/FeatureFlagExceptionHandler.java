package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import com.eduardo.gerenciador_tarefas_api.exceptions.FeatureFlagNotEnabledException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FeatureFlagExceptionHandler {

  private static final Logger LOGGER = Logger.getLogger(FeatureFlagExceptionHandler.class);

  @ExceptionHandler(FeatureFlagNotEnabledException.class)
  private ResponseEntity<RestExceptionResponse> featureFlagNotEnabledException(
      UserNotFoundException exception) {
    LOGGER.error(exception);
    RestExceptionResponse response =
        new RestExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
