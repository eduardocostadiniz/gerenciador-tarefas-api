package com.eduardo.gerenciador_tarefas_api.exceptions.handlers;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eduardo.gerenciador_tarefas_api.exceptions.TaskNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.RestExceptionResponse;

@ControllerAdvice
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(TaskExceptionHandler.class);

	@ExceptionHandler(TaskNotFoundException.class)
	private ResponseEntity<RestExceptionResponse> taskNotFoundHandler(TaskNotFoundException exception) {
		LOGGER.error(exception);
		RestExceptionResponse response = new RestExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
