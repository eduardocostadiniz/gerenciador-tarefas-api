package com.eduardo.gerenciador_tarefas_api.exceptions;

public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2060123041937898164L;

	public ProjectNotFoundException() {
		super("Projeto não encontrado!");
	}

	public ProjectNotFoundException(String message) {
		super(message);
	}

}
