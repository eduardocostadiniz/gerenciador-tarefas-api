package com.eduardo.gerenciador_tarefas_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GerenciadorTarefasApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(GerenciadorTarefasApiApplication.class, args);
  }
}
