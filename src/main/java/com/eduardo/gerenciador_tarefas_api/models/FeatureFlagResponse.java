package com.eduardo.gerenciador_tarefas_api.models;

import java.io.Serializable;

public record FeatureFlagResponse(String name, boolean status) implements Serializable {}
