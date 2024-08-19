package com.eduardo.gerenciador_tarefas_api.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6657490602454952072L;

	public UserNotFoundException() {
		super("Usuário não encontrado!");
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}
