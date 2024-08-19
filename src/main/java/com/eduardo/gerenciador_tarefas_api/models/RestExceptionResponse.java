package com.eduardo.gerenciador_tarefas_api.models;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestExceptionResponse {

	private HttpStatus statusCode;
	private String message;

}
