package com.eduardo.gerenciador_tarefas_api.models;

public record TaskUploadRequestDTO(String projectName, String userEmail, String taskName, String taskDescription,
		String taskStatus) {

}
