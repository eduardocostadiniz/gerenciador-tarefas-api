package com.eduardo.gerenciador_tarefas_api.configurations;

import org.ff4j.FF4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4JConfig {

  private static final String FF4J_APPLICATION_FEATURE = "gerenciador-tarefas-api-feature";

  @Bean
  public FF4j ff4J() {
    FF4j ff4j = new FF4j();
    ff4j.createFeature(FF4J_APPLICATION_FEATURE);
    ff4j.setEnableAudit(true);
    ff4j.setAutocreate(true);
    return ff4j;
  }
}
