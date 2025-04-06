package com.eduardo.gerenciador_tarefas_api.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${spring.application.description:}")
  private String applicationDescription;

  @Value("${gerenciador-tarefas.server-url}")
  private String serverUrl;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title(applicationName)
                .version("v1")
                .description(applicationDescription)
                .license(
                    new License()
                        .name("Licença Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
        .servers(
            List.of(
                new Server().url(serverUrl).description("Gerenciador de Tarefas API (Swagger)")));
  }
}
