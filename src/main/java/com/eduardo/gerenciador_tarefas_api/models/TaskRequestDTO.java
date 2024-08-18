package com.eduardo.gerenciador_tarefas_api.models;

public record TaskRequestDTO(String name, String description, String status, String ownerId, Long projectId) {

}
