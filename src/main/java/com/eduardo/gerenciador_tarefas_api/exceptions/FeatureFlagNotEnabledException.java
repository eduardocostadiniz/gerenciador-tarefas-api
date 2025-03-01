package com.eduardo.gerenciador_tarefas_api.exceptions;

public class FeatureFlagNotEnabledException extends RuntimeException {

  public FeatureFlagNotEnabledException(String message) {
    super(message);
  }
}
