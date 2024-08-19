package com.eduardo.gerenciador_tarefas_api.exceptions;

public class ProjectAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1131022582178006362L;

	public ProjectAlreadyExistsException() {
		super("Esse projeto já existe!");
	}

	public ProjectAlreadyExistsException(String message) {
		super(message);
	}

}
