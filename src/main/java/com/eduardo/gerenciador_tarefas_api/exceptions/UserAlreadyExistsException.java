package com.eduardo.gerenciador_tarefas_api.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6782704504914631026L;

	public UserAlreadyExistsException() {
		super("Esse usuário já existe!");
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
