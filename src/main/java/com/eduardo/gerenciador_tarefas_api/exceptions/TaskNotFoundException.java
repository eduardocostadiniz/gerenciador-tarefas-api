package com.eduardo.gerenciador_tarefas_api.exceptions;

public class TaskNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5304090070142769180L;

	public TaskNotFoundException() {
		super("Tarefa não encontrada!");
	}

	public TaskNotFoundException(String message) {
		super(message);
	}

}
