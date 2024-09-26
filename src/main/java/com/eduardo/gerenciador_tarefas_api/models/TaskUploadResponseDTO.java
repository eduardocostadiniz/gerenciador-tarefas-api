package com.eduardo.gerenciador_tarefas_api.models;

import java.util.List;

public record TaskUploadResponseDTO(int totalUploaded, int totalSuccess, int totalWarnings, int totalErrors,
                                    List<String> warnings, List<String> errors) {
}
